package company.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity // To make this class as Entity
@Table(name = "departments") // To map the departments table in database with this entity
public class Department {
	@Positive(message = "Department Code should be positive")
	@NotNull(message = "Department Code should not be Null")
	@Id // Primary Key
	@Column(name = "department_code")
	private int departmentCode;

	@NotBlank(message = "Department name should be required")
	@Size(min = 2, max = 20, message = "Department Name length should not be less than 2 and greater than 20")
	@Column(name = "department_name")
	private String departmentName;

	@NotBlank(message = "Hod name should be Required")
	// @Size(min=3,max=20,message="Hod Name length should not be less than 3 and
	// greater than 20")
	@Column(name = "hod")
	private String hod;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "department") // Establishes a relationship with Expenditure
	@JsonIgnore
	private List<Expenditure> expenditures = new ArrayList<Expenditure>();

	public int getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(int departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getHod() {
		return hod;
	}

	public void setHod(String hod) {
		this.hod = hod;
	}

	public List<Expenditure> getExpenditures() {
		return expenditures;
	}

	public void setExpenditures(List<Expenditure> expenditures) {
		this.expenditures = expenditures;
	}

	@Override
	public String toString() {
		return "Department [departmentCode=" + departmentCode + ", departmentName=" + departmentName + ", hod=" + hod
				+ ", expenditures=" + expenditures + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(departmentCode, departmentName, expenditures, hod);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return departmentCode == other.departmentCode && Objects.equals(departmentName, other.departmentName)
				&& Objects.equals(expenditures, other.expenditures) && Objects.equals(hod, other.hod);
	}

}