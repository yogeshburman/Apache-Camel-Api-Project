package com.camelapi.Components;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.util.JSONPObject;

@Component
public class TestProcessor implements Processor{

	@Autowired
	private Test_Service testService;
	
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		
		String body = exchange.getIn().getBody(String.class);
		testService.add_body("request body",body);
		
		System.out.println("body is ===========>" + body);
		
	}

}

