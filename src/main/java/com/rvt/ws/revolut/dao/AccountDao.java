package com.rvt.ws.revolut.dao;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvt.ws.revolut.model.Account;


public class AccountDao implements IAccountDao {
	
	private final ObjectMapper om = new ObjectMapper();
	private final ConcurrentMap<String, Account> accountMap;
	private static final Logger LOGGER = LogManager.getLogger(AccountDao.class.getName());
	
	/**
	 * In memory ConcurrentHashMap for the test
	 */
    public AccountDao() {
        this.accountMap = new ConcurrentHashMap<>();
    }
    
    
	/**
	 * 
	 * @param acc
	 * @return
	 */
    @Override
    public Account createAccount(Account acc) {
    	
    	
    	if (null != accountMap.putIfAbsent(acc.getId(), acc))  {
    		return null;
    	}
    	
    	else {
    		return acc;
    	}

    }
    
    /**
     * remove a user from the map
     * @param email
     * @return the deleted user
     */
    @Override
    public Account deleteAccount (String id) {
    	
    	return accountMap.remove(id);
    }
    
    /**
     * get a user from the map
     * @param email
     * @return the user
     */
    @Override
    public Account getAccount(String id) {
    	return accountMap.get(id);
    	
    }
    
    /**
     * Update a user 
     * @param user
     * @return the update user
     */
    @Override
    public Account updateAccount(Account account) {
        // the user didn't previously exist, return null
        if (null == accountMap.replace(account.getId(), account) ) {
            return null;
        }

        // return the updated user
        try {
			LOGGER.info(String.format("Account %s updated to %s", account.getId(), om.writeValueAsString(account)));
		} catch (JsonProcessingException e) {
			LOGGER.error(String.format("Unexpected Exception : %s", e.getMessage()));
			return null;
		}
        return account;
    }
    
    /**
     * Get the users in the map, sorted by email
     * @return the sorted users list
     */
    @Override
    public List<Account> getAccountsList (){
    	
        return accountMap.values()
                .stream()
                .sorted(Comparator.comparing((Account u) -> u.getId()))
                .collect(Collectors.toList());
    	
    }
    
    

}
