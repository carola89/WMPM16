package at.tu.wmpm16;

import java.text.SimpleDateFormat;

import org.apache.camel.main.Main;
import org.springframework.context.annotation.Bean;

import at.tu.wmpm16.models.ColdWaterConsumption;
import at.tu.wmpm16.smartHomeManagement.PollingConsumerRoute;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        Main main = new Main();
        //main.addRouteBuilder(new MyRouteBuilder());
        main.addRouteBuilder(new PollingConsumerRoute());
        main.run(args);
    }
    
    

}

