package com.money.transfer.ws;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.money.transfer.ws.dao.AccountDao;
import com.money.transfer.ws.model.Account;

public class AccountDaoTest {
	
	public static AccountDao dao;
	public static Account testAcc1 = new Account();
	public static Account testAcc2 = new Account();
	public static Account testAcc3 = new Account();
	
	@BeforeEach
	public void setup() {
		// Clean up the db before each step
		dao = new AccountDao();
		testAcc1.setId("1");
		testAcc1.setAccountBalance((long) 1000);
		testAcc1.setEmail("test1@test.com");
		testAcc1.setFirstname("testName1");
		testAcc1.setSurname("testSurname1");
		
		testAcc2.setId("2");
		testAcc2.setAccountBalance((long) 1000);
		testAcc2.setEmail("test2@test.com");
		testAcc2.setFirstname("testName2");
		testAcc2.setSurname("testSurname2");
		
		testAcc3.setId("3");
		testAcc3.setAccountBalance((long) 1000);
		testAcc3.setEmail("test3@test.com");
		testAcc3.setFirstname("testName3");
		testAcc3.setSurname("testSurname3");
		
		dao.createAccount(testAcc1);
		dao.createAccount(testAcc2);
		dao.createAccount(testAcc3);

		
	}
	
	@Test
	public void insertDataTest () {
		
		Account acc = new Account();
		acc.setId("4");
		acc.setAccountBalance((long) 1000);
		acc.setEmail("test4@test.com");
		acc.setFirstname("testName4");
		acc.setSurname("testSurname4");
		dao.createAccount(acc);
		
		assertEquals("check if the correct object has been inserted", acc, dao.getAccount("4"));
		
		
	}

}
