import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.tu.wmpm16.models.ColdWaterConsumption;
import at.tu.wmpm16.models.Customer;
import at.tu.wmpm16.models.Operator;
import at.tu.wmpm16.models.SmartMeter;
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
		int deletedCount = em.createQuery("DELETE FROM SmartMeterConsumptions").executeUpdate();
		System.out.println("deleted " + deletedCount);
		SmartMeterConsumptions data1 = new SmartMeterConsumptions(1, 1, dateFormat.parse("2016-05-13"), 100, 0, 0, 0, 0); 
//		SmartMeterConsumptions data2 = new SmartMeterConsumptions(2, 1, dateFormat.parse("2016-05-14"), 50, 0, 0, 0, 0);
//		SmartMeterConsumptions data3 = new SmartMeterConsumptions(3, 1, dateFormat.parse("2016-05-15"), 50, 0, 0, 0, 0); 
//		SmartMeterConsumptions data4 = new SmartMeterConsumptions(4, 4, dateFormat.parse("2016-05-16"), 50, 0, 0, 0, 0); 
//		SmartMeterConsumptions data5 = new SmartMeterConsumptions(5, 5, dateFormat.parse("2016-05-17"), 50, 0, 0, 0, 0); 
//		SmartMeterConsumptions data6 = new SmartMeterConsumptions(6, 6, dateFormat.parse("2016-05-18"), 50, 0, 0, 0, 0); 
//		
//		
		//ColdWaterConsumption
		/*ColdWaterConsumption c1 = new ColdWaterConsumption(100, 50, dateFormat.parse("2016-05-13"), 1);
		ColdWaterConsumption c2 = new ColdWaterConsumption(100, 124, dateFormat.parse("2016-05-14"), 1);
		ColdWaterConsumption c3 = new ColdWaterConsumption(50, 125, dateFormat.parse("2016-05-15"), 3);*/
		
		//Customer
		Customer customer1 = new Customer(1, "Jack Bauer", "test@wmpm.com", "+4356882145236", "Schottenring 5");
		Customer customer2 = new Customer(2, "Chloe O'Brian", "test@wmpm.com", "+4356882145442", "Schottenring 6");
		Customer customer3 = new Customer(3, "Patrick Sommer", "test@wmpm.com", "+435688214511", "Schottenring 7");
		
		List <Customer> customers = new ArrayList<Customer>();
		customers.add(customer1);
		customers.add(customer2);
		
		
		
		Operator operator1 = new Operator(1, "Paul Steiner", "Linke Wienzeile 3", "paul@steiner.com", customers, new ArrayList<SmartMeter>());
		Operator operator2 = new Operator(2, "Herbert Weber", "Linke Wienzeile 2", "herbert@weber.com", customers, new ArrayList<SmartMeter>());
		
		List<Operator> operators = new ArrayList<Operator>();
		
		operators.add(operator1);
		operators.add(operator2);

		SmartMeter smartMeter1 = new SmartMeter(1, "Schottenring 5" , customer1, operators);
		customer1.setSmartMeters(smartMeter1);
		SmartMeter smartMeter2 = new SmartMeter(3, "Schottenring 2" , customer2, operators);
		customer2.setSmartMeters(smartMeter2);
		
		List<SmartMeter> smartMeters = new ArrayList<SmartMeter>();


		operator1.setSmartMeters(smartMeters);
		operator2.setSmartMeters(smartMeters);

		//GasConsumption
		
		
		//ElectricityConsumption
		
		em.merge(data1);
//		em.merge(data2);
//		em.merge(data3);
//		em.merge(data4);
//		em.merge(data5);
//		em.merge(data6);
//		
		em.persist(customer1);
		em.persist(customer2);
		em.persist(customer3);
		
		em.persist(operator1);
		em.persist(operator2);
		
		em.persist(smartMeter1);
		em.persist(smartMeter2);

		em.getTransaction().commit();
		log.info("Testdata");
	}
	


}
