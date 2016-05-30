package at.tu.wmpm16.smartHomeManagement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;

import at.tu.wmpm16.Constants;
import at.tu.wmpm16.beans.SplitSmartMeterConsumptionsToSingleConsumptionsBean;
import at.tu.wmpm16.beans.TransformAllToCSV;
import at.tu.wmpm16.models.csv.ColdWaterConsumptionCSV;
import at.tu.wmpm16.models.csv.ElectricityConsumptionCSV;
import at.tu.wmpm16.models.csv.GasConsumptionCSV;
import at.tu.wmpm16.models.csv.HeatingConsumptionCSV;
import at.tu.wmpm16.models.csv.WarmWaterConsumptionCSV;
import at.tu.wmpm16.processor.WireTapLogPolling;

public class PollingConsumerRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		errorHandler(deadLetterChannel("log:dead?level=ERROR")
    			.useOriginalMessage().maximumRedeliveries(3).redeliveryDelay(5000));
		
		 DataFormat bindyCwc = new BindyCsvDataFormat(ColdWaterConsumptionCSV.class);
		 DataFormat bindyEc = new BindyCsvDataFormat(ElectricityConsumptionCSV.class);
		 DataFormat bindyGc = new BindyCsvDataFormat(GasConsumptionCSV.class);
		 DataFormat bindyWwc = new BindyCsvDataFormat(WarmWaterConsumptionCSV.class);
		 DataFormat bindyHc = new BindyCsvDataFormat(HeatingConsumptionCSV.class);
	 
		 from("jpa://at.tu.wmpm16.models.SmartMeterConsumptions?consumeDelete=false&consumer.delay=2m&consumer.query=select c from at.tu.wmpm16.models.SmartMeterConsumptions c")
		 	.split().method(SplitSmartMeterConsumptionsToSingleConsumptionsBean.class, "splitBody")
		 	.choice()
		 		.when(body().convertToString().contains("ColdWaterConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.ColdWaterConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyCwc)
		 			.to(Constants.filePath + "?fileName=coldwaterconsumption-${date:now:yyyyMMddhhmm}.csv")
		 			.to("direct:log")
		 		.when(body().convertToString().contains("GasConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.GasConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyGc)
		 			.to(Constants.filePath + "?fileName=gasconsumption-${date:now:yyyyMMddhhmm}.csv")
		 			.to("direct:log")
		 		.when(body().convertToString().contains("ElectricityConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.ElectricityConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyEc)
		 			.to(Constants.filePath + "?fileName=electricityconsumption-${date:now:yyyyMMddhhmm}.csv")
		 			.to("direct:log")
		 		.when(body().convertToString().contains("HeatingConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.HeatingConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyHc)
		 			.to(Constants.filePath + "?fileName=heatingconsumption-${date:now:yyyyMMddhhmm}.csv")
		 			.to("direct:log")
		 		.when(body().convertToString().contains("WarmWaterConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.WarmWaterConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyWwc)
		 			.to(Constants.filePath + "?fileName=warmwaterconsumption-${date:now:yyyyMMddhhmm}.csv")
		 			//.to("dropbox://put?accessToken=2SJnFMZYQSAAAAAAAAAACkmidQvXyHOdAnvBkDmMmcRCwAAuGUjPbLsHC1CJPhtl&clientIdentifier=wmpm16&uploadMode=add&localPath=" + filePathDropbox + "&remotePath=/consumptions")
		 			//.bean(DeleteFilesAfterDropboxUpdate.class, "delete")
		 			.to("direct:log")
		 	.endChoice()
		 	.end(); //end splitter
		 
		 from("direct:log")
		 .process(new Processor() {
			@SuppressWarnings("unchecked")
			public void process(Exchange exchange) throws Exception {
				List<MessageHistory> list = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
				for (MessageHistory m : list){
					System.out.println("Message History " + m.getNode().getShortName() + ": " + m.getNode().getLabel());
				}
			}
			})
		.wireTap("jms:consumptionAudit")
		.process(new WireTapLogPolling());
		 
		 	
//		 from("jpa://at.tu.wmpm16.models.ColdWaterConsumption?consumer.resultClass=at.tu.wmpm16.models.Customer&consumer.delay=200000&consumer.nativeQuery=select customer.* from customer, (select sn,s,v,location,smartmeternr,customer_id from (select sn,s,v from (SELECT sum(measuredValue) s,standardValue v,smartMeterNr sn FROM ColdWaterConsumption group by smartMeterNr,standardvalue) where s>v),smartmeter where smartmeternr = sn) where customer_id = customer.id")
//		 .process(new Processor() {
//				@SuppressWarnings("unchecked")
//				public void process(Exchange exchange) throws Exception {
//					System.out.println("Message History AAAA1 ");
//
//					Customer c = (Customer) exchange.getIn().getBody();
//					System.out.println("Message History AAAA ");
//					List<MessageHistory> list = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
//					Object custom = exchange.getIn().getHeaders();
//					System.out.println(c.getEmail() + " custom " + exchange.getIn().getHeaders().keySet().iterator().next());
//					for (MessageHistory m : list){
//						System.out.println("Message History AAAA " + m.getNode().getShortName() + ": " + m.getNode().getLabel());
//					}
//				}
//				}).to("smpp://smppclient1@localhost:2775?password=password&enquireLinkTimer=3000&transactionTimer=5000&systemType=producer");

//		 from("file:c:/wmpm/file")
//			 .bean(FileAsMailAttachementBean.class, "process")
//			 .setHeader("Subject", constant("testmail"))
//		 	 .setBody(constant("hello"))
//			 .to("smtps://smtp.gmail.com:587?username=wmpm16.10@gmail.com&password=wmpm1610&to=wmpm16.10@gmail.com");
		 
		 
//		from("jpa://at.tu.wmpm16.models.ColdWaterConsumption?consumeDelete=false&consumer.query=select o from at.tu.wmpm16.models.ColdWaterConsumption o")
//				.bean(new PollingConsumerBean(), "transform").aggregate(new FileAggregationStrategy()).header("id")
//				.completionSize(3)
//				.bean(new TransformToCSVBean()).marshal(bindy).log("transformed").
//				process(new Processor() {
//					@SuppressWarnings("unchecked")
//					public void process(Exchange exchange) throws Exception {
//						List<MessageHistory> list = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
//						for (MessageHistory m : list){
//							System.out.println("Message History " + m.getNode().getShortName());
//						}
//					}
//					})
//				.wireTap("jms:consumptionAudit")
//				.process(new WireTapLogPolling())
//				.setHeader("Subject", constant("Dear Customer, "))
//				.setHeader("From", constant("SmartHomeManagement"))
//				.to("file:C:/wmpm/file?fileName=company.csv");
		
//		from("log:dead?level=ERROR").to("mock:logger"); //---> DeadLetterChannel-TestLOG Output
		
		// aggregate(new FileAggregationStrategy())
		// from("jpa://at.tu.wmpm16.models.ColdWaterConsumption?consumer.query=select
		// o from at.tu.wmpm16.models.ColdWaterConsumption o").process(new
		// PollingConsumerBean()).marshal().csv().to("file:c:/wmpm/file");
	}
}
