package com.cxxy.eta8.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.cxxy.eta8.service.UserService;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

public class MainLayoutInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		Controller c = inv.getController();
		Record info = UserService.me.getCurrentUserInfo();
		Map<String, Object> attrMap = new HashMap<String, Object>();
		attrMap.put("l_name", info.getStr("name"));
		attrMap.put("l_username", (String) SecurityUtils.getSubject().getPrincipal());
		attrMap.put("l_role", info.getStr("roleName"));
		attrMap.put("l_grade", info.getStr("gradeName"));
		attrMap.put("l_genderId", info.getStr("genderId"));
		attrMap.put("l_major", info.getStr("majorName"));
		attrMap.put("l_college", info.getStr("collegeName"));
		attrMap.put("t_sector",info.getStr("sectorName"));
		attrMap.put("l_class", info.getStr("className"));
		attrMap.put("l_roleId", info.getInt("roleId"));
		c.setAttrs(attrMap);
		inv.invoke();
	}

}
