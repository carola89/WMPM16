package smartHomeManagement.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Pattern;
import org.apache.camel.impl.DefaultMessage;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import smartHomeManagement.exceptions.MailException;

@Service
public class MailProcessor {

	private static final Logger log = LoggerFactory.getLogger(MailProcessor.class);
	// private static final Pattern ID_PATTERN =
	// Pattern.compile("(ID:)([0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12})");

	public void process(Exchange exchange) throws MailException {
		log.debug(ReflectionToStringBuilder.toString(exchange));

		Message in = exchange.getIn();
		String inMessageBody = in.getBody(String.class);
		String subject = in.getHeaders().get("Subject").toString();
		log.debug("\n\nMail body:\n" + inMessageBody + "\n");

		/**
		 * Transform message body to model object
		 */

	}
}