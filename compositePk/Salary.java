package compositePk;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "compositePk.Salary")
@Table(name = "cpk_salaries")
public class Salary {
	@EmbeddedId
	private SalaryPK key;

	private int salary;

	public SalaryPK getKey() {
		return key;
	}

	public void setKey(SalaryPK key) {
		this.key = key;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

}
