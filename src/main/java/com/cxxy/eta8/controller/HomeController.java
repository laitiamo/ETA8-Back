package com.cxxy.eta8.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.model.Inform;
import com.cxxy.eta8.model.Picture;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.validator.ImageAddValidatior;
import com.cxxy.eta8.validator.ImageUpdateValidatior;
import com.cxxy.eta8.validator.InfomUpdateValidatior;
import com.cxxy.eta8.validator.InformAddValidatior;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class HomeController extends Controller {

    public void index() {
        Record info = UserService.me.getCurrentUserInfo();
        Map<String, Object> attrMap = new HashMap<String, Object>();
        setAttrs(attrMap);
        // System.out.println(record);
        setAttr("image", new DbRecord(DbConfig.T_PICTURE).orderByASC("id").query());
        setAttr("inform", new DbRecord(DbConfig.T_INFORM).orderByASC("id").query());
        //不知道干啥用的 注释了
//		setAttr("stuAward", new DbRecord(DbConfig.V_STUDENT_AWARD)
//								.whereContains("rankName", "国")
//								.orderByASC("rankName")
//								.query());

        //向前端发送全部attribute
        Enumeration<String> names = getAttrNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            attrMap.put(name, getAttr(name));
            //System.out.println(name);
        }
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void imagelist() {
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        Page<Record> p = new DbRecord(DbConfig.T_PICTURE).orderByASC("id").page(page, limit);
        renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
    }

    @Before(ImageUpdateValidatior.class)
    public void imageupdate() {
        Integer Id = getParaToInt("id");
        String writing = getPara("writing");
        boolean success = new Picture().setId(Id).setWriting(writing).update();

        if (success) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "更新成功"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "更新失败"));
        }
    }

    public void detail() {
        Record info = UserService.me.getCurrentUserInfo();
        List<Record> studentRecord = new DbRecord(DbConfig.V_STUDENT_AWARD).whereEqualTo("reviewName", "已审核").query();
        List<Record> teacherRecord = new DbRecord(DbConfig.V_TEACHER_AWARD).whereNotEqualTo("awardName","设计专利").whereNotEqualTo("awardName","教师培训").query();
        Map<String, Object> attrMap = new HashMap<String, Object>();
        attrMap.put("l_student", studentRecord.size());
        attrMap.put("l_teacher", teacherRecord.size());
        attrMap.put("l_document", studentRecord.size() + teacherRecord.size());
        if (info.getStr("roleNameEn").equals(WebConfig.ROLE_ADMIN) || info.getStr("roleNameEn").equals(WebConfig.ROLE_LEADER)) {
            List<Record> studentInternationalRecord = new DbRecord(DbConfig.V_STUDENT_AWARD).whereEqualTo("rankName", "国际级").whereEqualTo("reviewName", "已审核").query();
            List<Record> studentNationRecord = new DbRecord(DbConfig.V_STUDENT_AWARD).whereEqualTo("rankName", "国家级").whereEqualTo("reviewName", "已审核").query();
            List<Record> studentProvinceRecord = new DbRecord(DbConfig.V_STUDENT_AWARD).whereEqualTo("rankName", "省部级").whereEqualTo("reviewName", "已审核").query();
            List<Record> studentSchoolRecord = new DbRecord(DbConfig.V_STUDENT_AWARD).whereEqualTo("rankName", "校级").whereEqualTo("reviewName", "已审核").query();
            List<Record> PaperRecord = new DbRecord(DbConfig.V_PAPER_INFO).whereEqualTo("typeId",WebConfig.PAPER_TYPE_PAPER).whereEqualTo("reviewName", "已审核").query();
            List<Record> BookRecord = new DbRecord(DbConfig.V_PAPER_INFO).whereEqualTo("typeId",WebConfig.PAPER_TYPE_BOOK).whereEqualTo("reviewName", "已审核").query();
            List<Record> SubjectRecord = new DbRecord(DbConfig.V_SUBJECT_INFO).whereEqualTo("reviewName", "已审核").query();
            List<Record> PatentRecord = new DbRecord(DbConfig.V_PAPER_INFO).whereEqualTo("typeId",WebConfig.PAPER_TYPE_PATENT).whereEqualTo("reviewName", "已审核").query();
            List<Record> CultivateRecord = new DbRecord(DbConfig.V_TEACHER_AWARD).whereEqualTo("awardName","教师培训").whereEqualTo("reviewName", "已审核").query();
            List<Record> personalRecord = new DbRecord(DbConfig.V_TEACHER_AWARD).whereEqualTo("username", info.getStr("username")).whereEqualTo("reviewName", "已审核").query();
            attrMap.put("l_international", studentInternationalRecord.size());
            attrMap.put("l_nation", studentNationRecord.size());
            attrMap.put("l_province", studentProvinceRecord.size());
            attrMap.put("l_school", studentSchoolRecord.size());
            attrMap.put("l_book", BookRecord.size());
            attrMap.put("l_subject", SubjectRecord.size());
            attrMap.put("l_patent", PatentRecord.size());
            attrMap.put("l_paper", PaperRecord.size());
            attrMap.put("l_cultivate", CultivateRecord.size());
            attrMap.put("l_number", personalRecord.size());
        } else if (info.getStr("roleNameEn").equals(WebConfig.ROLE_STUDENT) || info.getStr("roleNameEn").equals(WebConfig.ROLE_ASSISTANT)) {
            List<Record> stuMajorRecord = new DbRecord(DbConfig.V_STUDENT_AWARD).whereEqualTo("gradeId", info.getStr("gradeId")).whereEqualTo("majorId", info.getStr("majorId")).whereEqualTo("reviewName", "已审核").query();
            List<Record> stuClassRecord = new DbRecord(DbConfig.V_STUDENT_AWARD).whereEqualTo("classId", info.getStr("classId")).whereEqualTo("reviewName", "已审核").query();
            List<Record> UnManagerRecord = new DbRecord(DbConfig.V_STUDENT_AWARD_ASSISTANT).whereEqualTo("assistantId", info.getStr("username")).whereEqualTo("reviewName", "未审核").query();
            List<Record> personalRecord = new DbRecord(DbConfig.V_STUDENT_AWARD).whereEqualTo("username", info.getStr("username")).whereEqualTo("reviewName", "已审核").query();
            attrMap.put("l_unmanage", UnManagerRecord.size());
            attrMap.put("l_stumajor", stuMajorRecord.size());
            attrMap.put("l_stuclass", stuClassRecord.size());
            attrMap.put("l_number", personalRecord.size());
        } else if (info.getStr("roleNameEn").equals(WebConfig.ROLE_INSTRUCTOR)) {
            List<Record> personalRecord = new DbRecord(DbConfig.V_TEACHER_AWARD).whereEqualTo("username", info.getStr("username")).whereEqualTo("reviewName", "已审核").query();
            List<Record> ManagerRecord = new DbRecord(DbConfig.V_STUDENT_AWARD_INSTRUCTOR).whereEqualTo("instructorId", info.getStr("username")).whereEqualTo("reviewName", "已审核").query();
            List<Record> UnManagerRecord = new DbRecord(DbConfig.V_STUDENT_AWARD_INSTRUCTOR).whereEqualTo("instructorId", info.getStr("username")).whereEqualTo("reviewName", "未审核").query();
            attrMap.put("l_instructor", ManagerRecord.size());
            attrMap.put("l_unmanage", UnManagerRecord.size());
            attrMap.put("l_number", personalRecord.size());
        }
        setAttrs(attrMap);
        Enumeration<String> names = getAttrNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            attrMap.put(name, getAttr(name));
            //System.out.println(name);
        }
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    @Before(ImageAddValidatior.class)
    public void imageadd() {
        String imageUrl = getPara("imageurl");
        String writing = getPara("imagecontent");
        boolean success = new Picture().setImagePath(imageUrl).setWriting(writing).save();

        if (success) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "添加成功"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "添加失败"));
        }
    }

    public void imagedel() {
        Integer ID = getParaToInt("id");
        boolean success = new Picture().deleteById(ID);

        if (success) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "删除成功"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "删除失败"));
        }
    }

    public void informlist() {
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        Page<Record> p = new DbRecord(DbConfig.T_INFORM).orderByASC("id").page(page, limit);
        renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
    }

    @Before(InfomUpdateValidatior.class)
    public void informupdate() {
        Integer ID = getParaToInt("id");
        String title = getPara("title");
        String content = getPara("content");
        boolean success = new Inform().setId(ID).setTitle(title).setContent(content).update();

        if (success) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "更新成功"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "更新失败"));
        }
    }

    @Before(InformAddValidatior.class)
    public void informadd() {
        String title = getPara("title");
        String content = getPara("content");
        boolean success = new Inform().setTitle(title).setContent(content).save();

        if (success) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "添加成功"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "添加失败"));
        }
    }

    public void informdel() {
        Integer ID = getParaToInt("id");
        boolean success = new Inform().deleteById(ID);

        if (success) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "删除成功"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "删除失败"));
        }
    }
}
