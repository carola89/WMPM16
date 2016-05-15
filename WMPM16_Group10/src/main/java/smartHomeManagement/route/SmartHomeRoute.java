package smartHomeManagement.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SmartHomeRoute extends RouteBuilder{
	
	 @Override
	    public void configure() throws Exception {
	        from("timer:hello?period={{timer.period}}")
	                .transform(method("smartHomeBean", "saySomething"))
	                .to("stream:out");
	    }
}
