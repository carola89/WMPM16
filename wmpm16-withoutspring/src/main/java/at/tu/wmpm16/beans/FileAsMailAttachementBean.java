package at.tu.wmpm16.beans;

import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.stereotype.Component;

@Component
public class FileAsMailAttachementBean {

	public List<Map<String, Object>> process(Exchange exchange) {
		Message in = exchange.getIn();
		System.out.println("in: " + in);
		byte[] file = in.getBody(byte[].class);
		System.out.println("file " + file.toString());
		String fileId = in.getHeader("CamelFileName", String.class);
		System.out.println("fileid " + fileId);
		in.addAttachment(fileId, new DataHandler(file, "plain/text"));
		System.out.println("in out " + in);
		in.setBody("");
		return null;
	}

}
