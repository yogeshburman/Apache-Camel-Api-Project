package com.CamelApi.whatsappApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.google.gson.JsonObject;

@org.springframework.stereotype.Service
public class Test_Service {

	//List<Object> temp_message = new ArrayList<Object>();
	HashMap<String, Object> message = new HashMap<String, Object>();
	
	//adding
	public void add_body(JSONObject json) {
		message.putAll(json);
		System.out.println("Message added Succesfully");
	}
	
	//getting 
	public HashMap<String, Object> get_body() {
		
		return message;
	}
	
}
