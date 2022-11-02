package com.tradez.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class User extends Auditable<String>{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=30,unique=true,nullable=false)
	private String username;
	
	@Column(length=50,unique=true,nullable=false)
	private String email;
	
	@Column(length=64)
	private String password;
	
	@Transient
	private String confirmPassword;
	
	private String postalCode;
	
	@Column(length=512)//default is too short
	private String description;
	
}
