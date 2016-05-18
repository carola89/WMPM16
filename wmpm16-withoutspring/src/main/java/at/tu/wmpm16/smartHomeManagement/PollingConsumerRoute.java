package at.tu.wmpm16.smartHomeManagement;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.spi.DataFormat;

import at.tu.wmpm16.beans.PollingConsumerBean;
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

		 DataFormat bindy = new
		 BindyCsvDataFormat(ColdWaterConsumptionCSV.class);

		from("jpa://at.tu.wmpm16.models.ColdWaterConsumption?consumeDelete=false&consumer.query=select o from at.tu.wmpm16.models.ColdWaterConsumption o")
				.bean(new PollingConsumerBean(), "transform").aggregate(new FileAggregationStrategy()).header("id")
				.completionSize(3).marshal().csv().log("transformed")
				.to("file:/Users/Patrick/wmpm/file?fileName=out.csv");

		// aggregate(new FileAggregationStrategy())
		// from("jpa://at.tu.wmpm16.models.ColdWaterConsumption?consumer.query=select
		// o from at.tu.wmpm16.models.ColdWaterConsumption o").process(new
		// PollingConsumerBean()).marshal().csv().to("file:c:/wmpm/file");
	}

}
