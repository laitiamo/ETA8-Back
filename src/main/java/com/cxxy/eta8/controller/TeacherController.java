package com.cxxy.eta8.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.model.Teacher;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.validator.DeleteTeachersValidator;
import com.cxxy.eta8.validator.ResetValidator;
import com.cxxy.eta8.validator.SetRoleValidator;
import com.cxxy.eta8.validator.TeacherInfoUpdateValidator;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class TeacherController extends Controller {

	public void index() {
		Map<String, Object> attrMap = new HashMap<String, Object>();
		attrMap.put("grade", new DbRecord(DbConfig.T_GRADE).orderByASC("id").query());
		attrMap.put("major", new DbRecord(DbConfig.T_MAJOR).orderByASC("id").query());
		attrMap.put("role", new DbRecord(DbConfig.T_ROLE).whereNotEqualTo("id",WebConfig.ROLE_ADMIN_ID).whereNotEqualTo("id",WebConfig.ROLE_STUDENT_ID).whereNotEqualTo("id",WebConfig.ROLE_ASSISTANT_ID).query());
		attrMap.put("college", new DbRecord(DbConfig.T_COLLEGE).orderByASC("id").query());
		attrMap.put("sector", new DbRecord(DbConfig.T_SECTOR).orderByASC("id").query());
		renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
	}

	public void list() {
		Integer roleId = getParaToInt("roleId");
		Integer collegeId = getParaToInt("collegeId");
		Integer sectorId = getParaToInt("sectorId");
		String keyUsername = getPara("keyUsername");
		String keyName = getPara("keyName");
		Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		String order=getPara("order");
		String field=getPara("field");
		String defaultField="username";
		
		Page<Record> p = new DbRecord(DbConfig.V_TEACHER_INFO)
						.whereEqualTo("username", keyUsername)
						.whereContains("name", keyName)
						.whereEqualTo("roleId", roleId)
				.whereEqualTo("sectorId",sectorId)
				.whereEqualTo("collegeId",collegeId)
						.orderBySelect(field,order,defaultField)
						.page(page, limit);
		renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
	}

	@Before(ResetValidator.class)
	public void reset() {
		Integer[] ids = getParaValuesToInt("ids[]");

		if (UserService.me.resetPassword(ids)) {
			renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "??????????????????"));
		} else {
			renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "??????????????????"));
		}
	}

	@Before(SetRoleValidator.class)
	public void setRole() {
		Integer[] ids = getParaValuesToInt("ids[]");
		Integer roleId = getParaToInt("roleId");
		Integer[] oldroleIds = getParaValuesToInt("oldroleIds[]");
		Integer[] classIds = getParaValuesToInt("classIds[]");
		String[] InstructorIds = getParaValues("usernames[]");

		if (UserService.me.setRole(ids, roleId)) {
			if (roleId == WebConfig.ROLE_INSTRUCTOR_ID) { 
				if (UserService.me.addLink(classIds, InstructorIds, oldroleIds)) {
					renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "??????????????????"));
				} else {
					renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "??????????????????"));
				}
			} else if (UserService.me.delLink(oldroleIds, InstructorIds)) { 
				renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "??????????????????"));
			} else {
				renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "??????????????????"));
			}
		} else {
			renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "??????????????????"));
		}
	}

	
	@Before(TeacherInfoUpdateValidator.class)
	public void update() {
		String username = getPara("username");
		String newName = getPara("newName");
		Integer newSector = getParaToInt("newSector");
		Integer newCollege = getParaToInt("newCollege");

		Record id = new DbRecord(DbConfig.T_TEACHER).whereEqualTo("teaNo", username).queryFirst();
		Integer ID = id.getInt("id");

		boolean success = new Teacher().setId(ID).setTeaName(newName).setSectorId(newSector).setCollegeId(newCollege).update();

		if (success) {
			renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "????????????"));
		} else {
			renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "????????????"));
		}
	}
	
	// TODO: ??????????????????????????????????????????????????????????????????????????????
	@Before(DeleteTeachersValidator.class)
	public void delete() {
		final Integer[] teacherNos = getParaValuesToInt("teacherNos[]");
		boolean recordsDeletionSuccess = true;
		boolean filesDeletionSuccess = true;
		
		filesDeletionSuccess = UserService.me.deleteFiles(teacherNos,WebConfig.ROLE_TEACHER);
		recordsDeletionSuccess = UserService.me.deleteUsers(teacherNos, WebConfig.ROLE_TEACHER);
		
		if (recordsDeletionSuccess && filesDeletionSuccess) {
			renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "??????????????????????????????????????????????????????"));
		} else if (!recordsDeletionSuccess && !filesDeletionSuccess){
			renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "??????????????????????????????????????????????????????"));
		} else if (recordsDeletionSuccess && !filesDeletionSuccess) {
			renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "??????????????????????????????????????????????????????"));
		} else {
			renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "??????????????????????????????????????????????????????"));
		}
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
}
