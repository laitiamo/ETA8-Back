package com.cxxy.eta8.controller;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.service.ExportService;
import com.cxxy.eta8.service.SubjectService;
import com.cxxy.eta8.validator.TeacherSubjectDeleteValidator;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.expr.ast.Id;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuerySubjectController extends Controller {

    public void index() {
//		setAttr("rank", new DbRecord(DbConfig.T_RANK).query());
//		renderTemplate("query-tea.html");
        Map<String, Object> attrMap = new HashMap<String, Object>();
        attrMap.put("level", new DbRecord(DbConfig.T_LEVEL).query());
        attrMap.put("rank", new DbRecord(DbConfig.T_RANK).query());
        attrMap.put("college", new DbRecord(DbConfig.T_COLLEGE).orderByASC("id").query());
        attrMap.put("sector", new DbRecord(DbConfig.T_SECTOR).orderByASC("id").query());
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void list() {
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

        Page<Record> p = new DbRecord(DbConfig.V_TEACHER_SUBJECT)
                .whereContains("SubjectNum", keySubjectNum)
                .whereContains("SubjectName", keySubjectName)
                .whereContains("SubjectPlace", keySubjectPlace)
                .whereContains("name", keyName)
                .whereEqualTo("username", keyUsername)
                .whereEqualTo("rankId", rankId)
                .whereEqualTo("sectorId",sectorId)
                .whereEqualTo("collegeId",collegeId)
                .whereEqualTo("LevelId", levelId)
                .whereEqualTo("ReviewId", reviewId)
                .whereEqualTo("CandidateId",WebConfig.CANDIDATE_MAJOR)
                .orderBySelect(field, order, defaultField)
                .page(page, limit);
        renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
    }

    public void updatePaper() {
        Integer PaperId = getParaToInt("PaperId");
        Integer SubjectId = getParaToInt("SubjectId");
        if (SubjectService.me.updateSubjectPaper(PaperId, SubjectId)) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "????????????"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "????????????"));
        }
    }


    @Before(TeacherSubjectDeleteValidator.class)
    public void del() {
        final Integer id = getParaToInt("id");

        Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean success = true;
                boolean success2 = true;
                Record r = new DbRecord(DbConfig.T_USER_SUBJECT).whereEqualTo("id", id).queryFirst();
                Integer levelId = r.getInt("levelId");
                if (levelId == 1) {
                    Record s = new DbRecord(DbConfig.T_SUBJECT_SPONSORED).whereEqualTo("SubjectId", id).queryFirst();
                    if (!Db.delete(DbConfig.T_SUBJECT_SPONSORED, "id", s)) {
                        success2 = false;
                    }
                } else if (levelId == 2) {
                    Record s = new DbRecord(DbConfig.T_SUBJECT_HORIZON).whereEqualTo("SubjectId", id).queryFirst();
                    if (!Db.delete(DbConfig.T_SUBJECT_HORIZON, "id", s)) {
                        success2 = false;
                    }
                } else if (levelId == 3) {
                    Record s = new DbRecord(DbConfig.T_SUBJECT_SCHOOL).whereEqualTo("SubjectId", id).queryFirst();
                    if (!Db.delete(DbConfig.T_SUBJECT_SCHOOL, "id", s)) {
                        success2 = false;
                    }
                }
                if (!Db.delete(DbConfig.T_USER_SUBJECT, "id", r)) {
                    success = false;
                }
                if (success && success2) {
                    renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "????????????"));
                } else {
                    renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "????????????"));
                }
                return success;
            }
        });
    }

    public void exportXLS() {
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
        List<Record> records = new DbRecord(DbConfig.V_TEACHER_SUBJECT)
                .whereContains("SubjectNum", keySubjectNum)
                .whereContains("SubjectName", keySubjectName)
                .whereContains("SubjectPlace", keySubjectPlace)
                .whereContains("name", keyName)
                .whereEqualTo("username", keyUsername)
                .whereEqualTo("rankId", rankId)
                .whereEqualTo("sectorId",sectorId)
                .whereEqualTo("collegeId",collegeId)
                .whereEqualTo("LevelId", levelId)
                .whereEqualTo("ReviewId", reviewId)
                .whereEqualTo("CandidateId",WebConfig.CANDIDATE_MAJOR)
                .query();
        try {
            File downloadFile = ExportService.me.exportTeacherSubject(records);
            if (downloadFile != null) {
                renderFile(downloadFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportPDF() {
        Integer id = getParaToInt("id");
        List<Record> records = new DbRecord(DbConfig.V_TEACHER_SUBJECT)
                .whereEqualTo("id", id)
                .query();
        try {
            File downloadFile = ExportService.me.exportTeacherSubjectPDF(records);
            if (downloadFile != null) {
                renderFile(downloadFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportZIP() {
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
        List<Record> records = new DbRecord(DbConfig.V_TEACHER_SUBJECT)
                .whereContains("SubjectNum", keySubjectNum)
                .whereContains("SubjectName", keySubjectName)
                .whereContains("SubjectPlace", keySubjectPlace)
                .whereContains("name", keyName)
                .whereEqualTo("username", keyUsername)
                .whereEqualTo("rankId", rankId)
                .whereEqualTo("sectorId",sectorId)
                .whereEqualTo("collegeId",collegeId)
                .whereEqualTo("LevelId", levelId)
                .whereEqualTo("ReviewId", reviewId)
                .whereEqualTo("CandidateId",WebConfig.CANDIDATE_MAJOR)
                .query();
        try {
            File downloadFile = ExportService.me.exportTeacherZIP(records);
            if (downloadFile != null) {
                renderFile(downloadFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
