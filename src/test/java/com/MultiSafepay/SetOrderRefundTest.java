package com.MultiSafepay;

import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

/**
 * @author Multisafepay.com
 */
public class SetOrderRefundTest {

	@Test
	public void main() {
    	MultiSafepayClient.init(true);

    	JsonObject jsonResponse	= MultiSafepayClient.SetOrderRefund("1418980067470",100,"EUR","Refund order");

    	System.out.println(jsonResponse);
    }
}
