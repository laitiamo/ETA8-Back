package com.cxxy.eta8.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.model.UserAward;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.validator.UploadValidator;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import net.coobird.thumbnailator.Thumbnails;

public class UploadController extends Controller {

    public void index() {

        List<Record> rank = new DbRecord(DbConfig.T_RANK).query();
        setAttr("rank", new DbRecord(DbConfig.T_RANK).query());
        //向前端发送全部attribute
        Map<String, Object> attrMap = new HashMap<String, Object>();
        Enumeration<String> names = getAttrNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            attrMap.put(name, getAttr(name));
            //System.out.println(name);
        }
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    @Before(UploadValidator.class)
    public void upload() {
        Record user = UserService.me.getCurrentUserInfo();

        // 获取上传原始文件(暂定最多5个)
        List<UploadFile> allFiles = getFiles("file");

        if (allFiles.size() > 5) {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "图片数量超出上限，上传失败"));
        } else {
            // 重命名元素: 用户名
            String username = (String) SecurityUtils.getSubject().getPrincipal();
            // 重命名元素: 上传时间
            Date now = new Date(System.currentTimeMillis());
            String uploadDateTime = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(now);

            // 重命名元素: 序号

            // 目标文件路径列表
            ArrayList<String> webPaths = new ArrayList<String>();
            // 源路径 =》  17软件工程3班_10517325操守正_高等数学_2020_04_01_19_43_17
            String originPath = "";
            // 每个文件名 多图的话 为源路径加下标 =》  17软件工程3班_10517325操守正_高等数学_2020_04_01_19_43_17_0、17软件工程3班_10517325操守正_高等数学_2020_04_01_19_43_17_1....
            String webPathString;
            username += user.getStr("name");
            if (SecurityUtils.getSubject().hasRole(WebConfig.ROLE_STUDENT) || SecurityUtils.getSubject().hasRole(UserService.ROLE_ASSISTANT)) {
                // 重命名元素：班级ID
                String className = user.getStr("className");
                originPath = File.separator + "upload" +
                        File.separator + "student" +
                        File.separator + className +
                        File.separator + className + "_" + username + "_" + getPara("awardName") + "_" + uploadDateTime;
            } else {
                originPath = File.separator + "upload" +
                        File.separator + "teacher" +
                        File.separator + username + "_" + getPara("awardName") + "_" + uploadDateTime;
                //File.separator+username+"_"+getPara("awardName")+"_"+uploadDateTime;           17软件工程3班_10517325操守正_高等数学_2020_04_01_19_43_17
            } // 使用File.separator能确保在Linux和Windows下都使用了对应的文件分隔符
            if (allFiles.size() == 1) {
                webPaths.add(originPath);
            } else {
                for (int i = 0; i < allFiles.size(); i++) {
                    webPathString = originPath + "_" + i;
                    webPaths.add(webPathString);
                }
            }
            //2020-10-20之前的单图片模式
            // File targetFile = new File(PathKit.getWebRootPath() + webPath);

            ArrayList<File> targetFiles = new ArrayList<File>();
            for (int i = 0; i < allFiles.size(); i++) {
                File oneFile = new File(PathKit.getWebRootPath() + webPaths.get(i));
                targetFiles.add(oneFile);
            }


            try {
                // 保存文件信息至数据库
                UserAward userAward = new UserAward();
                userAward.setAwardId(getParaToInt("awardId"));
                userAward.setAwardPlace(getPara("awardPlace"));
                userAward.setAwardTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("awardTime")));// parse方法可解析一个日期时间字符串


                // 数据库最终保存的路径，如果多图则在结尾加 "*"符号 跟上图片数量
                String finalPath = "";
                if (allFiles.size() == 1) {
                    finalPath = originPath + ".jpeg";
                } else {
                    finalPath = originPath + ".jpeg*" + allFiles.size();
                }
                userAward.setImagePath(finalPath);
                userAward.setRankId(getParaToInt("rankId"));
                userAward.setUserId(UserService.me.getCurrentUser().getInt("id"));

                //已更改，现在所有奖项都必须审核
                userAward.setReviewId(WebConfig.REVIEW_UNREAD);

                if (userAward.save()) {
                    for (int i = 0; i < allFiles.size(); i++) {
                        // 保存目标文件
                        if (!targetFiles.get(i).getParentFile().exists()) {
                            targetFiles.get(i).getParentFile().mkdirs(); // 递归创建父类文件夹
                        }
                        Thumbnails.of(allFiles.get(i).getFile())
                                .size(1280, 720)        //转换图片大小
                                .keepAspectRatio(true)  //不按横纵比压缩图片
                                .outputQuality(0.5F)    //压缩图片质量
                                .outputFormat("jpeg")   //转化图片形式
                                .toFile(targetFiles.get(i));
                    }

                }
                if (SecurityUtils.getSubject().hasRole(WebConfig.ROLE_STUDENT) || SecurityUtils.getSubject().hasRole(UserService.ROLE_ASSISTANT)) {
                    renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "上传成功，等待审核..."));
                } else {
                    renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "上传成功"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
            } finally {
                for (int i = 0; i < allFiles.size(); i++) {
                    allFiles.get(i).getFile().delete();
                }
            }
        }
    }

    public void listAward() {
        int page = getParaToInt("page");
        int limit = getParaToInt("limit");
        String key = getPara("key");
        String order = getPara("order");
        String field = getPara("field");
        String defaultField = "awardName";
        Page<Record> p = null;
        if (SecurityUtils.getSubject().hasRole(WebConfig.ROLE_STUDENT) || SecurityUtils.getSubject().hasRole(UserService.ROLE_ASSISTANT)) {
            p = new DbRecord(DbConfig.T_AWARD)
                    .whereEqualTo("awardTypeId", WebConfig.AWARD_TYPE_STUDENT)
                    .whereContains("awardName", key)
                    .orderBySelect(field, order, defaultField)
                    .page(page, limit);
        } else {
            p = new DbRecord(DbConfig.T_AWARD)
                    .whereEqualTo("awardTypeId", WebConfig.AWARD_TYPE_TEACHER)
                    .whereContains("awardName", key)
                    .orderBySelect(field, order, defaultField)
                    .page(page, limit);
        }
        renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
    }

    public void searchAward() {
        int page = getParaToInt("page");
        int limit = getParaToInt("limit");
        String key = getPara("key");
        String order = getPara("order");
        String field = getPara("field");
        String defaultField = "awardName";
        Page<Record> p = null;
        if (SecurityUtils.getSubject().hasRole(WebConfig.ROLE_STUDENT) || SecurityUtils.getSubject().hasRole(UserService.ROLE_ASSISTANT)) {
            p = new DbRecord(DbConfig.T_AWARD)
                    .whereEqualTo("awardTypeId", WebConfig.AWARD_TYPE_STUDENT)
                    .whereContains("awardName", key)
                    .orderBySelect(field, order, defaultField)
                    .page(page, limit);
        } else {
            p = new DbRecord(DbConfig.T_AWARD)
                    .whereEqualTo("awardTypeId", WebConfig.AWARD_TYPE_TEACHER)
                    .whereContains("awardName", key)
                    .orderBySelect(field, order, defaultField)
                    .page(page, limit);
        }
        renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
    }

}
