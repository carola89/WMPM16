package at.tu.wmpm16.smartHomeManagement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;

import at.tu.wmpm16.beans.DeleteFilesAfterDropboxUpdate;
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

		 
		 from("jpa://at.tu.wmpm16.models.SmartMeterConsumptions?consumeDelete=false&consumer.delay=50000&consumer.query=select c from at.tu.wmpm16.models.SmartMeterConsumptions c")
		 	.split().method(SplitSmartMeterConsumptionsToSingleConsumptionsBean.class, "splitBody")
		 	.choice()
		 		.when(body().convertToString().contains("ColdWaterConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.ColdWaterConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyCwc)
		 			.to("file:c:/wmpm/file?fileName=coldwaterconsumption-${date:now:yyyyMMddhhmmss}.csv")
		 			.to("direct:log")
		 		.when(body().convertToString().contains("GasConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.GasConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyGc)
		 			.to("file:c:/wmpm/file?fileName=gasconsumption-${date:now:yyyyMMddhhmmss}.csv")
		 			.to("direct:log")
		 		.when(body().convertToString().contains("ElectricityConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.ElectricityConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyEc)
		 			.to("file:c:/wmpm/file?fileName=electricityconsumption-${date:now:yyyyMMddhhmmss}.csv")
		 			.to("direct:log")
		 		.when(body().convertToString().contains("HeatingConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.HeatingConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyHc)
		 			.to("file:c:/wmpm/file?fileName=heatingconsumption-${date:now:yyyyMMddhhmmss}.csv")
		 			.to("direct:log")
		 		.when(body().convertToString().contains("WarmWaterConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.WarmWaterConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyWwc)
		 			.to("file:c:/wmpm/file?fileName=warmwaterconsumption-${date:now:yyyyMMddhhmmss}.csv")
		 			.to("dropbox://put?accessToken=2SJnFMZYQSAAAAAAAAAACkmidQvXyHOdAnvBkDmMmcRCwAAuGUjPbLsHC1CJPhtl&clientIdentifier=wmpm16&uploadMode=add&localPath=c:\\wmpm\\file&remotePath=/consumptions")
		 			.bean(DeleteFilesAfterDropboxUpdate.class, "delete")
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
