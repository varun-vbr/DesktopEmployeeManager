package resources;

public interface EMConstants {
	public static final String EMP_LOAD_FAILURE = "Atleast one of the Employees could not be loaded from the file";
	public static final String TXT_EXTN = "txt";
	public static final String INVALID_FILE = "Invalid file type. Only text files are allowed";
	public static final String INVALID_FILE_FORMAT = "Invalid file content format. Check the contents of the file.";
	public static final String DELETED = "deleted";
	public static final String US_PHONE_REGEX_WITHOUT_AREA_CODE = "\\d{3}[-\\.\\s]\\d{4}";
	public static final String INVALID_PHONE_NOS_IN_FILE = "Invalid phone numbers in the file.";
	public static final String MAX_EMP_LIMIT_REACHED = "Number of Employees imported exceeded the maximum number of Employees allowed";
	public static final String DUP_KEYS_IN_FILE = "There are duplicate keys in the file. Make sure Employee keys are unique.";
	public static final String DUP_EMP = "Employee with the key already exists";
	public static final String HASHTABLE_FULL = "Maximum Employee storage limit reached";
	public static final String EMP_NOT_FOUND = "Employee not found";
	public static final String ERROR = "Error";
	public static final String STACKTRACE_UNAVAILABLE = "Stacktrace unavailable. Check for input validation failures";
	public static final String STACKTRACE = "The exception stacktrace was:";
	public static final String HT_INITIALIZATION_ERROR = "HashTable Initialization Error";
	public static final String VALIDATION_ERROR = "Validation Error";
	public static final String NO_EMP = "No. Employees";
	public static final String SEL_FILE = "Selected File";
	public static final String FLD_REQUIRED = "Field Required";
	public static final String INV_NUM = "Enter a valid Number";
	public static final String INV_WAGE = "Enter a valid wage";
	public static final String INV_PHONE = "Invalid Phone Number";
	public static final String EMP_ID = "Id";
	public static final String FNAME = "FirstName";
	public static final String LNAME = "LastName";
	public static final String PHONE = "Phone";
	public static final String WAGE = "Wage";
	public static final String CHOOSE_FILE = "Choose import file";
	public static final String TXT_FILE_EXT_FILTER = "TXT files (*.txt)";
	public static final String TXT_FILE_EXT = "*.txt";
	public static final String FILE_CHOOSER_ERROR = "File chooser Error";
	public static final String FILE_CHOOSER_OPEN_FAILURE = "File chooser could not be opened";
	public static final String IMPORT_ERROR = "Import Error";
	public static final String FILE_NOT_CHOSEN_OR_FOUND = "No file was chosen for import or the file was not found";
	public static final String EMP_IMPORT_FAILURE = "There was an Error importing Employees";
	public static final String INSERT_FAILURE = "There was an Error inserting Employee";
	public static final String SEARCH_FAILURE = "There was an Error searching Employee";
	public static final String UPDATE_FAILURE = "There was an Error updating Employee";
	
	
}
