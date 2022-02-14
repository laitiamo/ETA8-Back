package com.cxxy.eta8.interceptor;

import java.util.List;

import org.apache.shiro.SecurityUtils;

import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.cxxy.eta8.service.UserService;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.activerecord.Record;

public class DetailTeaInterceptor implements Interceptor {

	public void intercept(Invocation inv) {

		boolean success = false;
		Record info = UserService.me.getCurrentUserInfo();
		if (info.getInt("roleId") == WebConfig.ROLE_ADMIN_ID || 
			info.getInt("roleId") == WebConfig.ROLE_LEADER_ID || info.getInt("roleId") == WebConfig.ROLE_MANAGER_ID) {
			success = true;
		} else if ((int)info.getInt("roleId") == WebConfig.ROLE_INSTRUCTOR_ID || 
				(int)info.getInt("roleId") == WebConfig.ROLE_TEACHER_ID) {
			List<Record> records = new DbRecord(DbConfig.V_TEACHER_AWARD)
					.whereEqualTo("username", SecurityUtils.getSubject().getPrincipal())
					.query();
			for (Record r : records) {
				if ((int)r.getInt("id") == inv.getController().getParaToInt("id")) {
					success = true;
					break;
				}
			}
		} else {

		}
		if (success) {
			inv.invoke();
		} else {
			inv.getController().renderTemplate("refuse.html");
		}
	}

}
