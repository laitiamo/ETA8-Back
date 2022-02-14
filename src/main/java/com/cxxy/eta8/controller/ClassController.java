package com.cxxy.eta8.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ClassController extends Controller {

	public void index() {
//		setAttr("rank", new DbRecord(DbConfig.T_RANK).query());
//		renderTemplate("query-tea.html");
		Map<String, Object> attrMap = new HashMap<String, Object>();
		attrMap.put("grade", new DbRecord(DbConfig.T_GRADE).orderByASC("id").query());
		attrMap.put("major", new DbRecord(DbConfig.T_MAJOR).orderByASC("id").query());
		attrMap.put("role", new DbRecord(DbConfig.T_ROLE).whereNotEqualTo("id",WebConfig.ROLE_ADMIN_ID).whereNotEqualTo("id",WebConfig.ROLE_LEADER_ID).whereNotEqualTo("id",WebConfig.ROLE_TEACHER_ID).whereNotEqualTo("id",WebConfig.ROLE_STUDENT_ID).whereNotEqualTo("id",WebConfig.ROLE_ASSISTANT_ID).query());
		renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
	}

	public void list() {
		Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		Integer gradeId = getParaToInt("gradeId");
		Integer majorId = getParaToInt("majorId");
		Integer classId = getParaToInt("classId");
		Integer classNo = getParaToInt("classNo");
		String keyUsername = getPara("keyUsername");
		String keyName = getPara("keyName");
		String order=getPara("order");
		String field=getPara("field");
		String defaultField="id";
		
		Page<Record> p = new DbRecord(DbConfig.V_INSTRUCTOR_CLASS_INFO)
						.whereEqualTo("gradeId", gradeId)
						.whereEqualTo("majorId", majorId)
						.whereEqualTo("classId", classId)
						.whereEqualTo("classNo", classNo)
						.whereContains("className", keyUsername)
						.whereContains("teaName", keyName)
						.orderBySelect(field,order,defaultField)
						.page(page, limit);
		renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
	}
	public void listClass() {
		Integer gradeId = getParaToInt("gradeId");
		Integer majorId = getParaToInt("majorId");
		List<Record> result = new DbRecord(DbConfig.T_CLASS)
							.whereEqualTo("gradeId", gradeId)
							.whereEqualTo("majorId", majorId)
							.query();
		renderJson(result);
	}
	public void delete() {
		final Integer[] classIds = getParaValuesToInt("classIds[]");
		final String[] instructorIds = getParaValues("instructorIds[]");
		boolean recordsDeletionSuccess = true;
		
		recordsDeletionSuccess = UserService.me.delLinkClass(classIds,instructorIds);

		if (recordsDeletionSuccess) {
			renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "辅导员与班级关系解绑成功"));
		} else if (!recordsDeletionSuccess) {
			renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "辅导员与班级关系解绑失败"));
		}
	}
}
