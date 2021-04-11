package com.garagemanagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class Person {
	
	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable = false, unique = true)
	private String name;
	
	@Column(name="phone_number", nullable = false)
	private String phoneNumber;
	
	@Column (name="gender", nullable = false)
	private String gender;
	
	@Column (name ="email", nullable = false)
	private String email;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
	private Set<Vehicle> vehicles = new HashSet<>();
	
	public Person() {
		super();
	}

	public Person (Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.phoneNumber = builder.phoneNumber;
		this.gender = builder.gender;
		this.email = builder.email;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getGender() {
		return gender;
	}

	public String getEmail() {
		return email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static Builder builder () {
		return new Builder();
	}
	
	public static class Builder {
		private Long id;
		private String name;
		private String phoneNumber;
		private String gender;
		private String email;
		
		public Builder id (Long id) {
			this.id = id;
			return this;
		}
		
		public Builder name (String name) {
			this.name = name;
			return this;
		}
		
		public Builder phoneNumber (String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}
		
		public Builder gender (String gender) {
			this.gender = gender;
			return this;
		}
		
		public Builder email (String email) {
			this.email = email;
			return this;
		}
		
		public Person build () {
			return new Person(this);
		}
	}
}
