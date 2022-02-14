package com.cxxy.eta8.controller;

import com.cxxy.eta8.model.Notice;
import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.core.Controller;

public class NoticeController extends Controller {
	
	public void index() {
		
	}
	
	public void edit() {
		String content = getPara("content");

		boolean success = new Notice().setId(1).setContent(content).update();

		if (success) {
			renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "公告编辑成功"));
		} else {
			renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "公告编辑失败"));
		}
	}
}
