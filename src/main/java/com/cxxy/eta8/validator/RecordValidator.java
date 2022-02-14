package com.cxxy.eta8.validator;

import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class RecordValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		setShortCircuit(true);
		if (c.getFile("file") == null) {
			addError("msg", "请选择上传图片");
		}
		validateRequired("paperName", "msg", "请输入成果名称");
		validateRequiredString("paperTime", "msg", "请选择日期");
		validateRequiredString("paperPlace", "msg", "请填写期刊名称");
		validateRequired("rankId", "msg", "请选择期刊类型");
		validateRequired("typeId", "msg", "请选择期刊类型");
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson(new AjaxResult(AjaxResult.CODE_ERROR, c.getAttrForStr("msg")));
	}

}
