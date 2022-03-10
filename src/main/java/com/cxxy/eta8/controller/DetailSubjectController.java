package com.cxxy.eta8.controller;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.interceptor.DetailSubjectInterceptor;
import com.cxxy.eta8.service.SubjectService;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.text.SimpleDateFormat;
import java.util.*;

public class DetailSubjectController extends Controller {

    @Before(DetailSubjectInterceptor.class)
    public void index() {
        Integer id = getParaToInt("id");
        Record r = new DbRecord(DbConfig.V_TEACHER_SUBJECT).whereEqualTo("id", id).whereEqualTo("CandidateId",WebConfig.CANDIDATE_MAJOR).queryFirst();
        List<Record> n = new DbRecord(DbConfig.V_TEACHER_SUBJECT).whereEqualTo("id", id).whereEqualTo("CandidateId",WebConfig.CANDIDATE_PART).query();
        String name = "";
        if(n.size() != 0) {
            for (int i = 0; i < n.size(); i++) {
                name += n.get(i).getStr("name") + " ";
            }
        }else{
            name = "无";
        }
        Map<String, Object> attrMap = new HashMap<String, Object>();

        String filePath = r.getStr("filePath");
        ArrayList<String> files = new ArrayList<String>();
        if (filePath.indexOf("*") != -1) {

            String[] parts = filePath.split("\\*");
            //如果存在"*"则分割，取出图片数量
            Integer picNum = Integer.parseInt(parts[1]);
            String[] pathParts = parts[0].split("\\.");
            String pathPart = pathParts[0];
            for (int i = 0; i < picNum; i++) {
                //图片url补全 2021-2-25
                files.add(getAttr("basePath").toString() + pathPart + "_" + i + ".pdf");
            }
        } else {
            files.add(getAttr("basePath").toString() + filePath);
        }

        attrMap.put("filePaths", files);
        attrMap.put("id", id);
        attrMap.put("username", r.getStr("username"));
        attrMap.put("name", r.getStr("name"));
        attrMap.put("gender", r.getStr("genderName"));
        attrMap.put("college",r.getStr("collegeName"));
        attrMap.put("subject", r.getStr("subjectName"));
        attrMap.put("num", r.getStr("subjectNum"));
        attrMap.put("place", r.getStr("subjectPlace"));
        attrMap.put("fund", r.getStr("subjectFund"));
        attrMap.put("writer", name);
        attrMap.put("time", new SimpleDateFormat("yyyy/MM/dd").format(r.getDate("subjectTime")));
        attrMap.put("rank", r.getStr("rankName"));
        attrMap.put("level", r.getStr("levelName"));
        attrMap.put("SubjectPaper", r.getStr("subjectPaper"));
        attrMap.put("createAt", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(r.getDate("createAt")));
        attrMap.put("reviewAt", r.getDate("reviewAt") == null ?
                "无" : new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(r.getDate("reviewAt")));
        attrMap.put("finishAt", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(r.getDate("FinishTime")));
        attrMap.put("review", r.getStr("reviewName"));
        attrMap.put("reviewId", r.getInt("reviewId"));
        attrMap.put("levelId", r.getInt("levelId"));

        //校级项目
        if (r.getInt("levelId") == 3) {
            Record s = new DbRecord(DbConfig.V_SUBJECT_LINK_SCHOOL).whereEqualTo("id", id).queryFirst();
            attrMap.put("DocumentFund",s.getStr("DocumentFund") == null ? "无" : s.getStr("DocumentFund"));
            attrMap.put("LaborFund",s.getStr("LaborFund") == null ? "无" : s.getStr("LaborFund"));
            attrMap.put("MaterialFund",s.getStr("MaterialFund") == null ? "无" : s.getStr("MaterialFund"));
            attrMap.put("HardwareFund",s.getStr("HardwareFund") == null ? "无" : s.getStr("HardwareFund"));
            attrMap.put("OutboundFund",s.getStr("OutboundFund") == null ? "无" : s.getStr("OutboundFund"));
            attrMap.put("PatentFund",s.getStr("PatentFund") == null ? "无" : s.getStr("PatentFund"));
            attrMap.put("Source", s.getStr("SourceName"));
            attrMap.put("Type", s.getStr("TypeName"));
            attrMap.put("Belong", s.getStr("BelongName"));
            attrMap.put("Cooperate", s.getStr("CooperateName"));
            attrMap.put("Remarks", s.getStr("Remarks") == null ? "无" : s.getStr("Remarks"));
        } else if (r.getInt("levelId") == 2) {
            Record s = new DbRecord(DbConfig.V_SUBJECT_LINK_HORIZON).whereEqualTo("id", id).queryFirst();
            attrMap.put("SignTime",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(s.getDate("SignTime")));
            attrMap.put("BelongName", s.getStr("BelongName"));
            attrMap.put("SoftwareFund",s.getStr("SoftwareFund") == null ? "无" : s.getStr("SoftwareFund"));
            attrMap.put("HardwareFund",s.getStr("HardwareFund") == null ? "无" : s.getStr("HardwareFund"));
            attrMap.put("OutboundFund",s.getStr("OutboundFund") == null ? "无" : s.getStr("OutboundFund"));
            attrMap.put("Introduction", s.getStr("Introduction") == null ? "无" : s.getStr("Introduction"));
            attrMap.put("EntrustName", s.getStr("EntrustName"));
            attrMap.put("CooperateName", s.getStr("CooperateName"));
            attrMap.put("ContractType", s.getStr("ContractType"));
            attrMap.put("SecContractType", s.getStr("SecContractType"));
            if(s.getStr("SecContractType").equals("专利技术转让")){
                List<Record> Third = new DbRecord(DbConfig.S_CONTRACT_THIRD).whereEqualTo("id", s.getInt("ThirdContractId")).query();
                attrMap.put("ThirdContractType", Third.get(0).getStr("typeName"));
            }
            attrMap.put("BankName", s.getStr("BankName"));
            if(s.getInt("PayId") == 1){
                attrMap.put("Pay", "一次支付");
            }else if(s.getInt("PayId") == 2){
                attrMap.put("Pay", "分期支付");
            }else if (s.getInt("PayId") == 3){
                attrMap.put("Pay", "提成支付");
            }
            attrMap.put("BankAccount", s.getStr("BankAccount"));
            attrMap.put("ContractDuty", s.getStr("ContractDuty"));
            attrMap.put("IsPromote", s.getInt("isPromote") == 2 ? "否" : "是");
            attrMap.put("IsDutyFree", s.getInt("isDutyFree") == 2 ? "否" : "是");
            attrMap.put("DutyFreeId", s.getStr("DutyFreeId") == null ? "无" : s.getStr("DutyFreeId"));
            attrMap.put("EconomicName", s.getStr("EconomicName"));
            attrMap.put("SocietyName", s.getStr("SocietyName"));
            attrMap.put("SourceName", s.getStr("SourceName"));
            attrMap.put("TechnicalName", s.getStr("TechnicalName"));
            attrMap.put("PropertyName", s.getStr("PropertyName"));
            attrMap.put("BuyerName",s.getStr("BuyerName"));
            attrMap.put("BuyerCountry",s.getStr("BuyerCountry"));
            attrMap.put("BuyerType",s.getStr("BuyerType"));
            attrMap.put("BuyerProvince",s.getStr("BuyerProvince") == null ? "无" : s.getStr("BuyerProvince"));
            attrMap.put("BuyerCity",s.getStr("BuyerCity") == null ? "无" : s.getStr("BuyerCity"));
            attrMap.put("BuyerCounty",s.getStr("BuyerCounty") == null ? "无" : s.getStr("BuyerCounty"));
            attrMap.put("BuyerPostCode",s.getStr("BuyerPostCode"));
            attrMap.put("BuyerContact",s.getStr("BuyerContact"));
            attrMap.put("BuyerTel",s.getStr("BuyerTel"));
            attrMap.put("BuyerLegalPerson",s.getStr("BuyerLegalPerson"));
            attrMap.put("BuyerLegalEntityCode",s.getStr("BuyerLegalEntityCode") == null ? "无" : s.getStr("BuyerLegalEntityCode"));
            attrMap.put("BuyerEmail",s.getStr("BuyerEmail"));
            attrMap.put("BuyerRegisteredAddress",s.getStr("BuyerRegisteredAddress"));
            attrMap.put("BuyerMailingAddress",s.getStr("BuyerMailingAddress"));
            attrMap.put("ContractFund",s.getStr("ContractFund") == null ? "无" : s.getStr("ContractFund"));
            attrMap.put("DevelopFund",s.getStr("DevelopFund") == null ? "无" : s.getStr("DevelopFund"));
            attrMap.put("ResearchFund",s.getStr("ResearchFund") == null ? "无" : s.getStr("ResearchFund"));
            attrMap.put("ServiceFund",s.getStr("ServiceFund") == null ? "无" : s.getStr("ServiceFund"));
            attrMap.put("OtherFund",s.getStr("OtherFund") == null ? "无" : s.getStr("OtherFund"));
        } else if (r.getInt("levelId") == 1) {
            Record s = new DbRecord(DbConfig.V_SUBJECT_LINK_SPONSORED).whereEqualTo("id", id).queryFirst();
            attrMap.put("CategoryName", s.getStr("CategoryName"));
            attrMap.put("SecCategoryName", s.getStr("SecCategoryName"));
            attrMap.put("SubjectFund", s.getStr("SubjectFund") == null ? "无" : s.getStr("SubjectFund"));
            attrMap.put("DocumentFund", s.getStr("DocumentFund") == null ? "无" : s.getStr("DocumentFund"));
            attrMap.put("DataFund", s.getStr("DataFund") == null ? "无" : s.getStr("DataFund"));
            attrMap.put("OutboundFund", s.getStr("OutboundFund") == null ? "无" : s.getStr("OutboundFund"));
            attrMap.put("MeetingFund", s.getStr("MeetingFund") == null ? "无" : s.getStr("MeetingFund"));
            attrMap.put("InternationalFund", s.getStr("InternationalFund") == null ? "无" : s.getStr("InternationalFund"));
            attrMap.put("HardwareFund", s.getStr("HardwareFund") == null ? "无" : s.getStr("HardwareFund"));
            attrMap.put("ConsultFund", s.getStr("ConsultFund") == null ? "无" : s.getStr("ConsultFund"));
            attrMap.put("LaborFund", s.getStr("LaborFund") == null ? "无" : s.getStr("LaborFund"));
            attrMap.put("MaterialFund", s.getStr("MaterialFund") == null ? "无" : s.getStr("MaterialFund"));
            attrMap.put("PatentFund", s.getStr("PatentFund") == null ? "无" : s.getStr("PatentFund"));
            attrMap.put("StaySchoolFund",s.getStr("StaySchoolFund") == null ? "无" : s.getStr("StaySchoolFund"));
            attrMap.put("ResearchName", s.getStr("ResearchName") == null ? "无" : s.getStr("ResearchName"));
            attrMap.put("ResearchAreaName", s.getStr("ResearchAreaName") == null ? "无" : s.getStr("ResearchAreaName"));
            attrMap.put("DutyFreeId", s.getStr("DutyFreeId") == null ? "无" : s.getStr("DutyFreeId"));
            attrMap.put("BelongName", s.getStr("BelongName") == null ? "无" : s.getStr("BelongName"));
            attrMap.put("FirstType", s.getStr("FirstType"));
            attrMap.put("SecondType", s.getStr("SecondType"));
            attrMap.put("ThirdType", s.getStr("ThirdType"));
            attrMap.put("EntrustName", s.getStr("EntrustPlace"));
            attrMap.put("TopicName", s.getStr("TopicName"));
            attrMap.put("MainSubjectName", s.getStr("MainSubjectName") == null ? "无" : s.getStr("MainSubjectName"));
            attrMap.put("MainProjectName", s.getStr("MainProjectName") == null ? "无" : s.getStr("MainProjectName"));
            attrMap.put("ApplicationCode", s.getStr("ApplicationCode") == null ? "无" : s.getStr("ApplicationCode"));
            attrMap.put("Introduction", s.getStr("Introduction") == null ? "无" : s.getStr("Introduction"));
            attrMap.put("Remarks", s.getStr("Remarks") == null ? "无" : s.getStr("Remarks"));
            attrMap.put("ContractFund", s.getStr("ContractFund") == null ? "无" : s.getStr("ContractFund"));
            attrMap.put("CooperatePrincipal", s.getStr("CooperatePrincipal"));
            attrMap.put("BankName", s.getStr("BankName"));
            attrMap.put("CooperateName", s.getStr("CooperateName"));
            attrMap.put("CooperateTypeName", s.getStr("CooperateTypeName"));
            attrMap.put("BankAccount", s.getStr("BankAccount"));
            attrMap.put("EconomicName", s.getStr("EconomicName"));
            attrMap.put("SocietyName", s.getStr("SocietyName"));
            attrMap.put("SourceName", s.getStr("SourceName"));
            attrMap.put("TechnicalName", s.getStr("TechnicalName"));
            attrMap.put("PropertyName", s.getStr("PropertyName"));
            attrMap.put("IsSecrecy", s.getInt("isSecrecy") == 2 ? "否" : "是");
            if (s.getInt("isVoucher") == 1) {
                attrMap.put("IsVoucher", "开行政事业收据");
            } else if (s.getInt("isVoucher") == 2) {
                attrMap.put("IsVoucher", "开发票");
            } else if (s.getInt("isVoucher") == 3) {
                attrMap.put("IsVoucher", "不开发票");
            }
            attrMap.put("IsSubmitFill", s.getInt("isSubmitFill") == 2 ? "否" : "是");
            attrMap.put("IsPromote", s.getInt("isPromote") == 2 ? "否" : "是");
            attrMap.put("IsDutyFree", s.getInt("isDutyFree") == 2 ? "否" : "是");
        }
        setAttrs(attrMap);

//		renderTemplate("detail-tea.html");

        //向前端发送全部attribute
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void paperlist() {
        Integer id = getParaToInt("id");
        List<Record> p = null;
        List<Record> a = new DbRecord(DbConfig.T_SUBJECT_LINK_PAPER).whereEqualTo("SubjectId", id).query();
        if (a.size() != 0) {
            p = new DbRecord(DbConfig.V_SUBJECT_LINK_PAPER).whereEqualTo("id", id).query();
        }
        renderJson(p);
    }

    public void updatePaper() {
        Integer SubjectId = getParaToInt("SubjectId");
        Integer PaperId = getParaToInt("PaperId");
        if (SubjectService.me.setSubjectPaper(SubjectId, PaperId)) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "上传成功"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "上传失败，该成果已被上传"));
        }
    }

    public void applyFinish() {
        Integer SubjectId = getParaToInt("SubjectId");
        Record r = new DbRecord(DbConfig.T_USER_SUBJECT).whereEqualTo("id", SubjectId).queryFirst();
        r.set("reviewId", WebConfig.SUBJECT_WAIT_FINISH);
        r.set("applyAt", new Date(System.currentTimeMillis()));
        List<Record> a = new DbRecord(DbConfig.T_SUBJECT_LINK_PAPER).whereEqualTo("SubjectId", SubjectId).query();
        if (a.size() != 0) {
            if (Db.update(DbConfig.T_USER_SUBJECT, "id", r)) {
                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "申请成功！请等待管理员审核"));
            } else {
                renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "操作失败"));
            }
        }else{
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "请先上传成果"));
        }
    }

    public void deletePaper() {
        Integer SubjectId = getParaToInt("SubjectId");
        Integer PaperId = getParaToInt("PaperId");
        Record r = new DbRecord(DbConfig.T_SUBJECT_LINK_PAPER).whereEqualTo("SubjectId", SubjectId).whereEqualTo("PaperId", PaperId).queryFirst();
        if (Db.delete(DbConfig.T_SUBJECT_LINK_PAPER, "id", r)) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "操作成功"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "操作失败"));
        }
    }
}
