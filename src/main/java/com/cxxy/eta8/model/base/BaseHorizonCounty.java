package com.cxxy.eta8.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseHorizonCounty<M extends BaseHorizonCounty<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCounty(java.lang.String County) {
		set("County", County);
		return (M)this;
	}
	
	public java.lang.String getCounty() {
		return getStr("County");
	}

	public M setCityId(java.lang.Integer CityId) {
		set("CityId", CityId);
		return (M)this;
	}
	
	public java.lang.Integer getCityId() {
		return getInt("CityId");
	}

}