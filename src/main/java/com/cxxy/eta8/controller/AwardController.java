package com.cxxy.eta8.controller;

import java.sql.SQLException;

import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.model.Award;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.validator.DeleteAwardValidator;
import com.cxxy.eta8.validator.UpdateAwardValidator;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class AwardController extends Controller {

    public void index() {
        renderTemplate("award.html");
    }

    public void list() {
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        Integer type = getParaToInt("type");
        String key = getPara("key");
        String order = getPara("order");
        String field = getPara("field");
        String defaultField = "id";
        Page<Record> p = null;
        Record info = UserService.me.getCurrentUserInfo();
        if (info.getStr("roleNameEn").equals(WebConfig.ROLE_MANAGER)) {
            p = new DbRecord(DbConfig.V_AWARD_INFO)
                    .whereEqualTo("awardTypeId", 2)
                    .whereContains("awardName", key)
                    .orderBySelect(field, order, defaultField)
                    .page(page, limit);
        } else if (info.getStr("roleNameEn").equals(WebConfig.ROLE_INSTRUCTOR)) {
            p = new DbRecord(DbConfig.V_AWARD_INFO)
                    .whereEqualTo("awardTypeId", 1)
                    .whereContains("awardName", key)
                    .orderBySelect(field, order, defaultField)
                    .page(page, limit);
        } else {
            if (type == WebConfig.AWARD_TYPE_ALL) {
                p = new DbRecord(DbConfig.V_AWARD_INFO)
                        .whereEqualTo("awardTypeId", type)
                        .whereContains("awardName", key)
                        .orderBySelect(field, order, defaultField)
                        .page(page, limit);
            }
        }
        renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
    }

    @Before(UpdateAwardValidator.class)
    public void update() {
        Integer id = getParaToInt("id");
        String newName = getPara("newName");

        boolean success = new Award().setId(id).setAwardName(newName).update();

        if (success) {
            renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "更新成功"));
        } else {
            renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "更新失败"));
        }
    }

    @Before(DeleteAwardValidator.class)
    public void delete() {
        final Integer[] ids = getParaValuesToInt("ids[]");

        Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean success = true;
                for (int i = 0; i < ids.length; i++) {
                    Record r = new DbRecord(DbConfig.T_AWARD).whereEqualTo("id", ids[i]).queryFirst();
                    if (!Db.delete(DbConfig.T_AWARD, "id", r)) {
                        success = false;
                    }
                }
                if (success) {
                    renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "删除成功"));
                } else {
                    renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "删除失败"));
                }
                return success;
            }
        });
    }

}
