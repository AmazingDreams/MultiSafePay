package com.MultiSafepay;

import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

public class GetTransactionTest {

	@Test
	public void main() {
		MultiSafepayClient.init(true);

		JsonObject jsonResponse = MultiSafepayClient.GetTransaction("9941812537632491");

		System.out.println(jsonResponse);
	}
}
