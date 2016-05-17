import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import at.tu.wmpm16.models.ColdWaterConsumption;

public class TestData {
	
	private static EntityManagerFactory factory;
	private static EntityManager em;
	
	@BeforeClass
	public static void setUp(){
		factory =Persistence.createEntityManagerFactory("camel");
		em = factory.createEntityManager();
		em.getTransaction().begin();
	}
	
	@Test
	public void insertData() throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-mm-DD");
		ColdWaterConsumption c1 = new ColdWaterConsumption(123456, 123, dateFormat.parse("2016-05-13"), 1);
		ColdWaterConsumption c2 = new ColdWaterConsumption(123456, 124, dateFormat.parse("2016-05-14"), 2);
		ColdWaterConsumption c3 = new ColdWaterConsumption(123456, 125, dateFormat.parse("2016-05-15"), 3);
		
		em.persist(c1);
		em.persist(c2);
		em.persist(c3);
		em.getTransaction().commit();
		
	}
	


}
