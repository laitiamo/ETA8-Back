package com.cxxy.eta8.route;

import com.cxxy.eta8.controller.LoginController;
import com.cxxy.eta8.interceptor.CORSInterceptor;
import com.jfinal.config.Routes;

public class IndexRoute extends Routes {

	@Override
	public void config() {
		setBaseViewPath("/view");
		addInterceptor(new CORSInterceptor()); //设置允许跨域
		add("/", LoginController.class, "/");
	}

}
