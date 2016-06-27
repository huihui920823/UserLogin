package com.huihui.domain;

public class User {
	public User() {
		super();
	}
	public User(int id, String username, String email, int grade, String passwd) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.grade = grade;
		this.passwd = passwd;
	}
	public int id;
	public String username;
	public String email;
	public int grade;
	public String passwd;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	

}
