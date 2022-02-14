package com.cxxy.eta8.controller;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.model.UserPaper;
import com.cxxy.eta8.service.SubjectService;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.validator.RecordValidator;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class RecordController extends Controller {
    public void index() {

        Map<String, Object> attrMap = new HashMap<String, Object>();
        setAttr("rank", new DbRecord(DbConfig.T_PAPER_RANK).query());
        setAttr("teacher", new DbRecord(DbConfig.T_TEACHER).query());
        //向前端发送全部attribute
        Enumeration<String> names = getAttrNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            attrMap.put(name, getAttr(name));
            //System.out.println(name);
        }
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void listType() {
        Integer typeId = getParaToInt("typeId");
        List<Record> result = new DbRecord(DbConfig.T_PAPER_RANK).whereEqualTo("typeId", typeId).query();
        renderJson(result);
    }

    @Before(RecordValidator.class)
    public void uploadpaper() {
        Record user = UserService.me.getCurrentUserInfo();

        // 获取上传原始文件(暂定最多5个)
        List<UploadFile> allFiles = getFiles("file");

        if (allFiles.size() > 5) {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "图片数量超出上限，上传失败"));
        } else {
            // 重命名元素: 上传时间
            Date now = new Date(System.currentTimeMillis());
            String uploadDateTime = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(now);

            // 目标文件路径列表
            ArrayList<String> webPaths = new ArrayList<String>();
            // 源路径 =》
            String originPath = "";
            // 每个文件名 多图的话 为源路径加下标 =》
            String webPathString;
            originPath = File.separator + "upload" +
                    File.separator + "teacher" + File.separator + "record" +
                    File.separator + getPara("paperType") + "_" + getPara("paperName") + "_" + uploadDateTime;
            //File.separator+username+"_"+getPara("awardName")+"_"+uploadDateTime;
            // 使用File.separator能确保在Linux和Windows下都使用了对应的文件分隔符
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

            //从前端读取除主创之外的用户id
            Integer[] ids = getParaValuesToInt("userids[]");
            try {
                // 保存文件信息至数据库
                UserPaper userpaper = new UserPaper();
                userpaper.setPaperName(getPara("paperName"));
                userpaper.setPaperPlace(getPara("paperPlace"));
                userpaper.setPaperTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("paperTime")));// parse方法可解析一个日期时间字符串


                // 数据库最终保存的路径，如果多图则在结尾加 "*"符号 跟上图片数量
                String finalPath = "";
                if (allFiles.size() == 1) {
                    finalPath = originPath + ".jpeg";
                } else {
                    finalPath = originPath + ".jpeg*" + allFiles.size();
                }
                userpaper.setImagePath(finalPath);
                userpaper.setRankId(getParaToInt("rankId"));
                userpaper.setTypeId(getParaToInt("typeId"));
                userpaper.setReviewId(WebConfig.REVIEW_UNREAD);

                if (userpaper.save()) {
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
                    Integer PaperId = new DbRecord(DbConfig.T_USER_PAPER)
                            .whereEqualTo("PaperName", getPara("paperName"))
                            .whereEqualTo("PaperPlace", getPara("paperPlace"))
                            .whereEqualTo("TypeId", getParaToInt("typeId"))
                            .queryFirst()
                            .getInt("id");

                    if (SubjectService.me.addPaper(ids, PaperId)) {
                        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "上传成功"));
                    } else {
                        renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                    }
                } else {
                    renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
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

    public void listPaper() {
        int page = getParaToInt("page");
        int limit = getParaToInt("limit");
        String order = getPara("order");
        String field = getPara("field");
        String defaultField = "awardName";
        Page<Record> p = null;
        p = new DbRecord(DbConfig.T_AWARD)
                .whereEqualTo("awardTypeId", WebConfig.PAPER_TYPE_TEACHER)
                .orderBySelect(field, order, defaultField)
                .page(page, limit);

        renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
    }

}
