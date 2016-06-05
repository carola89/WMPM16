package at.tu.wmpm16.smartHomeManagement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import at.tu.wmpm16.models.Customer;
import at.tu.wmpm16.processor.WireTapLogCheckExcel;
import at.tu.wmpm16.processor.WireTapLogFeeCheck;
import at.tu.wmpm16.processor.WireTapLogPolling;



//Um diesen Teil testen zu können, muss der SMPP Client gestartet werden ( SMPPSim/startsmppsim.bat ) 
public class FeeCheckRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {


		errorHandler(deadLetterChannel("log:dead?level=ERROR")
				.useOriginalMessage().maximumRedeliveries(3).redeliveryDelay(5000));

		from("jpa://at.tu.wmpm16.models.ColdWaterConsumption?consumeDelete=false&consumer.resultClass=at.tu.wmpm16.models.Customer&consumer.delay=1m&consumer.nativeQuery=select customer.* from customer, (select sn,s,v,location,smartmeternr,customer_id from (select sn,s,v from (SELECT sum(measuredValue) s,standardValue v,smartMeterNr sn FROM ColdWaterConsumption group by smartMeterNr,standardvalue) where s>v),smartmeter where smartmeternr = sn) where customer_id = customer.id")
		.process(new Processor() {

			@SuppressWarnings("unchecked")
			public void process(Exchange exchange) throws Exception {

				Customer c = (Customer) exchange.getIn().getBody();
				List<MessageHistory> list = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
				//Object custom = exchange.getIn().getHeaders();
				System.out.println(c.getName() + " dfdsfds " + c.getTelNr() + " " + exchange.getIn().getHeaders().keySet().iterator().next());
				for (MessageHistory m : list){
					System.out.println("Message History " + m.getNode().getShortName() + ": " + m.getNode().getLabel());
				}
				exchange.getOut().setBody("Dear " + c.getName() + ", you have exceeded your monthly consumption limit." );
				exchange.getOut().setHeader("CamelSmppDestAddr", c.getTelNr());;
			}
		})
		.wireTap("jms:cfeeCheckAudit")
		.process(new WireTapLogFeeCheck())
		.to("smpp://smppclient1@localhost:2775?password=password&enquireLinkTimer=3000&transactionTimer=5000&systemType=producer");

	}


}
