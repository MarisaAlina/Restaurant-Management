package uk.ac.ucl.cs.gc01.me.application.model;
/**==============TABLE MODEL=============
 * Table Model needs to save orders on specific table
 * enables clicking again on table will edit the same order 
 * 
 * -> activeOrder variable of OrderModel type to save 
 * an active orders on a table 
 * 
 * -> tableList = observable list in main -> saving tables into list
 * -> currentTable in main establishes reference to set/get the current table number 
 * 	  in RestaurantController
 * 
 */

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TableModel {
	
	/** The table ID. */
	private IntegerProperty tableID;
	
	/** The active order. */
	private OrderModel activeOrder;

	/**
	 * Instantiates a new table model.
	 * @param tableID the table ID
	 * not initializing activeOrder on purpose: when the app starts there is no active order
	 */
	public TableModel(int tableID) {
		this.tableID = new SimpleIntegerProperty(tableID);
	}

	/**
	 * Instantiates a new table model.
	 * Constructor with new "empty" objects (-1) for Import XML to fill 
	 */

	public TableModel() {
		this.tableID = new SimpleIntegerProperty(-1);
	}

	/**
	 * ==========GETTER & SETTER METHODS===========
	 */

	/**
	 * Table ID.
	 * @return the integer property
	 */
	public IntegerProperty tableID() {
		return this.tableID;
	}

	/**
	 * Gets the table ID.
	 * @return the table ID
	 */
	public int getTableID() {
		return this.tableID.get();
	}

	/**
	 * Sets the table ID.
	 * @param tableID the new table ID
	 */
	public void setTableID(int tableID) {
		this.tableID.set(tableID);
	}
	
	/** ==========Getter Setter for active Order Model========== 
	 * returns Reference (returns "filled container" whenever new order is created
	 * 
	 * Active order.
	 * @return the order model
	 */
	public OrderModel activeOrder() {
		return this.activeOrder;
	}
	
	/**
	 * Sets the active order.
	 * @param order the new active order
	 * Creating reference to order Model within order object 
	 * to enable setting orders active
	 */
	public void setActiveOrder(OrderModel order) {
		this.activeOrder = order;
	}
}
