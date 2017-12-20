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
        MultiSafepayClient client = new MultiSafepayClient("", true);

    	JsonObject jsonResponse	= client.SetOrderRefund("1418980067470",100d,"EUR","Refund order");

    	System.out.println(jsonResponse);
    }
}
