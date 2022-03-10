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

    //校级项目初始化
    public void initSchool() {
        Map<String, Object> attrMap = new HashMap<String, Object>();
        setAttr("cooperate", new DbRecord(DbConfig.S_COOPERATE).whereEqualTo("LevelId",WebConfig.SUBJECT_TYPE_SCHOOL).query());
        setAttr("type", new DbRecord(DbConfig.S_RESEARCH_TYPE).query());
        setAttr("school", new DbRecord(DbConfig.S_SCHOOL).query());
        //向前端发送全部attribute
        Enumeration<String> names = getAttrNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            attrMap.put(name, getAttr(name));
        }
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    //纵向项目初始化
    public void initSponsored() {
        Map<String, Object> attrMap = new HashMap<String, Object>();
        setAttr("cooperate", new DbRecord(DbConfig.S_COOPERATE).whereEqualTo("LevelId",WebConfig.SUBJECT_TYPE_SPONSORED).query());
        setAttr("type", new DbRecord(DbConfig.S_RESEARCH_TYPE).query());
        setAttr("area", new DbRecord(DbConfig.S_RESEARCH_AREA).query());
        setAttr("subject", new DbRecord(DbConfig.S_SUBJECT_FIRST).query());
        //向前端发送全部attribute
        Enumeration<String> names = getAttrNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            attrMap.put(name, getAttr(name));
        }
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    //横向项目初始化
    public void initHorizon() {
        Map<String, Object> attrMap = new HashMap<String, Object>();
        setAttr("contract", new DbRecord(DbConfig.S_CONTRACT).query());
        setAttr("entrust", new DbRecord(DbConfig.S_ENTRUST).query());
        setAttr("country", new DbRecord(DbConfig.HOR_COUNTRY).query());
        setAttr("type", new DbRecord(DbConfig.HOR_TYPE).query());
        setAttr("province", new DbRecord(DbConfig.HOR_PROVINCE).query());
        setAttr("technical", new DbRecord(DbConfig.S_TECHNICAL).query());
        setAttr("property", new DbRecord(DbConfig.S_PROPERTY).query());
        setAttr("society", new DbRecord(DbConfig.S_SOCIETY).query());
        setAttr("thirdcontract", new DbRecord(DbConfig.S_CONTRACT_THIRD).query());
        //向前端发送全部attribute
        Enumeration<String> names = getAttrNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            attrMap.put(name, getAttr(name));
        }
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    //获取一级学科二级目录列表
    public void listCategory(){
        Integer BelongId = getParaToInt("BelongId");
        List<Record> result = new DbRecord(DbConfig.S_CATEGORY).whereEqualTo("BelongId", BelongId).query();
        renderJson(result);
    }

    //获取一级学科三级目录列表
    public void listSecCategory(){
        Integer CategoryId = getParaToInt("CategoryId");
        List<Record> result = new DbRecord(DbConfig.S_CATEGORY_SECOND).whereEqualTo("CategoryId", CategoryId).query();
        renderJson(result);
    }

    //获取纵向项目一级学科二级目录列表
    public void listSecType(){
        Integer TypeId = getParaToInt("TypeId");
        List<Record> result = new DbRecord(DbConfig.S_SUBJECT_SECOND).whereEqualTo("FirstId", TypeId).query();
        renderJson(result);
    }

    //获取纵向项目一级学科三级目录列表
    public void listThirdType(){
        Integer SecTypeId = getParaToInt("SecTypeId");
        List<Record> result = new DbRecord(DbConfig.S_SUBJECT_THIRD).whereEqualTo("SecondId", SecTypeId).query();
        renderJson(result);
    }


    //横向项目合同类别二级目录
    public void listContract(){
        Integer ContractId = getParaToInt("ContractId");
        List<Record> result = new DbRecord(DbConfig.S_CONTRACT_SECOND).whereEqualTo("ContractId", ContractId).query();
        renderJson(result);
    }

    //横向项目合同类别三级目录
    public void listThirdContract(){
        List<Record> result = new DbRecord(DbConfig.S_CONTRACT_THIRD).query();
        renderJson(result);
    }

    //获取国民经济和社会服务二级目录
    public void listSecond() {
        Integer BelongId = getParaToInt("BelongId");
        List<Record> result = new DbRecord(DbConfig.S_SECOND).whereEqualTo("BelongId", BelongId).query();
        renderJson(result);
    }

    //获取国民经济三级目录列表
    public void listEconomic() {
        Integer SecondId = getParaToInt("SecondId");
        List<Record> result = new DbRecord(DbConfig.S_ECONOMIC).whereEqualTo("SecondId", SecondId).query();
        renderJson(result);
    }

    //获取城市列表
    public void listCity() {
        Integer ProvinceId = getParaToInt("ProvinceId");
        List<Record> result = new DbRecord(DbConfig.HOR_CITY).whereEqualTo("ProvinceId", ProvinceId).query();
        renderJson(result);
    }

    //获取区县列表
    public void listCounty() {
        Integer CityId = getParaToInt("CityId");
        List<Record> result = new DbRecord(DbConfig.HOR_COUNTY).whereEqualTo("CityId", CityId).query();
        renderJson(result);
    }

    //获取项目资源列表
    public void listSource() {
        Integer LevelId = getParaToInt("LevelId");
        List<Record> result = new DbRecord(DbConfig.S_SOURCE).whereEqualTo("LevelId", LevelId).query();
        renderJson(result);
    }

    //获取一级学科列表
    public void listFirstSubject() {
        Integer BelongId = getParaToInt("BelongId");
        List<Record> result = new DbRecord(DbConfig.S_TYPE).whereEqualTo("BelongId", BelongId).query();
        renderJson(result);
    }

    //获取教师角色
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


    //上传校级项目
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
                    // 保存项目基础信息至数据库
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
                        // 将项目id添加到校级分表中 并保存校级项目信息
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

    //上传横向项目
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
                    // 保存项目基础信息至数据库
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
                        // 将项目id添加到分表中 并保存横向项目信息
                        SubjectHorizon horizon = new SubjectHorizon();
                        horizon.setSubjectId(SubjectId);
                        horizon.setBelongId(getParaToInt("BelongId"));

                        horizon.setEntrustPlaceId(getParaToInt("EntrustPlaceId"));
                        horizon.setContractId(getParaToInt("ContractId"));
                        horizon.setSecContractId(getParaToInt("SecContractId"));
                        if(getParaToInt("SecContractId") == 2) {
                            horizon.setThirdContractId(getParaToInt("ThirdContractId"));
                        }else{
                            horizon.setThirdContractId(6);
                        }
                        horizon.setSignTime(new SimpleDateFormat("yyyy-MM-dd").parse(getPara("SignTime")));// parse方法可解析一个日期时间字符串
                        horizon.setContractFund(getPara("DevelopFund"));
                        horizon.setIsDutyFree(getParaToInt("isDutyFree"));
                        horizon.setDutyFreeId(getPara("DutyFreeId"));
                        horizon.setIsPromote(getParaToInt("isPromote"));
                        horizon.setContractDuty(getPara("ContractDuty"));
                        horizon.setIntroduction(getPara("Introduction"));

                        horizon.setResearchFund(getPara("ResearchFund"));
                        horizon.setServiceFund(getPara("ServiceFund"));
                        horizon.setOtherFund(getPara("OtherFund"));
                        horizon.setSoftwareFund(getPara("SoftwareFund"));
                        horizon.setHardwareFund(getPara("HardwareFund"));
                        horizon.setOutboundFund(getPara("OutboundFund"));

                        horizon.setBuyerName(getPara("BuyerName"));
                        horizon.setBuyerContinent(getPara("BuyerContinent"));
                        horizon.setBuyerType(getParaToInt("BuyerType"));
                        horizon.setBuyerPostCode(getPara("BuyerPostCode"));
                        horizon.setBuyerContact(getPara("BuyerContact"));
                        horizon.setBuyerTel(getPara("BuyerTel"));
                        horizon.setBuyerLegalPerson(getPara("BuyerLegalPerson"));
                        horizon.setBuyerLegalEntityCode(getParaToInt("BuyerLegalEntityCode"));
                        horizon.setBuyerEmail(getPara("BuyerEmail"));
                        horizon.setBuyerRegisteredAddress(getPara("BuyerRegisteredAddress"));
                        horizon.setBuyerMailingAddress(getPara("BuyerMailingAddress"));

                        horizon.setBuyerCountry(getParaToInt("BuyerCountry"));
                        if(getParaToInt("BuyerCountry")==13) {
                            horizon.setBuyerProvince(getParaToInt("BuyerProvince"));
                            horizon.setBuyerCity(getParaToInt("BuyerCity"));
                            horizon.setBuyerCounty(getParaToInt("BuyerCounty"));
                        }else{
                            horizon.setBuyerProvince(35);
                            horizon.setBuyerCity(348);
                            horizon.setBuyerCounty(3160);
                        }

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
                        sponsored.setCategoryId(getParaToInt("CategoryId"));
                        sponsored.setSecCategoryId(getParaToInt("SecCategoryId"));

                        //保存经费信息
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
                        sponsored.setTravelFund(getPara("TravelFund"));
                        sponsored.setDeviceFund(getPara("DeviceFund"));
                        sponsored.setStaySchoolFund(getPara("StaySchoolFund"));



                        sponsored.setResearchId(getParaToInt("ResearchId"));
                        sponsored.setAreaId(getParaToInt("AreaId"));
                        sponsored.setIsSecrecy(getParaToInt("isSecrecy"));
                        sponsored.setIsVoucher(getParaToInt("isVoucher"));
                        sponsored.setIsSubmitFill(getParaToInt("isSubmitFill"));
                        sponsored.setIsPromote(getParaToInt("isPromote"));

                        sponsored.setIsDutyFree(getParaToInt("isDutyFree"));
                        sponsored.setDutyFreeId(getParaToInt("DutyFreeId"));
                        sponsored.setBelongId(getParaToInt("BelongId"));
                        sponsored.setTypeId(getParaToInt("TypeId"));
                        sponsored.setSecTypeId(getParaToInt("SecTypeId"));
                        sponsored.setThirdTypeId(getParaToInt("ThirdTypeId"));
                        sponsored.setEntrustPlace(getPara("EntrustPlace"));
                        sponsored.setTopicId(getParaToInt("TopicId"));

                        sponsored.setMainProjectName(getPara("MainProjectName"));
                        sponsored.setMainSubjectName(getPara("MainSubjectName"));
                        sponsored.setApplicationCode(getPara("ApplicationCode"));

                        sponsored.setIntroduction(getPara("Introduction"));
                        sponsored.setRemarks(getPara("Remarks"));
                        sponsored.setCooperateName(getPara("CooperateName"));
                        sponsored.setCooperateId(getParaToInt("CooperateId"));
                        sponsored.setCooperatePrincipal(getPara("CooperatePrincipal"));

                        sponsored.setContractFund(getPara("ContractFund"));
                        sponsored.setBankName(getPara("BankName"));
                        sponsored.setBankAccount(getPara("BankAccount"));
                        sponsored.setEconomicId(getParaToInt("EconomicId"));
                        sponsored.setSocietyId(getParaToInt("SocietyId"));
                        sponsored.setSourceId(getParaToInt("SourceId"));

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

    //输出项目类型列表
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
