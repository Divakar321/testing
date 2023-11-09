package company.rest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
import org.springframework.web.server.ResponseStatusException;

import company.entities.Category;
import company.entities.Department;
import company.entities.Expenditure;
import company.entities.PaymentMode;
import company.repositories.CategoryRepo;
import company.repositories.DepartmentRepo;
import company.repositories.ExpenditureRepo;
import company.repositories.PaymentModeRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public class CompanyController {
	@Autowired
	ExpenditureRepo expenditureRepo;

	@Autowired // Injecting Payment Dependency
	PaymentModeRepo paymentRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	CategoryRepo categoryRepo;

	// Get All Expenditures
	@GetMapping("/expenditures")
	@Operation(
		    summary = "Get all Expenditures",
		    description = "Retrieve all expenditure details."
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Expenditures retrieved successfully."),
		    @ApiResponse(responseCode = "404", description = "Expenditures not found"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})
	public List<Expenditure> getAllExpenditures() {
		try {
			var expensesList = expenditureRepo.findAll();
			if (expensesList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category table is Empty!");
			}
			return expensesList;
		} catch (DataAccessException ex) {
			System.out.println("An error occurred while fetching expenditures: " + ex.getMessage());
			return Collections.emptyList();
		}
	}
	

	// 1
	// Add Expenditure
	@PostMapping("/expenditures/add")
	@Operation(summary = "Add Expenditure Details", description = "Add all Expenditure details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expenditure added successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	public Expenditure addExpenditure(@Valid @RequestBody Expenditure expenditure) {
		try {
			expenditureRepo.save(expenditure);
			return expenditure;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred while processing the request: " + ex.getMessage());
		}
	}

	// 1
	// Delete Exepnditure
	@DeleteMapping("/expenditures/delete/{id}")
	@Operation(summary = "Delete Expenditure", description = "Delete an expenditure by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expenditure successfully deleted"),
			@ApiResponse(responseCode = "404", description = "Expenditure not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server error") })
	public void deleteOneExpenditure(@PathVariable("id") int id) {
		Optional<Expenditure> optExpenditure = expenditureRepo.findById(id);
		try {
			if (optExpenditure.isPresent()) {
				expenditureRepo.deleteById(id);
			} else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expenditure with id " + id + " Not Found!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing the request: " + ex.getMessage());
		}

	}

	// 1
	// Update Expenditure
	@PutMapping("/expenditures/update/{id}")
	@Operation(summary = "Update Expenditure", description = "Update an expenditure by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expenditure successfully updated"),
			@ApiResponse(responseCode = "404", description = "Expenditure not found"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	public void updateExpenditure(@PathVariable("id") int id,@Valid @RequestBody Expenditure expenditure) {
		var optExpenditure = expenditureRepo.findById(id);
		try {
			if (optExpenditure.isPresent()) {
				var exp = optExpenditure.get();
				exp.setAmount(expenditure.getAmount());
				expenditureRepo.save(exp);
			} else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expenditure Not Found!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred while processing the request: " + ex.getMessage());
		}
	}

	// Get all Payment Details
	@GetMapping("/payments")
	@Operation(summary = "Get All Payment Modes", description = "Retrieve a list of all payment modes.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Payment modes retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No Payment Mode is found") })
	public List<PaymentMode> getAllPaymentmodes() {
		try {
			var paymentList= paymentRepo.findAll();
			if(paymentList.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Payment Modes List is Empty!");
			return paymentList;
		} catch (DataAccessException ex) {
			System.out.println("An error occurred while fetching expenditures: " + ex.getMessage());
			return Collections.emptyList();
		}
	}

	// 2
	// Add Payment
	@PostMapping("/payments/add")
	@Operation(summary = "Add New Payment Mode", description = "Add a new payment mode to the system.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Payment mode added successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	public PaymentMode addNewPaymentModes(@Valid @RequestBody PaymentMode mode) {
		try {
			var optCategory = paymentRepo.findById(mode.getPaymentCode());
			if (optCategory.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment Code Already Present");
			}
			paymentRepo.save(mode);
			return mode;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred while processing the request: " + ex.getMessage());
		}
	}

	// 2
	// Delete Payment
	@DeleteMapping("/payments/delete/{paymentCode}")
	@Operation(summary = "Delete Payment Mode", description = "Delete a payment mode by its payment code.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Payment mode deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Payment mode not found"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	public void deleteOneMode(@PathVariable("paymentCode") String paymentCode) {
		var optPayment = paymentRepo.findById(paymentCode);
		try {
			if (optPayment.isPresent())
				paymentRepo.deleteById(paymentCode);
			else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment code Not Found!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred while processing the request: "+ ex.getMessage());
		}
	}

	// 2
	// Update Payment
	@PutMapping("/payments/update/{paymentCode}")
	@Operation(summary = "Update Payment Mode", description = "Update an existing payment mode by its payment code.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Payment mode updated successfully"),
			@ApiResponse(responseCode = "404", description = "Payment mode not found"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	public void updatePaymentMode(@PathVariable("paymentCode") String code, @RequestBody PaymentMode mode) {
		var optPayment = paymentRepo.findById(code);
		try {
			if (optPayment.isPresent()) {
				var payment = optPayment.get();
				payment.setPaymentName(mode.getPaymentName());
				payment.setRemarks(mode.getRemarks());
				paymentRepo.save(payment);
			} else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Name Not Found!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred while processing the request: " + ex.getMessage());
		}
	}

	// List all departments
	@GetMapping("/departments")
	@Operation(summary = "Get All Departments", description = "Retrieve a list of all departments.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Departments retrieved successfully"),
			@ApiResponse(responseCode="404", description="No departments found"), })
	public List<Department> getAllDepartments() {
		try {
			var deptList= departmentRepo.findAll();
			if(deptList.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department List is Empty!");
			return deptList;
		} catch (DataAccessException ex) {
			System.out.println("An error occurred while fetching expenditures: " + ex.getMessage());
			return Collections.emptyList();
		}

	}

	// 3
	// Add Department
	@PostMapping("/departments/add")
	@Operation(summary = "Add Department", description = "Add a new department to the system.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Department added successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	public Department addDepartment(@Valid @RequestBody Department department) {
		try {
			var optDepartment = departmentRepo.findById(department.getDepartmentCode());
			if (optDepartment.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department Code Already Present");
			}
			departmentRepo.save(department);
			return department;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 3
	// Delete department
	@DeleteMapping("/departments/delete/{departmentCode}")
	@Operation(summary = "Delete Department", description = "Delete a department by its department code.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Department deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	public void deleteOneDepartment(@PathVariable("departmentCode") int departmentCode) {
		Optional<Department> optDepartment = departmentRepo.findById(departmentCode);
		try {
			if (optDepartment.isPresent()) {
				departmentRepo.deleteById(departmentCode);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department code Not Found!");
			}
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 3
	// update department

	@PutMapping("/departments/update/{departmentCode}")
	@Operation(summary = "Update Department", description = "Update an existing department by its department code.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Department updated successfully"),
			@ApiResponse(responseCode = "404", description = "Department not found"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	public void updatePaymentMode(@PathVariable("departmentCode") int deptCode, @RequestBody Department department) {
		var optDepartment = departmentRepo.findById(deptCode);
		try {
			if (optDepartment.isPresent()) {
				var dept = optDepartment.get();
				dept.setDepartmentName(department.getDepartmentName());
				dept.setHod(department.getHod());
				departmentRepo.save(dept);
			} else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Code Not Found!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// Get CategoryName based on the CategoryCode
	@GetMapping("/categories/getOne/{categoryCode}")
	@Operation(summary = "Get Category Name by Code", description = "Retrieve a category name by its category code.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Category retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Category not found") })
	public String getOneCategory(@PathVariable("categoryCode") String categoryCode) {
		var optCategory = categoryRepo.findById(categoryCode);
		try {
			if (optCategory.isPresent())
				return optCategory.get().getCategoryName();
			else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Code Not Found!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 4
	// Add Category
	@PostMapping("/categories/add")
	@Operation(summary = "Add New Category", description = "Add a new category to the system.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Category added successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	public Category addNewCategory(@Valid @RequestBody Category category) {
		try {
			var optCategory = categoryRepo.findById(category.getCategoryCode());
			if (optCategory.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category Code Already Present");
			}
			categoryRepo.save(category);
			return category;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 4
	// Delete Category
	@DeleteMapping("/categories/delete/{categoryCode}")
	@Operation(summary = "Delete Category", description = "Delete a category by its category code.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Category not found")})
	public void deleteOneCategory(@PathVariable("categoryCode") String categoryCode) {
		var optCategory = categoryRepo.findById(categoryCode);
		try {
			if (optCategory.isPresent())
				categoryRepo.deleteById(categoryCode);
			else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Code Not Found!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 4
	// Update Category
	@PutMapping("/categories/update/{categoryCode}")
	@Operation(summary = "Update Category", description = "Update an existing category's name by its category code.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Category name updated successfully"),
			@ApiResponse(responseCode = "404", description = "Category not found"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public void updateCategory(@PathVariable("categoryCode") String categoryCode,
			@RequestParam("categoryName") String categoryName) {
		var optCategory = categoryRepo.findById(categoryCode);
		try {
			if (optCategory.isPresent()) {
				var category = optCategory.get();
				category.setCategoryName(categoryName);
				categoryRepo.save(category);
			} else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Name Not Found!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	// 5

	@GetMapping("/getAllExpensesByCategory/{catCode}")
	@Operation(summary = "Get Expenses of page by Category Code", description = "Retrieve a page of expenditures by category code.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expenditures retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No expenses found for the given category code"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Page<Expenditure> getExpensesByCategory(@PathVariable String catCode,
			@RequestParam(name = "pagesize", required = false, defaultValue = "10") int pageSize, Pageable pageable) {
		Sort sort = Sort.by("id").descending(); // Create a Sort object for descending order
		try {
			Page<Expenditure> result = expenditureRepo.findByCatCode(catCode,
					PageRequest.of(pageable.getPageNumber(), pageSize, sort));

			if (result.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expenses found for catCode: " + catCode);
			}

			return result;
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expenses found for catCode: " + catCode);
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	// 6

	@GetMapping("/getAllExpensesByPageMode/{paymentCode}")
	@Operation(summary = "Get Expenses of page by Payment Mode", description = "Retrieve a page of expenditures by payment code.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expenditures retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No expenses found for the given payment code"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	public Page<Expenditure> getExpensesByPageMode(@PathVariable String paymentCode,
			@RequestParam(name = "pagesize", required = false, defaultValue = "10") int pageSize, Pageable pageable) {
		Sort sort = Sort.by("id").descending(); // Create a Sort object for descending order
		try {
			Page<Expenditure> result = expenditureRepo.findBypaymentCode(paymentCode,
					PageRequest.of(pageable.getPageNumber(), pageSize, sort));

			if (result.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"No expenses found for paymentCode: " + paymentCode);
			}

			return result;
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"No expenses found for paymentCode: " + paymentCode);
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 7
	@GetMapping("/expensesBetweenDates/sortByDate")
	@Operation(summary = "Get Expenses of page Between Dates", description = "Retrieve a page of expenditures between specified dates sorted by date.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expenditures retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No expenditures found between the given dates"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Page<Expenditure> getExpensesBetweenDates(@RequestParam("startDate") LocalDate startDate,
			@RequestParam("endDate") LocalDate endDate,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize, Pageable pageable) {
		Page<Expenditure> expenses = expenditureRepo.findExpensesBetweenDatesSortedByDate(startDate, endDate, pageable);
		try {
			if (expenses.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"No expenditures found between the given dates!");
			}
			return expenses;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 8
	@GetMapping("/expenses/category/{month}")
	@Operation(summary = "Get Expense Summary by Category in Month", description = "Retrieve the total expenses by category for a specific month.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expense summary retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Invalid month or no data found"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Object[]> getExpenseSummaryByCategoryInMonthJPQL(@PathVariable("month") int month) {
		try {
			if (month > 0 && month < 13) {
				return expenditureRepo.getTotalExpensesByCategoryInMonthJPQL(month);
			} else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "only 12 months are there!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}

	}

	@GetMapping("/expenditures/category/nq/{selectedMonth}")
	@Operation(summary = "Get Expense Summary by Category in Month (Named Query)", description = "Retrieve the total expenses by category for a specific month using a named query.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expense summary retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Invalid month or no data found"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Object[]> getExpenseSummaryByCategoryInMonth(@PathVariable("selectedMonth") int selectedMonth) {
		try {
			if (selectedMonth > 0 && selectedMonth < 13) {
				return expenditureRepo.getTotalExpensesByCategoryInMonthNQ(selectedMonth);
			} else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "only 12 months are there!");
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 9
	@GetMapping("/findExpensesBetweenDates")
	@Operation(summary = "Get Expenses by Department between Dates", description = "Retrieve expenditures by department code and date range.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expenditures retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No expenses found for the specified department and date range"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Expenditure> getExpensesByDepartmentAndDateRange(@RequestParam("deptCode") int deptCode,
			@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
		List<Expenditure> expenditures = expenditureRepo.findByDeptCodeAndExpdateBetween(deptCode, startDate, endDate);
		try {
			if (expenditures.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"No expenses found for the specified department and date range");
			}
			return expenditures;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 10
	@GetMapping("/findExpensesByEmployeeName")
	@Operation(summary = "Find Expenses by Employee Name", description = "Retrieve expenditures by the name of the employee who authorized them.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expenditures retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No expenses found for the specified employee name"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Expenditure> findByAuthorizedByEmployee(@RequestParam("authorizedBy") String authorizedBy) {
		var expensesList = expenditureRepo.findByAuthorizedBy(authorizedBy);
		try {
			if (expensesList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"No expenses found for Employee name : " + authorizedBy);
			}
			return expensesList;

		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 11
	@GetMapping("/findExpensesDescriptionContainingString")
	@Operation(summary = "Find Expenses by Description Containing String", description = "Retrieve expenditures with descriptions that contain the specified string.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expenditures retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No expenses found for the specified search string"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Expenditure> findByDescriptionContaining(@RequestParam("searchString") String searchString) {
		var expensesList = expenditureRepo.findByDescriptionContaining(searchString);
		try {
			if (expensesList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"No expenses found for the String : " + searchString);
			}
			return expensesList;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 12
	@GetMapping("/findExpensesWithinRangeOfAmount")
	@Operation(summary = "Find Expenses within Range of Amount", description = "Retrieve expenditures with amounts within the specified range.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Expenditures retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No expenditures found within the specified amount range"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Expenditure> searchTitlesByAmount(@RequestParam("min") Double min, @RequestParam("max") Double max) {
		List<Expenditure> expenditures = expenditureRepo.findByAmountBetween(min, max);
		try {
			if (expenditures.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expenditures found in the range of amount");
			}
			return expenditures;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 13
	// List All Categories
	@GetMapping("/categories")
	@Operation(summary = "Get All Categories", description = "Retrieve all categories from the category table.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No categories found in the table"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Category> getAllCategories() {
		try {
			var expensesList = categoryRepo.findAll();
			if (expensesList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category table is Empty!");
			}
			return expensesList;
		} catch (DataAccessException ex) {
			System.out.println("An error occurred while fetching expenditures: " + ex.getMessage());
			return Collections.emptyList();
		}
	}

	// 14
	@GetMapping("/totalAmountOfEachDepartmentByCode")
	@Operation(summary = "Get Total Amount of Each Department by Code", description = "Retrieve the total amount for each department by department code.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Total amounts retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No departments found"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Object[]> getTotalAmountOfEachDepartmentCode() {
		var expensesList = expenditureRepo.getTotalAmountOfEachDepartmentCode();
		try {
			if (expensesList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments found");
			}
			return expensesList;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	@GetMapping("/totalAmountOfEachDepartmentByName")
	@Operation(summary = "Get Total Amount of Each Department by Name", description = "Retrieve the total amount for each department by department name.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Total amounts retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No departments found"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Object[]> getTotalAmountOfEachDepartmentName() {
		var expensesList = expenditureRepo.getTotalAmountOfEachDepartmentName();
		try {
			if (expensesList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments found");
			}
			return expensesList;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	@GetMapping("/totalAmountOfEachDepartmentNQ")
	@Operation(summary = "Get Total Amount of Each Department (Native Query)", description = "Retrieve the total amount for each department using a native query.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Total amounts retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No departments found"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Object[]> getTotalAmountOfEachDepartmentNQ() {
		var expensesList = expenditureRepo.getTotalAmountOfEachDepartmentNQ();
		try {
			if (expensesList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Category found");
			}
			return expensesList;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 15
	@GetMapping("/totalAmountOfEachCategoryByCode")
	@Operation(summary = "Get Total Amount of Each Category by Code", description = "Retrieve the total amount for each category by category code.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Total amounts retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No categories found"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Object[]> getTotalAmountOfEachCategoryCode() {
		var expensesList = expenditureRepo.getTotalAmountOfEachCategoryCode();
		try {
			if (expensesList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Category found");
			}
			return expensesList;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	@GetMapping("/totalAmountOfEachCategoryByName")
	@Operation(summary = "Get Total Amount of Each Category by Name", description = "Retrieve the total amount for each category by category name.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Total amounts retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No categories found"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Object[]> getTotalAmountOfEachCategoryName() {
		var expensesList = expenditureRepo.getTotalAmountOfEachCategoryName();
		try {
			if (expensesList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Category found");
			}
			return expensesList;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	@GetMapping("/totalAmountOfEachCategoryNQ")
	@Operation(summary = "Get Total Amount of Each Category (Native Query)", description = "Retrieve the total amount for each category using a native query.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Total amounts retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No categories found"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Object[]> getTotalAmountOfEachCategoryNQ() {
		var expensesList = expenditureRepo.getTotalAmountOfEachCategoryNQ();
		try {
			if (expensesList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Category found");
			}
			return expensesList;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
}
