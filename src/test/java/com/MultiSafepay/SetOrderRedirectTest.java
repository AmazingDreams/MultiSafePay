package com.MultiSafepay;

import com.MultiSafepay.classes.*;
import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

/**
 * @author Multisafepay.com
 */

public class SetOrderRedirectTest {

	@Test
	public void main() {
    	java.util.Date date		= new java.util.Date();
    	
    	MultiSafepayClient.init(true);
    	
    	Order order = new Order();
    	order.setRedirect(
    			Long.toString(date.getTime()), 
    			"Product description", 
    			1000, 
    			"EUR", 
    			new PaymentOptions(
    					"http://example.com/notify", 
    					"http://example.com/success", 
    					"http://example.com/failed")
    	);
    	
    	order.customer 				= new Customer();
    	order.customer.first_name	= "John";
    	order.customer.last_name	= "Doe";
    	order.customer.address1		= "Kraanspoor 39";
    	order.customer.zip_code		= "1033SC";
    	order.customer.city			= "Amsterdam";
    	order.customer.country		= "NL";
    	
    	JsonObject jsonResponse 	= MultiSafepayClient.createOrder(order);
    	System.out.println(jsonResponse);
    	
    	String payment_url			= MultiSafepayClient.getPaymenUrl(jsonResponse);
    	System.out.println(payment_url);
    }
}
