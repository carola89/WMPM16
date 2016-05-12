package smartHomeManagement.beans;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@Component("pollingConsumerBean")
public class PollingConsumerBean {
	 
    private int count;
    private ConsumerTemplate consumer;
    private ProducerTemplate producer;
 
    public void setConsumer(ConsumerTemplate consumer) {
        this.consumer = consumer;
    }
 
    public void setProducer(ProducerTemplate producer) {
        this.producer = producer;
    }
 
    public void someBusinessLogic() {
        // loop to empty queue
        while (true) {
            // receive the message from the queue, wait at most 3 sec
            String msg = consumer.receiveBody("activemq:queue.inbox", 3000, String.class);
            if (msg == null) {
                // no more messages in queue
                break;
            }
 
            // do something with body
            msg = "Hello " + msg;
 
            // send it to the next queue
            producer.sendBodyAndHeader("activemq:queue.foo", msg, "number", count++);
        }
    }
}
