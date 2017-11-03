package uk.ac.ucl.cs.gc01.me.application.model;

/**
 * @author: Marisa Enhuber
 * The Class MenuItemModel.Defines what a MenuItems needs in param list
 * Private instance variables to encapsulate data model 
 * set/ get values via getter setter methods
 * Main app connect data in models with controller
 * 
 * @References on Properties for all Model Classes: 
 * https://docs.oracle.com/javase/tutorial/essential/environment/properties.html
 * http://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
 * http://code.makery.ch/library/javafx-8-tutorial/
 * @version: 1.0
 * @date: (last updated) 18/12/2016
 */

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MenuItemModel {

	/** ==============JAVA FX PROPERTIES (net beans architecture)=============. */
	private StringProperty dishType;
	
	/** The dish name. */
	private StringProperty dishName;
	
	/** The dish price. */
	private IntegerProperty dishPrice;


	/**
	 *  PARAMETERISED CONSTRUCTOR
	 *  new instance of class
	 *
	 * @param dishType the dish type
	 * @param dishName the dish name
	 * @param dishPrice the dish price
	 * used to initialse instance variables as properties
	 * passing on reference across main to controller
	 */
	public MenuItemModel(String dishType, String dishName, int dishPrice) {
		this.dishType = new SimpleStringProperty(dishType); 
		this.dishName = new SimpleStringProperty(dishName);
		this.dishPrice = new SimpleIntegerProperty(dishPrice); 
	}
	
	/**
	 * Instantiates a new menu item model.
	 */
	// Constructor with new "empty" objects for Import XML to fill 
	public MenuItemModel() {
		this.dishType = new SimpleStringProperty(""); 
		this.dishName = new SimpleStringProperty("");
		this.dishPrice = new SimpleIntegerProperty(-1);
	}
	
	/**
	 *  _____GETTER & SETTER METHODS for ENCAPSULATION of MENUTIEMS_____.
	 * @return the string property
	 */

	/**
	 * ___ITEM TYPE___
	 * @return the dish type
	 */
	public StringProperty dishType() {
		return dishType;
	}

	/**
	 * Gets the dish type.
	 * @return the dish type
	 */
	public String getDishType() {
		return dishType.get();
	}

	/**
	 * Sets the dish type.
	 *
	 * @param dishType the new dish type
	 */
	// itemType Setter for String value
	public void setDishType(String dishType) {
		this.dishType.set(dishType);
	}

	/**
	 * ___DISH NAME___.
	 * @return the string property
	 */
	public StringProperty dishName() {
		return dishName;
	}

	/**
	 * Gets the dish name.
	 * @return the dish name
	 */
	public String getDishName() {
		return dishName.get();
	}

	/**
	 * Sets the dish name.
	 * @param dishName the new dish name
	 */
	public void setDishName(String dishName) {
		this.dishName.set(dishName);
	}

	/**
	 * ___DISH PRICE___.
	 * @return the integer property
	 */
	public IntegerProperty dishPrice() {
		return dishPrice;
	}

	/**
	 * Gets the dish price.
	 * @return the dish price
	 */
	public int getDishPrice() {
		return dishPrice.get();
	}

	/**
	 * Sets the dish price.
	 * @param dishPrice the new dish price
	 */
	public void setDishPrice(int dishPrice) {
		this.dishPrice.set(dishPrice);
	}
}
