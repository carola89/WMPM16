package at.tu.wmpm16.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Service;

import at.tu.wmpm16.models.ColdWaterConsumption;

@Service
public class FileAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (null == oldExchange) {
            return newExchange;
        }

        List<ColdWaterConsumption> list = new ArrayList<>();
        
        List<Map<String,Object>> oldlist = (List<Map<String, Object>>) oldExchange.getIn().getBody();
        oldlist.add(((List<Map<String,Object>>) newExchange.getIn().getBody()).get(0));
        oldExchange.getIn().setBody(oldlist);
        System.out.println("**********" + oldExchange.getIn().getBody());
        return oldExchange;
    }
}