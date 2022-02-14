package com.cxxy.eta8.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseUserPaper<M extends BaseUserPaper<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setPaperName(java.lang.String paperName) {
		set("paperName", paperName);
		return (M)this;
	}
	
	public java.lang.String getPaperName() {
		return getStr("paperName");
	}

	public M setPaperTime(java.util.Date paperTime) {
		set("paperTime", paperTime);
		return (M)this;
	}
	
	public java.util.Date getPaperTime() {
		return get("paperTime");
	}

	public M setPaperPlace(java.lang.String paperPlace) {
		set("paperPlace", paperPlace);
		return (M)this;
	}
	
	public java.lang.String getPaperPlace() {
		return getStr("paperPlace");
	}

	public M setCreateAt(java.util.Date createAt) {
		set("createAt", createAt);
		return (M)this;
	}
	
	public java.util.Date getCreateAt() {
		return get("createAt");
	}

	public M setReviewAt(java.util.Date reviewAt) {
		set("reviewAt", reviewAt);
		return (M)this;
	}
	
	public java.util.Date getReviewAt() {
		return get("reviewAt");
	}

	public M setImagePath(java.lang.String imagePath) {
		set("imagePath", imagePath);
		return (M)this;
	}
	
	public java.lang.String getImagePath() {
		return getStr("imagePath");
	}

	public M setRankId(java.lang.Integer rankId) {
		set("rankId", rankId);
		return (M)this;
	}
	
	public java.lang.Integer getRankId() {
		return getInt("rankId");
	}

	public M setReviewId(java.lang.Integer reviewId) {
		set("reviewId", reviewId);
		return (M)this;
	}
	
	public java.lang.Integer getReviewId() {
		return getInt("reviewId");
	}

	public M setTypeId(java.lang.Integer typeId) {
		set("typeId", typeId);
		return (M)this;
	}
	
	public java.lang.Integer getTypeId() {
		return getInt("typeId");
	}

}
