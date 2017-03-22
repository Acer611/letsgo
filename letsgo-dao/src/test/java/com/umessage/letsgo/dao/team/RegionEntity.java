package com.umessage.letsgo.dao.team;

import java.util.Date;


public class RegionEntity {
	private long areaid;
	private long countryid;
	private String areachnname;
	private String areachnshortname;

	public void setAreaid(long areaid) {
		this.areaid = areaid;
	}

	public void setCountryid(long countryid) {
		this.countryid = countryid;
	}

	public void setAreachnname(String areachnname) {
		this.areachnname = areachnname;
	}

	public void setAreachnshortname(String areachnshortname) {
		this.areachnshortname = areachnshortname;
	}

	public void setAreaengname(String areaengname) {
		this.areaengname = areaengname;
	}

	public void setAreaengshortname(String areaengshortname) {
		this.areaengshortname = areaengshortname;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setAreapinyinname(String areapinyinname) {
		this.areapinyinname = areapinyinname;
	}

	public void setAreaabname(String areaabname) {
		this.areaabname = areaabname;
	}

	public void setDisplayseq(long displayseq) {
		this.displayseq = displayseq;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public void setParentid(long parentid) {
		this.parentid = parentid;
	}

	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	public void setModifyuserid(String modifyuserid) {
		this.modifyuserid = modifyuserid;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

	private String areaengname;
	private String areaengshortname;
	private String alias;
	private String areapinyinname;
	private String areaabname;
	private long displayseq;
	private int lv;

	public long getAreaid() {
		return areaid;
	}

	public long getCountryid() {
		return countryid;
	}

	public String getAreachnname() {
		return areachnname;
	}

	public String getAreachnshortname() {
		return areachnshortname;
	}

	public String getAreaengname() {
		return areaengname;
	}

	public String getAreaengshortname() {
		return areaengshortname;
	}

	public String getAlias() {
		return alias;
	}

	public String getAreapinyinname() {
		return areapinyinname;
	}

	public String getAreaabname() {
		return areaabname;
	}

	public long getDisplayseq() {
		return displayseq;
	}

	public int getLv() {
		return lv;
	}

	public long getParentid() {
		return parentid;
	}

	public String getCreateuserid() {
		return createuserid;
	}

	public Date getCreatedatetime() {
		return createdatetime;
	}

	public String getModifyuserid() {
		return modifyuserid;
	}

	public Date getModifydatetime() {
		return modifydatetime;
	}

	public String getDeleteflag() {
		return deleteflag;
	}

	private long parentid;
	private String createuserid;
	private Date createdatetime;
	private String modifyuserid;
	private Date modifydatetime;
	private String deleteflag;



}
