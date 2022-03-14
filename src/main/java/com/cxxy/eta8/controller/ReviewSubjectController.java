package com.cxxy.eta8.controller;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReviewSubjectController extends Controller {

    public void index() {
        Map<String, Object> attrMap = new HashMap<String, Object>();
        attrMap.put("type", new DbRecord(DbConfig.S_TYPE).query());
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void listSubject() {
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        Integer rankId = getParaToInt("rankId");
        Integer levelId = getParaToInt("levelId");
        Integer reviewId = getParaToInt("reviewId");
        Integer collegeId = getParaToInt("collegeId");
        Integer sectorId = getParaToInt("sectorId");
        String keyUsername = getPara("keyUsername");
        String keyName = getPara("keyName");
        String keySubjectNum = getPara("keySubjectNum");
        String keySubjectName = getPara("keySubjectName");
        String keySubjectPlace = getPara("keySubjectPlace");
        String order = getPara("order");
        String field = getPara("field");
        String defaultField = "id";
        Record info = UserService.me.getCurrentUserInfo();
        if (info.getStr("roleNameEn").equals(WebConfig.ROLE_ADMIN) || info.getStr("roleNameEn").equals(WebConfig.ROLE_MANAGER)) {//只有系统管理员和办公室主任可以审核全部项目
            Page<Record> p = new DbRecord(DbConfig.V_TEACHER_SUBJECT)
                    .whereContains("SubjectNum", keySubjectNum)
                    .whereContains("SubjectName", keySubjectName)
                    .whereContains("SubjectPlace", keySubjectPlace)
                    .whereContains("name", keyName)
                    .whereEqualTo("username", keyUsername)
                    .whereEqualTo("rankId", rankId)
                    .whereEqualTo("sectorId", sectorId)
                    .whereEqualTo("collegeId", collegeId)
                    .whereEqualTo("LevelId", levelId)
                    .whereEqualTo("ReviewId", reviewId)
                    .whereEqualTo("CandidateId", WebConfig.CANDIDATE_MAJOR)
                    .whereEqualTo("reviewId", WebConfig.REVIEW_UNREAD)
                    .orderBySelect(field, order, defaultField)
                    .page(page, limit);
            renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
        }
    }

    //项目立项通过
    public void passSubject() {
        Integer id = getParaToInt("id");
        String reviewName = getPara("reviewer");
        Integer reviewType = getParaToInt("reviewType");
        Record r = new DbRecord(DbConfig.T_USER_SUBJECT).whereEqualTo("id", id).queryFirst();
        r.set("reviewId", WebConfig.SUBJECT_NOT_FINISH);
        r.set("reviewAt", new Date(System.currentTimeMillis()));
        if (UserService.me.setLog(id, reviewType, reviewName)) {
            if (Db.update(DbConfig.T_USER_SUBJECT, "id", r)) {
                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "操作成功！您的操作为：批准立项"));
            } else {
                renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "操作失败"));
            }
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "操作失败"));
        }
    }

    //项目立项不通过
    public void notPassSubject() {
        Integer id = getParaToInt("id");
        String reviewName = getPara("reviewer");
        Integer reviewType = getParaToInt("reviewType");
        Record r = new DbRecord(DbConfig.T_USER_SUBJECT).whereEqualTo("id", id).queryFirst();
        r.set("reviewId", WebConfig.REVIEW_NOT_PASS);
        r.set("reviewAt", new Date(System.currentTimeMillis()));
        if (UserService.me.setLog(id, reviewType, reviewName)) {
            if (Db.update(DbConfig.T_USER_SUBJECT, "id", r)) {
                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "操作成功！您的操作为：驳回立项"));
            } else {
                renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "操作失败"));
            }
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "操作失败"));
        }
    }


//	public void detail() {
//		int id = getParaToInt("id");
//		redirect("/eta8/detail-stu?id=" + id);
//	}

}
