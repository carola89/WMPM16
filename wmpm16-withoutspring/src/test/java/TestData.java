import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.tu.wmpm16.models.SmartMeterConsumptions;

public class TestData {
	
	private static EntityManagerFactory factory;
	private static EntityManager em;
	private static final Logger log = LoggerFactory.getLogger(TestData.class);
	
	@BeforeClass
	public static void setUp(){
		factory =Persistence.createEntityManagerFactory("camel");
		em = factory.createEntityManager();
		em.getTransaction().begin();
	}
	
	@Test
	public void insertData() throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-mm-DD");
		
		SmartMeterConsumptions data1 = new SmartMeterConsumptions(1, 1, dateFormat.parse("2016-05-13"), 0, 0, 0, 0, 0); 
		SmartMeterConsumptions data2 = new SmartMeterConsumptions(2, 2, dateFormat.parse("2016-05-14"), 0, 0, 0, 0, 0);
		SmartMeterConsumptions data3 = new SmartMeterConsumptions(3, 3, dateFormat.parse("2016-05-15"), 0, 0, 0, 0, 0); 
		SmartMeterConsumptions data4 = new SmartMeterConsumptions(4, 4, dateFormat.parse("2016-05-16"), 0, 0, 0, 0, 0); 
		SmartMeterConsumptions data5 = new SmartMeterConsumptions(5, 5, dateFormat.parse("2016-05-17"), 0, 0, 0, 0, 0); 
		SmartMeterConsumptions data6 = new SmartMeterConsumptions(6, 6, dateFormat.parse("2016-05-18"), 0, 0, 0, 0, 0); 

		
		em.merge(data1);
		em.merge(data2);
		em.merge(data3);
		em.merge(data4);
		em.merge(data5);
		em.merge(data6);
		em.getTransaction().commit();
		log.info("Testdata");
		
	}
	


}
