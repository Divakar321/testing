package compositePk;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AddEmployees {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory
				   ("mssqlserver_ii4_ddl");
		EntityManager em = emf.createEntityManager();

		Employee e1 = new Employee();
		e1.setName("James Gosling");
	  
		Employee e2 = new Employee();
		e2.setName("Van Rossum");

		em.getTransaction().begin();
		em.persist(e1); // insert c into table 
		em.persist(e2);
		em.getTransaction().commit();

		em.close();
		// Detached
		emf.close();

	}

}
