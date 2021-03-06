package com.cxxy.eta8.common;

import com.cxxy.eta8.handler.BasePathHandler;
import com.cxxy.eta8.interceptor.CORSInterceptor;
import com.cxxy.eta8.model._MappingKit;
import com.cxxy.eta8.route.IndexRoute;
import com.cxxy.eta8.route.MainRoute;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;

/**
 * Web应用配置类
 * 
 * @author WYF
 * @update by YJ
 * @update by LZH
 */
public class WebConfig extends JFinalConfig {
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_LEADER = "leader";
	public static final String ROLE_INSTRUCTOR = "instructor";
	public static final String ROLE_TEACHER = "teacher";
	public static final String ROLE_STUDENT = "student";
	public static final String ROLE_ASSISTANT = "assistant";//新增角色：学生助理
	public static final String ROLE_MANAGER = "manager";//新增角色：办公室主任

	public static final int ROLE_ADMIN_ID = 1;
	public static final int ROLE_LEADER_ID = 2;
	public static final int ROLE_INSTRUCTOR_ID = 3;
	public static final int ROLE_TEACHER_ID = 4;
	public static final int ROLE_STUDENT_ID = 5;
	public static final int ROLE_ASSISTANT_ID = 6;
	public static final int ROLE_MANAGER_ID = 7;

	public static final int RANK_ECONOMIC = 1;
	public static final int RANK_SOCIETY = 2;

	public static final int AWARD_TYPE_ALL = 0;
	public static final int AWARD_TYPE_STUDENT = 1; 
	public static final int AWARD_TYPE_TEACHER = 2;
	public static final int PAPER_TYPE_TEACHER = 3;

	public static final int PAPER_TYPE_ALL = 0;
	public static final int PAPER_TYPE_PAPER = 1;
	public static final int PAPER_TYPE_BOOK = 2;
	public static final int PAPER_TYPE_PATENT = 3;
	public static final int PAPER_TYPE_AWARD = 4;

	public static final int SUBJECT_TYPE_SPONSORED = 1;
	public static final int SUBJECT_TYPE_HORIZON = 2;
	public static final int SUBJECT_TYPE_SCHOOL = 3;

	public static final int REVIEW_UNREAD = 1;
	public static final int REVIEW_PASS = 2;
	public static final int REVIEW_NOT_PASS = 3;
	public static final int SUBJECT_NOT_FINISH = 4;
	public static final int SUBJECT_WAIT_FINISH = 5;
	public static final int SUBJECT_FINISH = 6;

	public static final int CANDIDATE_MAJOR = 1;
	public static final int CANDIDATE_PART = 2;

	@Override
	public void configConstant(Constants me) {
		// 加载配置文件
		PropKit.use("config.properties");

		// 设置开发模式
		me.setDevMode(PropKit.getBoolean("devMode", false));

		// 设置模板引擎：为了实现动态网页 由程序控制 公用模板引擎使用
		me.setViewType(ViewType.JFINAL_TEMPLATE);

		// 设置文件默认上传路径 先接受再修改（复制重命名删除）
		me.setBaseUploadPath("upload/temp");

		// 设置401, 403, 404, 500错误页面
		me.setError401View("error/401.html");
		me.setError403View("error/403.html");
		me.setError404View("view/error/404.html");
		me.setError500View("error/500.html");

		// 设置第三方JsonFactory 阿里巴巴组装json速度快
		me.setJsonFactory(FastJsonFactory.me());
	}

	@Override
	public void configRoute(Routes me) { //配置路由
		me.add(new IndexRoute());
		me.add(new MainRoute());
	}

	@Override
	public void configEngine(Engine me) {
		me.setDevMode(PropKit.getBoolean("devMode", false));   //设置模板引擎部分的开发模式
		me.addSharedFunction("view/common/admin-layout.html"); // 配置公用模板html
	}

	@Override
	public void configPlugin(Plugins me) {
		DruidPlugin dp = new DruidPlugin(
			PropKit.get("jdbcUrl").trim(), 
			PropKit.get("user").trim(),
			PropKit.get("password").trim());
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.setShowSql(PropKit.getBoolean("devMode", false));
		arp.setDevMode(PropKit.getBoolean("devMode", false));
		arp.setDialect(new MysqlDialect());// 数据库方言
		arp.getEngine().setSourceFactory(new ClassPathSourceFactory()); // 设置了模板引擎将从 class path 或者 jar 包中读取 sql 文件。
																		// 可以将sql 文件放在maven项目下的resources 之下，
																		// 编译器会自动将其编译至classpath 之下，进而可以被读取到。
		arp.addSqlTemplate("web.sql");// sql统一在web.sql里写
		_MappingKit.mapping(arp);
		me.add(dp);
		me.add(arp);

		DruidPlugin dp2 = new DruidPlugin(
				PropKit.get("jdbcUrl2").trim(), 
				PropKit.get("user").trim(),
				PropKit.get("password").trim());
		ActiveRecordPlugin arp2 = new ActiveRecordPlugin("system", dp2);
		arp2.setShowSql(PropKit.getBoolean("devMode", false));
		arp2.setDevMode(PropKit.getBoolean("devMode", false));
		arp2.setDialect(new MysqlDialect());
		arp2.getEngine().setSourceFactory(new ClassPathSourceFactory());
		arp2.addSqlTemplate("web.sql");
		me.add(dp2);
		me.add(arp2);

		me.add(new EhCachePlugin());  //配置缓存
	}

	@Override
	public void configInterceptor(Interceptors me) {
 		// 全局范围超出权限范围 覆盖住shiro框架
		//解决跨域
		me.add(new CORSInterceptor());
	}

	@Override
	public void configHandler(Handlers me) { //添加处理器 接管所有web请求
		me.add(new BasePathHandler());
	}

	public static void main(String[] args) {
		UndertowServer.create(WebConfig.class).configWeb(builder -> {
			builder.addListener("org.apache.shiro.web.env.EnvironmentLoaderListener");
			builder.addFilter("shiro", "org.apache.shiro.web.servlet.ShiroFilter");
			builder.addFilterUrlMapping("shiro", "/*");
		}).start();
	}

}
