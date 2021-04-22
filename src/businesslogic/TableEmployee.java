package businesslogic;

public class TableEmployee {
	
	public TableEmployee(Employee emp) {
		this(String.valueOf(emp.id), emp.firstName, emp.lastName, emp.phone, String.valueOf(emp.wage));
	}
	
	public TableEmployee(String id, String firstName, String lastName, String phone, String wage) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.wage = wage;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWage() {
		return wage;
	}

	public void setWage(String wage) {
		this.wage = wage;
	}

	private String id, firstName,lastName, phone, wage;
	
}
