package company.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import company.entities.Department;
import company.entities.DepartmentRepo;
import company.entities.PaymentMode;

@RestController
public class DepartmentController {

	@Autowired
	DepartmentRepo departmentRepo;

	// List all departments
	@GetMapping("/departments")
	public List<Department> getAllDepartments() {
		return departmentRepo.findAll();
	}

	// 3
	// Add Department
	@PostMapping("/departments/add")
	public Department addDepartment(@RequestBody Department department) {
		try {
			var optDepartment = departmentRepo.findById(department.getDepartmentCode());
			if (optDepartment.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department Code Already Present");
			}
			departmentRepo.save(department);
			return department;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	// 3
	// Delete department
	@DeleteMapping("/departments/delete/{departmentCode}")
	public void deleteOneDepartment(@PathVariable("departmentCode") int departmentCode) {
		Optional<Department> optDepartment = departmentRepo.findById(departmentCode);
		if (optDepartment.isPresent()) {
			departmentRepo.deleteById(departmentCode);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department code Not Found!");
		}
	}

	// 3
	// update department
	@PutMapping("/departments/update/{departmentCode}")
	public void updatePaymentMode(@PathVariable("deptCode") int deptCode, @RequestBody Department department) {
		var optDepartment = departmentRepo.findById(deptCode);
		if (optDepartment.isPresent()) {
			var dept = optDepartment.get();
			dept.setDepartmentName(department.getDepartmentName());
			dept.setHod(department.getHod());
			departmentRepo.save(dept);
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Code Not Found!");
	}

}
