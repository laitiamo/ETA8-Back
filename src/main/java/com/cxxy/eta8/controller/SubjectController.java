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
import com.jfinal.plugin.activerecord.Db;
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
        setAttr("teacher", new DbRecord(DbConfig.V_TEACHER_INFO).query());
        setAttr("ecofirst", new DbRecord(DbConfig.S_FIRST).whereEqualTo("RankId", WebConfig.RANK_ECONOMIC).query());
        setAttr("socfirst", new DbRecord(DbConfig.S_FIRST).whereEqualTo("RankId", WebConfig.RANK_SOCIETY).query());
        setAttr("topic", new DbRecord(DbConfig.S_TOPIC).query());
        setAttr("belong", new DbRecord(DbConfig.S_BELONG).query());
        setAttr("paper", new DbRecord(DbConfig.T_TYPE).query());
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
        setAttr("category", new DbRecord(DbConfig.S_CATEGORY).whereEqualTo("TypeId", WebConfig.SUBJECT_TYPE_SCHOOL).query());
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
        setAttr("buyercountry", new DbRecord(DbConfig.B_COUNTRY).query());
        setAttr("buyertype", new DbRecord(DbConfig.B_TYPE).query());
        setAttr("buyerprovince", new DbRecord(DbConfig.B_PROVINCE).query());
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

    public void listCity() {
        Integer ProvinceId = getParaToInt("ProvinceId");
        List<Record> result = new DbRecord(DbConfig.B_CITY).whereEqualTo("ProvinceId", ProvinceId).query();
        renderJson(result);
    }

    public void listCounty() {
        Integer CityId = getParaToInt("CityId");
        List<Record> result = new DbRecord(DbConfig.B_COUNTY).whereEqualTo("CityId", CityId).query();
        renderJson(result);
    }

    public void listSource() {
        Integer LevelId = getParaToInt("LevelId");
        List<Record> result = new DbRecord(DbConfig.S_SOURCE).whereEqualTo("LevelId", LevelId).query();
        renderJson(result);
    }

    public void listFirstSubject() {
        Integer BelongId = getParaToInt("BelongId");
        List<Record> result = new DbRecord(DbConfig.S_TYPE).whereEqualTo("BelongId", BelongId).query();
        renderJson(result);
    }

    public void getRole() {
        Integer TeacherId = getParaToInt("TeacherId");
        List<Record> teacher = new DbRecord(DbConfig.V_TEACHER_INFO).whereEqualTo("userId", TeacherId).query();
        Map<String, Object> attrMap = new HashMap<String, Object>();
        attrMap.put("roleName", teacher.get(0).getStr("roleName"));
        attrMap.put("collegeName", teacher.get(0).getStr("collegeName"));
        attrMap.put("sectorName", teacher.get(0).getStr("sectorName"));
        attrMap.put("username", teacher.get(0).getStr("username"));
        setAttrs(attrMap);
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
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
            String uploadDateTime = new SimpleDateFormat("yyyy_MM_dd").format(now);

            // 目标文件路径列表
            ArrayList<String> webPaths = new ArrayList<String>();
            // 源路径
            String originPath = "";
            // 每个文件名 多图的话 为源路径加下标
            String webPathString;
            originPath = File.separator + "upload" +
                    File.separator + "teacher" + File.separator + "subject" +
                    File.separator + getPara("SubjectType") + File.separator + getPara("SubjectNum") + "_" + getPara("SubjectName") + "_" + getPara("SubjectPlace") + File.separator + user.getStr("name") + "_立项申请_" + uploadDateTime;
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
                    userSubject.setSubjectFund(getPara("SubjectFund"));
                    userSubject.setSubjectPaper(getPara("SubjectPaper"));
                    userSubject.setSubjectTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("SubjectTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setStartTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("StartTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setFinishTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("FinishTime")));// parse方法可解析一个日期时间字符串

                    // 数据库最终保存的路径，如果多图则在结尾加 "*"符号 跟上图片数量
                    String finalPath = "";
                    if (allFiles.size() == 1) {
                        finalPath = originPath + ".pdf";
                    } else {
                        finalPath = originPath + ".pdf*" + allFiles.size();
                    }
                    userSubject.setFilePath(finalPath);
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
                        school.setDocumentFund(getPara("DocumentFund"));
                        school.setLaborFund(getPara("LaborFund"));
                        school.setMaterialFund(getPara("MaterialFund"));
                        school.setHardwareFund(getPara("HardwareFund"));
                        school.setOutboundFund(getPara("OutboundFund"));
                        school.setPatentFund(getPara("PatentFund"));

                        school.setCategoryId(getParaToInt("CategoryId"));
                        school.setEconomicId(getParaToInt("EconomicId"));
                        school.setSocietyId(getParaToInt("SocietyId"));
                        school.setSourceId(getParaToInt("SourceId"));
                        school.setBelongId(getParaToInt("BelongId"));
                        school.setTypeId(getParaToInt("TypeId"));
                        school.setRemarks(getPara("Remarks"));
                        school.setResearchId(getParaToInt("ResearchId"));
                        school.setCooperateId(getParaToInt("CooperateId"));
                        if (SubjectService.me.addSubject(ids, SubjectId)) {
                            if (school.save()) {
                                for (int j = 0; j < allFiles.size(); j++) {
                                    // 保存目标文件
                                    if (!targetFiles.get(j).getParentFile().exists()) {
                                        targetFiles.get(j).getParentFile().mkdirs(); // 递归创建父类文件夹
                                    }
                                    allFiles.get(j).getFile().renameTo(new File(targetFiles.get(j) + ".pdf"));
                                }
                                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "上传成功"));
                            }
                        } else {
                            int id = new DbRecord(DbConfig.T_USER_SUBJECT)
                                    .whereEqualTo("subjectNum", getPara("SubjectNum"))
                                    .whereEqualTo("subjectName", getPara("SubjectName"))
                                    .whereEqualTo("SubjectPlace", getPara("SubjectPlace"))
                                    .queryFirst()
                                    .getInt("id");
                            if (Db.deleteById("T_USER_SUBJECT", id)) {
                                renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                            }
                        }
                    } else {
                        int id = new DbRecord(DbConfig.T_USER_SUBJECT)
                                    .whereEqualTo("subjectNum", getPara("SubjectNum"))
                                    .whereEqualTo("subjectName", getPara("SubjectName"))
                                    .whereEqualTo("SubjectPlace", getPara("SubjectPlace"))
                                    .queryFirst()
                                    .getInt("id");
                        if (Db.deleteById("T_USER_SUBJECT", id)) {
                            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                        }
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
            String uploadDateTime = new SimpleDateFormat("yyyy_MM_dd").format(now);

            // 目标文件路径列表
            ArrayList<String> webPaths = new ArrayList<String>();
            // 源路径
            String originPath = "";
            // 每个文件名 多图的话 为源路径加下标
            String webPathString;
            originPath = File.separator + "upload" +
                    File.separator + "teacher" + File.separator + "subject" +
                    File.separator + getPara("SubjectType") + File.separator + getPara("SubjectNum") + "_" + getPara("SubjectName") + "_" + getPara("SubjectPlace") + File.separator + user.getStr("name") + "_立项申请_" + uploadDateTime;
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
                    userSubject.setSubjectFund(getPara("SubjectFund"));
                    userSubject.setSubjectPaper(getPara("SubjectPaper"));
                    userSubject.setSubjectTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("SubjectTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setStartTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("StartTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setFinishTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("FinishTime")));// parse方法可解析一个日期时间字符串

                    // 数据库最终保存的路径，如果多图则在结尾加 "*"符号 跟上图片数量
                    String finalPath = "";
                    if (allFiles.size() == 1) {
                        finalPath = originPath + ".pdf";
                    } else {
                        finalPath = originPath + ".pdf*" + allFiles.size();
                    }
                    userSubject.setFilePath(finalPath);
                    userSubject.setRankId(getParaToInt("RankId"));
                    userSubject.setLevelId(getParaToInt("LevelId"));
                    userSubject.setReviewId(WebConfig.REVIEW_UNREAD);

                    if (userSubject.save()) {
                        SubjectId = new DbRecord(DbConfig.T_USER_SUBJECT)
                                .whereEqualTo("SubjectNum", getPara("SubjectNum"))
                                .whereEqualTo("SubjectName", getPara("SubjectName"))
                                .whereEqualTo("SubjectPlace", getPara("SubjectPlace"))
                                .queryFirst()
                                .getInt("id");
                        SubjectHorizon horizon = new SubjectHorizon();
                        horizon.setSubjectId(SubjectId);
                        horizon.setSoftwareFund(getPara("SoftwareFund"));
                        horizon.setHardwareFund(getPara("HardwareFund"));
                        horizon.setOutboundFund(getPara("OutboundFund"));
                        horizon.setIntroduction(getPara("Introduction"));
                        horizon.setRelyCenterSubject(getPara("RelyCenterSubject"));
                        horizon.setContractName(getPara("ContractName"));
                        horizon.setCooperatePrincipal(getPara("CooperatePrincipal"));
                        horizon.setContractNum(getPara("ContractNum"));
                        horizon.setFundNum(getPara("FundNum"));
                        horizon.setEntrustPlaceId(getParaToInt("EntrustPlaceId"));
                        horizon.setCooperateId(getParaToInt("CooperateId"));
                        horizon.setContractId(getParaToInt("ContractId"));
                        horizon.setContractFund(getPara("DevelopFund"));
                        horizon.setBankName(getPara("BankName"));
                        horizon.setBankAccount(getPara("BankAccount"));
                        horizon.setIsDutyFree(getParaToInt("isDutyFree"));
                        horizon.setDutyFreeId(getPara("DutyFreeId"));
                        horizon.setIsPromote(getParaToInt("isPromote"));
                        horizon.setContractDuty(getPara("ContractDuty"));

                        horizon.setResearchFund(getPara("ResearchFund"));
                        horizon.setServiceFund(getPara("ServiceFund"));
                        horizon.setOtherFund(getPara("OtherFund"));

                        horizon.setBuyerName(getPara("BuyerName"));
                        horizon.setBuyerContinent(getPara("BuyerContinent"));
                        horizon.setBuyerType(getParaToInt("BuyerType"));
                        horizon.setBuyerProvince(getParaToInt("BuyerProvince"));
                        horizon.setBuyerCity(getParaToInt("BuyerCity"));
                        horizon.setBuyerCounty(getParaToInt("BuyerCounty"));
                        horizon.setBuyerCountry(getParaToInt("BuyerCountry"));
                        horizon.setBuyerPostCode(getPara("BuyerPostCode"));
                        horizon.setBuyerContact(getPara("BuyerContact"));
                        horizon.setBuyerTel(getPara("BuyerTel"));
                        horizon.setBuyerLegalPerson(getPara("BuyerLegalPerson"));
                        horizon.setBuyerLegalEntityCode(getParaToInt("BuyerLegalEntityCode"));
                        horizon.setBuyerEmail(getPara("BuyerEmail"));
                        horizon.setBuyerRegisteredAddress(getPara("BuyerRegisteredAddress"));
                        horizon.setBuyerMailingAddress(getPara("BuyerMailingAddress"));

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
                                    allFiles.get(j).getFile().renameTo(new File(targetFiles.get(j) + ".pdf"));
                                }
                                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "上传成功"));
                            }
                        } else {
                            int id = new DbRecord(DbConfig.T_USER_SUBJECT)
                                    .whereEqualTo("subjectNum", getPara("SubjectNum"))
                                    .whereEqualTo("subjectName", getPara("SubjectName"))
                                    .whereEqualTo("SubjectPlace", getPara("SubjectPlace"))
                                    .queryFirst()
                                    .getInt("id");
                            if (Db.deleteById("T_USER_SUBJECT", id)) {
                                renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                            }
                        }
                    } else {
                        int id = new DbRecord(DbConfig.T_USER_SUBJECT)
                                    .whereEqualTo("subjectNum", getPara("SubjectNum"))
                                    .whereEqualTo("subjectName", getPara("SubjectName"))
                                    .whereEqualTo("SubjectPlace", getPara("SubjectPlace"))
                                    .queryFirst()
                                    .getInt("id");
                        if (Db.deleteById("T_USER_SUBJECT", id)) {
                            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                        }
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
            String uploadDateTime = new SimpleDateFormat("yyyy_MM_dd").format(now);

            // 目标文件路径列表
            ArrayList<String> webPaths = new ArrayList<String>();
            // 源路径
            String originPath = "";
            // 每个文件名 多图的话 为源路径加下标
            String webPathString;
            originPath = File.separator + "upload" +
                    File.separator + "teacher" + File.separator + "subject" +
                    File.separator + getPara("SubjectType") + File.separator + getPara("SubjectNum") + "_" + getPara("SubjectName") + "_" + getPara("SubjectPlace") + File.separator + user.getStr("name") + "_立项申请_" + uploadDateTime;
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
                    userSubject.setSubjectFund(getPara("SubjectFund"));
                    userSubject.setSubjectPaper(getPara("SubjectPaper"));
                    userSubject.setSubjectTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("SubjectTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setStartTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("StartTime")));// parse方法可解析一个日期时间字符串
                    userSubject.setFinishTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("FinishTime")));// parse方法可解析一个日期时间字符串

                    // 数据库最终保存的路径，如果多图则在结尾加 "*"符号 跟上图片数量
                    String finalPath = "";
                    if (allFiles.size() == 1) {
                        finalPath = originPath + ".pdf";
                    } else {
                        finalPath = originPath + ".pdf*" + allFiles.size();
                    }
                    userSubject.setFilePath(finalPath);
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
                        sponsored.setDocumentFund(getPara("DocumentFund"));
                        sponsored.setDataFund(getPara("DataFund"));
                        sponsored.setOutboundFund(getPara("OutboundFund"));
                        sponsored.setMeetingFund(getPara("MeetingFund"));
                        sponsored.setInternationalFund(getPara("InternationalFund"));
                        sponsored.setHardwareFund(getPara("HardwareFund"));
                        sponsored.setConsultFund(getPara("ConsultFund"));
                        sponsored.setLaborFund(getPara("LaborFund"));
                        sponsored.setMaterialFund(getPara("MaterialFund"));
                        sponsored.setPatentFund(getPara("PatentFund"));
                        sponsored.setResearchId(getParaToInt("ResearchId"));
                        sponsored.setIsSecrecy(getParaToInt("isSecrecy"));
                        sponsored.setIsVoucher(getParaToInt("isVoucher"));
                        sponsored.setIsSubmitFill(getParaToInt("isSubmitFill"));
                        sponsored.setIsPromote(getParaToInt("isPromote"));
                        sponsored.setIsDutyFree(getParaToInt("isDutyFree"));
                        sponsored.setDutyFreeId(getParaToInt("DutyFreeId"));
                        sponsored.setBelongId(getParaToInt("BelongId"));
                        sponsored.setTypeId(getParaToInt("TypeId"));
                        sponsored.setEntrustPlace(getPara("EntrustPlace"));
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
                                    allFiles.get(j).getFile().renameTo(new File(targetFiles.get(j) + ".pdf"));
                                }
                                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "上传成功"));
                            }
                        } else {
                            int id = new DbRecord(DbConfig.T_USER_SUBJECT)
                                    .whereEqualTo("subjectNum", getPara("SubjectNum"))
                                    .whereEqualTo("subjectName", getPara("SubjectName"))
                                    .whereEqualTo("SubjectPlace", getPara("SubjectPlace"))
                                    .queryFirst()
                                    .getInt("id");
                            if (Db.deleteById("T_USER_SUBJECT", id)) {
                                renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                            }
                        }
                    } else {
                        int id = new DbRecord(DbConfig.T_USER_SUBJECT)
                                    .whereEqualTo("subjectNum", getPara("SubjectNum"))
                                    .whereEqualTo("subjectName", getPara("SubjectName"))
                                    .whereEqualTo("SubjectPlace", getPara("SubjectPlace"))
                                    .queryFirst()
                                    .getInt("id");
                        if (Db.deleteById("T_USER_SUBJECT", id)) {
                            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败"));
                        }
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
