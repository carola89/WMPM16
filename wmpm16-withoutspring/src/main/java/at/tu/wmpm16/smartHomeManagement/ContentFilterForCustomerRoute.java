package at.tu.wmpm16.smartHomeManagement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;

import at.tu.wmpm16.beans.DeleteFilesAfterDropboxUpdate;
import at.tu.wmpm16.beans.FileAsMailAttachementBean;
import at.tu.wmpm16.processor.MailProcessor;
import at.tu.wmpm16.processor.WireTapLogContentFilter;
import at.tu.wmpm16.processor.WireTapLogPolling;

import org.apache.camel.Processor;

public class ContentFilterForCustomerRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		CsvDataFormat csv = new CsvDataFormat();
		
		errorHandler(deadLetterChannel("log:dead?level=ERROR")
    			.useOriginalMessage().maximumRedeliveries(3).redeliveryDelay(5000));

	    csv.setDelimiter(',');
	    csv.setQuoteDisabled(true);
	    
	    //Windows
		 String filePath = new String("file:c:/wmpm/file");
		 String filePathDropbox = new String ("c:\\wmpm\\file");

		 //Mac
		// String filePath = new String("file:/Users/Patrick/wmpm/file");
		 //String filePathDropbox = new String ("/Users/Patrick/wmpm/file");
	    
		 from(filePath + "?fileName=coldwaterconsumption-${date:now:yyyyMMddhhmm}.csv&noop=true")
		    .unmarshal(csv)
	        .convertBodyTo(List.class)
	        .process(new Processor() {

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
	        .to(filePath + "?fileName=coldwaterconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv")
	        .log("done.")
	        .end();
		 
		 from(filePath + "?fileName=coldwaterconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv")
		 .bean(FileAsMailAttachementBean.class, "process")
		 .process(new MailProcessor())
		 .setHeader("Subject", constant("testmail"))
	 	 .setBody(constant("hello"))
		 .to("smtps://smtp.gmail.com:587?username=wmpm16.10@gmail.com&password=wmpm1610&to=wmpm16.10@gmail.com");
		 
		 from(filePath + "?fileName=gasconsumption-${date:now:yyyyMMddhhmm}.csv&noop=true")
		    .unmarshal(csv)
	        .convertBodyTo(List.class)
	        .process(new Processor() {

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
	        .to(filePath + "?fileName=gasconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv")
	        .log("done.")
	        .end();
		 
		 from(filePath + "?fileName=electricityconsumption-${date:now:yyyyMMddhhmm}.csv&noop=true")
		    .unmarshal(csv)
	        .convertBodyTo(List.class)
	        .process(new Processor() {

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
	        .to(filePath + "?fileName=electricityconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv")
	        .log("done.")
	        .end();
		 
		 from(filePath + "?fileName=heatingconsumption-${date:now:yyyyMMddhhmm}.csv&noop=true")
		    .unmarshal(csv)
	        .convertBodyTo(List.class)
	        .process(new Processor() {

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
	        .to(filePath + "?fileName=heatingconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv")
	        .log("done.")
	        .end();
		
		 from(filePath + "?fileName=warmwaterconsumption-${date:now:yyyyMMddhhmm}.csv&noop=true")
		    .unmarshal(csv)
	        .convertBodyTo(List.class)
	        .process(new Processor() {

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
	        .to(filePath + "?fileName=warmwaterconsumptionCustomer-${date:now:yyyyMMddhhmm}.csv")
	        .log("done.")
	        .to("dropbox://put?accessToken=2SJnFMZYQSAAAAAAAAAACkmidQvXyHOdAnvBkDmMmcRCwAAuGUjPbLsHC1CJPhtl&clientIdentifier=wmpm16&uploadMode=add&localPath=" + filePathDropbox + "&remotePath=/consumptions")
			.bean(DeleteFilesAfterDropboxUpdate.class, "delete")
	        .end();
		
	}
	}
	
	


