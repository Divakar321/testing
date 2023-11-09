package company.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import company.entities.Expenditure;
import company.entities.ExpenditureRepo;

@RestController
public class ExpenditureController {
	@Autowired
	ExpenditureRepo expRepo;

	// Get All Expenditures
	@GetMapping("/expenditures")
	public List<Expenditure> getAllExpenditures() {
		return expRepo.findAll();
	}

	// 1
	// Add Expenditure
	@PostMapping("/expenditures/add")
	public Expenditure addExpenditure(@RequestBody Expenditure expenditure) {
		try {
			expRepo.save(expenditure);
			return expenditure;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	// 1
	// Delete Exepnditure
	@DeleteMapping("/expenditures/delete/{id}")
	public void deleteOneExpenditure(@PathVariable("id") int id) {
		Optional<Expenditure> optExpenditure = expRepo.findById(id);
		if (optExpenditure.isPresent()) {
			expRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department with code " + id + " Not Found!");
		}
	}

	// 1
	// Update Expenditure
	@PutMapping("/expenditures/update/{id}")
	public void updateExpenditure(@PathVariable("id") int id, @RequestBody Expenditure expenditure) {
		var optExpenditure = expRepo.findById(id);
		if (optExpenditure.isPresent()) {
			var exp = optExpenditure.get();
			exp.setAmount(expenditure.getAmount());
			expRepo.save(exp);
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expenditure Not Found!");
	}

	// 5
	@GetMapping("/getAllExpensesByCategory/{catCode}")
	public Page<Expenditure> getExpensesByCategory(@PathVariable String catCode,
			@RequestParam(name = "pagesize", required = false, defaultValue = "2") int pageSize, Pageable pageable) {
		Sort sort = Sort.by("catCode").descending(); // Create a Sort object for descending order
	    Page<Expenditure> result = expRepo.findBypaymentCode(catCode, PageRequest.of(pageable.getPageNumber(), pageSize, sort));
		return result;
	}

	// 6
//	@GetMapping("/getAllExpensesByPageMode/{paymentCode}")
//	public Page<Expenditure> getExpensesByPageMode(@PathVariable String paymentCode,
//			@RequestParam(name = "pagesize", required = false, defaultValue = "1") int pageSize, Pageable pageable) {
//		Page<Expenditure> result = expRepo.findBypaymentCode(paymentCode, pageable);
//		return result;
//	}
	
	@GetMapping("/getAllExpensesByPageMode/{paymentCode}")
	public Page<Expenditure> getExpensesByPageMode(@PathVariable String paymentCode,
	        @RequestParam(name = "pagesize", required = false, defaultValue = "1") int pageSize, Pageable pageable) {
	    Sort sort = Sort.by("paymentCode").descending(); // Create a Sort object for descending order
	    Page<Expenditure> result = expRepo.findBypaymentCode(paymentCode, PageRequest.of(pageable.getPageNumber(), pageSize, sort));
	    return result;
	}
	
	//7
	@GetMapping("/expensesBetweenDates/sortByDate")
	public Page<Expenditure> getExpensesBetweenDates(@RequestParam("startDate") LocalDate startDate,
	@RequestParam("endDate") LocalDate endDate,
	@RequestParam(name = "pageSize", required = false, defaultValue = "1") int pageSize, Pageable pageable) {
		Page<Expenditure> expenses = expRepo.findExpensesBetweenDatesSortedByDate(startDate, endDate, pageable);
		if (expenses.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expenditures found between the given dates!");
		}
		return expenses;
	}
	
	//8
	@GetMapping("/expenditures/category{selectedMonth}")
	public List<Object[]> getExpenseSummaryByCategoryInMonth(@RequestParam("selectedMonth") int selectedMonth) {
		return expRepo.getTotalExpensesByCategoryInMonth(selectedMonth);
	}

	// 9
	@GetMapping("/findExpensesBetweenDates")
	public List<Expenditure> getExpensesByDepartmentAndDateRange(@RequestParam("deptCode") int deptCode,
			@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
		List<Expenditure> expenditures = expRepo.findByDeptCodeAndExpdateBetween(deptCode, startDate, endDate);
		if (expenditures.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"No expenses found for the specified department and date range");
		}
		return expenditures;
	}

	// 10
	@GetMapping("/findExpensesByEmployeeName")
	public List<Expenditure> findByAuthorizedByEmployee(@RequestParam("authorizedBy") String authorizedBy) {
		return expRepo.findByAuthorizedBy(authorizedBy);
	}

	// 11
	@GetMapping("/findExpensesDescriptionContainingString")
	public List<Expenditure> findByDescriptionContaining(@RequestParam("searchString") String searchString) {
		return expRepo.findByDescriptionContaining(searchString);
	}

	// 12
	@GetMapping("/findExpensesWithinRangeOfAmount")
	public List<Expenditure> searchTitlesByAmount(@RequestParam("min") Double min, @RequestParam("max") Double max) {
		List<Expenditure> expenditures = expRepo.findByAmountBetween(min, max);
		if (expenditures.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expenditures found in the range of amount");
		}
		return expenditures;
	}

	// 14
	@GetMapping("/totalAmountOfEachDepartment")
	public List<Object[]> getTotalAmountOfEachDepartment() {
		return expRepo.getTotalAmountOfEachDepartment();
	}

	// 15
	@GetMapping("/totalAmountOfEachCategory")
	public List<Object[]> getTotalAmountOfEachCategory() {
		return expRepo.getTotalAmountOfEachCategory();
	}

//	@GetMapping("/getAllExpensesByCategory/{catCode}")
//	public Page<Expenditure> getExpensesByCategory(@PathVariable String catCode,
//			@RequestParam(name = "pagesize", required = false, defaultValue = "1") int pageSize, Pageable pageable) {
//		PageRequest pagereq = PageRequest.of(0, pageSize); // Set page number to 0 (first page)
//	   Page<Expenditure> result = expRepo.findByCatCode(catCode, pagereq);
//	   return result;
//	}

//	@GetMapping("/getExpensesBetweenDates")
//	public Page<Expenditure> getExpensesBetweenDates(
//	    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
//	    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
//	    @RequestParam(name = "page", required = false, defaultValue = "0") int page,
//	    @RequestParam(name = "size", required = false, defaultValue = "10") int size
//	) {
//	    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("date")));
//	    Page<Expenditure> result = expRepo.findExpensesBetweenDates(startDate, endDate, pageable);
//	    return result;
//	}

//	@GetMapping("/expensesSummaryForEachCategory/{month}")
//	public List<CategoryDTO> getSummary(@PathVariable("month") int month){
//		return expRepo.getSummaryForMonth(month);
//	}
//
}
