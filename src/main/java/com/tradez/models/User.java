package com.tradez.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
//@FieldsValueMatch(field = "plainPassword", fieldMatch = "plainConfirmPassword", message = "Password and confirm password do not match!")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@Email(message = "Please provide a valid email")
	@NotBlank(message = "Email must not be blank")
	private String email;

	@NotBlank(message = "Username must not be blank")
	private String username;

	private String password;

//	@ValidPassword
//	@Transient
	@NotBlank
	private String plainPassword;
//	@Transient
	@NotBlank
	private String confirmPassword;
//	@ValidPassword
//	@Transient
	private String plainConfirmPassword;

//	@Pattern(regexp = "^[ABCEGHJKLMNPRSTVXY]{1}\\d{1}[A-Z]{1} *\\d{1}[A-Z]{1}\\d{1}$", message = "Postal code must only include capital letter, numbers with or without space")
	private String postalCode;

	@Column(length = 512) // default is too short
	private String description;

	private LocalDateTime timestamp = LocalDateTime.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPlainConfirmPassword() {
		return plainConfirmPassword;
	}

	public void setPlainConfirmPassword(String plainConfirmPassword) {
		this.plainConfirmPassword = plainConfirmPassword;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
				+ ", plainPassword=" + plainPassword + ", confirmPassword=" + confirmPassword
				+ ", plainConfirmPassword=" + plainConfirmPassword + ", postalCode=" + postalCode + ", description="
				+ description + ", timestamp=" + timestamp + "]";
	}

}