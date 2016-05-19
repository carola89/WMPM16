package at.tu.wmpm16.smartHomeManagement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;

import at.tu.wmpm16.beans.PollingConsumerBean;
import at.tu.wmpm16.beans.TransformToCSVBean;
import at.tu.wmpm16.models.ColdWaterConsumptionCSV;
import at.tu.wmpm16.processor.FileAggregationStrategy;

public class PollingConsumerRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// from("timer://foo?period=5000").to("sql:select * from
		// GasConsumption").marshal().csv().to("file:target/out/gas");
		// from("timer://foo?period=5000").to("sql:select * from
		// ColdWaterConsumption").marshal().csv().to("file:target/out/water");
		// from("timer://foo?period=5000").to("sql:select * from
		// ElectricityConsumption").marshal().csv().to("file:target/out/electricity");
		// from("timer://foo?period=5000").to("sql:select * from
		// HeatingConsumption").marshal().csv().to("file:target/out/heating");
		// from("timer://foo?period=5000").to("sql:select * from
		// WarmWaterConsumption").marshal().csv().to("file:target/out/warmwater");
		
		errorHandler(deadLetterChannel("log:dead?level=ERROR")
    			.useOriginalMessage().maximumRedeliveries(3).redeliveryDelay(5000));
		
		 DataFormat bindy = new
		 BindyCsvDataFormat(ColdWaterConsumptionCSV.class);

		from("jpa://at.tu.wmpm16.models.ColdWaterConsumption?consumeDelete=false&consumer.query=select o from at.tu.wmpm16.models.ColdWaterConsumption o")
				.bean(new PollingConsumerBean(), "transform").aggregate(new FileAggregationStrategy()).header("id")
				.completionSize(3).bean(new TransformToCSVBean()).marshal(bindy).log("transformed").
				process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						List<MessageHistory> list = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
						for (MessageHistory m : list){
							System.out.println("Message History " + m.getNode().getShortName());
						}
					}
					}).
				wireTap("jms:consumptionAudit").
				process(new Processor() {
					public void process(Exchange exchange) throws Exception {
							System.out.println("Polling Consumer WireTap: ");
							System.out.println("Message id: " + exchange.getIn().getMessageId());
							System.out.println("Message header: " + exchange.getIn().getHeader("id"));
							System.out.println("Message body: " + exchange.getIn().getBody());
					}
					})
				.to("file:C:/wmpm/file?fileName=out.csv");

		
//		from("log:dead?level=ERROR").to("mock:logger"); //---> DeadLetterChannel-TestLOG Output
		
		// aggregate(new FileAggregationStrategy())
		// from("jpa://at.tu.wmpm16.models.ColdWaterConsumption?consumer.query=select
		// o from at.tu.wmpm16.models.ColdWaterConsumption o").process(new
		// PollingConsumerBean()).marshal().csv().to("file:c:/wmpm/file");
	}

}
