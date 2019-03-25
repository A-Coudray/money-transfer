package com.money.transfer.ws.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Account {
	
	@NotNull
	private String id;
	@NotNull
	private String email;
	@NotNull
	private String surname;
	@NotNull
	private String firstname;
	@NotNull
	private Long accountBalance;
	
		
	

	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public Long getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(Long accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	} 
	

}
