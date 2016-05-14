package smartHomeManagement.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class WireTapLogMail implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String sender = (String) exchange.getIn().getHeader("From");
        String receiver = (String) exchange.getIn().getHeader("To");
        String subject = (String) exchange.getIn().getHeader("Subject");
        String content = exchange.getIn().getBody(String.class);
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

        StringBuilder contentText = new StringBuilder();
        contentText.append("Logging Email exchange on: " + date + "\n");
        contentText.append("With the subject: " + subject + "\n");
        contentText.append("From sender: " + sender + "\n");
        contentText.append("To receiver: " + receiver + "\n");
        contentText.append("Containing the message: " + content);

        exchange.getIn().setBody(contentText.toString());

    }
}
