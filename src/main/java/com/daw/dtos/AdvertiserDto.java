package com.daw.dtos;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class AdvertiserDto {

	private Long id;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate createDate;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate updateDate;
	@NotEmpty
	private String name;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Pattern(regexp = "([a-zA-Z]{2})\\s*\\t*(\\d{2})\\s*\\t*(\\d{4})\\s*\\t*(\\d{4})\\s*\\t*(\\d{2})\\s*\\t*(\\d{10})",
			 message = "formato no válido")
	private String accountNumber;
	
	
	public AdvertiserDto() {
		super();
	}
	public AdvertiserDto(Long id, LocalDate createDate, LocalDate updateDate, String name, String email, String accountNumber) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.name = name;
		this.email = email;
		this.accountNumber = accountNumber;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	public LocalDate getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	
}
