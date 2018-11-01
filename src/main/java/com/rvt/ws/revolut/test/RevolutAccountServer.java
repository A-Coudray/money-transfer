package com.rvt.ws.revolut.test;


import org.rapidoid.setup.App;


/**
 * Main class to start the server
 * @author acoudray
 *
 */
public class RevolutAccountServer {
	

    public static void main(String[] args) {

    	
    	App.bootstrap(args);
    	
    	
    }
    
    public static void shutdown () {
    	
    	App.shutdown();
    	
    }
    
    

}
