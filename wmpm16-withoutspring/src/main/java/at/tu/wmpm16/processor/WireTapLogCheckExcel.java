package at.tu.wmpm16.processor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WireTapLogCheckExcel implements Processor{
	
	Logger logger = LoggerFactory.getLogger(WireTapLogCheckExcel.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		String date = new SimpleDateFormat("dd/MM/yyyy @ HH:mm:ss").format(Calendar.getInstance().getTime());
		logger.info("Check Excel WireTap: "+date.toString());
		logger.info("Message id: " + exchange.getIn().getMessageId());
		logger.info("Message body: " + exchange.getIn().getBody());
		
	}

}
