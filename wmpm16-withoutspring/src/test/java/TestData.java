import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.tu.wmpm16.models.ColdWaterConsumption;
import at.tu.wmpm16.models.Customer;

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
		
		//ColdWaterConsumption
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-mm-DD");
		ColdWaterConsumption c1 = new ColdWaterConsumption(123456, 123, dateFormat.parse("2016-05-13"), 1);
		ColdWaterConsumption c2 = new ColdWaterConsumption(123456, 124, dateFormat.parse("2016-05-14"), 2);
		ColdWaterConsumption c3 = new ColdWaterConsumption(123456, 125, dateFormat.parse("2016-05-15"), 3);
		
		//Customer
		Customer customer1 = new Customer(1, "Jack Bauer", "test@wmpm.com", "12345", "Schottenring 5");
		Customer customer2 = new Customer(2, "Chloe O'Brian", "test@wmpm.com", "678943", "Schottenring 6");
		Customer customer3 = new Customer(1, "Patrick Sommer", "test@wmpm.com", "12345", "Schottenring 7");
		
		//GasConsumption
		
		
		//ElectricityConsumption
		
		em.persist(c1);
		em.persist(c2);
		em.persist(c3);
		em.getTransaction().commit();
		log.info("Testdata");
		
	}
	


}
