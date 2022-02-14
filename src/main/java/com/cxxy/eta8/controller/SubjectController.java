package com.cxxy.eta8.controller;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.model.SubjectHorizon;
import com.cxxy.eta8.model.SubjectSchool;
import com.cxxy.eta8.model.SubjectSponsored;
import com.cxxy.eta8.model.UserSubject;
import com.cxxy.eta8.service.SubjectService;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.validator.SubjectValidator;
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

public class SubjectController extends Controller {
    public void index() {
        Map<String, Object> attrMap = new HashMap<String, Object>();
        setAttr("rank", new DbRecord(DbConfig.T_SUBJECT_RANK).query());
        setAttr("teacher", new DbRecord(DbConfig.T_TEACHER).query());
        setAttr("ecofirst", new DbRecord(DbConfig.S_FIRST).whereEqualTo("RankId", WebConfig.RANK_ECONOMIC).query());
        setAttr("socfirst", new DbRecord(DbConfig.S_FIRST).whereEqualTo("RankId", WebConfig.RANK_SOCIETY).query());
        setAttr("topic", new DbRecord(DbConfig.S_TOPIC).query());
        setAttr("belong", new DbRecord(DbConfig.S_BELONG).query());
        //向前端发送全部attribute
        Enumeration<String> names = getAttrNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            attrMap.put(name, getAttr(name));
        }
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void initSchool() {
        Map<String, Object> attrMap = new HashMap<String, Object>();
        setAttr("cooperate", new DbRecord(DbConfig.S_COOPERATE).query());
        setAttr("research", new DbRecord(DbConfig.S_RESEARCH).query());
        //向前端发送全部attribute
        Enumeration<String> names = getAttrNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            attrMap.put(name, getAttr(name));
        }
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void initSponsored() {
        Map<String, Object> attrMap = new HashMap<String, Object>();
        setAttr("cooperate", new DbRecord(DbConfig.S_COOPERATE).query());
        setAttr("contract", new DbRecord(DbConfig.S_CONTRACT).query());
        setAttr("entrust", new DbRecord(DbConfig.S_ENTRUST).query());
        setAttr("research", new DbRecord(DbConfig.S_RESEARCH).query());
        setAttr("property", new DbRecord(DbConfig.S_PROPERTY).query());
        setAttr("technical", new DbRecord(DbConfig.S_TECHNICAL).query());
        //向前端发送全部attribute
        Enumeration<String> names = getAttrNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            attrMap.put(name, getAttr(name));
        }
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void initHorizon() {
        Map<String, Object> attrMap = new HashMap<String, Object>();
        setAttr("contract", new DbRecord(DbConfig.S_CONTRACT).query());
        setAttr("cooperate", new DbRecord(DbConfig.S_COOPERATE).query());
        setAttr("entrust", new DbRecord(DbConfig.S_ENTRUST).query());
        setAttr("technical", new DbRecord(DbConfig.S_TECHNICAL).query());
        setAttr("property", new DbRecord(DbConfig.S_PROPERTY).query());
        //向前端发送全部attribute
        Enumeration<String> names = getAttrNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            attrMap.put(name, getAttr(name));
        }
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void listSecond() {
        Integer FirstId = getParaToInt("FirstId");
        List<Record> result = new DbRecord(DbConfig.S_SECOND).whereEqualTo("FirstId", FirstId).query();
        renderJson(result);
    }

    public void listEconomic() {
        Integer SecondId = getParaToInt("SecondId");
        List<Record> result = new DbRecord(DbConfig.S_ECONOMIC).whereEqualTo("SecondId", SecondId).query();
        renderJson(result);
    }

    public void listSource() {
        Integer levelId = getParaToInt("levelId");
        List<Record> result = new DbRecord(DbConfig.S_SOURCE).whereEqualTo("levelId", levelId).query();
        renderJson(result);
    }

    public void listFirstSubject() {
        Integer BelongId = getParaToInt("BelongId");
        List<Record> result = new DbRecord(DbConfig.S_TYPE).whereEqualTo("BelongId", BelongId).query();
        renderJson(result);
    }

    @Before(SubjectValidator.class)
    public void uploadSchool() {
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
            // 源路径
            String originPath = "";
            // 每个文件名 多图的话 为源路径加下标
            String webPathString;
            originPath = File.separator + "upload" +
                    File.separator + "teacher" + File.separator + "subject" +
                    File.separator + getPara("SubjectType") + File.separator + getPara("SubjectNum") + "_" + getPara("SubjectName") + "_" + getPara("SubjectPlace") + "_"  + uploadDateTime;
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

            //准备在分表中添加项目id
            Integer SubjectId = null;
            //从前端读取除主创之外的用户id
            Integer[] ids = getParaValuesToInt("userids[]");

            try {
                //先走一遍遍历 查看该项目是否已经上传，减少冗余数据的增加
                List<Record> subjectNums = new DbRecord(DbConfig.T_USER_SUBJECT)
                        .whereEqualTo("SubjectNum", getPara("SubjectNum")).query();
                if (subjectNums.isEmpty()) {
                    // 保存文件信息至数据库
                    UserSubject userSubject = new UserSubject();
                    userSubject.setSubjectNum(getPara("SubjectNum"));
                    userSubject.setSubjectName(getPara("SubjectName"));
                    userSubject.setSubjectPlace(getPara("SubjectPlace"));
                    userSubject.setSubjectFund(getParaToInt("SubjectFund"));
                    userSubject.setSubjectTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("SubjectTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setStartTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("StartTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setFinishTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("FinishTime")));// parse方法可解析一个日期时间字符串

                    // 数据库最终保存的路径，如果多图则在结尾加 "*"符号 跟上图片数量
                    String finalPath = "";
                    if (allFiles.size() == 1) {
                        finalPath = originPath + ".jpeg";
                    } else {
                        finalPath = originPath + ".jpeg*" + allFiles.size();
                    }
                    userSubject.setImagePath(finalPath);
                    userSubject.setRankId(getParaToInt("RankId"));
                    userSubject.setLevelId(getParaToInt("LevelId"));
                    userSubject.setReviewId(WebConfig.REVIEW_UNREAD);

                    if (userSubject.save()) {
                        SubjectId = new DbRecord(DbConfig.T_USER_SUBJECT)
                                .whereEqualTo("subjectNum", getPara("SubjectNum"))
                                .whereEqualTo("subjectName", getPara("SubjectName"))
                                .whereEqualTo("SubjectPlace", getPara("SubjectPlace"))
                                .queryFirst()
                                .getInt("id");
                        SubjectSchool school = new SubjectSchool();
                        school.setSubjectId(SubjectId);
                        school.setEconomicId(getParaToInt("EconomicId"));
                        school.setSocietyId(getParaToInt("SocietyId"));
                        school.setSourceId(getParaToInt("SourceId"));
                        school.setBelongId(getParaToInt("BelongId"));
                        school.setTypeId(getParaToInt("TypeId"));
                        school.setResearchId(getParaToInt("ResearchId"));
                        school.setCooperateId(getParaToInt("CooperateId"));
                        if (SubjectService.me.addSubject(ids, SubjectId)) {
                            if (school.save()) {
                                for (int j = 0; j < allFiles.size(); j++) {
                                    // 保存目标文件
                                    if (!targetFiles.get(j).getParentFile().exists()) {
                                        targetFiles.get(j).getParentFile().mkdirs(); // 递归创建父类文件夹
                                    }
                                    allFiles.get(j).getFile().renameTo(targetFiles.get(j));
                                }
                                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "上传成功"));
                            }
                        } else {
                            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                        }
                    } else {
                        renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                    }
                } else {
                    renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "项目编号重复，请重新输入"));
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

    @Before(SubjectValidator.class)
    public void uploadHorizon() {
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
            // 源路径
            String originPath = "";
            // 每个文件名 多图的话 为源路径加下标
            String webPathString;
            originPath = File.separator + "upload" +
                    File.separator + "teacher" + File.separator + "subject" +
                    File.separator + getPara("SubjectType") + File.separator + getPara("SubjectNum") + "_" + getPara("SubjectName") + "_" + getPara("SubjectPlace") + "_"  + uploadDateTime;
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

            //准备在分表中添加项目id
            Integer SubjectId = null;
            //从前端读取除主创之外的用户id
            Integer[] ids = getParaValuesToInt("userids[]");

            try {
                //先走一遍遍历 查看该项目是否已经上传，减少冗余数据的增加
                List<Record> subjectNums = new DbRecord(DbConfig.T_USER_SUBJECT)
                        .whereEqualTo("SubjectNum", getPara("subjectNum")).query();
                if (subjectNums.isEmpty()) {
                    // 保存文件信息至数据库
                    UserSubject userSubject = new UserSubject();
                    userSubject.setSubjectNum(getPara("SubjectNum"));
                    userSubject.setSubjectName(getPara("SubjectName"));
                    userSubject.setSubjectPlace(getPara("SubjectPlace"));
                    userSubject.setSubjectFund(getParaToInt("SubjectFund"));
                    userSubject.setSubjectTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("SubjectTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setStartTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("StartTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setFinishTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("FinishTime")));// parse方法可解析一个日期时间字符串

                    // 数据库最终保存的路径，如果多图则在结尾加 "*"符号 跟上图片数量
                    String finalPath = "";
                    if (allFiles.size() == 1) {
                        finalPath = originPath + ".jpeg";
                    } else {
                        finalPath = originPath + ".jpeg*" + allFiles.size();
                    }
                    userSubject.setImagePath(finalPath);
                    userSubject.setRankId(getParaToInt("RankId"));
                    userSubject.setLevelId(getParaToInt("LevelId"));
                    userSubject.setReviewId(WebConfig.REVIEW_UNREAD);

                    if (userSubject.save()) {
                        SubjectId = new DbRecord(DbConfig.T_USER_SUBJECT)
                                .whereEqualTo("subjectNum", getPara("SubjectNum"))
                                .whereEqualTo("subjectName", getPara("SubjectName"))
                                .whereEqualTo("SubjectPlace", getPara("SubjectPlace"))
                                .queryFirst()
                                .getInt("id");
                        SubjectHorizon horizon = new SubjectHorizon();
                        horizon.setSubjectId(SubjectId);
                        horizon.setIntroduction(getPara("Introduction"));
                        horizon.setRelyCenterSubject(getPara("RelyCenterSubject"));
                        horizon.setContractName(getPara("ContractName"));
                        horizon.setCooperatePrincipal(getPara("CooperatePrincipal"));
                        horizon.setContractNum(getPara("ContractNum"));
                        horizon.setFundNum(getPara("FundNum"));
                        horizon.setEntrustPlaceId(getParaToInt("EntrustPlaceId"));
                        horizon.setCooperateId(getParaToInt("CooperateId"));
                        horizon.setContractId(getParaToInt("ContractId"));
                        horizon.setContractFund(getParaToInt("ContractFund"));
                        horizon.setBankName(getPara("BankName"));
                        horizon.setBankAccount(getPara("BankAccount"));
                        horizon.setIsDutyFree(getParaToInt("isDutyFree"));
                        horizon.setDutyFreeId(getPara("DutyFreeId"));
                        horizon.setIsPromote(getParaToInt("isPromote"));
                        horizon.setContractDuty(getPara("ContractDuty"));
                        horizon.setPayId(getParaToInt("PayId"));
                        horizon.setEconomicId(getParaToInt("EconomicId"));
                        horizon.setSocietyId(getParaToInt("SocietyId"));
                        horizon.setSourceId(getParaToInt("SourceId"));
                        horizon.setTechnicalId(getParaToInt("TechnicalId"));
                        horizon.setPropertyId(getParaToInt("PropertyId"));

                        if (SubjectService.me.addSubject(ids, SubjectId)) {
                            if (horizon.save()) {
                                for (int j = 0; j < allFiles.size(); j++) {
                                    // 保存目标文件
                                    if (!targetFiles.get(j).getParentFile().exists()) {
                                        targetFiles.get(j).getParentFile().mkdirs(); // 递归创建父类文件夹
                                    }
                                    allFiles.get(j).getFile().renameTo(targetFiles.get(j));
                                }
                                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "上传成功"));
                            }
                        } else {
                            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                        }
                    } else {
                        renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                    }
                } else {
                    renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "项目编号重复，请重新输入"));
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

    @Before(SubjectValidator.class)
    public void uploadSponsored() {
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
            // 源路径
            String originPath = "";
            // 每个文件名 多图的话 为源路径加下标
            String webPathString;
            originPath = File.separator + "upload" +
                    File.separator + "teacher" + File.separator + "subject" +
                    File.separator + getPara("SubjectType") + File.separator + getPara("SubjectNum") + "_" + getPara("SubjectName") + "_" + getPara("SubjectPlace") + "_"  + uploadDateTime;
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

            //准备在分表中添加项目id
            Integer SubjectId = null;
            //从前端读取除主创之外的用户id
            Integer[] ids = getParaValuesToInt("userids[]");

            try {
                //先走一遍遍历 查看该项目是否已经上传，减少冗余数据的增加
                List<Record> subjectNums = new DbRecord(DbConfig.T_USER_SUBJECT)
                        .whereEqualTo("SubjectNum", getPara("subjectNum")).query();
                if (subjectNums.isEmpty()) {
                    // 保存文件信息至数据库
                    UserSubject userSubject = new UserSubject();
                    userSubject.setSubjectNum(getPara("SubjectNum"));
                    userSubject.setSubjectName(getPara("SubjectName"));
                    userSubject.setSubjectPlace(getPara("SubjectPlace"));
                    userSubject.setSubjectFund(getParaToInt("SubjectFund"));
                    userSubject.setSubjectTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("SubjectTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setStartTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("StartTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setFinishTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("FinishTime")));// parse方法可解析一个日期时间字符串

                    // 数据库最终保存的路径，如果多图则在结尾加 "*"符号 跟上图片数量
                    String finalPath = "";
                    if (allFiles.size() == 1) {
                        finalPath = originPath + ".jpeg";
                    } else {
                        finalPath = originPath + ".jpeg*" + allFiles.size();
                    }
                    userSubject.setImagePath(finalPath);
                    userSubject.setRankId(getParaToInt("RankId"));
                    userSubject.setLevelId(getParaToInt("LevelId"));
                    userSubject.setReviewId(WebConfig.REVIEW_UNREAD);

                    if (userSubject.save()) {
                        SubjectId = new DbRecord(DbConfig.T_USER_SUBJECT)
                                .whereEqualTo("subjectNum", getPara("SubjectNum"))
                                .whereEqualTo("subjectName", getPara("SubjectName"))
                                .whereEqualTo("SubjectPlace", getPara("SubjectPlace"))
                                .queryFirst()
                                .getInt("id");
                        SubjectSponsored sponsored = new SubjectSponsored();
                        sponsored.setSubjectId(SubjectId);
                        sponsored.setResearchId(getParaToInt("ResearchId"));
                        sponsored.setIsSecrecy(getParaToInt("isSecrecy"));
                        sponsored.setIsVoucher(getParaToInt("isVoucher"));
                        sponsored.setIsSubmitFill(getParaToInt("isSubmitFill"));
                        sponsored.setIsPromote(getParaToInt("isPromote"));
                        sponsored.setIsDutyFree(getParaToInt("isDutyFree"));
                        sponsored.setDutyFreeId(getParaToInt("DutyFreeId"));
                        sponsored.setBelongId(getParaToInt("BelongId"));
                        sponsored.setTypeId(getParaToInt("TypeId"));
                        sponsored.setEntrustPlaceId(getParaToInt("EntrustPlaceId"));
                        sponsored.setTopicId(getParaToInt("TopicId"));
                        sponsored.setMainProjectName(getPara("MainProjectName"));
                        sponsored.setApplicationCode(getPara("ApplicationCode"));
                        sponsored.setIntroduction(getPara("Introduction"));
                        sponsored.setRemarks(getPara("Remarks"));
                        sponsored.setCooperateId(getParaToInt("CooperateId"));
                        sponsored.setContractId(getParaToInt("ContractId"));
                        sponsored.setFundNum(getPara("FundNum"));
                        sponsored.setContractNum(getPara("ContractNum"));
                        sponsored.setContractName(getPara("ContractName"));
                        sponsored.setContractId(getParaToInt("ContractId"));
                        sponsored.setContractFund(getPara("ContractFund"));
                        sponsored.setCooperatePrincipal(getPara("CooperatePrincipal"));
                        sponsored.setBankName(getPara("BankName"));
                        sponsored.setBankAccount(getPara("BankAccount"));
                        sponsored.setContractDuty(getPara("ContractDuty"));
                        sponsored.setPayId(getParaToInt("PayId"));
                        sponsored.setEconomicId(getParaToInt("EconomicId"));
                        sponsored.setSocietyId(getParaToInt("SocietyId"));
                        sponsored.setSourceId(getParaToInt("SourceId"));
                        sponsored.setTechnicalId(getParaToInt("TechnicalId"));
                        sponsored.setPropertyId(getParaToInt("PropertyId"));

                        if (SubjectService.me.addSubject(ids, SubjectId)) {
                            if (sponsored.save()) {
                                for (int j = 0; j < allFiles.size(); j++) {
                                    // 保存目标文件
                                    if (!targetFiles.get(j).getParentFile().exists()) {
                                        targetFiles.get(j).getParentFile().mkdirs(); // 递归创建父类文件夹
                                    }
                                    allFiles.get(j).getFile().renameTo(targetFiles.get(j));
                                }
                                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "上传成功"));
                            }
                        } else {
                            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                        }
                    } else {
                        renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                    }
                } else {
                    renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "项目编号重复，请重新输入"));
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


    public void listSubject() {
        int page = getParaToInt("page");
        int limit = getParaToInt("limit");
        String order = getPara("order");
        String field = getPara("field");
        String defaultField = "rankName";
        Page<Record> p = null;
        p = new DbRecord(DbConfig.T_SUBJECT_RANK)
                .orderBySelect(field, order, defaultField)
                .page(page, limit);
        renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
    }

}
