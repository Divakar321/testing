package compositePk;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ListEmployeeSalaries {
	
	public static void main(String[] args) throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mssqlserver_ii4_ddl");
		EntityManager em = emf.createEntityManager();

		List<Salary> salaries = em.createQuery("from compositePk.Salary",
				Salary.class).getResultList();

		for (Salary s : salaries) {
			System.out.printf("%s  %s  %d\n", 
					  s.getKey().getEmployee(), 
					  s.getKey().getMonth(),
					  s.getSalary());
		}
		
	    em.close();
	    emf.close();
	}
}
