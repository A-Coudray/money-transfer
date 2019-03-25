package com.money.transfer.ws.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class BankTransaction {
	
	@NotNull
	private String source;
	
	@NotNull
	private String destination;
	
	@NotNull
	private Long amount;



	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	

}
