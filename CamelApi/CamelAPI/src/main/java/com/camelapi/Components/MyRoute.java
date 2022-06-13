package com.camelapi.Components;
import org.apache.camel.BeanInject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder ;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component ;

@Component
public class MyRoute extends RouteBuilder{
    
	@Autowired
	private Test_Service testService;
	
	@BeanInject
	//@Autowired
	private TestProcessor processor;
	
	@Autowired
	private TestProcessor get_process;

	@Override
	public void configure() throws Exception{
		
	restConfiguration().component("servlet").host("localhost").port(9090).bindingMode(RestBindingMode.json);
		
		// get route 
		rest().get("/getUsers")
//		.produces(org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
//		.log(testService.temp_message)
		.route()
		.bean(this.testService ,"setbody")
		.log("message headers is ==========> ${headers}")
		.log("body test =====> ${body}");
		
		// post route
		rest().post("/addUsers")
//		.type(User.class)
//		.outType(User.class)
		.route()
		.process(processor);
//		.log("message headers is ==========> ${headers}")
//		.log("body test =====> ${body}");
//		.to("jms:queue:GUPSHUP");
		
		}
	
	public void setbody(Exchange exchange) {
		exchange.getIn().setBody(testService.get_body());
	}
		
}
