package com.cxxy.eta8.controller;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.interceptor.DetailSubjectInterceptor;
import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailSubjectController extends Controller {

    @Before(DetailSubjectInterceptor.class)
    public void index() {
        Integer id = getParaToInt("id");
        Record r = new DbRecord(DbConfig.V_TEACHER_SUBJECT).whereEqualTo("id", id).queryFirst();
        List<Record> n = new DbRecord(DbConfig.V_TEACHER_SUBJECT).whereEqualTo("id", id).query();
        String name = "";
        for (int i = 0; i < n.size(); i++) {
            name += n.get(i).getStr("name") + " ";
        }
        Map<String, Object> attrMap = new HashMap<String, Object>();
        //多图优化，字符分割 2020-10-20

        String imagePath = r.getStr("imagePath");
        ArrayList<String> images = new ArrayList<String>();
        if (imagePath.indexOf("*") != -1) {

            String[] parts = imagePath.split("\\*");
            //如果存在"*"则分割，取出图片数量
            Integer picNum = Integer.parseInt(parts[1]);
            String[] pathParts = parts[0].split("\\.");
            String pathPart = pathParts[0];
            for (int i = 0; i < picNum; i++) {
                //图片url补全 2021-2-25
                images.add(getAttr("basePath").toString() + pathPart + "_" + i + ".jpeg");
            }
        } else {
            images.add(getAttr("basePath").toString() + imagePath);
        }

        attrMap.put("imagePaths", images);
        attrMap.put("username", r.getStr("username"));
        attrMap.put("name", r.getStr("name"));
        attrMap.put("gender", r.getStr("genderName"));
        attrMap.put("subject", r.getStr("subjectName"));
        attrMap.put("num", r.getStr("subjectNum"));
        attrMap.put("place", r.getStr("subjectPlace"));
        attrMap.put("fund", r.getInt("subjectFund"));
        attrMap.put("writer", name);
        attrMap.put("time", new SimpleDateFormat("yyyy/MM/dd").format(r.getDate("subjectTime")));
        attrMap.put("rank", r.getStr("rankName"));
        attrMap.put("level", r.getStr("levelName"));
        attrMap.put("createAt", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(r.getDate("createAt")));
        attrMap.put("reviewAt", r.getDate("reviewAt") == null ?
                "" : new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(r.getDate("reviewAt")));
        attrMap.put("finishAt", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(r.getDate("FinishTime")));
        attrMap.put("review", r.getStr("reviewName"));
        attrMap.put("levelId", r.getInt("levelId"));

        //校级项目
        if (r.getInt("levelId") == 3) {
            Record s = new DbRecord(DbConfig.V_SUBJECT_LINK_SCHOOL).whereEqualTo("id", id).queryFirst();
            attrMap.put("Source", s.getStr("SourceName"));
            attrMap.put("Type", s.getStr("TypeName"));
            attrMap.put("Belong", s.getStr("BelongName"));
            attrMap.put("Cooperate", s.getStr("CooperateName"));
        } else if (r.getInt("levelId") == 2) {
            Record s = new DbRecord(DbConfig.V_SUBJECT_LINK_HORIZON).whereEqualTo("id", id).queryFirst();
            attrMap.put("Introduction", s.getStr("Introduction"));
            attrMap.put("RelyCenterSubject", s.getStr("RelyCenterSubject"));
            attrMap.put("ContractName", s.getStr("ContractName"));
            attrMap.put("CooperatePrincipal", s.getStr("CooperatePrincipal"));
            attrMap.put("ContractNum", s.getStr("ContractNum"));
            attrMap.put("FundNum", s.getStr("FundNum"));
            attrMap.put("EntrustName", s.getStr("EntrustName"));
            attrMap.put("CooperateName", s.getStr("CooperateName"));
            attrMap.put("ContractType", s.getStr("ContractType"));
            attrMap.put("BankName", s.getStr("BankName"));
            attrMap.put("Pay", s.getInt("PayId") == 1 ? "一次支付" : "分期支付");
            attrMap.put("BankAccount", s.getStr("BankAccount"));
            attrMap.put("ContractDuty", s.getStr("ContractDuty"));
            attrMap.put("IsPromote", s.getInt("isPromote") == 2 ? "否" : "是");
            attrMap.put("IsDutyFree", s.getInt("isDutyFree") == 2 ? "否" : "是");
            attrMap.put("DutyFreeId", s.getStr("DutyFreeId"));
            attrMap.put("EconomicName", s.getStr("EconomicName"));
            attrMap.put("SocietyName", s.getStr("SocietyName"));
            attrMap.put("SourceName", s.getStr("SourceName"));
            attrMap.put("TechnicalName", s.getStr("TechnicalName"));
            attrMap.put("PropertyName", s.getStr("PropertyName"));
        } else if (r.getInt("levelId") == 1) {
            Record s = new DbRecord(DbConfig.V_SUBJECT_LINK_SPONSORED).whereEqualTo("id", id).queryFirst();
            attrMap.put("ResearchName", s.getStr("ResearchName"));
            attrMap.put("DutyFreeId", s.getStr("DutyFreeId"));
            attrMap.put("BelongName", s.getStr("BelongName"));
            attrMap.put("TypeName", s.getStr("TypeName"));
            attrMap.put("EntrustName", s.getStr("EntrustName"));
            attrMap.put("TopicName", s.getStr("TopicName"));
            attrMap.put("MainProjectName", s.getStr("MainProjectName") == null ? "无" : s.getStr("MainProjectName"));
            attrMap.put("ApplicationCode", s.getStr("ApplicationCode") == null ? "无" : s.getStr("ApplicationCode"));
            attrMap.put("Introduction", s.getStr("Introduction") == null ? "无" : s.getStr("Introduction"));
            attrMap.put("Remarks", s.getStr("Remarks") == null ? "无" : s.getStr("Remarks"));
            attrMap.put("CooperateName", s.getStr("CooperateName"));
            attrMap.put("ContractNum", s.getStr("ContractNum"));
            attrMap.put("FundNum", s.getStr("FundNum"));
            attrMap.put("ContractName", s.getStr("ContractName"));
            attrMap.put("ContractType", s.getStr("ContractType"));
            attrMap.put("ContractFund", s.getStr("ContractFund"));
            attrMap.put("CooperatePrincipal", s.getStr("CooperatePrincipal"));
            attrMap.put("BankName", s.getStr("BankName"));
            attrMap.put("BankAccount", s.getStr("BankAccount"));
            attrMap.put("ContractDuty", s.getStr("ContractDuty"));
            attrMap.put("Pay", s.getInt("PayId") == 1 ? "一次支付" : "分期支付");
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
}
