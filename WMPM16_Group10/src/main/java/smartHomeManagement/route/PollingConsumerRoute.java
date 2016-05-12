package smartHomeManagement.route;

import org.apache.camel.builder.RouteBuilder;

import smartHomeManagement.beans.PollingConsumerBean;

public class PollingConsumerRoute extends RouteBuilder {
	
	PollingConsumerBean pollingConsumerBean = new PollingConsumerBean();
	//pollingConsumerBean.setProducer(template);
	//pollingConsumerBean.setConsumer(consumer);
	
	 @Override
	    public void configure() throws Exception {
		 from("timer://foo?period=5000").bean(pollingConsumerBean, "someBusinessLogic");
		 from("activemq:queue.foo").to("mock:result");
	    }
	 


	
	  
	 
}
