package com.camelapi.Components;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

@org.springframework.stereotype.Service
public class Test_Service {

	JSONObject temp_message = new JSONObject();
	
	//adding
	public void add_body(String key, String value) {
		temp_message.put(key,value);
		System.out.println("Message added Succesfully");
	}
	
	//getting 
	public String get_body() {
		return temp_message.toJSONString();
	}
	
}
