package com.MultiSafepay;

import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

/**
 * @author Multisafepay.com
 */
public class GetGatewayTest {

	@Test
    public void main() {
        MultiSafepayClient client = new MultiSafepayClient("",true);

    	JsonObject jsonResponse = client.GetGateway("VISA");
    	System.out.println(jsonResponse);
    }
}