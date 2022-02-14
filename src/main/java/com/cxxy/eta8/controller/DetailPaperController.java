package com.cxxy.eta8.controller;

import com.alibaba.fastjson.JSON;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.interceptor.DetailPaperInterceptor;
import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailPaperController extends Controller {

    @Before(DetailPaperInterceptor.class)
    public void index() {
        Integer id = getParaToInt("id");
        Record r = new DbRecord(DbConfig.V_TEACHER_PAPER).whereEqualTo("id", id).queryFirst();
        List<Record> n = new DbRecord(DbConfig.V_TEACHER_PAPER).whereEqualTo("id", id).query();
        String name = "";
        for (int i = 0; i < n.size(); i++) {
            name += n.get(i).getStr("name") + " ";
        }
        Map<String, Object> attrMap = new HashMap<String, Object>();
        //多图优化，字符分割 2020-10-20

        String imagePath = r.getStr("imagePath");
        ArrayList<String> images = new ArrayList<String>();
        if (imagePath.indexOf("*") != -1) {

            String[] parts = imagePath.split("\\*");
            //如果存在"*"则分割，取出图片数量
            Integer picNum = Integer.parseInt(parts[1]);
            String[] pathParts = parts[0].split("\\.");
            String pathPart = pathParts[0];
            for (int i = 0; i < picNum; i++) {
                //图片url补全 2021-2-25
                images.add(getAttr("basePath").toString() + pathPart + "_" + i + ".jpeg");
            }
        } else {
            images.add(getAttr("basePath").toString() + imagePath);
        }


        attrMap.put("imagePaths", images);
        attrMap.put("username", r.getStr("username"));
        attrMap.put("name", r.getStr("name"));
        attrMap.put("gender", r.getStr("genderName"));
        attrMap.put("award", r.getStr("paperName"));
        attrMap.put("type", r.getStr("typeName"));
        attrMap.put("rank", r.getStr("rankName"));
        attrMap.put("place", r.getStr("paperPlace"));
        attrMap.put("writer", name);
        attrMap.put("time", new SimpleDateFormat("yyyy/MM/dd").format(r.getDate("paperTime")));
        attrMap.put("createAt", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(r.getDate("createAt")));
        attrMap.put("reviewAt", r.getDate("reviewAt") == null ?
                "" : new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(r.getDate("reviewAt")));
        attrMap.put("review", r.getStr("reviewName"));
        setAttrs(attrMap);

//		renderTemplate("detail-tea.html");

        //向前端发送全部attribute
        renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, JSON.toJSONString(attrMap)));
    }
}
