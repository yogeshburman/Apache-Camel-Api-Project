package com.CamelApi.whatsappApi;


import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder ;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component ;

@Component
public class MyRoute extends RouteBuilder{
    
	@Autowired
	private Test_Service testService;

	@Override
	public void configure() throws Exception{
		
	restConfiguration().component("servlet").host("localhost").port(9090).bindingMode(RestBindingMode.auto);
		
		
		// get route 
		rest().get("/getUsers")
		.route()
		.bean(this,"getbody")
		.log("get message headers is ==========> ${headers}")
		.log("get body test =====> ${body}")
//		.to("activemq:queue:WA")
		;
		
		// post route
		rest().post("/addUsers")
		.route()
		.bean(this,"setbody")
		.log("message headers is ==========> ${headers}")
		.log("body test =====> ${body}");
		//.to("activemq:queue:WA")
		;
			
		}
	
	public void getbody(Exchange exchange) {
		exchange.getIn().setBody(testService.get_body());
	}
	
	public void setbody(Exchange exchange) {
		
		Map<String, Object>  body = exchange.getIn().getBody(HashMap.class);
		JSONObject json = new JSONObject(body);
		System.out.println("json object is =====> " + json);
		testService.add_body(json);
		exchange.getIn().setBody("Data Saved Successfully");
	}
	

		
}
