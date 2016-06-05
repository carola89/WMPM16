package at.tu.wmpm16.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplitAttachementsBean{

	Logger logger = LoggerFactory.getLogger(SplitAttachementsBean.class); 


	public Object splittAttachments(Exchange exchange) {
		logger.info("MailProcessor....");

		Map<String, DataHandler> attachments = exchange.getIn().getAttachments();

		// must use getAttachments to ensure attachments is initial populated
		if (attachments.isEmpty()) {
			return null;
		}

		// we want to provide a list of messages with 1 attachment per mail
		List<Message> answer = new ArrayList<Message>();;

		for (Map.Entry<String, DataHandler> entry : exchange.getIn().getAttachments().entrySet()) {
			final Message copy = exchange.getIn().copy();
			copy.getAttachments().clear();
			copy.getAttachments().put(entry.getKey(), entry.getValue());
			copy.setBody(entry.getKey());
			System.out.println("Attachment name: " +entry.getKey());
			answer.add(copy);
		}
		logger.info("MailProcessor end....");
		return answer;
	}

}
