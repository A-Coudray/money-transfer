package com.rvt.ws.revolut.test;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.rvt.ws.revolut.RevolutAccountServer;
import com.rvt.ws.revolut.model.Account;
import com.rvt.ws.revolut.model.RevolutTransaction;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RevolutApiTest {
	public static Account testAcc1 = new Account();
	public static Account testAcc2 = new Account();
	public static Account testAcc3 = new Account();

	private static Client client;
	 
	@BeforeAll
	public static void setup() {
		
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);
        clientConfig.register(JacksonJsonProvider.class);
        client = ClientBuilder.newClient(clientConfig);
		// Start the server
		
		String[] args = new String[0];
		
		RevolutAccountServer.main(args);
		
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
		
		sendCreateRequest(testAcc1);
		sendCreateRequest(testAcc2);
		sendCreateRequest(testAcc3);
		

		
		
	}
	
	@AfterAll 
	public static void cleanup() {
		// stop the server
		RevolutAccountServer.shutdown();
	}
	
	@Test
	public void createAccountTest() {
		
		
		Account testAcc = new Account();
		testAcc.setId("4");
		testAcc.setAccountBalance((long) 1000);
		testAcc.setEmail("test4@test.com");
		testAcc.setFirstname("testName4");
		testAcc.setSurname("testSurname4");
		
		Response resp = sendCreateRequest(testAcc);
		
		Account responseAcc = resp.readEntity(Account.class);
		
		assertEquals("test the account creation", 200, resp.getStatus());
		assertEquals("test the valid response", testAcc.getId(),responseAcc.getId());
		
	}
	
	@Test
	public void putAccountTest() {
		
		Account testAcc = new Account();
		testAcc.setId("5");
		testAcc.setAccountBalance((long) 1000);
		testAcc.setEmail("test5@test.com");
		testAcc.setFirstname("testName5");
		testAcc.setSurname("testSurname5");
		Response resp = sendCreateRequest(testAcc);
		assertEquals("test the account creation", 200, resp.getStatus());
		
		testAcc.setAccountBalance((long) 2000);
		
		Response resp2 = sendPutRequest(testAcc);
		Account responseAcc = resp2.readEntity(Account.class);
		assertEquals("test correct balance", testAcc.getAccountBalance(),responseAcc.getAccountBalance());
		
		
	}
	
	@Test
	public void putAccountNotFoundTest() {
		
		Account testAcc = new Account();
		testAcc.setId("10000");
		testAcc.setAccountBalance((long) 1000);
		testAcc.setEmail("test5@test.com");
		testAcc.setFirstname("testName5");
		testAcc.setSurname("testSurname5");
		
		Response resp = sendPutRequest(testAcc);
		assertEquals("test account not found on PUT", 404, resp.getStatus());
		
		
	}
	
	@Test
	public void getAccountTest() {
		
		Response resp = sendGetRequest(testAcc1.getId());
		
		Account res = resp.readEntity(Account.class);
		
		assertEquals("test the account get", 200, resp.getStatus());
		
		assertEquals("test the correct account was returned", testAcc1.getId(),res.getId());
		
	}
	
	@Test
	public void getAccountNotFoundTest() {
		
		Response resp = sendGetRequest("1000");
		
		
		assertEquals("test if 404 was correctly returned", 404, resp.getStatus());
		
	}
	
	@Test
	public void deleteAccountTest() {
		
		Account testAcc = new Account();
		testAcc.setId("6");
		testAcc.setAccountBalance((long) 1000);
		testAcc.setEmail("test6@test.com");
		testAcc.setFirstname("testName6");
		testAcc.setSurname("testSurname6");
		Response resp = sendCreateRequest(testAcc);
		assertEquals("test the account creation", 200, resp.getStatus());
		
		
		Response resp2 = sendDeleteRequest(testAcc.getId());
		Account responseAcc = resp2.readEntity(Account.class);
		assertEquals("test the account deletion", 200, resp2.getStatus());
		assertEquals("test account has been removed", testAcc.getId(),responseAcc.getId());
		
		
	}
	
	@Test
	public void deleteAccountNotFoundTest() {
		
		Account testAcc = new Account();
		testAcc.setId("1000");
		testAcc.setAccountBalance((long) 1000);
		testAcc.setEmail("test6@test.com");
		testAcc.setFirstname("testName6");
		testAcc.setSurname("testSurname6");
		
		Response resp2 = sendDeleteRequest(testAcc.getId());
		assertEquals("test account not found on Delete", 404, resp2.getStatus());
		
		
	}
	
	@Test 
	public void transactionTest() {
		
		RevolutTransaction tran = new RevolutTransaction();
		tran.setAmount((long) 100);
		tran.setSource("1");
		tran.setDestination("2");
		
		Response res = sendTransactionRequest(tran);
		assertEquals("test the account transation", 200, res.getStatus());
		
		Response resp1 = sendGetRequest(testAcc1.getId());
		
		Account result1 = resp1.readEntity(Account.class);
		
		assertEquals("test the account get", 200, resp1.getStatus());
		
		assertEquals("test the correct amount of money was returned", new Long (900) ,result1.getAccountBalance());
		
		Response resp2 = sendGetRequest(testAcc2.getId());
		
		Account result2 = resp2.readEntity(Account.class);
		
		assertEquals("test the account get", 200, resp2.getStatus());
		
		assertEquals("test the correct amount of money was returned", new Long (1100) ,result2.getAccountBalance());
	}
	
	@Test 
	public void transactionTestSourceUnknown() {
		
		RevolutTransaction tran = new RevolutTransaction();
		tran.setAmount((long) 100);
		tran.setSource("1000");
		tran.setDestination("2");
		
		Response res = sendTransactionRequest(tran);
		assertEquals("test the account transation", 404, res.getStatus());
		
		
	}
	
	@Test 
	public void transactionTestDestinationUnknown() {
		
		RevolutTransaction tran = new RevolutTransaction();
		tran.setAmount((long) 100);
		tran.setSource("1");
		tran.setDestination("2000");
		
		Response res = sendTransactionRequest(tran);
		assertEquals("test the account transation", 404, res.getStatus());
		
		
	}
	
	
	
	
	
	
    private static Response sendCreateRequest(Account accountInfo) {
        return client.target("http://localhost:8080/create/account").request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(accountInfo));
    }
    
    private Response sendGetRequest(String id) {
        return client.target("http://localhost:8080/accounts/"+id).request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE).get();
    }
    
    private Response sendPutRequest(Account accountInfo) {
        return client.target("http://localhost:8080/accounts/"+accountInfo.getId()).request(MediaType.APPLICATION_JSON_TYPE)
        		.accept(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(accountInfo));
    }
    
    private Response sendDeleteRequest(String id) {
    	  return client.target("http://localhost:8080/accounts/"+id).request(MediaType.APPLICATION_JSON_TYPE)
                  .accept(MediaType.APPLICATION_JSON_TYPE).delete();
    }
    
    private Response sendGetListRequest() {
        return client.target("http://localhost:8080/accounts").request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE).get();
    }
    
    private Response sendTransactionRequest(RevolutTransaction trans) {
        return client.target("http://localhost:8080/transactions/transfert").request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(trans));
    }
}
