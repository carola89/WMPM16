package at.tu.wmpm16.smartHomeManagement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;

import at.tu.wmpm16.beans.SplitAttachementsBean;
import at.tu.wmpm16.processor.ChangeMailProcessor;
import at.tu.wmpm16.processor.ColdWaterConsumptionMailToDbProcessor;
import at.tu.wmpm16.processor.ElectricityConsumptionMailToDbProcessor;
import at.tu.wmpm16.processor.GasConsumptionMailToDbProcessor;
import at.tu.wmpm16.processor.HeatingConsumptionMailToDbProcessor;
import at.tu.wmpm16.processor.WarmWaterConsumptionMailToDbProcessor;
import at.tu.wmpm16.processor.WireTapLogMail;


public class GetEmailBackForChangeRoute extends RouteBuilder{

	private static EntityManagerFactory factory;
	private static EntityManager em;
	
	@Override
	public void configure() throws Exception {
		
		factory =Persistence.createEntityManagerFactory("camel");
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		CsvDataFormat csv = new CsvDataFormat();

	    csv.setDelimiter(','); // Tabs
	    csv.setQuoteDisabled(true); // Otherwise single quotes will be doubled.

		errorHandler(deadLetterChannel("log:dead?level=ERROR")
				.useOriginalMessage().maximumRedeliveries(3).redeliveryDelay(5000));
		
		from("imaps://imap.gmail.com?username=wmpm16.10@gmail.com&password=wmpm1610&consumer.delay=1000&delete=false&unseen=true&searchTerm.from=mmiheller@gmail.com")
		.split().method(SplitAttachementsBean.class, "splittAttachments")
		.log("Body: "+body().convertToString())
		.choice()
		.when(body().convertToString().contains("coldwaterconsumption"))
		.log("Hinein1")
		.process(new ChangeMailProcessor())
		.log("Saving new values to CWC")
		.to("log:email")
		.when(body().convertToString().contains("gasconsumption"))
		.log("Hinein2")
		.process(new ChangeMailProcessor())
		.to("jpa://at.tu.wmpm16.models.GasConsumption")
		.log("Saving new values to the GC")
		.to("log:email")
		.when(body().convertToString().contains("electricityconsumption"))
		.log("Hinein3")
		.process(new ChangeMailProcessor())
		.to("jpa://at.tu.wmpm16.models.ElectricityConsumption")
		.log("Saving new values to EC")
		.to("log:email")
		.when(body().convertToString().contains("heatingconsumption"))
		.log("Hinein4")
		.process(new ChangeMailProcessor())
		.to("jpa://at.tu.wmpm16.models.HeatingConsumption")
		.log("Saving new values to the HC")
		.to("log:email")
		.when(body().convertToString().contains("warmwaterconsumption"))
		.log("Hinein5")
		.process(new ChangeMailProcessor())
		.to("jpa://at.tu.wmpm16.models.WarmWaterConsumption")
		.log("Saving new values to the WWc")
		.to("log:email")
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
		
		
		from("file://wmpm/back?fileName=coldwaterconsumption.csv&noop=true")
		   .unmarshal(csv)
		   .convertBodyTo(List.class)
		   .log("unmarshal CWC")
		      .process(new ColdWaterConsumptionMailToDbProcessor()).end();
		
		from("file://wmpm/back?fileName=warmwaterconsumption.csv&noop=true")
		   .unmarshal(csv)
		   .convertBodyTo(List.class)
		   .log("unmarshal WWC")
		      .process(new WarmWaterConsumptionMailToDbProcessor()).end();
		
		from("file://wmpm/back?fileName=electricityconsumption.csv&noop=true")
		   .unmarshal(csv)
		   .convertBodyTo(List.class)
		   .log("unmarshal EC")
		      .process(new ElectricityConsumptionMailToDbProcessor()).end();
		
		from("file://wmpm/back?fileName=gasconsumption.csv&noop=true")
		   .unmarshal(csv)
		   .convertBodyTo(List.class)
		   .log("unmarshal GC")
		      .process(new GasConsumptionMailToDbProcessor()).end();
		
		from("file://wmpm/back?fileName=heatingconsumption.csv&noop=true")
		   .unmarshal(csv)
		   .convertBodyTo(List.class)
		   .log("unmarshal HC")
		      .process(new HeatingConsumptionMailToDbProcessor()).end();
	}
	
	

}
