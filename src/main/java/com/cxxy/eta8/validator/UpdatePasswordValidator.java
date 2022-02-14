package com.cxxy.eta8.validator;

import com.cxxy.eta8.common.Md5Util;
import com.cxxy.eta8.service.UserService;
import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class UpdatePasswordValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		setShortCircuit(true);
		validateRequiredString("old-pass", "msg", "请输入旧密码！");
		validateRequiredString("new-pass", "msg", "请输入新密码！");
		validateRequiredString("new-pass-v", "msg", "请再次输入新密码！");
		validateRequiredString("captcha", "msg", "验证码不能为空");


		String username = UserService.me.getCurrentUser().getStr("username");
		String encryptedOldPassword = Md5Util.Md5(c.getPara("old-pass"), username);

		if (!encryptedOldPassword.equals(UserService.me.getCurrentUser().getStr("password"))) {
			addError("msg", "旧密码不正确！");
		}
		
		validateRegex("new-pass", "^[a-zA-Z0-9]{6,20}+$", true, "msg", "密码格式不符合要求！");
		validateEqualField("new-pass", "new-pass-v", "msg", "两次密码输入不一致！");
		validateCaptcha("captcha", "msg", "验证码不正确");

		if (c.getPara("old-pass").equals(c.getPara("new-pass"))) {
			addError("msg", "新密码不能与旧密码相同");
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson(new AjaxResult(AjaxResult.CODE_ERROR, c.getAttrForStr("msg")));
	}

}
