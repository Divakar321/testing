package company.entities;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity // To make this class as Entity
@Table(name = "expenditures") // To map the expenditures table in database with this entity
public class Expenditure {
	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "catcode")
	private String catCode;

	@Column(name = "deptcode")
	private int deptCode;

	@Column(name = "amount")
	private double amount;

	@Column(name = "exp_date")
	private LocalDate expdate;

	@Column(name = "authorizedby")
	private String authorizedBy;

	@Column(name = "description")
	private String description;

	@Column(name = "paymentcode")
	private String paymentCode;

	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "catcode", insertable = false, updatable = false) // Establishes a relationship with Category
	@JsonIgnore
	private Category category;

	@ManyToOne
	@JoinColumn(name = "paymentcode", insertable = false, updatable = false) // Establishes a relationship with
																				// PaymentMode
	@JsonIgnore
	private PaymentMode payment;

	@ManyToOne
	@JoinColumn(name = "deptcode", insertable = false, updatable = false) // Establishes a relationship with Department
	@JsonIgnore
	private Department department;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	public int getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(int deptCode) {
		this.deptCode = deptCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getExpdate() {
		return expdate;
	}

	public void setExpdate(LocalDate expdate) {
		this.expdate = expdate;
	}

	public String getAuthorizedBy() {
		return authorizedBy;
	}

	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public PaymentMode getPayment() {
		return payment;
	}

	public void setPayment(PaymentMode payment) {
		this.payment = payment;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, authorizedBy, catCode, category, department, deptCode, description, expdate, id,
				payment, paymentCode, remarks);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Expenditure other = (Expenditure) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(authorizedBy, other.authorizedBy) && Objects.equals(catCode, other.catCode)
				&& Objects.equals(category, other.category) && Objects.equals(department, other.department)
				&& deptCode == other.deptCode && Objects.equals(description, other.description)
				&& Objects.equals(expdate, other.expdate) && id == other.id && Objects.equals(payment, other.payment)
				&& Objects.equals(paymentCode, other.paymentCode) && Objects.equals(remarks, other.remarks);
	}

	@Override
	public String toString() {
		return "Expenditure [id=" + id + ", catCode=" + catCode + ", deptCode=" + deptCode + ", amount=" + amount
				+ ", expdate=" + expdate + ", authorizedBy=" + authorizedBy + ", description=" + description
				+ ", paymentCode=" + paymentCode + ", remarks=" + remarks + ", category=" + category + ", payment="
				+ payment + ", department=" + department + "]";
	}

}