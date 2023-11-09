package compositePk;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity(name = "compositePk.Salary2")
@Table(name = "cpk_salaries")
@IdClass(SalaryPK.class)
public class Salary2 {
	@Id
	private int employee;

	@Id
	private String month;

	private int salary;

	 

	public int getEmployee() {
		return employee;
	}

	public void setEmployee(int employee) {
		this.employee = employee;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

}
