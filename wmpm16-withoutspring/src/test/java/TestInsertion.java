import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestInsertion {
	
	private static EntityManagerFactory factory;
	private static EntityManager em;
	
	@BeforeClass
	public static void setUp(){
		factory =Persistence.createEntityManagerFactory("camel");
		em = factory.createEntityManager();
		em.getTransaction().begin();
	}
	@Test
	public void dataInsertedSuccessfully(){
		System.out.println(em.createQuery("select c from ColdWaterConsumption c").getResultList().size());
	}

}
