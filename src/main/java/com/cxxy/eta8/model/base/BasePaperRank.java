package com.cxxy.eta8.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePaperRank<M extends BasePaperRank<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setRankName(java.lang.String rankName) {
		set("rankName", rankName);
		return (M)this;
	}
	
	public java.lang.String getRankName() {
		return getStr("rankName");
	}

	public M setTypeId(java.lang.Integer typeId) {
		set("typeId", typeId);
		return (M)this;
	}
	
	public java.lang.Integer getTypeId() {
		return getInt("typeId");
	}

}