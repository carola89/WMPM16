package at.tu.wmpm16.processor;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeMailProcessor implements Processor{

	Logger logger = LoggerFactory.getLogger(ChangeMailProcessor.class);  


	@Override
	public void process(Exchange exchange) throws Exception {	
		logger.info("MailProcessor....");

		Map<String, DataHandler> attachments = exchange.getIn().getAttachments();
		
		System.out.println(exchange.getIn().getHeader("From"));
		System.out.println(exchange.getIn().getHeader("Subject"));
		System.out.println(exchange.getIn().getBody());
		
		logger.info("After reading attachments....");
		if (attachments.size() > 0) {
			System.out.println("In if hinein " + attachments.size());
			for (String name : attachments.keySet()) {

				DataHandler dh = attachments.get(name);
				System.out.println("got the DataHandler " + dh.toString());
				String filename = dh.getName();
				System.out.println(name);
				
				String data = exchange.getContext().getTypeConverter().convertTo(String.class, dh.getInputStream());
				String newName = "";
				if(name.contains("coldwaterconsumption")){
					newName = "coldwaterconsumption";
				}else if(name.contains("warmwaterconsumption")){
					newName = "warmwaterconsumption";
				}else if(name.contains("electricityconsumption")){
					newName = "electricityconsumption";
				}else if(name.contains("gasconsumption")){
					newName = "gasconsumption";
				}else if(name.contains("heatingconsumption")){
					newName = "heatingconsumption";
				}
				
				FileWriter fw = new FileWriter("./wmpm/back/"+newName+".csv");
				PrintWriter out = new PrintWriter(fw);
				out.print(data);
				out.flush();
				out.close();
				fw.close();       

				 
			}
		}
		logger.info("MailProcessor complete");  
	}
}
