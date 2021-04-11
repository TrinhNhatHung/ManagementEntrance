package com.garagemanagement.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {

	private static final long serialVersionUID = 4404215421358974456L;

	private String fullname;

	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
			String fullname) {
		super(username, password, authorities);
		this.fullname = fullname;
	}

	public String getFullname() {
		return fullname;
	}

}
