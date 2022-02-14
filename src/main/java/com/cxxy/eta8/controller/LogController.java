package com.cxxy.eta8.controller;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.vo.AjaxResult;
import com.cxxy.eta8.vo.LayUITableResult;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.apache.shiro.SecurityUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LogController extends Controller {

    public void index() {
        Map<String, Object> attrMap = new HashMap<String, Object>();
        attrMap.put("type", new DbRecord(DbConfig.T_LOG_TYPE).query());
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }

    public void list() {
        int page = getParaToInt("page");
        int limit = getParaToInt("limit");
        Integer typeId = getParaToInt("typeId");
        String order = getPara("order");
        String field = getPara("field");
        String defaultField = "reviewAt";
        if (SecurityUtils.getSubject().hasRole(UserService.ROLE_ADMIN)) {
            Page<Record> p = new DbRecord(DbConfig.V_LOG_INFO)
                    .whereEqualTo("reviewTypeId",typeId)
                    .orderBySelect(field, order, defaultField).page(page, limit);
            renderJson(new LayUITableResult<Record>(AjaxResult.CODE_SUCCESS, "", p.getTotalRow(), p.getList()));
        }
    }


    public void delete() {
        final Integer id = getParaToInt("id");

        Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean success = true;
                Record r = new DbRecord(DbConfig.V_LOG_INFO).whereEqualTo("id", id).queryFirst();
                if (!Db.delete(DbConfig.T_LOG_REVIEW, "id", r)) {
                    success = false;
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
