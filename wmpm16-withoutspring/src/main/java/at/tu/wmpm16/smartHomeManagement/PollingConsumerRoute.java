package at.tu.wmpm16.smartHomeManagement;

import org.apache.camel.builder.RouteBuilder;

import at.tu.wmpm16.beans.PollingConsumerBean;

public class PollingConsumerRoute extends RouteBuilder {
	
	 @Override
	    public void configure() throws Exception {
//		 from("timer://foo?period=5000").to("sql:select * from GasConsumption").marshal().csv().to("file:target/out/gas");
//		 from("timer://foo?period=5000").to("sql:select * from ColdWaterConsumption").marshal().csv().to("file:target/out/water");
//		 from("timer://foo?period=5000").to("sql:select * from ElectricityConsumption").marshal().csv().to("file:target/out/electricity");
//		 from("timer://foo?period=5000").to("sql:select * from HeatingConsumption").marshal().csv().to("file:target/out/heating");
//		 from("timer://foo?period=5000").to("sql:select * from WarmWaterConsumption").marshal().csv().to("file:target/out/warmwater");
		 
		 from("jpa://at.tu.wmpm16.models.ColdWaterConsumption?consumer.query=select o from at.tu.wmpm16.models.ColdWaterConsumption o").bean(PollingConsumerBean.class, "doSomething");
//		 from("jpa://at.tu.wmpm16.models.ColdWaterConsumption?consumer.query=select o from at.tu.wmpm16.models.ColdWaterConsumption o").process(new PollingConsumerBean()).marshal().csv().to("file:c:/wmpm/file");
	    }
	 


	
	  
	 
}
