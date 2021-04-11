package com.garagemanagement.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="history")
public class History {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id", nullable = false)
	private Vehicle vehicle;
	
	@Column(name = "time", nullable = false)
	private Timestamp time;
		
	@Column(name = "isout", nullable = false,columnDefinition="tinyint(1) default 0")
	private boolean isOut;
	
	@Column(name = "image")
	private String image;
	
	public History() {
		super();
	}

	public History(Builder builder) {
		this.id = builder.id;
		this.vehicle = builder.vehicle;
		this.time = builder.time;
		this.isOut = builder.isOut;
		this.image = builder.image;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Timestamp getTime() {
		return time;
	}

	public boolean isOut() {
		return isOut;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getImage () {
		return image;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public void setOut(boolean isOut) {
		this.isOut = isOut;
	}
	
	public void setImage (String image) {
		this.image = image;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private Vehicle vehicle;
		private Timestamp time;
		private boolean isOut;
		private String image;

		public Builder vehicle(Vehicle vehicle) {
			this.vehicle = vehicle;
			return this;
		}

		public Builder time(Timestamp time) {
			this.time = time;
			return this;
		}

		public Builder isOut(boolean isOut) {
			this.isOut = isOut;
			return this;
		}
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder image (String image) {
			this.image = image;
			return this;
		}

		public History build() {
			return new History(this);
		}
	}
}
