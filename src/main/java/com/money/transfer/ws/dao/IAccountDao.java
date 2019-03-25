package com.money.transfer.ws.dao;

import java.util.List;

import com.money.transfer.ws.model.Account;

public interface IAccountDao {

	Account createAccount(Account acc);

	Account deleteAccount(String id);

	Account getAccount(String id);

	Account updateAccount(Account account);

	List<Account> getAccountsList();

	
}
