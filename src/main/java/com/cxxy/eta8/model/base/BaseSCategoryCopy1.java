package com.cxxy.eta8.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSCategoryCopy1<M extends BaseSCategoryCopy1<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCategoryName(java.lang.String CategoryName) {
		set("CategoryName", CategoryName);
		return (M)this;
	}
	
	public java.lang.String getCategoryName() {
		return getStr("CategoryName");
	}

	public M setBelongId(java.lang.Integer BelongId) {
		set("BelongId", BelongId);
		return (M)this;
	}
	
	public java.lang.Integer getBelongId() {
		return getInt("BelongId");
	}

}
