package com.cxxy.eta8.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;

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

public class ReviewController extends Controller {

    public void index() {
//		setAttr("grade", new DbRecord(DbConfig.T_GRADE).query());
//		setAttr("major", new DbRecord(DbConfig.T_MAJOR).query());
//		setAttr("rank", new DbRecord(DbConfig.T_RANK).query());
//		renderTemplate("review.html");
        Map<String, Object> attrMap = new HashMap<String, Object>();
        attrMap.put("grade", new DbRecord(DbConfig.T_GRADE).query());
        attrMap.put("major", new DbRecord(DbConfig.T_MAJOR).query());
        attrMap.put("rank", new DbRecord(DbConfig.T_RANK).query());
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void list() {
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        String order = getPara("order");
        String field = getPara("field");
        String defaultField = "createAt";
        Integer gradeId = getParaToInt("gradeId");
        Integer majorId = getParaToInt("majorId");
        Integer classId = getParaToInt("classId");
        Integer rankId = getParaToInt("rankId");
        String keyUsername = getPara("keyUsername");
        String keyName = getPara("keyName");
        String keyAwardName = getPara("keyAwardName");

        Record info = UserService.me.getCurrentUserInfo();
        if (info.getStr("roleNameEn").equals(WebConfig.ROLE_ADMIN)) {//系统管理员默认审核全部奖项（教师+学生）
            Page<Record> p = new DbRecord(DbConfig.V_ALL_AWARD)
                    .whereEqualTo("reviewId", WebConfig.REVIEW_UNREAD)
                    .whereEqualTo("rankId", rankId)
                    .whereEqualTo("username", keyUsername)
                    .whereContains("awardName", keyAwardName)
                    .whereContains("name", keyName)
                    .orderBySelect(field, order, defaultField).page(page, limit);
            renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
        } else if (info.getStr("roleNameEn").equals(WebConfig.ROLE_MANAGER)){//办公室主任可以审核教师奖项
            Page<Record> p = new DbRecord(DbConfig.V_TEACHER_AWARD)
                    .whereEqualTo("reviewId", WebConfig.REVIEW_UNREAD)
                    .whereEqualTo("rankId", rankId)
                    .whereEqualTo("username", keyUsername)
                    .whereContains("awardName", keyAwardName)
                    .whereContains("name", keyName)
                    .orderBySelect(field, order, defaultField).page(page, limit);
            renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
        } else if (info.getStr("roleNameEn").equals(WebConfig.ROLE_INSTRUCTOR)) {//辅导员只能审核自己所管理班级学生的奖项
            Page<Record> p = new DbRecord(DbConfig.V_STUDENT_AWARD_INSTRUCTOR)
                    .whereEqualTo("reviewId", WebConfig.REVIEW_UNREAD)
                    .whereEqualTo("instructorId", (String) SecurityUtils.getSubject().getPrincipal())
                    .whereEqualTo("gradeId", gradeId).whereEqualTo("majorId", majorId)
                    .whereEqualTo("classId", classId)
                    .whereEqualTo("rankId", rankId)
                    .whereEqualTo("username", keyUsername)
                    .whereContains("awardName", keyAwardName)
                    .whereContains("name", keyName)
                    .orderBySelect(field, order, defaultField).page(page, limit);
            renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
        } else {
            Page<Record> p = new DbRecord(DbConfig.V_STUDENT_AWARD_ASSISTANT)//学生助理只能审核自己班级学生的奖项
                    .whereEqualTo("reviewId", WebConfig.REVIEW_UNREAD)
                    .whereEqualTo("assistantId", (String) SecurityUtils.getSubject().getPrincipal())
                    .whereEqualTo("gradeId", gradeId)
                    .whereEqualTo("majorId", majorId)
                    .whereEqualTo("classId", classId)
                    .whereEqualTo("rankId", rankId)
                    .whereEqualTo("username", keyUsername)
                    .whereContains("awardName", keyAwardName)
                    .whereContains("name", keyName)
                    .orderBySelect(field, order, defaultField).page(page, limit);
            renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
        }
    }

    public void pass() {
        Integer id = getParaToInt("id");
        String reviewName = getPara("reviewer");
        Integer reviewType = getParaToInt("reviewType");
        Record r = new DbRecord(DbConfig.T_USER_AWARD).whereEqualTo("id", id).queryFirst();
        r.set("reviewId", WebConfig.REVIEW_PASS);
        r.set("reviewAt", new Date(System.currentTimeMillis()));
        if (UserService.me.setLog(id, reviewType, reviewName)) {
            if (Db.update(DbConfig.T_USER_AWARD, "id", r)) {
                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "操作成功！您的操作为：通过"));
            } else {
                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "操作失败"));
            }
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "操作失败"));
        }
    }

    public void notPass() {
        Integer id = getParaToInt("id");
        String reviewName = getPara("reviewer");
        Integer reviewType = getParaToInt("reviewType");
        Record r = new DbRecord(DbConfig.T_USER_AWARD).whereEqualTo("id", id).queryFirst();
        r.set("reviewId", WebConfig.REVIEW_NOT_PASS);
        r.set("reviewAt", new Date(System.currentTimeMillis()));
        if (UserService.me.setLog(id, reviewType, reviewName)) {
            if (Db.update(DbConfig.T_USER_AWARD, "id", r)) {
                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "操作成功！您的操作为：驳回"));
            } else {
                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "操作失败"));
            }
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "操作失败"));
        }
    }

//	public void detail() {
//		int id = getParaToInt("id");
//		redirect("/eta8/detail-stu?id=" + id);
//	}

}
