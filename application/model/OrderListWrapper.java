package uk.ac.ucl.cs.gc01.me.application.model;
/** 
 * @date: (last updated) 18/12/2016
 * 
 * ===========WRAPPER CLASS FOR XML===========
 * to add or remove data to or from Arraylist -> order data
 * List nests order data (orderList), which nests dishList
 * ============================================
 * Reference: http://code.makery.ch/library/javafx-8-tutorial/part5/
 */
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="orders")
public class OrderListWrapper {
	
	/** orders List */
	private List<OrderModel> orders;
	
	/**
	 * Gets the orders.
	 * @return the orders
	 */
	@XmlElement(name = "order")
	public List<OrderModel> getOrders() {
		return this.orders;
	}
	
	/**
	 * Sets the orders. reference to orders
	 * @param the new orders
	 * 
	 */
	public void setOrders(List<OrderModel> orders) {
		this.orders = orders;
	}
}
