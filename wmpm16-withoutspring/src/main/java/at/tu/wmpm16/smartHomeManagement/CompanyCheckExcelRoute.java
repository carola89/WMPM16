package at.tu.wmpm16.smartHomeManagement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import at.tu.wmpm16.Constants;
import at.tu.wmpm16.beans.ExcelConverterBean;
import at.tu.wmpm16.processor.WireTapLogCheckExcel;
import at.tu.wmpm16.processor.WireTapLogPolling;



//Um diesen Teil testen zu können, muss der SMPP Client gestartet werden ( SMPPSim/startsmppsim.bat ) 
public class CompanyCheckExcelRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
	 	
		errorHandler(deadLetterChannel("log:dead?level=ERROR")
    			.useOriginalMessage().maximumRedeliveries(3).redeliveryDelay(5000));
		
		 from(Constants.filePathExcel+"?noop=true&delay=20000").
		 bean(new ExcelConverterBean(), "readExcelColdWaterConsumption")
		 .to("jpa:at.tu.wmpm16.models.ColdWaterConsumption")
		 .to("pdf:create")
		 .to(Constants.filePathPdf+"?fileName=test.pdf")
		 .wireTap("jms:checkExcelAudit")
		 .process(new WireTapLogCheckExcel())
		 .log("saved");
	}
	

}
