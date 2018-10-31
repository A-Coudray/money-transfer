package com.rvt.ws.revolut.test.processor;

import com.rvt.ws.revolut.test.dao.AccountDao;
import com.rvt.ws.revolut.test.model.Account;

import exceptions.DaoException;

public class TransactionsProcessor {
	
	
	private AccountDao accountDao = new AccountDao();
    /**
     * Instantiates a new TransactionsProcessor.
     */
    private TransactionsProcessor() {
        // Private constructor
    }
    
    /**
     * The Class LoaderTransactionsProcessor to create a singleton
     */
    private static class LoaderTransactionsProcessor {

        /** The instance. */
        private static TransactionsProcessor instance = new TransactionsProcessor();

        /**
         * Instantiates a new loader TransactionsProcessor.
         */
        private LoaderTransactionsProcessor() {
        }
    }

    /**
     * singleton public method - method to call to create manager.
     * 
     * @return single instance of TransactionsProcessor
     */
    public static TransactionsProcessor getInstance() {
        return LoaderTransactionsProcessor.instance;
    }
    
    /**
     *  Method to manage money transfers between accounts
     * @param emitter
     * @param receiver
     * @param amount
     * @throws DaoException 
     */
    
    public void transfertBetweenAccounts(String idEmitter, String idReceiver, Long amount) throws DaoException {
    	
    	Account emitter = accountDao.getAccount(idEmitter);
    	Account receiver = accountDao.getAccount(idReceiver);
    	// check of the accounts exist
    	if (null == emitter) {
    		 throw new DaoException(String.format("Emitter account %s not found", idEmitter));
    	}
    	else if (null == receiver){
    		throw new DaoException(String.format("Receiver account %s not found", idReceiver));
    	} 
    	else {
    		// calculate the new balance and update the dao
        	Long emitterNewAccountBalance = emitter.getAccountBalance() - amount;
        	Long receiverNewAccountBalance = receiver.getAccountBalance() + amount;
        	emitter.setAccountBalance(emitterNewAccountBalance);
        	receiver.setAccountBalance(receiverNewAccountBalance);
        	accountDao.updateAccount(emitter);
        	accountDao.updateAccount(receiver);
        	
    	}
    	

    	
    }
    
    public AccountDao getAccountDao() {
    	return this.accountDao;
    }

}
