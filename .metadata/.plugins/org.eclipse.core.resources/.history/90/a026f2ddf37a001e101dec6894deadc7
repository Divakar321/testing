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

@Entity // To make this class as Entity
@Table(name = "paymentmodes") // To map the paymentmodes table in database with this entity
public class PaymentMode {
	@Id // Primary Key
	@Column(name = "payment_code")
	private String paymentCode;

	@Column(name = "payment_name")
	private String paymentName;

	@Column(name = "remarks")
	private String remarks;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "payment") // Establishes a relationship with Expenditure
	@JsonIgnore
	private List<Expenditure> expenditures = new ArrayList<Expenditure>();

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<Expenditure> getExpenditures() {
		return expenditures;
	}

	public void setExpenditures(List<Expenditure> expenditures) {
		this.expenditures = expenditures;
	}

	@Override
	public int hashCode() {
		return Objects.hash(expenditures, paymentCode, paymentName, remarks);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentMode other = (PaymentMode) obj;
		return Objects.equals(expenditures, other.expenditures) && Objects.equals(paymentCode, other.paymentCode)
				&& Objects.equals(paymentName, other.paymentName) && Objects.equals(remarks, other.remarks);
	}

	@Override
	public String toString() {
		return "PaymentMode [paymentCode=" + paymentCode + ", paymentName=" + paymentName + ", remarks=" + remarks
				+ ", expenditures=" + expenditures + "]";
	}

}