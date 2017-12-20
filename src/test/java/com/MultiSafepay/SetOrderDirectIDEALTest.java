package com.MultiSafepay;

import com.MultiSafepay.classes.*;
import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

/**
 * @author Multisafepay.com
 */
public class SetOrderDirectIDEALTest {

	@Test
	public void main() {
    	java.util.Date date		= new java.util.Date();

		MultiSafepayClient client = new MultiSafepayClient("", true);

    	Order order = new Order();
    	order.setDirectIdeal(
    			Long.toString(date.getTime()), 
    			"Product description", 
    			1000, 
    			"EUR",
    			new PaymentOptions("http://example.com/notify", "http://example.com/success", "http://example.com/failed"),
    			GatewayInfo.Ideal("3151")
    			
    	);
    	
    	JsonObject jsonResponse 	= client.createOrder(order);
    	System.out.println(jsonResponse);
    	
    	String payment_url			= client.getPaymenUrl(jsonResponse);
    	System.out.println("Payment Url: " + payment_url);
    }
}
