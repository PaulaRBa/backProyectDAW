package com.daw.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
//Para que se generen automáticamente las fechas de creación y actualización
@EntityListeners(AuditingEntityListener.class)
@Table(name = "advertisers")
public class Advertiser {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@CreatedDate
	@Column(name = "create_date", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate createDate;
	
	@LastModifiedDate
	@Column(name = "update_date")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate updateDate;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	@Column(name = "email", length = 128, nullable = false)
	private String email;
	
	@Column(name = "account_number", length = 32, nullable = false)
	private String accountNumber;
	
	
	public Advertiser() {
		super();
	}	
	public Advertiser(Long id, LocalDate createDate, LocalDate updateDate, String name, String email,
			String accountNumber) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.name = name;
		this.email = email;
		this.accountNumber = accountNumber;
	}
	public Advertiser(String name, String email, String accountNumber) {
		super();
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
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Advertiser other = (Advertiser) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}
