package smartHomeManagement.route;

import org.apache.camel.builder.RouteBuilder;

import smartHomeManagement.beans.PollingConsumerBean;

public class PollingConsumerRoute extends RouteBuilder {
	
	PollingConsumerBean pollingConsumerBean = new PollingConsumerBean();
	//pollingConsumerBean.setProducer(template);
	//pollingConsumerBean.setConsumer(consumer);
	
	 @Override
	    public void configure() throws Exception {
		 from("timer://foo?period=5000").to("sql:select * from GasConsumption").marshal().csv().to("file:target/out/gas");
		 from("timer://foo?period=5000").to("sql:select * from ColdWaterConsumption").marshal().csv().to("file:target/out/water");
		 from("timer://foo?period=5000").to("sql:select * from ElectricityConsumption").marshal().csv().to("file:target/out/electricity");
		 from("timer://foo?period=5000").to("sql:select * from HeatingConsumption").marshal().csv().to("file:target/out/heating");
		 from("timer://foo?period=5000").to("sql:select * from WarmWaterConsumption").marshal().csv().to("file:target/out/warmwater");
		 
	    }
	 


	
	  
	 
}
