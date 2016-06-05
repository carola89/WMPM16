package at.tu.wmpm16.smartHomeManagement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import at.tu.wmpm16.beans.FileAsMailAttachementBean;
import at.tu.wmpm16.models.Customer;
import at.tu.wmpm16.processor.WireTapLogDisorderInformer;

public class InformingDisordersRoute  extends RouteBuilder {


	@Override
	public void configure() throws Exception {
		
		errorHandler(deadLetterChannel("log:dead?level=ERROR")
    			.useOriginalMessage().maximumRedeliveries(5).redeliveryDelay(5000));
		
		
		from("file://../wmpm?fileName=disorder.rtf&noop=true")
		 .bean(FileAsMailAttachementBean.class, "process")
		 .setHeader("Subject", constant("Information mail about Disorder"))
		 .setHeader("From", constant("wmpm16.10@gmail.com"))
	 	 .setBody(constant("Dear Operator, " + "\n" + "we have a disorder. " + "\n" + "Best regards, Smart home"))
	 	 .multicast()
		 .to("smtps://smtp.gmail.com?username=wmpm16.10@gmail.com&password=wmpm1610&to=netzwerkbetreiber@gmail.com", "smtps://smtp.gmail.com?username=wmpm16.10@gmail.com&password=wmpm1610&to=kraftwerkbetreiber@gmail.com")
		 .log("Email done Disorder");
		
		
		from("timer://OperatorDisorderInfrmr?fixedRate=true&period=3m") //SENDING MAILS TO CUSTOMERS
		.process(new Processor() {
			@SuppressWarnings("unchecked")
			public void process(Exchange exchange) throws Exception {
				List<MessageHistory> list = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
				for (MessageHistory m : list){
					System.out.println("Message History " + m.getNode().getShortName() + ": " + m.getNode().getLabel());
				}
				exchange.getOut().setHeader("customers", "direct:customer1,direct:customer2,direct:customer3");
			}
			})
        .to("direct:customers")
        .log("RECIPIENT LIST disorder set customers");
		
		from("direct:customers")
		.process(new Processor() {
			@SuppressWarnings("unchecked")
			public void process(Exchange exchange) throws Exception {
				List<MessageHistory> list = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
				for (MessageHistory m : list){
					System.out.println("Message History " + m.getNode().getShortName() + ": " + m.getNode().getLabel());
				}
			}
			})
		.wireTap("jms:disorderAudit")
		.process(new WireTapLogDisorderInformer())
        .recipientList(header("customers"));
         
        from("direct:customer1")
        .setHeader("Subject", constant("Information mail about Disorder"))
		 .setHeader("From", constant("wmpm16.10@gmail.com"))
	 	 .setBody(constant("Dear Customer 1, " + "\n" + "we have a disorder. Sorry. " + "\n" + "Best regards, Smart home"))
	 	 .to("smtps://smtp.gmail.com?username=wmpm16.10@gmail.com&password=wmpm1610&to=wmpm16.10@gmail.com")
		 .log("Email done Disorder Customer1");
         
        from("direct:customer2")
        .setHeader("Subject", constant("Information mail about Disorder"))
		 .setHeader("From", constant("wmpm16.10@gmail.com"))
	 	 .setBody(constant("Dear Customer 2, " + "\n" + "we have a disorder. Sorry. " + "\n" + "Best regards, Smart home"))
	 	 .to("smtps://smtp.gmail.com?username=wmpm16.10@gmail.com&password=wmpm1610&to=wmpm16.10@gmail.com")
		 .log("Email done Disorder Customer2");
         
        from("direct:customer3")
        .setHeader("Subject", constant("Information mail about Disorder"))
		 .setHeader("From", constant("wmpm16.10@gmail.com"))
	 	 .setBody(constant("Dear Customer 3, " + "\n" + "we have a disorder. Sorry. " + "\n" + "Best regards, Smart home"))
	 	 .to("smtps://smtp.gmail.com?username=wmpm16.10@gmail.com&password=wmpm1610&to=wmpm16.10@gmail.com")
		 .log("Email done Disorder Customer3");
        
        
        from("timer://DisorderCustomerEnricher?fixedRate=true&period=1m")
        .pollEnrich("jpa://at.tu.wmpm16.models.Customer?consumeDelete=false&consumer.resultClass=at.tu.wmpm16.models.Customer&consumer.delay=3s&consumer.nativeQuery=select customer.* from customer ")
    	.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				Customer c = (Customer) exchange.getIn().getBody();
				List<MessageHistory> list = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
				Object custom = exchange.getIn().getHeaders();
				System.out.println("CONTENT ENRICHER "+c.getEmail() + " got the email " + exchange.getIn().getHeaders().keySet().iterator().next());
				for (MessageHistory m : list){
					System.out.println("Message History Enricher " + m.getNode().getShortName() + ": " + m.getNode().getLabel());
				}
				
			}
		})
    	.wireTap("jms:disorderEnricherAudit")
		.process(new WireTapLogDisorderInformer())
        .to("direct:customerHotline");
        
        from("direct:customerHotline")
        .wireTap("jms:disorderEnricherAudit")
		.process(new WireTapLogDisorderInformer())
        .log("CONTENT ENRICHED BY THE HOTLINE");
	}

}
