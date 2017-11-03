package uk.ac.ucl.cs.gc01.me.application.view;
/**
 * @author Marisa Enhuber
 * @Order Registration Form - Controller Class
 * @version: 1.4
 * @date (last update): 18/12/2016
 * 
 * ============================ORDER REGISTRATION MECHANISM============================
 * @Reference: http://code.makery.ch/library/javafx-8-tutorial/
 * Variables from Model Classes 
 * Getter & Setter encapsulated 
 * Data within Observ List & Constructors in MainApp
 * Creating a path to MainApp (this.app) by initializing MainApp object 
 * Hooking up the data with the GUI/FXML -> within Initialize
 * Initializing the table with the three columns
 * by calling the first Getter function of the Model Class
 * 
 * ============================BINDING============================
 * userLabel to data model to get the currently logged in user
 * for employee activity tracking
 * Reference: http://stackoverflow.com/questions/13227809/displaying-changing-values-in-javafx-label
 * =================
 * TableNumber -> IntegerProperty
 * Reference: http://stackoverflow.com/questions/33146167/javafx-binding-label-with-int-value
 */

import java.io.IOException;
import java.time.LocalDateTime;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import uk.ac.ucl.cs.gc01.me.application.MainApp;
import uk.ac.ucl.cs.gc01.me.application.model.LocalDateTimeConverter;
import uk.ac.ucl.cs.gc01.me.application.model.MenuItemModel;
import uk.ac.ucl.cs.gc01.me.application.model.OrderModel;
/**
 * The Class OrderRegistrationController.
 */
public class OrderRegistrationController {

	/** The lbl order reg. */
	@FXML private Label lblOrderReg;

	/** The lbl table nr. */
	@FXML private Label lblTableNr;

	/** The t1. */
	@FXML private Button T1;

	/** The t2. */
	@FXML private Button T2;

	/** The t3. */
	@FXML private Button T3;

	/** The t4. */
	@FXML private Button T4;

	/** The menu table. */
	@FXML private TableView<MenuItemModel> menuTable;

	/** The item type column. */
	@FXML private TableColumn<MenuItemModel, String> itemTypeColumn;

	/** The item name column. */
	@FXML private TableColumn<MenuItemModel, String> itemNameColumn;

	/** The item price column. */
	@FXML private TableColumn<MenuItemModel, Integer> itemPriceColumn;

	/** The item type label. */
	@FXML private Label itemTypeLabel;

	/** The item name label. */
	@FXML private Label itemNameLabel;

	/** The item price label. */
	@FXML private Label itemPriceLabel;

	/** The employee label. */
	@FXML private Label employeeLabel;

	/** The ordered dishes table. 
	 * Graphical representation of dishPrice list items 
	 * added into each cell in initialize
	 * Iterating through the list to find the item of the table for one column
	 */
	@FXML private TableView<MenuItemModel> orderedDishesTable;

	/** The order item type column. */
	@FXML private TableColumn<MenuItemModel, String> orderItemTypeColumn;

	/** The order item name column. */
	@FXML private TableColumn<MenuItemModel, String> orderItemNameColumn;

	/** The order item price column. */
	@FXML private TableColumn<MenuItemModel, Integer> orderItemPriceColumn;

	/** The order IDFMXL label. */
	@FXML private Label orderIDFMXLLabel;

	/** The order ID placeholder label. */
	@FXML private Label orderIDPlaceholderLabel;

	/** The lbl time stamp. */
	@FXML private Label lblTimeStamp;

	/** The order comments. */
	@FXML private TextArea orderComments;

	/**
	 * ============================METHOD TO INVOKE INSTANCE OF MAINAPP============================
	 * to instantiate the lists on the tableView & add data items
	 * invoked methods used for order specific data
	 * binding label to model to observe and represent changing values
	 * ==============
	 * Reference: http://code.makery.ch/library/javafx-8-tutorial/part2/
	 */

	/** The app. 
	 *  at the point where app is opened, no order exists*/
	private MainApp app = null;

	/**
	 * Sets the app.
	 * @param input the new app
	 */
	public void setApp(MainApp input) {
		this.app = input;

		// create the new order if none yet exists for that table
		if (this.app.currentTable.activeOrder() == null) {
			int orderID = this.app.nextOrderID.get(); // local var to reference & load to next available order ID

			// create new instance of OrderModel for new order with Constructor data
			OrderModel order = new OrderModel(
					orderID, calculateTotalValue(),"", LocalDateTime.now(), this.app.currentTable, this.app.currentUser
					);
			this.app.currentTable.setActiveOrder(order);
			this.app.orderList.add(order);
			this.app.nextOrderID.set(orderID + 1); // adding up orderIDs whenever new order is created
		}

		/**"Printing" values from Observable Lists onto the the menu Table View 
		 * from menuList into orderList via call/ reference 
		 * table number & order Model for dishList
		 * setting each cell value within initialize
		 */
		menuTable.setItems(this.app.menuList);
		employeeLabel.textProperty().bind(this.app.currentUser.userName()); // getting userName from Model and mainApp reference 
		lblTableNr.textProperty().bind(this.app.currentTable.tableID().asString()); // getting tableID from Model and mainApp reference 

		/** 
		 * New instance of activeOrder with path to currentTable in main
		 * enables to click on a table again and open same order
		 */
		OrderModel activeOrder = this.app.currentTable.activeOrder();

		/**
		 * activating the order from Add
		 */
		orderedDishesTable.setItems(activeOrder.dishList());
		lblTimeStamp.setText(activeOrder.orderTimeStamp().get().format(LocalDateTimeConverter.dateFormat));

		/**
		 * =====Order ID=====
		 * every time app launches orderReg form, 
		 * nextOrderId needs to be initialised to be shown
		 */
		orderIDPlaceholderLabel.textProperty().bind(this.app.currentTable.activeOrder().orderID().asString()); 

		/**
		 * Invoked in setApp 
		 * to keep the totalValue once orderRegView is closed
		 */
		calculateTotalValue();
		displayTotalValue();

		/**
		 * TextArea
		 * needs Bidirectional binding for XML export
		 * ========
		 * Reference:
		 * http://stackoverflow.com/questions/11314863/javafx-bidirectional-binding-not-working-with-the-control-becoming-not-editable
		 */
		Bindings.bindBidirectional(orderComments.textProperty(), this.app.currentTable.activeOrder().orderComments());
	}

	/**
	 * ============================ADD TO ORDER BUTTON============================
	 * Get Selected Row from menuTable-> getSelectionModel & ItemProperties
	 * Save selection into @param: selectedMenuDish
	 * Making use of param in 3 ways: 
	 * -> passing the selection (reference var) into activeOrderModel
	 * -> adding the MenuSelection to the ObservList "dishList" by passing it as parameter
	 * 		=> will save selected row into dishList, 
	 * reference back to OrderModel fills dishList with data
	 * 		=> if statement for User Validation
	 * ===========================================================================
	 * "Printing" selection onto OrderTableView:
	 * Filling each row of the saved dishSelection List (as @param) 
	 * into the orderedDishesTable (setItems()) 
	 * 		=> within initialize
	 * ===========================================================================
	 * Since clicking on table again needs to save data
	 * 		=> adding creates order
	 * activeOrder in TableModel: setting the path to reference in main this.app.currentTable.activeOrder()
	 * filling dishList with data of selection: dishList().add(selectedMenuDish);
	 * adding total value by invoking calculate() function
	 * 
	 * ============================FEEDBACK to USER============================
	 * Show popup dialog if nothing is selected (same if-else logic on variable as manager table)
	 * =========================================================================
	 * References: 
	 * http://code.makery.ch/library/javafx-8-tutorial/part2/
	 * http://docs.oracle.com/javafx/2/ui_controls/table-view.htm
	 * http://stackoverflow.com/questions/17388866/getting-selected-item-from-a-javafx-tableview
	 * http://docs.oracle.com/javafx/2/api/javafx/scene/control/TableCell.html#getTableColumn()
	 */

	/** The add to order Button. */
	@FXML private Button addToOrderBtn;

	/**
	 * Adds the to order.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	public void addToOrder() throws IOException {
		MenuItemModel selectedMenuDish = menuTable.getSelectionModel().selectedItemProperty().get();
		if (selectedMenuDish != null) {
			this.app.currentTable.activeOrder().dishList().add(selectedMenuDish);
			this.app.currentTable.activeOrder().totalValue().set(this.calculateTotalValue()); // saving added total value to activeOrder
			displayTotalValue(); // updates label with newly added dish prices
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Selection");
			alert.setHeaderText("Nothing Selected");
			alert.setContentText("Please select a row in the Menu Table to add dishes to the order.");
			alert.showAndWait();	
		}
	}

	/** ============================REMOVE FROM ORDER BUTTON============================
	 * Reverse logic from above:
	 * selected row saved into new param
	 * validating that param is not null (making sure row is selected)
	 * remove function on dishList from active Order path
	 */
	@FXML private Button removeFromOrderBtn;

	/**
	 * Removes the from order.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	public void removeFromOrder() throws IOException {
		MenuItemModel removeSelectedDish = orderedDishesTable.getSelectionModel().selectedItemProperty().get();
		if (removeSelectedDish != null) {
			this.app.currentTable.activeOrder().dishList().remove(removeSelectedDish);
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Selection");
			alert.setHeaderText("Nothing Selected");
			alert.setContentText("Please select a row in the Order Table to remove an entry from the order.");
			alert.showAndWait();
		}
	}

	/** ============================SAVE ORDER BUTTON============================ 
	 * Now, save order only needs to go back to Restaurant screen 
	 * since order is created via Add button functionality (makes sure data is not lost)
	 */
	@FXML private Button btnSave;

	/**
	 * Save order.
	 */
	@FXML
	public void saveOrder() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("New Order");
		alert.setHeaderText("Order Saved");
		alert.setContentText("Menu selection saved.");
		alert.showAndWait();
		// this.app.showRestaurantView();
	}

	/**============================CANCEL ORDER=============================
	 * "resetting" activeOrder reference to null as order is cancelled
	 * Then going back to Restaurant View
	 */
	@FXML private Button btnDelete;

	/**
	 * Delete order.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void deleteOrder() throws IOException {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Delete Order");
		alert.setHeaderText("Confirm Order Delete");
		alert.setContentText("Please confirm that you want to delete the current order.");
		alert.showAndWait();
		// "reset" activeOrder reference variable to null
		this.app.currentTable.setActiveOrder(null);
		this.app.showRestaurantView();
	}

	/**
	 * ============================PRINT RECEIPT=============================
	 * Export order as XML - call to resp. function in main
	 * then reset active Order to 0
	 * then go back to Restaurant View
	 */
	@FXML private Button exportOrderButton;

	/**
	 * Export order.
	 */
	@FXML
	private void exportOrder(){
		this.app.saveOrdersToFile();
		this.app.currentTable.setActiveOrder(null);
		this.app.showRestaurantView();
	}

	/** ============================GO BACK=============================
	 * Going back to Restaurant View
	 */
	@FXML private Button btnGoBack;

	/**
	 * Go back.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void goBack() throws IOException {
		this.app.showRestaurantView();
	}

	/**
	 * ============================TOTAL VALUE============================
	 * Two methods used: 
	 * 1) update value in label -> binding Property in setApp method (void)
	 * 2) return value to save in order
	 * 
	 * Enhanced for loop like User Login: 
	 * when iterating through the orderedDishesTable, "dish" variable is used as container 
	 * "dish" variable then calls getter for variables from model 
	 * then gets assigned to new int var "totalOrderValue" 
	 * 		=> 2nd container passing on value to be added to itself & printed onto GUI
	 * =====================================
	 * for (MyDataType item : table.getItems()) {
	 * columnData.add(col.getCellObservableValue(item).getValue());
	 * =====================================
	 * References: 
	 * http://stackoverflow.com/questions/29559522/javafx-how-to-get-all-values-of-one-column-from-tableview
	 * http://docs.oracle.com/javafx/2/api/javafx/scene/control/TableCell.html#getTableColumn()
	 */

	/** The lbl total calc. */
	@FXML private Label lblTotalCalc;

	/**
	 * Display total value.
	 */
	@FXML
	public void displayTotalValue() { // print onto Label
		lblTotalCalc.setText(Integer.toString(calculateTotalValue()));
	}

	/**
	 * Calculate total value.
	 * @return the int
	 */
	public int calculateTotalValue() { // return sum to add to order
		int totalOrderValue = 0;
		for (MenuItemModel dish : orderedDishesTable.getItems()) {
			// getter in model calling constructor in main for values -> reference to handover
			totalOrderValue = totalOrderValue + dish.getDishPrice();
		}
		return totalOrderValue;
	}

	/**======================================================================================
	 * Initialize.
	 */
	@FXML
	private void initialize() {
		// filling in the menuTable Cells via getter & setters in MenuItemModel
		itemTypeColumn.setCellValueFactory(cellData -> cellData.getValue().dishType());
		itemNameColumn.setCellValueFactory(cellData -> cellData.getValue().dishName());
		itemPriceColumn.setCellValueFactory(cellData -> cellData.getValue().dishPrice().asObject());

		// filling the Order Cells with the menu selection (in dishList) for each column (projecting the menu choice)
		orderItemTypeColumn.setCellValueFactory(cellData -> cellData.getValue().dishType());
		orderItemNameColumn.setCellValueFactory(cellData -> cellData.getValue().dishName());
		orderItemPriceColumn.setCellValueFactory(cellData -> cellData.getValue().dishPrice().asObject());
	}
}
