package at.tu.wmpm16.smartHomeManagement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;

import at.tu.wmpm16.beans.SplitAttachementsBean;
import at.tu.wmpm16.models.csv.ColdWaterConsumptionCSV;
import at.tu.wmpm16.models.csv.ElectricityConsumptionCSV;
import at.tu.wmpm16.models.csv.GasConsumptionCSV;
import at.tu.wmpm16.models.csv.HeatingConsumptionCSV;
import at.tu.wmpm16.models.csv.WarmWaterConsumptionCSV;
import at.tu.wmpm16.processor.WireTapLogMail;


public class GetEmailBackForChangeRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {

		DataFormat bindyCwc = new BindyCsvDataFormat(ColdWaterConsumptionCSV.class);
		DataFormat bindyEc = new BindyCsvDataFormat(ElectricityConsumptionCSV.class);
		DataFormat bindyGc = new BindyCsvDataFormat(GasConsumptionCSV.class);
		DataFormat bindyWwc = new BindyCsvDataFormat(WarmWaterConsumptionCSV.class);
		DataFormat bindyHc = new BindyCsvDataFormat(HeatingConsumptionCSV.class);

		errorHandler(deadLetterChannel("log:dead?level=ERROR")
				.useOriginalMessage().maximumRedeliveries(3).redeliveryDelay(5000));
		//		from("imaps://imap.gmail.com?username=wmpm16.10@gmail.com&password=wmpm1610&searchTerm.from=mmiheller@gmail.com&consumer.delay=1000&delete=false&unseen=true")
		//Speicherung funktioniert noch nicht
		
		from("imaps://imap.gmail.com?username=wmpm16.10@gmail.com&password=wmpm1610&consumer.delay=1000&delete=false&unseen=true&searchTerm.from=mmiheller@gmail.com")
		//.process(new ChangeMailProcessor())
		.split().method(SplitAttachementsBean.class, "splittAttachments")
		.log("Body: "+body().convertToString())
		.choice()
		.when(body().convertToString().contains("coldwaterconsumption"))
		.log("Hinein1")
		.unmarshal(bindyCwc)
		.to("jpa://at.tu.wmpm16.models.ColdWaterConsumption")
		.log("Saving new values to CWC")
		.to("log:email")
		.when(body().convertToString().contains("gasconsumption"))
		.log("Hinein2")
		.unmarshal(bindyGc)
		.to("jpa://at.tu.wmpm16.models.GasConsumption")
		.log("Saving new values to the GC")
		.to("log:email")
		.when(body().convertToString().contains("electricityconsumption"))
		.log("Hinein3")
		.unmarshal(bindyEc)
		.to("jpa://at.tu.wmpm16.models.ElectricityConsumption")
		.log("Saving new values to EC")
		.to("log:email")
		.when(body().convertToString().contains("heatingconsumption"))
		.log("Hinein4")
		.unmarshal(bindyHc)
		.to("jpa://at.tu.wmpm16.models.HeatingConsumption")
		.log("Saving new values to the HC")
		.to("log:email")
		.when(body().convertToString().contains("warmwaterconsumption"))
		.log("Hinein5")
		.unmarshal(bindyWwc)
		.to("jpa://at.tu.wmpm16.models.WarmWaterConsumption")
		.log("Saving new values to the WWc")
		.to("log:email")
		.log("Saving new values to the DB done")
		.endChoice()
		.end(); //end splitter


		from("log:email")
		.process(new Processor() {
			@SuppressWarnings("unchecked")
			public void process(Exchange exchange) throws Exception {
				List<MessageHistory> list = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
				for (MessageHistory m : list){
					System.out.println("Message History " + m.getNode().getShortName() + ": " + m.getNode().getLabel());
				}
			}
		})
		.wireTap("jms:mailAudit")
		.process(new WireTapLogMail());
	}

}
