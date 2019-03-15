package com.rest.api.ws;


import org.rapidoid.setup.App;


/**
 * Main class to start the server
 * @author acoudray
 *
 */
public class BankAccountServer {
	

    public static void main(String[] args) {

    	
    	App.bootstrap(args);
    	
    	
    }
    
    public static void shutdown () {
    	
    	App.shutdown();
    	
    }
    
    

}
