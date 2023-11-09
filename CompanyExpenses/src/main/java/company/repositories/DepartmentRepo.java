package company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import company.entities.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

}