package com.garagemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	@JsonIgnore
	private String password;
	
	@Column(name="fullname")
	private String fullName;
	
	@Column(name="role")
	private String role;
	
	public User() {

	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFullName() {
		return fullName;
	}

	public String getRole() {
		return role;
	}

	public User(Builder builder) {
		this.username = builder.username;
		this.password = builder.password;
		this.fullName = builder.fullName;
		this.role = builder.role;
	}
	
	public static Builder builder () {
		return new Builder();
	}
	
	public static class Builder {
		private String username;
		private String password;
		private String fullName;
		private String role;
		
		public Builder username (String username) {
			this.username = username;
			return this;
		}
		
		public Builder password (String password) {
			this.password = password;
			return this;
		}
		
		public Builder fullName (String fullName) {
			this.fullName = fullName;
			return this;
		}
		
		public Builder role (String role) {
			this.role = role;
			return this;
		}
		
		public User build () {
			return new User(this);
		}
	}
}
