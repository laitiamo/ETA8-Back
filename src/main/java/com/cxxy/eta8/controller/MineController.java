package com.cxxy.eta8.controller;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.validator.StudentAwardDeleteValidator;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.apache.shiro.SecurityUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineController extends Controller {

	public void index() {
		renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "1"));
	}

	public void list() {
		int page = getParaToInt("page");
		int limit = getParaToInt("limit");
		String order = getPara("order");
		String field = getPara("field");
		String defaultField = "createAt";
		if (SecurityUtils.getSubject().hasRole(UserService.ROLE_STUDENT) || SecurityUtils.getSubject().hasRole(UserService.ROLE_ASSISTANT)) {
			Page<Record> p = new DbRecord(DbConfig.V_STUDENT_AWARD)
					.whereEqualTo("userId", UserService.me.getCurrentUser().getInt("id"))
					.orderBySelect(field, order, defaultField).page(page, limit);
			renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
		}else {
			Page<Record> p = new DbRecord(DbConfig.V_TEACHER_AWARD)
					.whereEqualTo("userId", UserService.me.getCurrentUser().getInt("id"))
					.orderBySelect(field, order, defaultField).page(page, limit);
			renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
		}
	}

	public void listpaper() {
		int page = getParaToInt("page");
		int limit = getParaToInt("limit");
		String order = getPara("order");
		String field = getPara("field");
		String defaultField = "createAt";
			Page<Record> p = new DbRecord(DbConfig.V_TEACHER_PAPER)
					.whereEqualTo("userId", UserService.me.getCurrentUser().getInt("id"))
					.orderBySelect(field, order, defaultField).page(page, limit);
			renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
	}

	public void listsubject() {
		int page = getParaToInt("page");
		int limit = getParaToInt("limit");
		String order = getPara("order");
		String field = getPara("field");
		String defaultField = "createAt";
		Page<Record> p = new DbRecord(DbConfig.V_TEACHER_SUBJECT)
				.whereEqualTo("userId", UserService.me.getCurrentUser().getInt("id"))
				.orderBySelect(field, order, defaultField).page(page, limit);
		renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
	}

	public void getPaperList() {
		Map<String, Object> attrMap = new HashMap<String, Object>();
		attrMap.put("rank3", new DbRecord(DbConfig.T_AWARD).whereEqualTo("awardTypeId",WebConfig.PAPER_TYPE_TEACHER).query());
		renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
	}

	public void getSubjectList() {
		Map<String, Object> attrMap = new HashMap<String, Object>();
		attrMap.put("rank3", new DbRecord(DbConfig.T_AWARD).whereEqualTo("awardTypeId",WebConfig.PAPER_TYPE_TEACHER).query());
		renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
	}

	public void listPerSubject() {
		Record info = UserService.me.getCurrentUserInfo();
		Integer awardId = getParaToInt("awardId");
		List<Record> result = new DbRecord(DbConfig.V_TEACHER_SUBJECT)
				.whereEqualTo("awardId", awardId)
				.whereEqualTo("userId",info.getStr("userId"))
				.query();
		renderJson(result);
	}

	public void detail() {
		int id = getParaToInt("id");
		if (SecurityUtils.getSubject().hasRole(UserService.ROLE_STUDENT) || SecurityUtils.getSubject().hasRole(UserService.ROLE_ASSISTANT)) {
			redirect("/eta8/detail-stu?id=" + id);
		} else {
			redirect("/eta8/detail-tea?id=" + id);
		}
	}

	@Before(StudentAwardDeleteValidator.class)
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

	public void delete() {
		final Integer id = getParaToInt("id");

		Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				boolean success = true;
				if (SecurityUtils.getSubject().hasRole(UserService.ROLE_STUDENT) || SecurityUtils.getSubject().hasRole(UserService.ROLE_ASSISTANT)) {
					Record r = new DbRecord(DbConfig.V_STUDENT_AWARD).whereEqualTo("id", id)
							.whereEqualTo("reviewId", WebConfig.REVIEW_NOT_PASS).queryFirst();
					if (!Db.delete(DbConfig.T_USER_AWARD, "id", r)) {
						success = false;
					}
					if (success) {
						renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "删除成功"));
					} else {
						renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "删除失败"));
					}
					return success;
				}else {
					Record r = new DbRecord(DbConfig.V_TEACHER_AWARD).whereEqualTo("id", id)
							.whereEqualTo("reviewId", WebConfig.REVIEW_NOT_PASS).queryFirst();

					if (!Db.delete(DbConfig.T_USER_AWARD, "id", r)) {
						success = false;
					}
					if (success) {
						renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "删除成功"));
					} else {
						renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "删除失败"));
					}
					return success;
				}
			}
		});
	}

}
