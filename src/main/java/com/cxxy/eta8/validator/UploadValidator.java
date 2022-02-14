package com.cxxy.eta8.validator;

import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class UploadValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		setShortCircuit(true);
		if (c.getFile("file") == null) {
			addError("msg", "请选择上传图片");
		}
		validateRequired("awardId", "msg", "请选择奖项名称");
		validateRequired("rankId", "msg", "请选择奖项等级");
		validateRequiredString("awardTime", "msg", "请选择获奖日期");
		validateRequiredString("awardPlace", "msg", "请填写获奖名次");
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson(new AjaxResult(AjaxResult.CODE_ERROR, c.getAttrForStr("msg")));
	}

}
