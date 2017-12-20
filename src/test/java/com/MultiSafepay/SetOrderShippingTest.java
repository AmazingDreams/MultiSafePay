package com.MultiSafepay;

import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

/**
 * @author Multisafepay.com
 */
public class SetOrderShippingTest {

	@Test
	public void main() {
    	//Initialises MspClient
    	MultiSafepayClient.init(true);
    	
    	JsonObject jsonResponse	= MultiSafepayClient.SetOrderShipping("1418980067470", "16-12-2014", "UPS", "123456");

    	System.out.println(jsonResponse);
    }
}
