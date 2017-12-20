package com.MultiSafepay;

import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

/**
 * @author Multisafepay.com
 */
public class GetOrderTest {

	@Test
	public void main() {
		MultiSafepayClient client = new MultiSafepayClient("", true);

		JsonObject jsonResponse = client.GetOrder("1418980067470");

		System.out.println(jsonResponse);
	}
}
