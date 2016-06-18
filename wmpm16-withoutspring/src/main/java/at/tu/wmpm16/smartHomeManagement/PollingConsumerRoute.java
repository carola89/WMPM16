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
	 
		 from("jpa://at.tu.wmpm16.models.SmartMeterConsumptions?initialDelay=5s&consumeDelete=false&consumer.delay=2m&consumer.query=select c from at.tu.wmpm16.models.SmartMeterConsumptions c")
		 	.split().method(SplitSmartMeterConsumptionsToSingleConsumptionsBean.class, "splitBody")
		 	.choice()
		 		.when(body().convertToString().contains("ColdWaterConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.ColdWaterConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyCwc)
					.setHeader("CamelFileName" , simple("coldwaterconsumption-${date:now:yyyyMMddhhmm}.csv"))
					.log("!!!!wrote water consumption")
		 			.to(Constants.filePath + "?fileName=coldwaterconsumption-${date:now:yyyyMMddhhmm}.csv")
		 		//	.to("direct:log")
		 		.when(body().convertToString().contains("GasConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.GasConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyGc)
					.setHeader("CamelFileName" , simple("gasconsumption-${date:now:yyyyMMddhhmm}.csv"))
		 			.to(Constants.filePath + "?fileName=gasconsumption-${date:now:yyyyMMddhhmm}.csv")
		 			//.to("direct:log")
		 		.when(body().convertToString().contains("ElectricityConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.ElectricityConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyEc)
					.setHeader("CamelFileName" , simple("electricityconsumption-${date:now:yyyyMMddhhmm}.csv"))
		 			.to(Constants.filePath + "?fileName=electricityconsumption-${date:now:yyyyMMddhhmm}.csv")
		 			//.to("direct:log")
		 		.when(body().convertToString().contains("HeatingConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.HeatingConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyHc)
					.setHeader("CamelFileName" , simple("heatingconsumption-${date:now:yyyyMMddhhmm}.csv"))
		 			.to(Constants.filePath + "?fileName=heatingconsumption-${date:now:yyyyMMddhhmm}.csv")
		 			//.to("direct:log")
		 		.when(body().convertToString().contains("WarmWaterConsumption"))
		 			.to("jpa://at.tu.wmpm16.models.WarmWaterConsumption")
		 			.bean(TransformAllToCSV.class, "transform")
		 			.marshal(bindyWwc)
					.setHeader("CamelFileName" , simple("warmwaterconsumption-${date:now:yyyyMMddhhmm}.csv"))
		 			.to(Constants.filePath + "?fileName=warmwaterconsumption-${date:now:yyyyMMddhhmm}.csv")
		 	.endChoice()
		 	.end()
 			.to("direct:log"); //end splitter
		 
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
		.wireTap("ftp://speedtest.tele2.net/upload/")
		.wireTap("jms:consumptionAudit")
		.process(new WireTapLogPolling());
	}
}
