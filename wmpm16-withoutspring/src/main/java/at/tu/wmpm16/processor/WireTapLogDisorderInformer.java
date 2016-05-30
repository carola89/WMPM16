package at.tu.wmpm16.processor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WireTapLogDisorderInformer implements Processor{
	
	 Logger logger = LoggerFactory.getLogger(WireTapLogDisorderInformer.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String date = new SimpleDateFormat("dd/MM/yyyy @ HH:mm:ss").format(Calendar.getInstance().getTime());
		logger.info("Disorder Informer WireTap: "+date.toString());
		logger.info("Message id: " + exchange.getIn().getMessageId());
		logger.info("Message header: " + exchange.getIn().getHeader("id"));
		logger.info("Message body: " + exchange.getIn().getBody());
		
	}
}
