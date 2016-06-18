package at.tu.wmpm16.smartHomeManagement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;

import at.tu.wmpm16.Constants;
import at.tu.wmpm16.beans.DeleteFilesAfterDropboxUpdate;
import at.tu.wmpm16.beans.FileAsMailAttachementBean;
import at.tu.wmpm16.processor.WireTapLogContentFilter;

import org.apache.camel.Processor;

public class ContentFilterForCustomerRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
	    
		CsvDataFormat csv = new CsvDataFormat();
		
		errorHandler(deadLetterChannel("log:dead?level=ERROR")
    			.useOriginalMessage().maximumRedeliveries(3).redeliveryDelay(5000));

	    csv.setDelimiter(',');
	    csv.setQuoteDisabled(true);
	    
		 from(Constants.filePath + "?fileName=coldwaterconsumption-${date:now:yyyyMMddhhmm}.csv&noop=true")
		    .unmarshal(csv)
	        .convertBodyTo(List.class)
	        .process(new Processor() {

	            @SuppressWarnings("unchecked")
				@Override
	            public void process(Exchange exchange) throws Exception {
	                List<List<String>> data = (List<List<String>>) exchange.getIn().getBody();
	                for (List<String> line : data) {
	                   
	                	line.remove(0);
	                	line.remove(line.size()-1);
	                	
	                }
	            }
	        }).marshal(csv)
	        .wireTap("jms:filteringAudit")
			.process(new WireTapLogContentFilter())
			.setHeader("CamelFileName" , simple("coldwaterconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv"))
	        .to(Constants.filePath + "?fileName=coldwaterconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv")
	        .log("done.")
			.wireTap(Constants.ftpUrl)
	        .end();

		 //Mail coldwaterconsumptionCustomer
		 from(Constants.filePath + "?fileName=coldwaterconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv&noop=true")
		 .bean(FileAsMailAttachementBean.class, "process")
		 .setHeader("Subject", constant("Information mail ColdwaterConsumption"))
		 .setHeader("From", constant("wmpm16.10@gmail.com"))
	 	 .setBody(constant("Dear Customer, " + "\n" + "we have read your data in the database. Please find enclosed your data. " + "\n" + "Best regards, Smart home"))
		 //.to("velocity://mail-templates/mail.vm")
		 .to("smtps://{{mail.smtp.address}}?username={{mail.userName}}&password={{mail.password}}&to={{mail.to}}")
		 .log("Email done coldwaterconsumptionCustomer");
		 
		 from(Constants.filePath + "?fileName=gasconsumption-${date:now:yyyyMMddhhmm}.csv&noop=true")
		    .unmarshal(csv)
	        .convertBodyTo(List.class)
	        .process(new Processor() {

	            @SuppressWarnings("unchecked")
				@Override
	            public void process(Exchange exchange) throws Exception {
	                List<List<String>> data = (List<List<String>>) exchange.getIn().getBody();
	                for (List<String> line : data) {
	                   
	                	line.remove(0);
	                	line.remove(line.size()-1);
	                	
	                }
	            }
	        }).marshal(csv)
	        .wireTap("jms:filteringAudit")
			.process(new WireTapLogContentFilter())
			.setHeader("CamelFileName" , simple("gasconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv"))
	        .to(Constants.filePath + "?fileName=gasconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv")
	        .log("done.")
			.wireTap(Constants.ftpUrl)
	        .end();
		 
		 //Mail gasconsumptionCustomer
		 from(Constants.filePath + "?fileName=gasconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv&noop=true")
		 .bean(FileAsMailAttachementBean.class, "process")
		 .setHeader("Subject", constant("Information mail GasConsumption"))
		 .setHeader("From", constant("wmpm16.10@gmail.com"))
	 	 .setBody(constant("Dear Customer, " + "\n" + "we have read your data in the database. Please find enclosed your data. " + "\n" + "Best regards, Smart home"))
		 //.to("velocity://mail-templates/mail.vm")
		 .to("smtps://{{mail.smtp.address}}?username={{mail.userName}}&password={{mail.password}}&to={{mail.to}}")
		 .log("Email done gasconsumptionCustomer");
		 
		 from(Constants.filePath + "?fileName=electricityconsumption-${date:now:yyyyMMddhhmm}.csv&noop=true")
		    .unmarshal(csv)
	        .convertBodyTo(List.class)
	        .process(new Processor() {

	            @SuppressWarnings("unchecked")
				@Override
	            public void process(Exchange exchange) throws Exception {
	                List<List<String>> data = (List<List<String>>) exchange.getIn().getBody();
	                for (List<String> line : data) {
	                   
	                	line.remove(0);
	                	line.remove(line.size()-1);
	                	
	                }
	            }
	        }).marshal(csv)
	        .wireTap("jms:filteringAudit")
			.process(new WireTapLogContentFilter())
			.setHeader("CamelFileName" , simple("electricityconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv"))
	        .to(Constants.filePath + "?fileName=electricityconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv")
	        .log("done.")
			.wireTap(Constants.ftpUrl)
	        .end();
		 
		 //Mail electricityconsumptionCustomer
		 from(Constants.filePath + "?fileName=electricityconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv&noop=true")
		 .bean(FileAsMailAttachementBean.class, "process")
		 .setHeader("Subject", constant("Information mail ElectricityConsumption"))
		 .setHeader("From", constant("wmpm16.10@gmail.com"))
	 	 .setBody(constant("Dear Customer, " + "\n" + "we have read your data in the database. Please find enclosed your data. " + "\n" + "Best regards, Smart home"))
		 //.to("velocity://mail-templates/mail.vm")
		 .to("smtps://{{mail.smtp.address}}?username={{mail.userName}}&password={{mail.password}}&to={{mail.to}}")
		 .log("Email done electricityconsumptionCustomer");
		 
		 from(Constants.filePath + "?fileName=heatingconsumption-${date:now:yyyyMMddhhmm}.csv&noop=true")
		    .unmarshal(csv)
	        .convertBodyTo(List.class)
	        .process(new Processor() {

	            @SuppressWarnings("unchecked")
				@Override
	            public void process(Exchange exchange) throws Exception {
	                List<List<String>> data = (List<List<String>>) exchange.getIn().getBody();
	                for (List<String> line : data) {
	                   
	                	line.remove(0);
	                	line.remove(line.size()-1);
	                	
	                }
	            }
	        }).marshal(csv)
	        .wireTap("jms:filteringAudit")
			.process(new WireTapLogContentFilter())
			.setHeader("CamelFileName" , simple("heatingconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv"))
	        .to(Constants.filePath + "?fileName=heatingconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv")
			.wireTap(Constants.ftpUrl)
	        .log("done.")
	        .end();
		 
		 //Mail heatingconsumptionCustomer
		 from(Constants.filePath + "?fileName=heatingconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv&noop=true")
		 .bean(FileAsMailAttachementBean.class, "process")
		 .setHeader("Subject", constant("Information mail HeatingConsumption"))
		 .setHeader("From", constant("wmpm16.10@gmail.com"))
	 	 .setBody(constant("Dear Customer, " + "\n" + "we have read your data in the database. Please find enclosed your data. " + "\n" + "Best regards, Smart home"))
		 //.to("velocity://mail-templates/mail.vm")
		 .to("smtps://{{mail.smtp.address}}?username={{mail.userName}}&password={{mail.password}}&to={{mail.to}}")
		 .log("Email done heatingconsumptionCustomer");
		
		 from(Constants.filePath + "?fileName=warmwaterconsumption-${date:now:yyyyMMddhhmm}.csv&noop=true")
		    .unmarshal(csv)
	        .convertBodyTo(List.class)
	        .process(new Processor() {

	            @SuppressWarnings("unchecked")
				@Override
	            public void process(Exchange exchange) throws Exception {
	                List<List<String>> data = (List<List<String>>) exchange.getIn().getBody();
	                for (List<String> line : data) {
	                   
	                	line.remove(0);
	                	line.remove(line.size()-1);
	                	
	                }
	            }
	        }).marshal(csv)
	        .wireTap("jms:filteringAudit")
			.process(new WireTapLogContentFilter())
			.setHeader("CamelFileName" , simple("warmwaterconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv"))
	        .to(Constants.filePath + "?fileName=warmwaterconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv")
			.wireTap(Constants.ftpUrl)
	        .log("done.");
			//Mail warmwaterconsumptionCustomer
		 	from(Constants.filePath + "?fileName=warmwaterconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv&noop=true")
		 	.bean(FileAsMailAttachementBean.class, "process")
		 	.setHeader("Subject", constant("Information mail WarmwaterConsumption"))
		 	.setHeader("From", constant("wmpm16.10@gmail.com"))
		 	.setBody(constant("Dear Customer, " + "\n" + "we have read your data in the database. Please find enclosed your data. " + "\n" + "Best regards, Smart home"))
		 	//.to("velocity://mail-templates/mail.vm")
		 	.to("smtps://{{mail.smtp.address}}?username={{mail.userName}}&password={{mail.password}}&to={{mail.to}}")
		 	.log("Email done warmwaterconsumptionCustomer")
		 	.log("BEGINNING DROPBOX")
	        .to("dropbox://put?{{dropbox.auth.param}}&uploadMode=add&localPath=" + Constants.filePathDropbox + "&remotePath=/consumptions")
			.bean(DeleteFilesAfterDropboxUpdate.class, "delete")
	        .end();
	}
}
	
	


