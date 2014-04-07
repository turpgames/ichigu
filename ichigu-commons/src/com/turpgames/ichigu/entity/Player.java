package com.turpgames.ichigu.entity;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 7212302611654853700L;
	
	private int id;
	private String username;
	private String password;
	private String email;
	private String facebookId;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	private String profilePictureUrl;

	public String getFacebookProfilePictureUrl() {
		if (profilePictureUrl == null)
			profilePictureUrl = "http://graph.facebook.com/" + facebookId + "/picture?width=64&height=64";
		return profilePictureUrl;
	}

	@Override
	public String toString() {
		return username;
	}
}
