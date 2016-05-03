package smartMeterManagement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("myBean")
public class SmartHomeBean {
	
	@Value("${greeting}")
    private String say;

    public String saySomething() {
        return say;
    }
}
