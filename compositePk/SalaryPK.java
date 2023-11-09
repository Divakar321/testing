package compositePk;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SalaryPK implements Serializable {
	@Column(name = "employee_id")
	private int employee;
	
	@Column(name = "month", length = 10)
	private String month;

	
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

	@Override
	public int hashCode() {
		return Objects.hash(employee, month);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaryPK other = (SalaryPK) obj;
		return employee == other.employee && Objects.equals(month, other.month);
	}

}
