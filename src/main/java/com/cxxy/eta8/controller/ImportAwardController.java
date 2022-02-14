package com.cxxy.eta8.controller;

import java.io.File;

import com.cxxy.eta8.service.ImportService;
import com.cxxy.eta8.vo.AjaxResult;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;

public class ImportAwardController extends Controller {

	public void index() {
		renderTemplate("import-award.html");
	}

	public void download() {
		File templateFile = new File(PathKit.getWebRootPath() + File.separator + 
									"download" + File.separator + 
									"template" + File.separator + 
									"template_award.xls");
		renderFile(templateFile);
	}

	public void upload() {
		File xls = getFile().getFile();
		try {
			if (ImportService.me.importAward(xls)) {
				renderJson(new AjaxResult(AjaxResult.CODE_SUCCESS, "数据导入成功"));
			} else {
				renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "数据导入失败"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new AjaxResult(AjaxResult.CODE_ERROR, "数据导入失败"));
		}
		xls.delete();
	}
}
