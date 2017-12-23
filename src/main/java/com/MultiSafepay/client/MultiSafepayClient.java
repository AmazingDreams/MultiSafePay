package com.MultiSafepay.client;

import com.MultiSafepay.classes.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class MultiSafepayClient {

    private static String USER_AGENT = "Mozilla/5.0";
    private static String testApiUrl = "https://testapi.multisafepay.com/v1/json/";
    private static String apiUrl = "https://api.multisafepay.com/v1/json/";

    private String endPoint = "";

	public String apiKey;
	public boolean testMode;

	/**
	 * Initialises MspClient
     * @param apiKey Key provided by MultisafePay
	 * @param testMode
	 */
	public MultiSafepayClient(String apiKey, boolean testMode) {
		this.testMode = testMode;

		endPoint = (testMode)
                ? MultiSafepayClient.testApiUrl
                : MultiSafepayClient.apiUrl;
	}

	public JsonObject GetGateways() {
		return sendRequest("gateways");
	}
	
	public JsonObject GetGateway(String name) {
		return sendRequest("gateways/" + name);
	}
	
	public JsonObject GetIssuer(String name) {
		return sendRequest("issuers/" + name);
	}
	
	public JsonObject GetOrder(String order_id) {
		return sendRequest("orders/" + order_id);
	}
	
	public JsonObject GetTransaction(String transaction_id) {
		return sendRequest("transactions/" + transaction_id);
	}
	
	public JsonObject GetOrderTransactions(String order_id) {
		return sendRequest("orders/" + order_id +"/transactions");
	}
	
	public JsonObject SetOrderRefund(String order_id,Integer amount,String currency,String description) {
		Order order				= new Order();
    	order.currency			= currency;
    	order.amount			= amount;
    	order.description		= description;
    	
    	return sendRequest("orders/"+order_id+"/refunds","POST",order);
	}
	
	public JsonObject SetOrderInvoice(String order_id,String invoice_id) {
		Order order				= new Order();
		order.invoice_id		= invoice_id;
		
		return sendRequest("orders/"+order_id,"PATCH",order);
	}
	
	public JsonObject SetOrderShipping(String order_id,String ship_date,String carrier,String tracktrace_code) {
		Order order				= new Order();
    	order.ship_date			= ship_date;
    	order.carrier			= carrier;
    	order.tracktrace_code	= tracktrace_code;
    	
    	return sendRequest("orders/"+order_id,"PATCH",order);
	}
	
	public JsonObject createOrder(Order order) {
		return sendRequest("orders","POST",order);
	}

	/**
	 * Send Http request to Multisafepay
	 * @param url
	 * @param method
	 * @param mspObject
	 * @return
	 */
	public JsonObject sendRequest(String url, String method, Object mspObject) {
		JsonParser parser 		= new JsonParser();
		JsonObject jsonResponse = null;
		String _overrideMethod	= null;
		String jsonString		= null;
		if(mspObject !=null)
		{
			jsonString 	= JsonHandler(mspObject);
		}
		if(method == "")
		{
			method	= "GET";
		}
		
		if(method == "PATCH") //Workaround HttpURLConnection does not support all modern methods like PATCH
		{
			_overrideMethod = "PATCH";
			method 			= "POST";
		}
		
		method	= method.toUpperCase();
		try {
			
			System.out.println("Send Api Request: " + endPoint + url);
			
			URL obj = new URL(endPoint + url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();           
			con.setDoOutput(true);
			con.setInstanceFollowRedirects(false); 
			con.setRequestMethod(method);
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			if(_overrideMethod != null)
			{
				con.setRequestProperty("X-HTTP-Method-Override", _overrideMethod);
			}
			
			con.setRequestProperty("api_key", apiKey);
			con.setRequestProperty("charset", "utf-8");
			con.setUseCaches (false);
			
			if(method == "POST" || method == "PUT" || method == "PATCH")
			{
				con.setDoInput(true);
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
				con.setRequestProperty("Content-Length", "" + Integer.toString(jsonString.getBytes().length));
				DataOutputStream wr = new DataOutputStream(con.getOutputStream ());
				wr.writeBytes(jsonString);
				wr.flush();
				wr.close();
				System.out.println(method + " Data:");
				System.out.println(jsonString);
			}
			
			int status = con.getResponseCode();
			System.out.println("Http response code:");
			System.out.println(status);
			
			String inputLine;
			BufferedReader reader = null;
			if(status == 200)
			{
				 reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
			else
			{
				 reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			StringBuffer response = new StringBuffer();

			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			reader.close();
			
			jsonResponse = (JsonObject) parser.parse(response.toString());

			con.disconnect();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return jsonResponse;
	}
	
	public JsonObject sendRequest(String url, String method) {
		return sendRequest(url, method,null);
	}
	
	public JsonObject sendRequest(String url) {
		return sendRequest(url, "GET",null);
	}
	
	/**
	 * Helper 
	 * @param _object
	 * @return
	 */
	private String JsonHandler(Object _object) {
		Gson gson 			= new Gson();
		String jsonString	= gson.toJson(_object);
		
		Type type = new TypeToken<Map<String, Object>>() {}.getType();
		Map<String, Object> data = new Gson().fromJson(jsonString, type);

		for (Iterator<Map.Entry<String, Object>> it = data.entrySet().iterator(); it.hasNext();) {
		    Map.Entry<String, Object> entry = it.next();
		    if (entry.getValue() == null) {
		        it.remove();
		    } else if (entry.getValue().getClass().equals(ArrayList.class)) {
		        if (((ArrayList<?>) entry.getValue()).size() == 0) {
		            it.remove();
		        }
		    }
		}
		String json = new GsonBuilder().create().toJson(data);
		return json;
	}
	
	
	/**
	 * Parse payment_url from response for transactions with redirection or payment_url
	 * @param response
	 * @return
	 */
    public String getPaymenUrl(JsonObject response) {
    	String payment_url	= null;
    	try
    	{
    		JsonObject data   	= response.getAsJsonObject("data");
        	payment_url			= data.get("payment_url").getAsString();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.toString());
    	}
    	return payment_url;
    }
    
    /**
	 * Parse qr_url from response for transactions with redirection or payment_url
	 * @param response
	 * @return
	 */
    public String getQrUrl(JsonObject response) {
    	String qr_url	= null;
    	try
    	{
    		JsonObject data   	= response.getAsJsonObject("data");
    		qr_url				= data.get("qr_url").getAsString();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.toString());
    	}
    	return qr_url;
    }
}
