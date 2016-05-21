package at.tu.wmpm16.smartHomeManagement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.Processor;

public class ContentFilterForCustomerRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		CsvDataFormat csv = new CsvDataFormat();
		
		errorHandler(deadLetterChannel("log:dead?level=ERROR")
    			.useOriginalMessage().maximumRedeliveries(3).redeliveryDelay(5000));

	    csv.setDelimiter(',');
	    csv.setQuoteDisabled(true);
	    
	    from("file:C:/wmpm/file?fileName=company.csv&noop=true&delay=15m")
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
        }).marshal(csv).to("file:C:/wmpm/file?fileName=customer.csv")
        .log("done.").end();
		
	}

}
