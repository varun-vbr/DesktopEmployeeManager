package view;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

import businesslogic.Employee;
import businesslogic.TableEmployee;
import businesslogic.UsingHashTable;
import exceptions.HashTableOperationException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import resources.EMConstants;
import javafx.scene.control.TabPane;

public class EmployeeManagerController{
	
	
    @FXML
    private JFXTextField noEmpTxtFild;

    @FXML
    private TableColumn<TableEmployee, String> fNameTblCol;

    @FXML
    private JFXTextField fndEmpIdTxtFld;

    @FXML
    private AnchorPane insAnchorPane;

    @FXML
    private TableColumn<TableEmployee, String> wageTblCol;

    @FXML
    private TableColumn<TableEmployee, String> empIdTblCol;

    @FXML
    private JFXButton fileChooseButton;

    @FXML
    private JFXButton updBtn;

    @FXML
    private JFXTextField phoneTxtFld;

    @FXML
    private Tab fndTab;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXTextField delEmpIdTxtField;

    @FXML
    private Label updValLbl;

    @FXML
    private JFXTextField insEmpIdTxt;

    @FXML
    private JFXTextField lNameTxtFld;

    @FXML
    private Label insValLbl;

    @FXML
    private TableView<TableEmployee> empTblView;

    @FXML
    private JFXTextField insWageTxtFld;

    @FXML
    private Tab updTab;

    @FXML
    private JFXButton deleteBtn;
    
    @FXML
    private JFXTabPane opTabPane;

    @FXML
    private Tab insTab;

    @FXML
    private JFXButton fndSearchBtn;
    
    @FXML
    private JFXTextField updEmpIdTxtFld;

    @FXML
    private JFXTextField fNameTxtFld;

    @FXML
    private TableColumn<TableEmployee, String> lNameTblCol;

    @FXML
    private JFXButton insertBtn;

    @FXML
    private JFXTextField fileTxtFld;

    @FXML
    private JFXTextField updNewWageTxtFld;

    @FXML
    private JFXButton uploadButton;

    @FXML
    private TableColumn<TableEmployee, String> phnTblCol;
    
    private UsingHashTable usingHTable;
    
    private File file = null;
    
    ObservableList<TableEmployee> allImportedEmployees = FXCollections.observableArrayList();
    
    private void showErrorMessage(Exception ex, String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(EMConstants.ERROR);
        alert.setHeaderText(title);
        alert.setContentText(content);

        String exceptionText = EMConstants.STACKTRACE_UNAVAILABLE;
		if (ex != null && ex.getCause() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			exceptionText = sw.toString();
		}

        Label label = new Label(EMConstants.STACKTRACE);

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }
    
    private void showErrorMessage(String title, String content) {
    	showErrorMessage(null, title, content);	
    }
    
    public boolean importEmployeesFromFile(final ActionEvent event, File file, int numEmployees) {
    	boolean success = false;
    	if(file != null) {
			try {
				usingHTable = new UsingHashTable(file, numEmployees);
				success = true;
			} catch (Exception e) {
				showErrorMessage(e, EMConstants.HT_INITIALIZATION_ERROR, e.getMessage());
			}
		}
		return success;
    }
    
    private void attachValidatorAndValidateIfRequired(JFXTextField textField, ValidatorBase validator) {
    	this.attachValidatorAndValidateIfRequired(textField, validator, null);
    }
    
    private boolean validateTextFields(List<JFXTextField> textFields) {
    	for(JFXTextField textField : textFields) {
    		if(!textField.validate()) {
        		showErrorMessage(EMConstants.VALIDATION_ERROR, textField.getPromptText() + " : " + textField.getActiveValidator().getMessage());
        		return false;
        	}
    	}
    	return true;
    }
    
    private void attachValidatorAndValidateIfRequired(JFXTextField textField, ValidatorBase validator, String regex){
    	if(regex != null && validator instanceof RegexValidator) {
    		((RegexValidator)validator).setRegexPattern(regex);
    	}
        textField.setValidators(validator);
    	textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    boolean valid = textField.validate();
                    boolean tabDisabled = opTabPane.isDisable();
                    String fldPrompt = textField.getPromptText();
                       	if(valid && fldPrompt.equalsIgnoreCase(EMConstants.NO_EMP)) {
                    		fileChooseButton.setDisable(false);
                    		fileTxtFld.clear();
                    		allImportedEmployees.clear();
                    		uploadButton.setDisable(true);
                    		opTabPane.setDisable(true);
                    	} else if(valid && fldPrompt.equalsIgnoreCase(EMConstants.SEL_FILE)){
                    		uploadButton.setDisable(false);
                    	} else if(!tabDisabled && valid && opTabPane.getSelectionModel().getSelectedIndex() == 0) {
                    		 insertBtn.setDisable(false);
                    	} else if(!tabDisabled && valid && opTabPane.getSelectionModel().getSelectedIndex() == 1) {
                    		fndSearchBtn.setDisable(false);
                    	} else if(!tabDisabled && valid && opTabPane.getSelectionModel().getSelectedIndex() == 2) {
                    		updBtn.setDisable(false);
                    	} else if(!tabDisabled && valid && opTabPane.getSelectionModel().getSelectedIndex() == 3) {
                    		deleteBtn.setDisable(false);
                    	}
                    }

                }
    		});
    }
    
    public void clearAllTabTextFields() {
    	uploadButton.setDisable(true);
    	insertBtn.setDisable(true);
    	fndSearchBtn.setDisable(true);
    	updBtn.setDisable(true);
    	deleteBtn.setDisable(true);
    	updEmpIdTxtFld.clear();
    	updNewWageTxtFld.clear();
    	delEmpIdTxtField.clear();
    	fndEmpIdTxtFld.clear();
    	insEmpIdTxt.clear();
    	insWageTxtFld.clear();
    	fNameTxtFld.clear();
    	lNameTxtFld.clear();
    	phoneTxtFld.clear();
    }
    
   
    
    @FXML
    protected void initialize() {
    	
    	RequiredFieldValidator reqValidator = new RequiredFieldValidator(EMConstants.FLD_REQUIRED);
    	NumberValidator numValidator = new NumberValidator(EMConstants.INV_NUM);
    	DoubleValidator doubleValidator = new DoubleValidator(EMConstants.INV_WAGE);
    	
        attachValidatorAndValidateIfRequired(delEmpIdTxtField, reqValidator);
        attachValidatorAndValidateIfRequired(delEmpIdTxtField, numValidator);
        attachValidatorAndValidateIfRequired(noEmpTxtFild, reqValidator);
        attachValidatorAndValidateIfRequired(noEmpTxtFild, numValidator);
        attachValidatorAndValidateIfRequired(fNameTxtFld, reqValidator);
        attachValidatorAndValidateIfRequired(fileTxtFld, reqValidator);
        attachValidatorAndValidateIfRequired(fndEmpIdTxtFld, reqValidator);
        attachValidatorAndValidateIfRequired(fndEmpIdTxtFld, numValidator);
        attachValidatorAndValidateIfRequired(insEmpIdTxt, reqValidator);
        attachValidatorAndValidateIfRequired(insEmpIdTxt, numValidator);
        attachValidatorAndValidateIfRequired(insWageTxtFld, reqValidator);
        attachValidatorAndValidateIfRequired(insWageTxtFld, doubleValidator);
        attachValidatorAndValidateIfRequired(lNameTxtFld, reqValidator);
        attachValidatorAndValidateIfRequired(updEmpIdTxtFld, reqValidator);
        attachValidatorAndValidateIfRequired(updEmpIdTxtFld, numValidator);
        attachValidatorAndValidateIfRequired(updNewWageTxtFld, reqValidator);
        attachValidatorAndValidateIfRequired(updNewWageTxtFld, doubleValidator);
        attachValidatorAndValidateIfRequired(phoneTxtFld, reqValidator);
        attachValidatorAndValidateIfRequired(phoneTxtFld, new RegexValidator(EMConstants.INV_PHONE), EMConstants.US_PHONE_REGEX_WITHOUT_AREA_CODE);
        empIdTblCol.setCellValueFactory(new PropertyValueFactory<TableEmployee, String>(EMConstants.EMP_ID));
	    fNameTblCol.setCellValueFactory(new PropertyValueFactory<TableEmployee, String>(EMConstants.FNAME));
	    lNameTblCol.setCellValueFactory(new PropertyValueFactory<TableEmployee, String>(EMConstants.LNAME));
	    phnTblCol.setCellValueFactory(new PropertyValueFactory<TableEmployee, String>(EMConstants.PHONE));
	    wageTblCol.setCellValueFactory(new PropertyValueFactory<TableEmployee, String>(EMConstants.WAGE));
	    empTblView.getColumns().clear();
	    empTblView.setItems(allImportedEmployees);
	    empTblView.getColumns().addAll(empIdTblCol, fNameTblCol, lNameTblCol, phnTblCol, wageTblCol);
	    opTabPane.getSelectionModel().selectedItemProperty().addListener(
	    	    new ChangeListener<Tab>() {
	    	        @Override
	    	        public void changed(ObservableValue<? extends Tab> ov, Tab oldValue, Tab newValue) {
	    	        	clearAllTabTextFields();
	    	            if(oldValue.getText().equalsIgnoreCase("Find")) {
	    	            	allImportedEmployees.clear();
	    	            	for(Employee employee : usingHTable.getAllEmployees())
	    	        			allImportedEmployees.add(new TableEmployee(employee));
	    	            }
	    	        }
	    	    }
	    	);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	noEmpTxtFild.requestFocus();
            }
        });
    };
            
    @FXML
    public void chooseFile(final ActionEvent event) {
    	Node node = (Node)event.getSource();
    	if(node != null && node.getScene() != null) {
    		FileChooser fileChooser = new FileChooser();
    		fileChooser.setTitle(EMConstants.CHOOSE_FILE);
    		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(EMConstants.TXT_FILE_EXT_FILTER, EMConstants.TXT_FILE_EXT);
    		fileChooser.getExtensionFilters().add(extFilter);
			file = fileChooser.showOpenDialog(node.getScene().getWindow());
			   if(file != null) {
	    		fileTxtFld.setText(file.getAbsolutePath());
	    		uploadButton.setDisable(false);
			   }
		}
    	if(node == null || node.getScene() == null){
    		showErrorMessage(EMConstants.FILE_CHOOSER_ERROR, EMConstants.FILE_CHOOSER_OPEN_FAILURE);
    	}
    }
    
    @FXML
    public void importFromFile(final ActionEvent event){
    	List<JFXTextField> textFlds = new ArrayList<JFXTextField>();
    	textFlds.add(noEmpTxtFild);
    	textFlds.add(fileTxtFld);
    	if (!validateTextFields(textFlds)) {
    		return;
		}
		String empCount = noEmpTxtFild.getText();	
    	try {
			boolean success = importEmployeesFromFile(event, file, Integer.parseInt(empCount));
			if(success) {
				allImportedEmployees.clear();
				for(Employee employee : usingHTable.getAllEmployees())
					allImportedEmployees.add(new TableEmployee(employee));
				opTabPane.setDisable(false);
				fileTxtFld.clear();
				noEmpTxtFild.clear();
				uploadButton.setDisable(true);
				fileChooseButton.setDisable(true);
				
			} else {
				showErrorMessage(EMConstants.IMPORT_ERROR, EMConstants.FILE_NOT_CHOSEN_OR_FOUND);
			}
		} catch (Exception e) {
			showErrorMessage(e, EMConstants.EMP_IMPORT_FAILURE, e.getMessage());
		}
    	
    }
     
    @FXML
    public void insertEmployee(final ActionEvent event) {
    	List<JFXTextField> insTxtFlds = new ArrayList<JFXTextField>();
    	insTxtFlds.add(insEmpIdTxt);
    	insTxtFlds.add(fNameTxtFld);
    	insTxtFlds.add(lNameTxtFld);
    	insTxtFlds.add(phoneTxtFld);
    	insTxtFlds.add(insWageTxtFld);
    	if (!validateTextFields(insTxtFlds)) {
    		return;
		}
    	Employee newEmp = new Employee(Integer.parseInt(insEmpIdTxt.getText()), fNameTxtFld.getText(), lNameTxtFld.getText(), phoneTxtFld.getText(), Double.parseDouble(insWageTxtFld.getText()));
    	try {
    		List<Employee> allEmps = usingHTable.getAllEmployees();
			if(usingHTable.addEmployee(newEmp)) {
				allImportedEmployees.clear();
				for(Employee employee : usingHTable.getAllEmployees())
					allImportedEmployees.add(new TableEmployee(employee));
			}
		} catch (Exception e) {
			showErrorMessage(e, EMConstants.INSERT_FAILURE, e.getMessage());
		}
    }
    
    @FXML
    public void findEmployee(final ActionEvent event) {
    	List<JFXTextField> fndTxtFlds = new ArrayList<JFXTextField>();
    	fndTxtFlds.add(fndEmpIdTxtFld);
    	if(!validateTextFields(fndTxtFlds)) {
    		return;
    	}
    	
    	Employee emp = null;
		try {
			emp = usingHTable.findEmployee(Integer.parseInt(fndEmpIdTxtFld.getText()));
		} catch (Exception e) {
			showErrorMessage(e, EMConstants.SEARCH_FAILURE, e.getMessage());
		}
    	allImportedEmployees.clear();
    	allImportedEmployees.add(new TableEmployee(emp));
    	clearAllTabTextFields();
    }
    
    @FXML
    public void updateEmployee(final ActionEvent event) {
    	List<JFXTextField> updTxtFlds = new ArrayList<JFXTextField>();
    	updTxtFlds.add(updEmpIdTxtFld);
    	updTxtFlds.add(updNewWageTxtFld);
    	if(!validateTextFields(updTxtFlds)) {
    		return;
    	}
    	try {
			Employee emp = null;
			emp = usingHTable.findEmployee(Integer.parseInt(updEmpIdTxtFld.getText()));
			Employee updatedEmployee = usingHTable.updateWage(Integer.parseInt(updEmpIdTxtFld.getText()), Double.parseDouble(updNewWageTxtFld.getText()));
			if(usingHTable.getAllEmployees().contains(updatedEmployee)) {
				allImportedEmployees.clear();
				for(Employee employee : usingHTable.getAllEmployees())
					allImportedEmployees.add(new TableEmployee(employee));
			}
			clearAllTabTextFields();
		} catch (Exception e) {
			showErrorMessage(e, EMConstants.UPDATE_FAILURE, e.getMessage());
		}
    }
    
    @FXML 
    public void deleteEmployee(final ActionEvent event) {
    	List<JFXTextField> delTxtFlds = new ArrayList<JFXTextField>();
    	delTxtFlds.add(delEmpIdTxtField);
    	if(!validateTextFields(delTxtFlds)) {
    		return;
    	}
    	Employee delEmp = usingHTable.findEmployee(Integer.parseInt(delEmpIdTxtField.getText()));
    	if(usingHTable.deleteEmployee(Integer.parseInt(delEmpIdTxtField.getText()))) {
    		allImportedEmployees.clear();
    		for(Employee employee : usingHTable.getAllEmployees())
    			allImportedEmployees.add(new TableEmployee(employee));
    	}
    	clearAllTabTextFields();
    }
}
