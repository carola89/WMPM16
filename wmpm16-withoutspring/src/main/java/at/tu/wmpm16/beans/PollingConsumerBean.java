package at.tu.wmpm16.beans;

import java.util.List;
import java.util.Map;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import at.tu.wmpm16.models.ColdWaterConsumption;

@Component("pollingConsumerBean")
public class PollingConsumerBean{
	 
    private int count;
    private ConsumerTemplate consumer;
    private ProducerTemplate producer;
 
    public void setConsumer(ConsumerTemplate consumer) {
        this.consumer = consumer;
    }
 
    public void setProducer(ProducerTemplate producer) {
        this.producer = producer;
    }
 
    public void doSomething(Exchange message) {
        System.out.println("My ColdWaterConsumption Bean-Value" + ((ColdWaterConsumption) message.getIn().getBody()).getMeasuredValue());
//        return message;
    }
    
   
}
