package com.CamelApi.whatsappApi;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

@Component
public class HittingGet extends RouteBuilder {

	@Autowired
	private Test_Service testService;
	
	@Override
	public void configure() throws Exception {
		
//		// this route automatic hitting get request
//		from("scheduler://mytimer?delay=20s")
//		.setHeader(Exchange.HTTP_METHOD, HttpMethods.GET)
//		.setHeader(Exchange.CONTENT_TYPE,constant(MediaType.APPLICATION_JSON_VALUE))
//		.marshal().json(JsonLibrary.Jackson)
//		.to("http://localhost:9090/getUsers")
//		.bean(this,"setbody1")
//		.log("My body is ===>> ${body}")
//		.to("activemq:queue:WA")
//		;
		
		
		
		// this route automatic hitting get request
				from("scheduler://mytimer?delay=20s")
				.setHeader(Exchange.HTTP_METHOD, HttpMethods.GET)
				.setHeader(Exchange.CONTENT_TYPE,constant(MediaType.APPLICATION_JSON_VALUE))
				.marshal().json(JsonLibrary.Jackson)
				.to("http://localhost:9090/getUsers")
				.bean(this,"setbody1")
				 .choice()
				 .when(simple("${header.type} == 'text' "))
				      .to("activemq:queue:WA_text")
				 .when(simple("${header.type} == 'image' "))
				      .to("activemq:queue:WA_image") 
				 .when(simple("${header.type} == 'video' "))
				      .to("activemq:queue:WA_video")      
				 .otherwise()
				      .to("activemq:queue:WA_error")
				    
				.log("My body is ===>> ${body}")
				.log("My headers is ===>  ${headers}")
				.to("activemq:queue:WA")
				;
		
	}
	
	public void setbody1(Exchange exchange) {
		  HashMap<String, Object> message = testService.get_body();
		  exchange.getIn().setBody(message);
		  System.out.println("Hitting Get body  == > " + message);
		  
		  // setting type on header
		  Document body = exchange.getIn().getBody(Document.class);
		  Configuration con = Configuration.defaultConfiguration().setOptions(Option.SUPPRESS_EXCEPTIONS);
		   DocumentContext documentCon = JsonPath.using(con).parse(body);
		   
		   String type = documentCon.read("payload.type",String.class);
		   exchange.getIn().setHeader("type", type);
		 //  System.out.println("geadedmknfiksn====> " + type);
		  
		}
	

	
	
	
	
	// for post json manually
//	public void setbody(Exchange exchange) {
//		Map<String, Object> headers = exchange.getIn().getHeaders();
//		String message = "{\"queId\":\"testMessages\",\"properties\":{\"module\":\"USER\",\"event\":\"EDIT\",\"action\":\"AFTER\",\"clientId\":\"61eae4e5ebe7a42ada073f9f\",\"wotEventId\":\"626e55dd900fb927d87a0dc1\",\"eventTimestamp\":1651398109000},\"data\":{\"modules\":\"USER\",\"event\":\"EDIT\",\"eventTimestamp\":1651398109000,\"eventSourceObject\":{\"createdAt\":1651384750000,\"createdBy\":\"62079af1af8f1215cf469b32\",\"description\":\"Pay Electricity bill today to avoid late fee charge\",\"bpm_processId\":\"698_WOT_Task\",\"bpm_assignmentId\":\"5355_698_WOT_Task_Task\",\"workspaceId\":\"62652067e241131e276b7a66\",\"car\":\"ferrari\",\"new_car\":\"indica\",\"thingId\":\"62652067e54a3e433a30f9b2\",\"userId\":\"620554eda572ff31aa5df2da\",\"userName\":\"Yogesh Burman\",\"new_user_Name\":\"abcd\",\"userInitials\":\"AMS\",\"new_userInitials\":\"AMS\",\"startTime\":1651384693287,\"dueTime\":1651989493287,\"stage\":{\"key\":\"pending\",\"value\":\"Pending\",\"description\":\"Pending\",\"status\":\"active\",\"lifecycle\":\"open\"},\"timelineStatus\":{\"key\":\"started\",\"value\":\"Started\",\"description\":\"Started\"},\"priorityLevel\":1,\"status\":\"active\",\"properties\":{\"thing_name\":\"Aman Corp\"},\"resources\":[],\"name\":\"Pay Electricity Bill\",\"hId\":\"T-4\",\"sId\":4,\"_id\":\"626e21ae900fb927d87a0d82\"},\"client\":{\"clientId\":\"61eae4e5ebe7a42ada073f9f\",\"name\":\"Taask Ai Private Limited\",\"database\":{\"name\":\"db_61eae4e5ebe7a42ada073f9f\"},\"config\":{\"stages\":[{\"key\":\"pending\",\"value\":\"Pending\",\"description\":\"Pending\",\"status\":\"active\",\"lifecycle\":\"open\"},{\"key\":\"waiting_for_customer\",\"value\":\"Waiting for customer\",\"description\":\"Waiting for customer\",\"status\":\"active\",\"lifecycle\":\"open\"},{\"key\":\"internal_wait\",\"value\":\"Internal Wait\",\"description\":\"Internal Wait\",\"status\":\"active\",\"lifecycle\":\"open\"},{\"key\":\"completed\",\"value\":\"Completed\",\"description\":\"Completed\",\"status\":\"active\",\"lifecycle\":\"closed\"},{\"key\":\"not_required\",\"value\":\"Not Required\",\"description\":\"Not Required\",\"status\":\"active\",\"lifecycle\":\"closed\"},{\"key\":\"on_hold\",\"value\":\"On Hold\",\"description\":\"On Hold\",\"status\":\"active\",\"lifecycle\":\"closed\"}],\"timeline\":[{\"key\":\"started\",\"value\":\"Started\",\"description\":\"Started\"},{\"key\":\"delayed\",\"value\":\"Delayed\",\"description\":\"Delayed\"},{\"key\":\"finished\",\"value\":\"Finished\",\"description\":\"Ended\"}],\"schedule-stages\":[{\"key\":\"not_started\",\"value\":\"Not Started\",\"description\":\"Pending\",\"status\":\"active\",\"lifecycle\":\"open\"},{\"key\":\"running\",\"value\":\"Running\",\"description\":\"Running\",\"status\":\"active\",\"lifecycle\":\"open\"},{\"key\":\"completed\",\"value\":\"Completed\",\"description\":\"Completed\",\"status\":\"active\",\"lifecycle\":\"closed\"},{\"key\":\"cancelled\",\"value\":\"Cancelled\",\"description\":\"Cancelled\",\"status\":\"active\",\"lifecycle\":\"closed\"}]}}}}";
//		JSONParser parser = new JSONParser();
//		try {
//			JSONObject body = (JSONObject) parser.parse(message);
//			exchange.getIn().setBody(body);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}


}
