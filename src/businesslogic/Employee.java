package businesslogic;


public class Employee {

	public int key;
	protected int id;
	protected String firstName,lastName, phone;
	protected double wage;

    public Employee(int eID, String fn, String ln, String ph, double w) {
        key = eID;
    	id = eID;
    	firstName = fn;
    	lastName = ln;
    	phone = ph;
    	wage = w;
    }

    public void setWage(double wg){
    	wage = wg;
    }

    public void details(){
    	System.out.println(firstName + " " + lastName + " Details: ");
    	System.out.println("Employee ID:\t" + id);
    	System.out.println("Phone Number:\t" + phone);
    	System.out.println("Monthly Salary:\t" + wage);
    }




}