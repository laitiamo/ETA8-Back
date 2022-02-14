package com.cxxy.eta8.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.service.ExportService;
import com.cxxy.eta8.validator.TeacherAwardDeleteValidator;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class QueryTeaController extends Controller {

	public void index() {
//		setAttr("rank", new DbRecord(DbConfig.T_RANK).query());
//		renderTemplate("query-tea.html");
		Map<String, Object> attrMap = new HashMap<String, Object>();
		attrMap.put("rank", new DbRecord(DbConfig.T_RANK).query());
		renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
	}

	public void list() {
		Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		Integer rankId = getParaToInt("rankId");
		String keyUsername = getPara("keyUsername");
		String keyName = getPara("keyName");
		String keyAwardName = getPara("keyAwardName");
		String order=getPara("order");
		String field=getPara("field");
		String defaultField="id";
		
		Page<Record> p = new DbRecord(DbConfig.V_TEACHER_AWARD)
						.whereContains("name", keyName)
						.whereEqualTo("username", keyUsername)
						.whereEqualTo("rankId", rankId)
						.whereContains("awardName", keyAwardName)
						.orderBySelect(field,order,defaultField)
						.page(page, limit);
		renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
	}

//	public void detail() {
//		int id = getParaToInt("id");
//		redirect("/eta8/detail-tea?id=" + id);
//	}
	
	@Before(TeacherAwardDeleteValidator.class)
	public void del() {
		final Integer id = getParaToInt("id");
		
		Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				boolean success = true;
				Record r = new DbRecord(DbConfig.T_USER_AWARD).whereEqualTo("id", id).queryFirst();
				if (!Db.delete(DbConfig.T_USER_AWARD, "id", r)) {
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

	public void exportXLS() {
		Integer rankId = getParaToInt("rankId");
		String keyUsername = getPara("keyUsername");
		String keyName = getPara("keyName");
		String keyAwardName = getPara("keyAwardName");
		List<Record> records = new DbRecord(DbConfig.V_TEACHER_AWARD)
								.whereContains("name", keyName)
								.whereEqualTo("username", keyUsername)
								.whereEqualTo("rankId", rankId)
								.whereEqualTo("awardName", keyAwardName)
								.query();
		try {
			File downloadFile = ExportService.me.exportTeacherAward(records);
			renderFile(downloadFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exportZIP() {
		Integer rankId = getParaToInt("rankId");
		String keyUsername = getPara("keyUsername");
		String keyName = getPara("keyName");
		String keyAwardName = getPara("keyAwardName");
		List<Record> records = new DbRecord(DbConfig.V_TEACHER_AWARD)
								.whereContains("name", keyName)
								.whereEqualTo("username", keyUsername)
								.whereEqualTo("rankId", rankId)
								.whereEqualTo("awardName", keyAwardName)
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
