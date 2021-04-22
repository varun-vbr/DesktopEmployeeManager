package businesslogic;

import java.util.ArrayList;
import java.util.List;

import resources.EMConstants;

public class HashTableADT {

    private Employee[] hashArray; // array holds hash table
    private int arraySize;
    private Employee deleted;
    private int cur_size;
    private int hashTableSize;
    private List<Employee> allEmployees;

    public HashTableADT(int size) {
		getsuitableArraySize(size);
		cur_size = 0;
		hashArray = new Employee[arraySize];
		hashTableSize = size;
		deleted = new Employee(-1, EMConstants.DELETED, EMConstants.DELETED, EMConstants.DELETED, -999);
		allEmployees = new ArrayList<Employee>();
    }

    private int h1(int key){	// hash function 1
        return key % arraySize;
    }
    
    private int h2(int key){	// hash function 2
        return 7 - key % 7;
    }
    
    private boolean isPrime(int n){
    	// Corner cases
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;
     
        // This is checked so that we can skip
        // middle five numbers in below loop
        if (n % 2 == 0 || n % 3 == 0)
            return false;
     
        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
     
        return true;
    }
    
    private void getsuitableArraySize(int userSize) {
    	int idealSize = (int)(userSize/0.7);
    	for (int i = idealSize; ;i++) {
            if (isPrime(i)) {
            	this.arraySize = i;
            		return;
            	
            }
                
        }
    }
    
    protected int size() {
    	return cur_size;
    }
    
    
    // if hash table is full
    protected boolean isFull() {
    	return cur_size == hashTableSize;
    }

    // function to insert key into hash table
    protected void insert(Employee item){
    	int key = item.key;
    	int newIndex = -1;
    	// get index from first hash
        int hashIndex = h1(key);
        
        // if collision occurs
        if (hashArray[hashIndex] != null && hashArray[hashIndex].key != -1){
        	// get index2 from second hash
        	int index2 = h2(key);
	        int i = 1;
	        	do{
	        		// get newIndex
	        		newIndex = (hashIndex + i * index2) % arraySize;
	        		i++;
	        } while(hashArray[newIndex] != null && hashArray[newIndex].key != -1);
	        
	        // if no collision occurs at new index, store
            // the key at new index	
	        hashArray[newIndex] = item;
	        
        }else {
        	// if no collision occurs
        	hashArray[hashIndex] = item;
        }
        allEmployees.add(item);
        cur_size++;
    }//end insert()

    protected int find(int key){ // find item with key
    	int index1 = h1(key);
        int index2 = h2(key);
        int i = 0;
        int itemIndex = (index1 + i * index2) % arraySize;
        while(hashArray[itemIndex] != null && hashArray[itemIndex].key != -1){
        	itemIndex = (index1 + i * index2) % arraySize;
        	i++;
            if(hashArray[itemIndex] != null && hashArray[itemIndex].key == key)//item found
                return itemIndex;
        }
        return -1; // item not found
    }//end find()

    protected Employee delete(int key){ // delete a DataItem

        int index = find(key);

        if (index == -1)
                return null;
        else{
             Employee temp = hashArray[index];
             hashArray[index] = deleted;
             allEmployees.remove(temp);
             cur_size--;
             return hashArray[index];
        }   
    } // end delete()
    
    protected List<Employee> getAllEmployees(){
		return allEmployees;
    }

    protected Employee getEmployee(int index){
        return hashArray[index];
    }

    protected void setEmployeeWage(int index, double amount){
        hashArray[index].setWage(amount);
    }

    protected void printEmployeeDetails(int index){
        hashArray[index].details();
    }

}