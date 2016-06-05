package at.tu.wmpm16.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class WireTapLogMail implements Processor {
	
	Logger logger = LoggerFactory.getLogger(WireTapLogMail.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        String content = exchange.getIn().getBody(String.class);
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

      
        logger.info("Logging Email exchange on: " + date + "\n");
        logger.info("With the subject: " + exchange.getIn().getHeader("Subject").toString() + "\n");
        logger.info("From sender: " + exchange.getIn().getHeader("From").toString() + "\n");
        logger.info("To receiver: " + exchange.getIn().getHeader("To").toString() + "\n");
        logger.info("Containing the message: " + content);


    }
}
