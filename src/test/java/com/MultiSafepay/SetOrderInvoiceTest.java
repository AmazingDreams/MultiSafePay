package com.MultiSafepay;

import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

public class SetOrderInvoiceTest {

	@Test
	public void main() {
    	MultiSafepayClient.init(true);

    	JsonObject jsonResponse	= MultiSafepayClient.SetOrderInvoice("1418980067470","112345");

    	System.out.println(jsonResponse);
    }
}
