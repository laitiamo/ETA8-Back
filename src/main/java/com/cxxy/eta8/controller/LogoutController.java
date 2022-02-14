package com.cxxy.eta8.controller;

import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.core.Controller;

public class LogoutController extends Controller {
	public void index() {
		if (UserService.me.logout()) {
			renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "已退出登陆"));
		}
	}

}
