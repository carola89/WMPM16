package smartHomeManagement.processor;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Service;

@Service
public class FileAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (null == oldExchange) {
            return newExchange;
        }

        String body = oldExchange.getIn().getBody(String.class) + "\n\n"
                + newExchange.getIn().getBody(String.class);
        oldExchange.getIn().setBody(body);

        return oldExchange;
    }
}