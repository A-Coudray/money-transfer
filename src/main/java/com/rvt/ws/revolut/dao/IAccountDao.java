package com.rvt.ws.revolut.dao;

import java.util.List;

import com.rvt.ws.revolut.model.Account;

public interface IAccountDao {

	Account createAccount(Account acc);

	Account deleteAccount(String id);

	Account getAccount(String id);

	Account updateAccount(Account account);

	List<Account> getAccountsList();

	
}
