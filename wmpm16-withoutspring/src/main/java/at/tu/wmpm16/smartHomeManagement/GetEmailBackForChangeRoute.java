package at.tu.wmpm16.smartHomeManagement;

import org.apache.camel.builder.RouteBuilder;

import at.tu.wmpm16.processor.ChangeMailProcessor;


public class GetEmailBackForChangeRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		errorHandler(deadLetterChannel("log:dead?level=ERROR")
    			.useOriginalMessage().maximumRedeliveries(3).redeliveryDelay(5000));
		
		from("imaps://imap.gmail.com?username=wmpm16.10@gmail.com&password=wmpm1610&from=mmiheller@gmail.com&delete=false&unseen=true&consumer.delay=60000")
		.process(new ChangeMailProcessor()); 
	}

}
