package company.entities;

//import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenditureRepo extends JpaRepository<Expenditure, Integer> {

//	@Query("SELECT e.deptCode, SUM(e.amount) FROM Expenditure e GROUP BY e.deptCode")
//    List<Object[]> getTotalAmountOfEachDept();
//	
//	@Query("SELECT e.catCode, SUM(e.amount) FROM Expenditure e GROUP BY e.catCode")
	// List<Object[]> findTotalAmountOfEachCategory();

//	@Query(value="SELECT e FROM expenditures e WHERE e.catCode = :catCode",nativeQuery=true)
//    Page<Expenditure> findByCatCode(@Param("catCode") String catCode, Pageable pageable);

//	@Query(value = "SELECT * FROM expenditures e WHERE e.catCode = :catCode", countQuery = "SELECT count(*) FROM expenditures e WHERE e.catCode = :catCode", nativeQuery = true)
//	Page<Expenditure> findByCatCode(@Param("catCode") String catCode, PageRequest pagereq);

	// 5
	@Query(value = "SELECT * FROM expenditures e WHERE e.catCode = :catCode", nativeQuery = true)
	Page<Expenditure> findByCatCode(@Param("catCode") String catCode, Pageable pageable);

	// 6
	@Query(value = "SELECT * FROM expenditures e WHERE e.paymentCode = :paymentCode", nativeQuery = true)
	Page<Expenditure> findBypaymentCode(@Param("paymentCode") String paymentCode, Pageable pageable);
	
	//7
	@Query("SELECT e FROM Expenditure e WHERE e.expdate BETWEEN :startDate AND :endDate ORDER BY e.expdate DESC")
    Page<Expenditure> findExpensesBetweenDatesSortedByDate(@Param("startDate") LocalDate startDate,
    @Param("endDate") LocalDate endDate, Pageable pageable);
	
	//8
	@Query(value = "SELECT e.CatCode, SUM(e.Amount) FROM expenditures e	WHERE MONTH(e.exp_date) = :selectedMonth GROUP BY e.CatCode", nativeQuery = true)
    List<Object[]> getTotalExpensesByCategoryInMonth(@Param("selectedMonth") int selectedMonth);

    // 9
	List<Expenditure> findByDeptCodeAndExpdateBetween(int deptCode, LocalDate startDate, LocalDate endDate);

	// 10
	List<Expenditure> findByAuthorizedBy(String authorizedBy);

	// 11
	List<Expenditure> findByDescriptionContaining(String description);

	// 12
	List<Expenditure> findByAmountBetween(Double min, Double max);

	// 14
	@Query(value = "SELECT deptCode, SUM(amount) as total_amount FROM expenditures GROUP BY deptCode", nativeQuery = true)
	List<Object[]> getTotalAmountOfEachDepartment();

	// 15<----JPQL------>
	 @Query("SELECT catCode, SUM(amount) as total_amount FROM Expenditure GROUP BY catCode")
	 List<Object[]> getTotalAmountOfEachCategory();
	 
	 //<----Native Query---->
//	@Query(value = "SELECT catCode, SUM(amount) as total_amount FROM expenditures GROUP BY catCode", nativeQuery = true)
//	List<Object[]> getTotalAmountOfEachCategory();
//	
   
	

//    @Query("SELECT e.getCategory().getCategoryName(), SUM(amount) as total_amount FROM Expenditure e GROUP BY catCode")
//    List<Object[]> getTotalAmountOfEachCategory();


}