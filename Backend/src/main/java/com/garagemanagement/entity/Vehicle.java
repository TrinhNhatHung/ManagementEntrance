package com.garagemanagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "vehicle")
public class Vehicle {
	
	@Id
	@Column(name = "number_plate")
	private String numberPlate;
	
	@Column(name = "car_manufacturer", nullable = false)
	private String carManufacturer;
	
	@Column(name = "name_vehicle", nullable = false)
	private String nameVehicle;
	
	@Column(name = "color", nullable = false)
	private String color;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
	private Set<History> listHistory = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name="person_id", nullable = false)
	private Person person;
	
	public Vehicle() {
		super();
	}

	public Vehicle(Builder builder) {
		this.numberPlate = builder.numberPlate;
		this.carManufacturer = builder.carManufacturer;
		this.person = builder.person;
		this.nameVehicle = builder.nameVehicle;
		this.color = builder.color;
	}

	public String getNumberPlate() {
		return numberPlate;
	}
	
	public String getCarManufacturer() {
		return carManufacturer;
	}

	public Person getPerson() {
		return person;
	}
	
	public String getNameVehicle() {
		return nameVehicle;
	}
	
	public String getColor() {
		return color;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}

	public void setCarManufacturer(String carManufacturer) {
		this.carManufacturer = carManufacturer;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setNameVehicle(String nameVehicle) {
		this.nameVehicle = nameVehicle;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String numberPlate;
		private String carManufacturer;
		private Person person;
		private String nameVehicle;
		private String color;

		public Builder numberPlate(String numberPlate) {
			this.numberPlate = numberPlate;
			return this;
		}
		
		public Builder carManufacturer(String carManufacturer) {
			this.carManufacturer = carManufacturer;
			return this;
		}
		
		public Builder person (Person person) {
			this.person = person;
			return this;
		}
		
		public Builder nameVehicle (String nameVehicle) {
			this.nameVehicle = nameVehicle;
			return this;
		}
		
		public Builder color (String color) {
			this.color = color;
			return this;
		}

		public Vehicle build() {
			return new Vehicle(this);
		}
	}
}
