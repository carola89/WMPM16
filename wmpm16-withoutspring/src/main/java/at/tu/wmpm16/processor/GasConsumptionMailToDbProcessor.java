package at.tu.wmpm16.processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.tu.wmpm16.models.GasConsumption;

public class GasConsumptionMailToDbProcessor implements Processor{

	Logger logger = LoggerFactory.getLogger(GasConsumptionMailToDbProcessor.class);
	private static EntityManagerFactory factory;
	private static EntityManager em;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		factory =Persistence.createEntityManagerFactory("camel");
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
        List<List<String>> data = (List<List<String>>) exchange.getIn().getBody();
        int i=0;
        for (List<String> line : data) {
       	 if(i!=0){
                 GasConsumption c = new GasConsumption();
                 c.setId(Long.valueOf(line.get(0)).longValue());
                 c.setStandardValue(Long.valueOf(line.get(1)).longValue());
                 c.setMeasuredValue(Long.valueOf(line.get(2)).longValue());
                 SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                 try {
                  c.setDate(formatter.parse(line.get(3).toString()));
                 } catch (ParseException e) {
                  e.printStackTrace();
                 }
                 c.setSmartMeter(Integer.parseInt(line.get(4)));   
                 System.out.println("Object "+c.toString());
                 
                 em.merge(c);
                 em.getTransaction().commit();
       	 }
           i++;
        }
		
	}

}
