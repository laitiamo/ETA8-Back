package com.cxxy.eta8.controller;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.service.ExportService;
import com.cxxy.eta8.validator.TeacherPaperDeleteValidator;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryPaperController extends Controller {

    public void index() {
//		setAttr("rank", new DbRecord(DbConfig.T_RANK).query());
//		renderTemplate("query-tea.html");
        Map<String, Object> attrMap = new HashMap<String, Object>();
        attrMap.put("rank", new DbRecord(DbConfig.T_TYPE).query());
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void list() {
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        Integer typeId = getParaToInt("typeId");
        Integer rankId = getParaToInt("rankId");
        String keyPaperName = getPara("keyPaperName");
        String keyPaperPlace = getPara("keyPaperPlace");
        String order = getPara("order");
        String field = getPara("field");
        String defaultField = "id";

        Page<Record> p = new DbRecord(DbConfig.V_PAPER_INFO)
                .whereContains("paperName", keyPaperName)
                .whereContains("paperPlace", keyPaperPlace)
                .whereEqualTo("rankId", rankId)
                .whereEqualTo("typeId", typeId)
                .orderBySelect(field, order, defaultField)
                .page(page, limit);
        renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
    }


    @Before(TeacherPaperDeleteValidator.class)
    public void del() {
        final Integer id = getParaToInt("id");

        Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean success = true;
                Record r = new DbRecord(DbConfig.T_USER_PAPER).whereEqualTo("id", id).queryFirst();
                if (!Db.delete(DbConfig.T_USER_PAPER, "id", r)) {
                    success = false;
                }
                if (success) {
                    renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "删除成功"));
                } else {
                    renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "删除失败"));
                }
                return success;
            }
        });
    }

    public void listType() {
        Integer typeId = getParaToInt("typeId");
        List<Record> result = new DbRecord(DbConfig.T_PAPER_RANK).whereEqualTo("typeId", typeId).query();
        renderJson(result);
    }

    public void exportXLS() {
        Integer rankId = getParaToInt("rankId");
        Integer typeId = getParaToInt("typeId");
        String keyAwardName = getPara("keyAwardName");
        String keyAwardPlace = getPara("keyAwardPlace");
        List<Record> records = new DbRecord(DbConfig.V_PAPER_INFO)
                .whereEqualTo("rankId", rankId)
                .whereEqualTo("typeId", typeId)
                .whereContains("paperName", keyAwardName)
                .whereContains("paperPlace", keyAwardPlace)
                .query();
        try {
            File downloadFile = ExportService.me.exportTeacherPaper(records);
            renderFile(downloadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportZIP() {
        Integer rankId = getParaToInt("rankId");
        Integer typeId = getParaToInt("typeId");
        String keyAwardName = getPara("keyAwardName");
        String keyAwardPlace = getPara("keyAwardPlace");
        List<Record> records = new DbRecord(DbConfig.V_PAPER_INFO)
                .whereEqualTo("rankId", rankId)
                .whereEqualTo("typeId", typeId)
                .whereContains("paperName", keyAwardName)
                .whereContains("paperPlace", keyAwardPlace)
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
