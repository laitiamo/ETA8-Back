package com.cxxy.eta8.controller;

import com.cxxy.eta8.model.Award;
import com.cxxy.eta8.validator.AddAwardValidator;
import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class AwardAddController extends Controller {

	public void index() {

	}

	@Before(AddAwardValidator.class)
	public void add() {
		String awardName = getPara("name");
		Integer type = getParaToInt("type");
		boolean success = new Award().setAwardName(awardName).setAwardTypeId(type).save();
		if (success) {
			renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "奖项添加成功"));
		} else {
			renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "奖项添加失败"));
		}
	}

}
