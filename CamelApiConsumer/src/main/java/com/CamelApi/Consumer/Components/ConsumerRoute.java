package com.CamelApi.Consumer.Components;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRoute extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
			
	// this route consuming message from Wa queue
		from("activemq:queue:WA_text")
		.log("Header Consuming ====> ${headers}")
		.log("Body Consuming text Files ====> ${body}")
		;
		
		// this route consuming message from Wa queue
				from("activemq:queue:WA_image")
				.log("Header Consuming ====> ${headers}")
				.log("Body Consuming image Files ====> ${body}")
				;
				
				// this route consuming message from Wa queue
				from("activemq:queue:WA_video")
				.log("Header Consuming ====> ${headers}")
				.log("Body Consuming video Files====> ${body}")
				;		
				
				
				// this route consuming message from Wa queue
				from("activemq:queue:WA_error")
				.log("Header Consuming ====> ${headers}")
				.log("Body Consuming different Files====> ${body}")
				;
	
	}
	

}
