package com.MultiSafepay;

import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

public class GetTransactionTest {

	@Test
	public void main() {
		MultiSafepayClient client = new MultiSafepayClient("", true);

		JsonObject jsonResponse = client.GetTransaction("9941812537632491");

		System.out.println(jsonResponse);
	}
}
