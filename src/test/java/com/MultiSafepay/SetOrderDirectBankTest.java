package com.MultiSafepay;

/**
 * @author Multisafepay.com
 */
import com.MultiSafepay.classes.*;
import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

public class SetOrderDirectBankTest {

	@Test
	public void main() {
		java.util.Date date		= new java.util.Date();

		MultiSafepayClient client = new MultiSafepayClient("", true);

    	Order order = new Order();
    	order.setDirectBank(
    			Long.toString(date.getTime()), 
    			"Product description", 
    			1000d,
    			"EUR", 
    			GatewayInfo.DirectBank(
    					"John Doe", 
    					"Amsterdam", 
    					"NL",
    					"IBAN 999999999",
    					"NL")
    			
    	);
    	
    	JsonObject jsonResponse = client.createOrder(order);
    	System.out.println(jsonResponse);
	}
}
