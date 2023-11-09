package compositePk;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ListEmployeeSalaries2 {
	
	public static void main(String[] args) throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory
				   ("mssqlserver_ii4_ddl");
		EntityManager em = emf.createEntityManager();

		List<Salary2> salaries = em.createQuery("from compositePk.Salary2",
				Salary2.class).getResultList();

		for (Salary2 s : salaries) {
			System.out.printf("%s  %s  %d\n", 
					  s.getEmployee(), 
					  s.getMonth(),
					  s.getSalary());
		}
		
	    em.close();
	    emf.close();
	}
}
