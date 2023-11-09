package compositePk;

/* insert rows into Employees and Salaries tables 
 
insert into i3_cpk_employees values('Dave')
insert into i3_cpk_salaries values(1,'Jan',34000);
insert into i3_cpk_salaries values(1,'Mar',38000)

*/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "compositePk.Employee")
@Table(name = "cpk_employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 30)
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
