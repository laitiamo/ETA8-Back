package com.cxxy.eta8.validator;

import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class DeleteTeachersValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		setShortCircuit(true);
		Integer[] teacherNos = c.getParaValuesToInt("teacherNos[]");
		if (teacherNos == null || teacherNos.length == 0) {
			addError("msg", "至少选择一个用户进行操作");
		}
		for (int i = 0; i < teacherNos.length; i++) {
			String roleName = new DbRecord(DbConfig.V_TEACHER_INFO).whereEqualTo("teaNo", teacherNos[i]).queryFirst().get("roleNameEn");
			if (roleName.equals(WebConfig.ROLE_ADMIN) || roleName.equals(WebConfig.ROLE_INSTRUCTOR)) {
				addError("msg", "选中的用户中有辅导员或系统管理员，不允许删除");
				break;
			}
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson(new AjaxResult(AjaxResult.CODE_ERROR, c.getAttrForStr("msg")));
	}

}
