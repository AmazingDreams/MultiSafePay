package com.MultiSafepay;

import com.MultiSafepay.classes.GatewayInfo;
import com.MultiSafepay.classes.Order;
import com.MultiSafepay.classes.PaymentOptions;
import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

public class SetOrderDirectIdealQRTest {

	@Test
	public void main() {
    	java.util.Date date		= new java.util.Date();

		MultiSafepayClient client = new MultiSafepayClient("", true);

    	Order order = new Order();
    	order.setDirectIdealQR(
    			Long.toString(date.getTime()), 
    			"Product description", 
    			1000d,
    			"EUR",
    			new PaymentOptions("http://example.com/notify", "http://example.com/success", "http://example.com/failed"),
    			GatewayInfo.IdealQR(300)
    			
    	);
    	
    	JsonObject jsonResponse = client.createOrder(order);
    	System.out.println(jsonResponse);
    	
    	String qr_url = client.getQrUrl(jsonResponse);
    	System.out.println("QR Url: " + qr_url);
    }
}
