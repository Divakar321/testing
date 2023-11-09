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
import jakarta.validation.constraints.Size;

@Entity // To make this class as Entity
@Table(name = "categories") // To map the categories table in database with this entity
public class Category {
	@Id // Primary Key
	@Column(name = "category_code")
	@NotBlank(message = "Category Code should not be Null")
	@Size(max = 5, message = "Category Code should not be Greater than 5")
	private String categoryCode;

	@Column(name = "category_name")						//@not null :- it takes blank  if we give as @not blank then it will not take spaces
	@NotBlank(message = "Category name cannot be Blank")
	@Size(min = 3, max = 50, message = "Category Name length should be Greater than 2 and Less than 50")
	private String categoryName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category") // Establishes a relationship with Expenditure
	@JsonIgnore
	private List<Expenditure> expenditures = new ArrayList<Expenditure>();

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Expenditure> getExpenditures() {
		return expenditures;
	}

	public void setExpenditures(List<Expenditure> expenditures) {
		this.expenditures = expenditures;
	}

	@Override
	public String toString() {
		return "Category [categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", expenditures="
				+ expenditures + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryCode, categoryName, expenditures);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(categoryCode, other.categoryCode) && Objects.equals(categoryName, other.categoryName)
				&& Objects.equals(expenditures, other.expenditures);
	}

}