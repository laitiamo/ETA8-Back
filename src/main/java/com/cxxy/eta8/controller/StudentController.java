package com.cxxy.eta8.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.model.Student;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.validator.DeleteStudentsValidator;
import com.cxxy.eta8.validator.ResetValidator;
import com.cxxy.eta8.validator.SetRoleValidator;
import com.cxxy.eta8.validator.StudentInfoUpdateValidator;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.apache.shiro.SecurityUtils;

public class StudentController extends Controller {

    public void index() {
//		setAttr("grade", new DbRecord(DbConfig.T_GRADE).orderByASC("id").query());
//		setAttr("major", new DbRecord(DbConfig.T_MAJOR).orderByASC("id").query());
        Map<String, Object> attrMap = new HashMap<String, Object>();
        attrMap.put("grade", new DbRecord(DbConfig.T_GRADE).orderByASC("id").query());
        attrMap.put("major", new DbRecord(DbConfig.T_MAJOR).orderByASC("id").query());
        attrMap.put("role", new DbRecord(DbConfig.T_ROLE).whereNotEqualTo("id", WebConfig.ROLE_ADMIN_ID).whereNotEqualTo("id", WebConfig.ROLE_LEADER_ID).whereNotEqualTo("id", WebConfig.ROLE_INSTRUCTOR_ID).whereNotEqualTo("id", WebConfig.ROLE_TEACHER_ID).query());
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
//		renderTemplate("student-management.html");
    }

    public void list() {
        Integer roleId = getParaToInt("roleId");
        Integer gradeId = getParaToInt("gradeId");
        Integer majorId = getParaToInt("majorId");
        Integer classId = getParaToInt("classId");
        String keyUsername = getPara("keyUsername");
        String keyName = getPara("keyName");
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        String order = getPara("order");
        String field = getPara("field");
        String defaultField = "username";

        Record info = UserService.me.getCurrentUserInfo();
        if (info.getStr("roleNameEn").equals(WebConfig.ROLE_ADMIN)) {
            Page<Record> p = new DbRecord(DbConfig.V_STUDENT_INFO)
                    .whereEqualTo("gradeId", gradeId)
                    .whereEqualTo("majorId", majorId)
                    .whereEqualTo("classId", classId)
                    .whereEqualTo("username", keyUsername)
                    .whereEqualTo("roleId", roleId)
                    .whereContains("name", keyName)
                    .orderBySelect(field, order, defaultField)
                    .page(page, limit);
            renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
        } else {
            Page<Record> p = new DbRecord(DbConfig.V_STUDENT_INFO_INSTRUCTOR)
                    .whereEqualTo("gradeId", gradeId)
                    .whereEqualTo("instructorId", (String) SecurityUtils.getSubject().getPrincipal())
                    .whereEqualTo("majorId", majorId)
                    .whereEqualTo("classId", classId)
                    .whereEqualTo("username", keyUsername)
                    .whereEqualTo("roleId", roleId)
                    .whereContains("name", keyName)
                    .orderBySelect(field, order, defaultField)
                    .page(page, limit);
            renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
        }
    }

    @Before(ResetValidator.class)
    public void reset() {
        Integer[] ids = getParaValuesToInt("ids[]");

        if (UserService.me.resetPassword(ids)) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "密码重置成功"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "密码重置失败"));
        }
    }

    public void listClass() {
        Integer gradeId = getParaToInt("gradeId");
        Integer majorId = getParaToInt("majorId");
        List<Record> result = new DbRecord(DbConfig.T_CLASS).whereEqualTo("gradeId", gradeId)
                .whereEqualTo("majorId", majorId).query();
        renderJson(result);
    }

    @Before(SetRoleValidator.class)
    public void setRole() {
        Integer[] ids = getParaValuesToInt("ids[]");
        Integer roleId = getParaToInt("roleId");
        Integer[] oldroleIds = getParaValuesToInt("oldroleIds[]");
        Integer[] classIds = getParaValuesToInt("classIds[]");
        String[] assistantIds = getParaValues("usernames[]");

        if (UserService.me.setRole(ids, roleId)) {
            if (roleId == WebConfig.ROLE_ASSISTANT_ID) {
                if (UserService.me.addLinkAs(classIds, assistantIds, oldroleIds)) {
                    renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "角色设置成功"));
                } else {
                    renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "角色设置失败"));
                }
            } else if (UserService.me.delLinkAs(oldroleIds, assistantIds)) {
                renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "角色设置成功"));
            } else {
                renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "角色设置失败"));
            }
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "角色设置失败"));
        }
    }

    @Before(StudentInfoUpdateValidator.class)
    public void update() {
        String stuNo = getPara("stuNo");
        String modifiedField = getPara("modifiedField");
        String newName = getPara("newName");
        Integer newGradeId = getParaToInt("newGradeId");
        String newMajorName = getPara("newMajorName");
        Integer newClassNo = getParaToInt("newClassNo");

        Record record = new DbRecord(DbConfig.T_STUDENT).whereEqualTo("stuNo", stuNo).queryFirst();
        Integer id = record.getInt("id");

        boolean success = true;
        if (modifiedField.equals("name")) {
            success = new Student().setId(id).setStuName(newName).update();
        } else {
            Integer newMajorId = new DbRecord(DbConfig.T_MAJOR).whereEqualTo("majorName", newMajorName).queryFirst()
                    .getInt("id");
            Record classRecord = new DbRecord(DbConfig.T_CLASS).whereEqualTo("classNo", newClassNo)
                    .whereEqualTo("majorId", newMajorId).whereEqualTo("gradeId", newGradeId).queryFirst();

            if (classRecord == null) {
                success = false;
            } else {
                Integer newClassId = classRecord.getInt("id");
                success = new Student().setId(id).setClassId(newClassId).update();
            }
        }

        if (success) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "更新成功"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "更新失败"));
        }
    }

    @Before(DeleteStudentsValidator.class)
    public void delete() {
        final Integer[] studentNos = getParaValuesToInt("studentNos[]");
        boolean recordsDeletionSuccess = true;
        boolean filesDeletionSuccess = true;

        filesDeletionSuccess = UserService.me.deleteFiles(studentNos, WebConfig.ROLE_STUDENT) || UserService.me.deleteFiles(studentNos, WebConfig.ROLE_ASSISTANT);
        recordsDeletionSuccess = UserService.me.deleteUsers(studentNos, WebConfig.ROLE_STUDENT) || UserService.me.deleteFiles(studentNos, WebConfig.ROLE_ASSISTANT);

        if (recordsDeletionSuccess && filesDeletionSuccess) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "学生信息删除成功，已上传文件清理成功"));
        } else if (!recordsDeletionSuccess && !filesDeletionSuccess) {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "学生信息删除失败，已上传文件清理失败"));
        } else if (recordsDeletionSuccess && !filesDeletionSuccess) {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "学生信息删除成功，已上传文件清理失败"));
        } else if (!recordsDeletionSuccess && filesDeletionSuccess) {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "学生信息删除失败，已上传文件清理成功"));
        }
    }
}
