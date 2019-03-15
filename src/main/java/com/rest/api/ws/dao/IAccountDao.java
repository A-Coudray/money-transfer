package com.rest.api.ws.dao;

import java.util.List;

import com.rest.api.ws.model.Account;

public interface IAccountDao {

	Account createAccount(Account acc);

	Account deleteAccount(String id);

	Account getAccount(String id);

	Account updateAccount(Account account);

	List<Account> getAccountsList();

	
}
