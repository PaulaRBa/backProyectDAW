package com.daw.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CreateAdvertiserDto {

	@NotEmpty
	private String name;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Pattern(regexp = "([a-zA-Z]{2})\\s*\\t*(\\d{2})\\s*\\t*(\\d{4})\\s*\\t*(\\d{4})\\s*\\t*(\\d{2})\\s*\\t*(\\d{10})",
			 message = "Formato no válido")
	private String accountNumber;
	
	
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
