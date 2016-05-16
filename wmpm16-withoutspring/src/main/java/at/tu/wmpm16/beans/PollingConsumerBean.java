package at.tu.wmpm16.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import at.tu.wmpm16.models.ColdWaterConsumption;

@Component
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
    
    public List<Map<String, Object>> transform(Exchange exchange)
    {
      ColdWaterConsumption cwc = (ColdWaterConsumption) exchange.getIn().getBody();
  	  List<Map<String,Object>> unmarshalledModel = new ArrayList<Map<String,Object>>();
  	  Map<String, Object> mapp = new HashMap<String, Object>();
  	  mapp.put("ColdWaterConsumption", cwc);
  	  unmarshalledModel.add(mapp);
  	  System.out.println("***" + unmarshalledModel);
  	  return unmarshalledModel;
//      
//      List<ColdWaterConsumption> cList = new ArrayList<ColdWaterConsumption>();
//      cList.add(cwc);
//      return cList;
    }
    
   
}
