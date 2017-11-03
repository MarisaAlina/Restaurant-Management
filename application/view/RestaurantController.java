package uk.ac.ucl.cs.gc01.me.application.view;
import java.io.IOException;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/*
 * @author  Marisa Enhuber
 * @Main Controller Class for Restaurant Management System 
 * @version: 1.1
 * @date:14/12/2016
 * @changes: Class now contains:
 * New stage for Order Registration Form
 * Login Logic Main Stage & Opening new Order stages
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import uk.ac.ucl.cs.gc01.me.application.MainApp;
import uk.ac.ucl.cs.gc01.me.application.model.MenuItemModel;
import uk.ac.ucl.cs.gc01.me.application.model.OrderModel;

/**
 * ============================RESTAURANT TABLE VIEW============================
 * The Class RestaurantController.
 * @author Marisa Enhuber
 * @version: 1.2
 * @date (last update): 18/12/2016
 */
public class RestaurantController {

	/** The app. */
	private MainApp app = null;

	/**
	 * Sets the app.
	 * @param input the new app
	 */
	public void setApp(MainApp input) {
		this.app = input;
	}

	/** The lbl order reg. */
	@FXML private Label lblOrderReg;
	
	/** The lbl table nr. */
	@FXML private Label lblTableNr;

	/**
	 * Open order registration T 1.
	 * gets tables from table list, e.g. T1 is at index (0)
	 */
	@FXML
	public void openOrderRegistrationT1() {
		this.app.showOrderRegistration(this.app.tableList.get(0)); //list of tables at index of 0 = T1
	}	

	/**
	 * Open order registration T 2.
	 */
	@FXML
	public void openOrderRegistrationT2() {
		this.app.showOrderRegistration(this.app.tableList.get(1));
	}

	/**
	 * Open order registration T 3.
	 */
	@FXML
	public void openOrderRegistrationT3() {
		this.app.showOrderRegistration(this.app.tableList.get(2));
	}

	/**
	 * Open order registration T 4.
	 */
	@FXML
	public void openOrderRegistrationT4() {
		this.app.showOrderRegistration(this.app.tableList.get(3)); 
	}

	/**
	 *============================SEARCH ORDERS============================
	 * iterate through orderList to find matches
	 * saves searchResults into observList 
	 * prints results onto TableView (in usual manner)
	 * 
	 * matches substring/ integers .contains / .equals
	 * needs to convert ints to string - saved in String variable, invoked Integer.toString method
	 * ========
	 * References: 
	 * http://code.makery.ch/library/javafx-8-tutorial/part5/
	 * http://stackoverflow.com/questions/5071040/java-convert-integer-to-string
	 */
	@FXML private TextField searchTextField;
	
	/** The search orders table. */
	@FXML private TableView<OrderModel> searchOrdersTable;
	
	/** The search dish list table. */
	@FXML private TableView<MenuItemModel> searchDishListTable;

	/** The search order ID column. */
	@FXML private TableColumn<OrderModel, Integer> searchOrderIDColumn;
	
	/** The search order table number column. */
	@FXML private TableColumn<OrderModel, Integer> searchOrderTableNumberColumn;
	
	/** The search user name column. */
	@FXML private TableColumn<OrderModel, String> searchUserNameColumn;
	
	/** The search order time stamp. */
	@FXML private TableColumn<OrderModel, LocalDateTime> searchOrderTimeStamp;

	/** The search dish type column. */
	@FXML private TableColumn<MenuItemModel, String> searchDishTypeColumn;
	
	/** The search dish name column. */
	@FXML private TableColumn<MenuItemModel, String> searchDishNameColumn;
	
	/** The search dish price column. */
	@FXML private TableColumn<MenuItemModel, Integer> searchDishPriceColumn;


	/** The search result list. 
	 * ObservableList to save SearchResults into List
	 * Get Results via model getter calls 
	 * to iterate through and match
	 */
	private ObservableList<OrderModel> searchResultList = FXCollections.observableArrayList();

	/**
	 * Search orders. (line by line)
	 * clear all content to start with empty list
	 * search for matches - saved into string to print onto label
	 * object "order" as variable (container) to save what I get via getter path 
	 * iterating through the orderList in main
	 * Search by user name, get (path) result = searchTerm
	 * add result (content in object) if matches to result list 
	 * if not, try to match the next
	 */
	@FXML
	private void searchOrders() {
		this.searchResultList.clear(); 
		String searchTerm = searchTextField.getText();
		for (OrderModel order : this.app.orderList) {
			if (order.getUser().getUserName().equals(searchTerm)) { 
				this.searchResultList.add(order);
				continue;
			}

			// Search by table ID
			String tableID = Integer.toString(order.getTable().getTableID());
			if (tableID.equals(searchTerm)) {
				this.searchResultList.add(order);
				continue;
			}
			// Search by order ID - convert from Int to String to search/match
			String orderID = Integer.toString(order.getOrderID());
			if (orderID.equals(searchTerm)) {
				this.searchResultList.add(order);
				continue;
			}
			// Search by time
			if (order.orderTimeStamp().toString().equals(searchTerm)) {
				this.searchResultList.add(order);
				continue;
			}
			/**========== Search for ordered DishList
			 *  Search by dish name -> add to second table
			 *  Only one needs to match, set break statement
			 */
			for (MenuItemModel dish : order.dishList()) {
				if (dish.dishName().get().contains(searchTerm)) {
					this.searchResultList.add(order);
					break;
				}
				if (dish.dishType().get().contains(searchTerm)) {
					this.searchResultList.add(order);
					break;
				}
				String dishPrice = Integer.toString(dish.getDishPrice());
				if (dishPrice.contains(searchTerm)) {
					this.searchResultList.add(order);
					break;
				}
			}
			//==========
		}
	}

	/**
	 * Show search order details.
	 */
	@FXML
	private void showSearchOrderDetails() {
		// put selected row into variable 
		OrderModel selectedOrderDishes = searchOrdersTable.getSelectionModel().getSelectedItem();
		// ensure that something is selected
		if (selectedOrderDishes != null) {
			searchDishListTable.setItems(selectedOrderDishes.dishList());
		}
	}

	/**
	 * ============================EXIT and LOGOUT BUTTON METHOD REFERENCE============================
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void ExitProgramme (ActionEvent event) throws IOException {
		this.app.exitProgramme();
	}

	/**
	 * Logout.
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void Logout (ActionEvent event) throws IOException {
		this.app.showLogin();
	}

	/** Special Feature
	 * 
	 * ============================NEWS FROM THE KITCHEN FEATURE============================
	 * Random word generator for News from the Kitchen 
	 * Adapted from Head First Java, p.17
	 * Available online at: http://dslab.us/wp-content/uploads/book/pdf/java/HeadFisrt_JAVA.pdf
	 * In conjunction with JavaFx Tutorial For Beginners 6 - Events with JavaFX Scene Builder
	 * Available online at: https://www.youtube.com/watch?v=cgv63JD7pfc
	 * @version: 1.0
	 * @date:6/12/2016
	 */

	/** The txt area kitch mess. */
	@FXML private TextArea txtAreaKitchMess;

	/**
	 * Gen kitchen message.
	 * @param kitchevent the kitchevent
	 */
	public void genKitchenMessage(ActionEvent kitchevent) {
		// arrays of Strings to have a usable list of words/ phrases 
		String[] wordListOne = {"ran out of", "got in new", "don't have anymore", "just bought", "have only today", "have no more"};
		String[] wordListTwo = {"delicious", "fresh", "amazing", "ambrosial", "spicy", "tasty", "piquant", "delightful", "yummi", "savory", "divine", "rich", "toothsome", "mouthwatering"};
		String[] wordListThree = {"strawberries", "aubergines", "mushrooms", "bananas","cherries", "figs", "oranges", "potatoes", "flour", "zucchini", "pesto", "spinach", "sweet potato", "butternut", "peas", "beetroot", "apricots", "asparagus","dates","guava"};

		// saving the whole lengths of the String arrays into int variables to make use of them in the below random function
		int oneLength = wordListOne.length; 
		int twoLength = wordListTwo.length; 
		int threeLength = wordListThree.length;

		// randomising the words through the random function, again saved in int variables
		int rand1 = (int) (Math.random() * oneLength); 
		int rand2 = (int) (Math.random() * twoLength); 
		int rand3 = (int) (Math.random() * threeLength);

		// accessing randomised elements from each of the String arrays, saved into String variable to print
		String phrase = wordListOne[rand1] + " " + wordListTwo[rand2] + " " + wordListThree[rand3];
		txtAreaKitchMess.setText("We " + phrase);
	}
	
	/**
	 * Initialize.
	 */
	@FXML
	private void initialize() {
		this.searchOrdersTable.setItems(this.searchResultList);
		// filling the Order Cells with the menu selection (in dishList) for each column (projecting the menu choice)
		searchOrderIDColumn.setCellValueFactory(cellData -> cellData.getValue().orderID().asObject());
		searchOrderTableNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getTable().tableID().asObject());
		searchUserNameColumn.setCellValueFactory(cellData -> cellData.getValue().getUser().userName());
		searchOrderTimeStamp.setCellValueFactory(cellData -> cellData.getValue().orderTimeStamp());

		// this.searchDishListTable.setItems(this.searchResultList);
		searchDishTypeColumn.setCellValueFactory(cellData -> cellData.getValue().dishType());
		searchDishNameColumn.setCellValueFactory(cellData -> cellData.getValue().dishName());
		searchDishPriceColumn.setCellValueFactory(cellData -> cellData.getValue().dishPrice().asObject());
	}

}
