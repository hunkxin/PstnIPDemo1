package com.yike.DAO;

public class DbContent {
	private String conference_id;
	private String caller_name;
	private String caller_password;
	private String uuid;
	private String isused;
	public String getConference_id() {
		return conference_id;
	}
	public void setConference_id(String conference_id) {
		this.conference_id = conference_id;
	}
	public String getCaller_name() {
		return caller_name;
	}
	public void setCaller_name(String caller_name) {
		this.caller_name = caller_name;
	}
	public String getCaller_password() {
		return caller_password;
	}
	public void setCaller_password(String caller_password) {
		this.caller_password = caller_password;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getIsused() {
		return isused;
	}
	public void setIsused(String isused) {
		this.isused = isused;
	}
	public DbContent(String conference_id, String caller_name, String caller_password, String isused) {
		//super();
		this.conference_id = conference_id;
		this.caller_name = caller_name;
		this.caller_password = caller_password;
		this.isused = isused;
	}
	
}
