package com.cxxy.eta8.route;

import com.cxxy.eta8.controller.*;
import com.cxxy.eta8.interceptor.AuthInterceptor;
import com.cxxy.eta8.interceptor.CORSInterceptor;
import com.cxxy.eta8.interceptor.MainLayoutInterceptor;
import com.jfinal.config.Routes;

public class MainRoute extends Routes {

	@Override
	public void config() {
		setBaseViewPath("/view");
		addInterceptor(new CORSInterceptor()); //设置允许跨域
		addInterceptor(new MainLayoutInterceptor());	//主要弹出层拦截类（公用模板那边用来显示登录用户的个人详细用的）
		addInterceptor(new AuthInterceptor());   //设置权限拦截器类

		add("/eta8/", HomeController.class, "/");
		add("/eta8/home", HomeController.class, "/");
		add("/eta8/upload", UploadController.class, "/");
		add("/eta8/record", RecordController.class, "/");
		add("/eta8/subject", SubjectController.class, "/");
		add("/eta8/mine", MineController.class, "/");
		add("/eta8/review", ReviewController.class, "/");
		add("/eta8/record-review", ReviewRecordController.class, "/");
		add("/eta8/query-stu", QueryStuController.class, "/");
		add("/eta8/query-tea", QueryTeaController.class, "/");
		add("/eta8/query-paper", QueryPaperController.class, "/");
		add("/eta8/query-subject", QuerySubjectController.class, "/");
		add("/eta8/award", AwardController.class, "/");
		add("/eta8/award-add", AwardAddController.class, "/");
		add("/eta8/student-management", StudentController.class, "/");
		add("/eta8/teacher-management", TeacherController.class, "/");
		add("/eta8/class-management", ClassController.class, "/");
		add("/eta8/import-award", ImportAwardController.class, "/");
		add("/eta8/import-stu", ImportStuController.class, "/");
		add("/eta8/import-tea", ImportTeaController.class, "/");
		add("/eta8/password", PasswordController.class, "/");
		add("/eta8/logout", LogoutController.class, "/");
		add("/eta8/log", LogController.class, "/");
		add("/eta8/detail-stu", DetailStuController.class, "/");
		add("/eta8/detail-tea", DetailTeaController.class, "/");
		add("/eta8/detail-paper", DetailPaperController.class, "/");
		add("/eta8/detail-subject", DetailSubjectController.class, "/");
		add("/eta8/notice",NoticeController.class, "/");
	}
}
