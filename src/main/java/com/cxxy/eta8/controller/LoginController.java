package com.cxxy.eta8.controller;

import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.validator.LoginValidator;
import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class LoginController extends Controller {

	@Before(LoginValidator.class)
	public void login() {
		if (UserService.me.login(getPara("username"), getPara("password"))) {
			renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "登陆成功"));
		} else {
			renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "用户名或密码错误，登陆失败"));
		}
	}

	public void captcha() {
		renderCaptcha();
	}
}
