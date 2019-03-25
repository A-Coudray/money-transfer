package com.money.transfer.ws.service;

import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.DELETE;
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.POST;
import org.rapidoid.annotation.PUT;
import org.rapidoid.annotation.Valid;
import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;

import com.money.transfer.ws.dao.AccountDao;
import com.money.transfer.ws.dao.DaoException;
import com.money.transfer.ws.model.Account;
import com.money.transfer.ws.model.BankTransaction;



/**
 * Controller class, exposes all apis
 * @author acoudray
 *
 */
@Controller
public class AccountTransactionRoutes {
	
	 private static final Logger LOGGER = LogManager.getLogger(AccountTransactionRoutes.class.getName());
	
	AccountDao accountDao = TransactionsProcessor.getInstance().getAccountDao();
	
	
	/**
	 * Api to create a new account
	 * @param account
	 * @param req
	 * @return
	 */
	@POST("/create/account")
	public Resp createAccount (@Valid Account account, Req req) {
		
		Resp resp = req.response();
		
		Account res = accountDao.createAccount(account);
		if (null == res) {
			LOGGER.info(String.format("Account %s already existing.", account.getId()));
			resp.code(HttpStatus.SC_CONFLICT);
			resp.result("Account already existing.");
		}
		else {
			LOGGER.info(String.format("New account created, id : %s", account.getId()));
			resp.code(HttpStatus.SC_OK);
			resp.result(res);
		}
		
		return resp;
	}
	
	
	
	/**
	 * api to get an account infos 
	 * @param id
	 * @return
	 */
	@GET("/accounts/{id}")
	public Account getAccount (String id) {
		
		return accountDao.getAccount(id);
	}
	
	/**
	 * Api to remove an account
	 * @param id
	 * @return
	 */
	@DELETE("/accounts/{id}") 
	public Account deleteAccount (String id) {
		return accountDao.deleteAccount(id);
	}
	
	/**
	 * Api to update an existing account
	 * @param account
	 * @return
	 */
	@PUT("/accounts/{id}") 
	public Resp updateAccount (@Valid Account account, Req req) {
		Resp resp = req.response();
        if (null == account.getAccountBalance()) {
        	LOGGER.error(String.format("Invalid account balance value for %s.", account.getId()));
        	resp.code(HttpStatus.SC_BAD_REQUEST);
        	resp.result("Invalid account balance value.");
        }
        else {
        	Account acc = accountDao.updateAccount(account);
        	if (null == acc) {
               	resp.code(HttpStatus.SC_NOT_FOUND);
            	resp.result("Account not found.");
        	}
        	else {
               	resp.code(HttpStatus.SC_OK);
            	resp.result(acc);
        	}
 
        }
		
		return resp;
	}
	
	/**
	 * Api to list the currently existing accounts
	 * @return
	 */
	@GET("/accounts")
	public List<Account> listAccounts () {
		return accountDao.getAccountsList();
	}
	
	@POST("/transactions/transfert")
	public Resp processTransfert(@Valid BankTransaction trans, Req req) {
		Resp resp = req.response();
		
		try {
			TransactionsProcessor.getInstance().transfertBetweenAccounts(trans.getSource(), trans.getDestination(), trans.getAmount());
			resp.code(HttpStatus.SC_OK);
			resp.result("Transaction completed successfully.");
		} catch (DaoException e) {
			LOGGER.error(String.format("Exception while processing the transaction : %s", e.getMessage()));
			resp.code(HttpStatus.SC_NOT_FOUND);
			resp.result(e.getMessage());
		}
		
		
		return resp;
		
	}
	
	

}
