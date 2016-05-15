package smartHomeManagement.route;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smartHomeManagement.exceptions.DropboxLogException;
import smartHomeManagement.exceptions.MailException;

public abstract class CustomRouteBuilder extends RouteBuilder {
	
	private static final Logger log = LoggerFactory.getLogger(CustomRouteBuilder.class);
	
	@Override
    public void configure() throws Exception {
	       // Exception handling
        onException(MailException.class)
                .transform(simple("${exception.stacktrace}"))
                .handled(true)
                .to("");
        //TODO
        
        onException(DropboxLogException.class)
        .transform(simple("${exception.stacktrace}"))
        .handled(true)
        .to("");
        
      //TODO
	}

}
