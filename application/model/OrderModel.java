package uk.ac.ucl.cs.gc01.me.application.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class OrderModel.
 * Sets all variables that an order needs:
 * OrderID, totalValue, Timestamp, 
 * reference to TableModel, @return instance (object) 
 * reference to UserModel, @return instance (object) 
 * List of dishes (reference from MenuItemModel)
 * Creates link between user and createdDishList to save for employeeActivity
 */
public class OrderModel {

	/** The order ID. */
	private IntegerProperty orderID;

	/** The total value. */
	private IntegerProperty totalValue;

	/** The order comments. */
	private StringProperty orderComments;

	/** The order time stamp. */
	private ObjectProperty<LocalDateTime> orderTimeStamp; 

	/** The table. 
	 * Reference to other models to save order values*/
	private TableModel table;

	/** The dish list. */
	private ObservableList<MenuItemModel> dishList;

	/** The user. */
	private UserModel user; 


	/**===========PARAMETERISED CONSTRUCTOR FOR USERMODEL===========
	 *Instantiates a new order model.
	 *passing the variables as parameters to "fill" during usage of app
	 * @param orderID the order ID
	 * @param totalValue the total value
	 * @param orderComments the order comments
	 * @param orderTimeStamp the order time stamp
	 * @param table the table
	 * @param user the user
	 */

	public OrderModel (int orderID, int totalValue, String orderComments, LocalDateTime orderTimeStamp, TableModel table, UserModel user) {
		this.orderID = new SimpleIntegerProperty(orderID); 
		this.totalValue = new SimpleIntegerProperty(totalValue);
		this.orderTimeStamp = new SimpleObjectProperty<>(orderTimeStamp); 
		this.orderComments = new SimpleStringProperty(orderComments);
		this.table = table; 
		this.user = user;
		this.dishList = FXCollections.observableArrayList();

		user.createdOrdersList().add(this);
	}

	/**
	 * Constructor with new "empty" objects for Import XML to fill 
	 * Instantiates a new order model.
	 */

	public OrderModel() {
		this.orderID = new SimpleIntegerProperty(-1); 
		this.totalValue = new SimpleIntegerProperty(-1);
		this.orderComments = new SimpleStringProperty("");
		this.orderTimeStamp = new SimpleObjectProperty<>(null);
		this.table = null;
		this.user = null;
		this.dishList = FXCollections.observableArrayList();
	}


	/** ===========ENCAPSULATION - GETTER and SETTER METHODS===========
	 * 
	 *___________Order ID___________
	 * @return the integer property
	 */
	public IntegerProperty orderID() {
		return orderID;
	}

	/**Gets the order ID.
	 * @return the order ID
	 */
	public int getOrderID() {
		return orderID.get();
	}

	/**Sets the order ID.
	 * @param orderID the new order ID
	 */
	public void setOrderID(int orderID) {
		this.orderID.set(orderID);
	}

	/**_______ Total value_______
	 * @return the integer property
	 */
	public IntegerProperty totalValue() {
		return totalValue;
	}

	/**
	 * Gets the total value.
	 * @return the total value
	 */
	public int getTotalValue() {
		return totalValue.get();
	}

	/**
	 * Sets the total value.
	 * @param totalValue the new total value
	 */
	public void setTotalValue(int totalValue) {
		this.totalValue.set(totalValue);
	}

	/**____Order comments.____
	 * @return the string property
	 */
	public StringProperty orderComments() {
		return orderComments;
	}

	/**
	 * Gets the order comments.
	 * @return the order comments
	 */
	public String getOrderComments() {
		return orderComments.get();
	}

	/**
	 * Sets the order comments.
	 * @param orderComments the new order comments
	 */
	public void setOrderComments(String orderComments) {
		this.orderComments.set(orderComments);
	}

	/**________Order time stamp________
	 * @return the object property
	 */
	public ObjectProperty<LocalDateTime> orderTimeStamp() {
		return orderTimeStamp;
	}

	/**
	 * Gets the order time stamp.
	 * @return the order time stamp
	 */
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	public LocalDateTime getOrderTimeStamp() {
		return this.orderTimeStamp.get();
	}

	/**
	 * Sets the order time stamp.
	 * @param timeStamp the new order time stamp
	 */
	public void setOrderTimeStamp(LocalDateTime timeStamp) {
		this.orderTimeStamp.set(timeStamp);
	}

	/**____REFERENCE TO TABLE NUMBER___
	 *  Gets the tableID
	 * @return the table
	 */
	public TableModel getTable() {
		return this.table;
	}

	/**
	 * Sets the table.
	 * @param table the new table
	 */
	public void setTable(TableModel table) {
		this.table = table;
	}

	/**
	 * Dish list - OBSERVABLE LIST FOR DISHSELECTION 
	 * ====================================
	 * where the selected menu dishes (items) will later be saved in
	 * Reference for XML export: http://stackoverflow.com/questions/24441810/copy-observablelist-java
	 * @return the observable list
	 */

	public ObservableList<MenuItemModel> dishList() {
		return this.dishList;
	}

	/**
	 * Gets the dish list.
	 * @return the dish list
	 * Reference:http://stackoverflow.com/questions/3683598/jaxb-how-to-marshal-objects-in-lists
	 */
	@XmlElementWrapper(name="dishes")
	@XmlElement(name = "dish")
	public List<MenuItemModel> getDishList() {
		return this.dishList;
	}

	/**
	 * Sets the dish list.
	 * @param dishList the new dish list
	 */
	public void setDishList(List<MenuItemModel> dishList) {
		this.dishList = FXCollections.observableArrayList(dishList);
	}

	/** ========GETTER SETTER FOR USERNAME======
	 * Gets the user.
	 * @return the user
	 */

	public UserModel getUser() {
		return this.user;
	}

	/**______Link User to createdOrderList______
	 * when the user for an order changes or gets imported/exported
	 * Makes sure to keep user.createdOrderList up to date 
	 * @param user - createdOrderList
	 */

	public void setUser(UserModel user) {
		if (this.user != null) {
			// delete order from old user
			this.user.createdOrdersList().remove(this);
		}

		// add order to new user
		user.createdOrdersList().add(this);
		this.user = user;
	}
}	
