package com.cxxy.eta8.validator;

import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class ImportTeaValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		setShortCircuit(true);
		if (c.getFile() == null) {
			addError("msg", "请选择上传文件");
		}
		validateInteger("collegeId", "msg", "请选择二级学院");
		validateInteger("sectorId", "msg", "请选择部门");
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson(new AjaxResult(AjaxResult.CODE_ERROR, c.getAttrForStr("msg")));
	}

}
