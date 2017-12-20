package com.MultiSafepay;

import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

/**
 * @author Multisafepay.com
 */
public class GetGatewaysTest {

    @Test
    public void main() {
        MultiSafepayClient client = new MultiSafepayClient("",true);

        JsonObject jsonResponse = client.GetGateways();

        System.out.println(jsonResponse);
    }
}
