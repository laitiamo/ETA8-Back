package com.cxxy.eta8.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSubjectSchool<M extends BaseSubjectSchool<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setSubjectId(java.lang.Integer SubjectId) {
		set("SubjectId", SubjectId);
		return (M)this;
	}
	
	public java.lang.Integer getSubjectId() {
		return getInt("SubjectId");
	}

	public M setEconomicId(java.lang.Integer EconomicId) {
		set("EconomicId", EconomicId);
		return (M)this;
	}
	
	public java.lang.Integer getEconomicId() {
		return getInt("EconomicId");
	}

	public M setSocietyId(java.lang.Integer SocietyId) {
		set("SocietyId", SocietyId);
		return (M)this;
	}
	
	public java.lang.Integer getSocietyId() {
		return getInt("SocietyId");
	}

	public M setSourceId(java.lang.Integer SourceId) {
		set("SourceId", SourceId);
		return (M)this;
	}
	
	public java.lang.Integer getSourceId() {
		return getInt("SourceId");
	}

	public M setBelongId(java.lang.Integer BelongId) {
		set("BelongId", BelongId);
		return (M)this;
	}
	
	public java.lang.Integer getBelongId() {
		return getInt("BelongId");
	}

	public M setTypeId(java.lang.Integer TypeId) {
		set("TypeId", TypeId);
		return (M)this;
	}
	
	public java.lang.Integer getTypeId() {
		return getInt("TypeId");
	}

	public M setResearchId(java.lang.Integer ResearchId) {
		set("ResearchId", ResearchId);
		return (M)this;
	}
	
	public java.lang.Integer getResearchId() {
		return getInt("ResearchId");
	}

	public M setCooperateId(java.lang.Integer CooperateId) {
		set("CooperateId", CooperateId);
		return (M)this;
	}
	
	public java.lang.Integer getCooperateId() {
		return getInt("CooperateId");
	}

	public M setPaperType(java.lang.Integer PaperType) {
		set("PaperType", PaperType);
		return (M)this;
	}
	
	public java.lang.Integer getPaperType() {
		return getInt("PaperType");
	}

	public M setPaperId(java.lang.Integer PaperId) {
		set("PaperId", PaperId);
		return (M)this;
	}
	
	public java.lang.Integer getPaperId() {
		return getInt("PaperId");
	}

}