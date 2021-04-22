package businesslogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import exceptions.HashTableInitializationException;
import exceptions.HashTableOperationException;
import resources.EMConstants;

public class UsingHashTable {
	private HashTableADT hashTable = null;
	
	public UsingHashTable(File file, int maxEmployees) {
		try {
			boolean success = initialize(file, maxEmployees);
			if(!success) {
				throw new HashTableInitializationException(EMConstants.EMP_LOAD_FAILURE);
			}
		} catch (Exception e) {
			throw new HashTableInitializationException(e.getMessage(), e);
		}
	}
		
	private List<Employee> importEmployeesFromTxtFile(File file) throws IOException {
		if(!file.getName().split("[.]")[1].equalsIgnoreCase(EMConstants.TXT_EXTN)) {
			throw new IllegalArgumentException(EMConstants.INVALID_FILE);
		}
		List<Employee> employees = new ArrayList<Employee>();
		FileReader fileReader = new FileReader(file);
		try (BufferedReader reader = new BufferedReader(fileReader)) {
			String line = new String();
			List<String> lines = new ArrayList<String>();
			while(line != null) {
				line = reader.readLine();
				if(line != null && !line.isEmpty()) {
					lines.add(line);
				} else {
					if(lines.size() != 5) {
						throw new IllegalArgumentException(EMConstants.INVALID_FILE_FORMAT);
					}
					int id = -1; String firstName = EMConstants.DELETED, lastName = EMConstants.DELETED, phone = EMConstants.DELETED; double wage = -999;
					for(int i = 0; i < lines.size(); i++) {
						String curLine = lines.get(i).trim();
						switch(i) {
						 case 0:
							  id = Integer.parseInt(curLine);
							 break;
						 case 1:
							  firstName = curLine;
							 break;
						 case 2:
							  lastName = curLine;
							 break;
						 case 3:
							  if(!curLine.matches(EMConstants.US_PHONE_REGEX_WITHOUT_AREA_CODE))
									  throw new IllegalArgumentException(EMConstants.INVALID_PHONE_NOS_IN_FILE);	  
							  phone = curLine;
							 break;
						 case 4:
							  wage = Double.parseDouble(curLine);
							 break;
						}
					}
					employees.add(new Employee(id, firstName, lastName, phone, wage));
					lines.removeAll(lines);
				}
			}
		}
		
		return employees;
	} 
	
	public boolean initialize(File file, int maxEmployees) throws Exception {
		List<Employee> employees = importEmployeesFromTxtFile(file);
		if(employees.size() > maxEmployees) {
			throw new IllegalArgumentException(EMConstants.MAX_EMP_LIMIT_REACHED);
		}
		hashTable = new HashTableADT(maxEmployees);
		for(Employee emp : employees)
			if(hashTable.find(emp.key) == -1) {
				hashTable.insert(emp);
			}else {
				throw new IllegalArgumentException(EMConstants.DUP_KEYS_IN_FILE);
			}
			
    		
		
		if(employees.size() != hashTable.size())
			return false;
		
		return true;
		
	}
	
	public boolean addEmployee(Employee emp) {
		int oldSize = hashTable.size();
		if(hashTable.find(emp.key) != -1) {
			throw new HashTableOperationException(EMConstants.DUP_EMP);
		}
		if(hashTable.isFull()) {
			throw new HashTableOperationException(EMConstants.MAX_EMP_LIMIT_REACHED);
		}
		hashTable.insert(emp);
		return oldSize + 1 == hashTable.size();
	}
	
	public Employee findEmployee(int empId) {
		int index = hashTable.find(empId);
		if(index == -1) {
			throw new HashTableOperationException(EMConstants.EMP_NOT_FOUND);
		}
		return hashTable.getEmployee(index);
	}
	
	public Employee updateWage(int empId, double newWage) {
		int index = hashTable.find(empId);
		if(index == -1) {
			throw new HashTableOperationException(EMConstants.EMP_NOT_FOUND);
		}
		hashTable.setEmployeeWage(index, newWage);
		return hashTable.getEmployee(index);
	}
	
	public boolean deleteEmployee(int empId) {
		Employee emp = hashTable.delete(empId);
		if(emp == null) {
			throw new HashTableOperationException(EMConstants.EMP_NOT_FOUND);
		}
		return emp.key == -1;
	}
	
	public List<Employee> getAllEmployees() {
		return hashTable.getAllEmployees();
	}
	
	
// Very basic test script for HashTableADT and its various operations to run without UI.	
//    public static void main(String[] args) {
//        //1: Create the hash table
//    	HashTableADT  hashTable = new HashTableADT(16);
//    	List<Employee> employees = new ArrayList<Employee>();
//    	try {
//			 employees= importEmployeesFromTxtFile(new File("EmployeeDetails.txt"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    	for(Employee emp : employees)
//    		hashTable.insert(emp);
//    	
//
//    	System.out.println(hashTable.find(1234));
//    	hashTable.getEmployee(hashTable.find(1234)).details();
//    	System.out.println(hashTable.find(4213));
//    	hashTable.getEmployee(hashTable.find(4213)).details();
//    	System.out.println(hashTable.find(7453));
//    	hashTable.getEmployee(hashTable.find(7453)).details();
//    	System.out.println(hashTable.find(1125));
//    	hashTable.getEmployee(hashTable.find(1125)).details();
////		//2: Insert the data from the file into the hash table
////        try{
////			System.out.println("Loading data from the file....");
////        }
////        catch(Exception e){
////           System.out.println("File error: " + e.getMessage());
////        }
////
////        try (//3: Allow the user to manage the employees in the hash table:
////		Scanner input = new Scanner(System.in)) {
////			String option;
////			while (true){
////			    System.out.println("\n\nfind / insert / update / delete / exit ");
////			    System.out.print("What would you like to do? ");
////			    option = input.next();
////
////			    if(option.equalsIgnoreCase("exit"))
////			        break;
////			    else if(option.equalsIgnoreCase("find")){
////
////			    }
////			    else if(option.equalsIgnoreCase("insert")){
////
////			    }
////			    else if(option.equalsIgnoreCase("update")){
////
////			    }
////			    else if(option.equalsIgnoreCase("delete")){
////
////			    }
////			    else
////			        System.out.println("Invalid option selected.");
////
////			}
////		}
//    }

}
