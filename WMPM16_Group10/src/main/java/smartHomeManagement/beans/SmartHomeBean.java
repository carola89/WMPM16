package smartHomeManagement.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("smartHomeBean")
public class SmartHomeBean {
	
	@Value("${greeting}")
    private String say;

    public String saySomething() {
        return say;
    }
}
