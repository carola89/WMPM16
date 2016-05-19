package at.tu.wmpm16;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.main.Main;

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
        CamelContext context = main.getOrCreateCamelContext();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
        context.addComponent("jms", JmsComponent.jmsComponent(connectionFactory));
        main.addRouteBuilder(new PollingConsumerRoute());
        main.run(args);
    }
    
    

}

