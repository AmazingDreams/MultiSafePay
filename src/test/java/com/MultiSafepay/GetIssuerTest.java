package com.MultiSafepay;

import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

/**
 * @author Multisafepay.com
 */
public class GetIssuerTest {

	@Test
	public void main() {
		MultiSafepayClient.init(true);

		JsonObject jsonResponse = MultiSafepayClient.GetIssuer("ideal");

		System.out.println(jsonResponse);
	}
}
