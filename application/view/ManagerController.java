package uk.ac.ucl.cs.gc01.me.application.view;

/**============================HANDLING Method Reference with SceneBuilder============================
 * Annotate either with via making methods public or via @FXML private void methodName()
 * Reference: http://code.makery.ch/library/javafx-8-tutorial/part3/
 */

import java.io.IOException;
import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import uk.ac.ucl.cs.gc01.me.application.MainApp;
import uk.ac.ucl.cs.gc01.me.application.model.LocalDateTimeConverter;
import uk.ac.ucl.cs.gc01.me.application.model.MenuItemModel;
import uk.ac.ucl.cs.gc01.me.application.model.OrderModel;
import uk.ac.ucl.cs.gc01.me.application.model.UserModel;

/**
 * The Class ManagerController.
 * @author Marisa Enhuber
 * @version: 1.3
 * @date (last update): 18/12/2016
 */
public class ManagerController {
	
	/** The app. create reference to main and set to 0 to launch when started */
	private MainApp app = null;

	/**
	 * Sets the app.
	 * @param input the new app
	 * add list to table view to display when logged in
	 */
	public void setApp(MainApp input) {
		this.app = input;

		employeeTable.setItems(this.app.userList);
		menuTable.setItems(this.app.menuList);
		orderTable.setItems(this.app.orderList);
	}

	/** The name column. */
	@FXML private TableColumn<UserModel, String> nameColumn;
	
	/** The password column. */
	@FXML private TableColumn<UserModel, String> passwordColumn;
	
	/** The access level column. */
	@FXML private TableColumn<UserModel, Boolean> accessLevelColumn;
	
	/** The employee table. */
	@FXML private TableView<UserModel> employeeTable;

	/** The menu table. */
	@FXML private TableView<MenuItemModel> menuTable;
	
	/** The item type column. */
	@FXML private TableColumn<MenuItemModel, String> itemTypeColumn;
	
	/** The item name column. */
	@FXML private TableColumn<MenuItemModel, String> itemNameColumn;
	
	/** The item price column. */
	@FXML private TableColumn<MenuItemModel, Integer> itemPriceColumn;


	/**
	 * ====================EXIT and LOGOUT BUTTON METHOD REFERENCE========================.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void ExitProgramme (ActionEvent event) throws IOException {
		this.app.exitProgramme();
	}

	/**
	 * Logout.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void Logout (ActionEvent event) throws IOException {
		this.app.showLogin();
	}

	/**
	 * ============================ADD USER ENTRY============================
	 * When manager clicks add, the values entered in TextFields are passed into observable "userList" 
	 * observList of user (userList) in MainApp -> access via this.app path
	 * The new entry is then included and assigned to the UserModel variables
	 * UserData is appended to the employeeTable within initialize() by calling getters and setters on Model
	 * Reference: https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm
	 * 
	 * ============================INPUT VALIDATON============================
	 * Input TextFields cannot be null or only whitespace
	 * Reference: http://stackoverflow.com/questions/10421395/whats-inside-the-textfield
	 * 
	 *  ============================CLEARING TEXFIELDS============================
	 * Clearing the TextFields after the name and password was manually added via Label.clear() method
	 * Reference: http://www.java2s.com/Code/Java/JavaFX/SetandclearvalueforTextField.htm
	 * 
	 */

	@FXML private Button addUserBtn;
	
	/** The user name text field. */
	@FXML private TextField userNameTextField;
	
	/** The password text field. */
	@FXML private TextField passwordTextField;

	/**
	 * Adds the user.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void addUser (ActionEvent event) throws IOException {
		if (userNameTextField.getText() != null && !userNameTextField.getText().trim().isEmpty() && 
				passwordTextField.getText() != null && !passwordTextField.getText().trim().isEmpty()) {
			this.app.userList.add (new UserModel(userNameTextField.getText(), 
					passwordTextField.getText(), 
					false));
			userNameTextField.clear();
			passwordTextField.clear();
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Valid Input");
			alert.setHeaderText("Nothing Entered");
			alert.setContentText("Please enter a letter, number or punctuation mark.");
			alert.showAndWait();
		}
	}

	/**
	 *  ===========================DELETING From USER TABLE ===========================
	 * getting the selected row (model), saved into an int variable, then removing the selected row with listMethod
	 * If-statement added to show popup dialog whenever user pushes the delete button while nothing is selected in the table
	 * Reference: http://code.makery.ch/library/javafx-8-tutorial/part3/
	 */

	@FXML
	private void deleteUser() {
		int selectedIndex = employeeTable.getSelectionModel().getSelectedIndex();
		// making sure something is selected -> if-else & with warning dialog
		if (selectedIndex >= 0) {
			employeeTable.getItems().remove(selectedIndex);
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Selection");
			alert.setHeaderText("Nothing Selected");
			alert.setContentText("Please select a row in the table to delete or edit an entry by double-clicking into the cell.");
			alert.showAndWait();
		}
	} 

	/**
	 *  ===========================ADDING and DELETING FROM MENUTABLE ===========================
	 * Same principle as above: getting content from TextFields and adding them to the menuList & on to TableView
	 * Reference for DishPrice (int to string): 
	 * http://stackoverflow.com/questions/14169240/getting-integer-values-from-textfield
	 * 
	 * ======================================================
	 * VALIDATING PRICE INPUT:
	 * Only numbers allowed, regex to validate digit input
	 * Reference: http://stackoverflow.com/questions/30935279/javafx-input-validation-textfield, http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
	 * 
	 */

	@FXML private TextField newDishTypeLabel;
	
	/** The new dish name label. */
	@FXML private TextField newDishNameLabel;
	
	/** The new dish price label. */
	@FXML private TextField newDishPriceLabel;

	/**
	 * Adds the dish.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void addDish (ActionEvent event) throws IOException {
		if (newDishTypeLabel.getText() != null && !newDishTypeLabel.getText().trim().isEmpty() &&
				newDishNameLabel.getText() != null && !newDishNameLabel.getText().trim().isEmpty() &&
				newDishPriceLabel.getText().matches("\\d+")) {
			this.app.menuList.add (new MenuItemModel(newDishTypeLabel.getText(), newDishNameLabel.getText(),
					Integer.parseInt(newDishPriceLabel.getText())));
			newDishTypeLabel.clear();
			newDishNameLabel.clear();
			newDishPriceLabel.clear();
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Invalid Input");
			alert.setHeaderText("Invalid Input");
			alert.setContentText("Please use letters to enter dish name and type and numbers for the price.");
			alert.showAndWait();
		}
	}
	
	/**
	 * Deletes the dish from menutable.
	 *
	 * @param selectedIndex to get handle on Model and items 
	 * remove from list (same logic as above)
	 * 
	 */

	@FXML
	private void deleteDish() {
		int selectedIndex = menuTable.getSelectionModel().getSelectedIndex();
		// making sure something is selected -> if-else & with warning popup
		if (selectedIndex >= 0) {
			menuTable.getItems().remove(selectedIndex);
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Selection");
			alert.setHeaderText("Nothing Selected");
			alert.setContentText("Please select a row in the table to delete or edit an entry by double-clicking into the cell.");
			alert.showAndWait();
		}
	}

/** ==============================SHOW ORDER DETAILS=================== 
 * Save row that manager selects from order table into variable (from Model & getItem) 
 * Get items saved from that order within dishList and print onto orderDetails table. 
 * orderTable with orderInfo of type OrderModel and Respective Column for each type of table
 */
	
	@FXML private TableView<OrderModel> orderTable;
	
	/** The order ID column. */
	@FXML private TableColumn<OrderModel, Integer> orderIDColumn;
	
	/** The order user name column. */
	@FXML private TableColumn<OrderModel, String> orderUserNameColumn;
	
	/** The order table column. */
	@FXML private TableColumn<OrderModel, Integer> orderTableColumn;
	
	/** The order time stamp column. */
	@FXML private TableColumn<OrderModel, LocalDateTime> orderTimeStampColumn;
	
	/** The order total value column. */
	@FXML private TableColumn<OrderModel, Integer> orderTotalValueColumn;

	/** The order details table. 
	 * orderDetailsTable for dishes of type MenuItemModel
	 */
	@FXML private TableView<MenuItemModel> orderDetailsTable;
	
	/** The order details dish type column. */
	@FXML private TableColumn<MenuItemModel, String> orderDetailsDishTypeColumn;
	
	/** The order details dish name column. */
	@FXML private TableColumn<MenuItemModel, String> orderDetailsDishNameColumn;
	
	/** The order details dish price column. */
	@FXML private TableColumn<MenuItemModel, Integer> orderDetailsDishPriceColumn;

	/**
	 * Show order details.
	 */
	@FXML
	private void showOrderDetails() {
		// put user choice of selected row of Order Table into variable to see which dishes were selected
		OrderModel selectedOrder = orderTable.getSelectionModel().getSelectedItem();
		// ensure that something is selected
		if (selectedOrder != null) {
			// add the items of the dishlist via the selected order onto table -> init to set values
			orderDetailsTable.setItems(selectedOrder.dishList());
		}
	}

/** ==============================EMPLOYEE ACTIVITY===================. */

	@FXML private TableView<OrderModel> activityLogTable;
	
	/** The activity log order ID column. */
	@FXML private TableColumn<OrderModel, Integer> activityLogOrderIDColumn;
	
	/** The activity log table nr column. */
	@FXML private TableColumn<OrderModel, Integer> activityLogTableNrColumn;
	
	/** The activity log time stamp column. */
	@FXML private TableColumn<OrderModel, LocalDateTime> activityLogTimeStampColumn;
	
	/** The activity log order value column. */
	@FXML private TableColumn<OrderModel, Integer> activityLogOrderValueColumn;

	/**
	 * Show employee activity.
	 */
	@FXML
	private void showEmployeeActivity() {
		UserModel selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
		if (selectedEmployee != null) {
			activityLogTable.setItems(selectedEmployee.createdOrdersList());
		}
	}

/**
 * ==============================IMPORT/ EXPORT ORDERS===================.
 */

	@FXML
	private void exportOrders() {
		this.app.saveOrdersToFile();
	}

	/**
	 * Import orders.
	 */
	@FXML
	private void importOrders() {
		this.app.importOrdersFromFile();
	}

/**
 * ======================================================================================.
 */
	@FXML	
	private void initialize() {
		/**
		 * Manager's editable Employee TableView
		 * Getting variables from UserModel over MainConstructor reference & setApp()
		 * print values via observable List onto GUI - filling each cell value via getter, setters
		 * Reference to make TableCells editable: http://stackoverflow.com/questions/19335196/how-to-make-javafx-tableview-cells-editable
		 * Reference to add editable CheckBoxes to accessLevelCells: http://www.programcreek.com/java-api-examples/index.php?api=javafx.scene.control.cell.CheckBoxTableCell
		 */
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().userName());
		nameColumn.setCellFactory(TextFieldTableCell.<UserModel>forTableColumn());
		passwordColumn.setCellValueFactory(cellData -> cellData.getValue().userPassword());
		passwordColumn.setCellFactory(TextFieldTableCell.<UserModel>forTableColumn());
		accessLevelColumn.setCellValueFactory(cellData -> cellData.getValue().isManager());
		accessLevelColumn.setCellFactory(CheckBoxTableCell.<UserModel>forTableColumn(accessLevelColumn));

		/**
		 * Manager's editable Menu TableView
		 * Reference to make Integer fields editable: http://stackoverflow.com/questions/20020037/editing-a-number-cell-in-a-tableview
		 */
		itemTypeColumn.setCellValueFactory(cellData -> cellData.getValue().dishType());
		itemTypeColumn.setCellFactory(TextFieldTableCell.<MenuItemModel>forTableColumn());
		itemNameColumn.setCellValueFactory(cellData -> cellData.getValue().dishName());
		itemNameColumn.setCellFactory(TextFieldTableCell.<MenuItemModel>forTableColumn());
		itemPriceColumn.setCellValueFactory(cellData -> cellData.getValue().dishPrice().asObject());
		itemPriceColumn.setCellFactory(TextFieldTableCell.<MenuItemModel, Integer>forTableColumn(new IntegerStringConverter()));

		/**
		 * Manager' view of the Orders 
		 * Filling Table for orderDetails
		 * Reference for LocalDateTime Converter: http://stackoverflow.com/questions/38045546/formatting-an-objectpropertylocaldatetime-in-a-tableview-column
		 */
		orderIDColumn.setCellValueFactory(cellData -> cellData.getValue().orderID().asObject());
		orderUserNameColumn.setCellValueFactory(cellData -> cellData.getValue().getUser().userName());
		orderTableColumn.setCellValueFactory(cellData -> cellData.getValue().getTable().tableID().asObject());
		orderTimeStampColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeConverter()));
		orderTimeStampColumn.setCellValueFactory(cellData -> cellData.getValue().orderTimeStamp());
		orderTotalValueColumn.setCellValueFactory(cellData -> cellData.getValue().totalValue().asObject());

		/**
		 * Get Dishes from each order
		 * filling each cell via getter method in MenuItemModel
		 */
		orderDetailsDishTypeColumn.setCellValueFactory(cellData -> cellData.getValue().dishType());
		orderDetailsDishNameColumn.setCellValueFactory(cellData -> cellData.getValue().dishName());
		orderDetailsDishPriceColumn.setCellValueFactory(cellData -> cellData.getValue().dishPrice().asObject());

		/**
		 * Manager' view of Employee Activity
		 * for Table number: 
		 * getter within OrderModel for Table Nr: returns instance of Table Model to reference
		 * then getter within Table Model to get value for table number
		 * Reference for LocalDateTime Converter: http://stackoverflow.com/questions/38045546/formatting-an-objectpropertylocaldatetime-in-a-tableview-column
		 */
		activityLogOrderIDColumn.setCellValueFactory(cellData -> cellData.getValue().orderID().asObject());
		activityLogTableNrColumn.setCellValueFactory(cellData -> cellData.getValue().getTable().tableID().asObject());
		activityLogTimeStampColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeConverter()));
		activityLogTimeStampColumn.setCellValueFactory(cellData -> cellData.getValue().orderTimeStamp());
		activityLogOrderValueColumn.setCellValueFactory(cellData -> cellData.getValue().totalValue().asObject());

	}

}
