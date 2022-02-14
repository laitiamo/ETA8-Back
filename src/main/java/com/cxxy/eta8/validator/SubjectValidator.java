package com.cxxy.eta8.validator;

import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class SubjectValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		setShortCircuit(true);
		if (c.getFile("file") == null) {
			addError("msg", "请选择上传图片");
		}
		validateRequired("subjectName", "msg", "请输入项目名称");
		validateRequired("subjectNum","msg","请输入项目编号");
		validateRequired("SubjectPlace","msg","请输入所属单位");
		validateRequired("rankId", "msg", "请选择项目类别");
		validateRequired("levelId", "msg", "请选择项目级别");
		validateRequiredString("SubjectTime", "msg", "请选择日期");
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson(new AjaxResult(AjaxResult.CODE_ERROR, c.getAttrForStr("msg")));
	}

}
