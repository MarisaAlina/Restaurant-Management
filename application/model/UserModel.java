package uk.ac.ucl.cs.gc01.me.application.model;
/**
 * ==============JAVA FX PROPERTIES (net beans architecture)=============
 * "JavaFX properties are often used in conjunction with binding, 
 * a powerful mechanism for expressing direct relationships between variables. 
 * When objects participate in bindings, changes made to one object will automatically be reflected in another object."
 * 
 * -> Properties are used to bind data in models to controller/view via mainApp
 * Encapsulated via getter & setter methods for each model
 * 
 * References:
 * http://code.makery.ch/library/javafx-8-tutorial/part2
 * http://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
 */
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// needs reference to adapter class to export/import correct user
@XmlJavaTypeAdapter(UserAdapter.class)
public class UserModel {

	/** The user name. */
	private StringProperty userName;

	/** The user password. */
	private StringProperty userPassword;

	/** The is manager. */
	private BooleanProperty isManager;

	/** The created orders list. 
	 * saving details of order (items) into new List for employee activity
	 */
	private ObservableList<OrderModel> createdOrdersList; 

	/**PARAMETERISED CONSTRUCTOR FOR USERMODEL
	 * Instantiates a new user model.
	 * used to initialize instance variables as properties to pass on via main to controllers
	 * @param userName the user name
	 * @param userPassword the user password
	 * @param isManager the is manager
	 */

	public UserModel(String userName, String userPassword, boolean isManager) {
		this.userName = new SimpleStringProperty(userName); 
		this.userPassword = new SimpleStringProperty(userPassword);
		this.isManager = new SimpleBooleanProperty(isManager);
		this.createdOrdersList = FXCollections.observableArrayList();
	}

	/**
	 * Instantiates a new user model.
	 * Constructor with new "empty" objects for Import XML to fill
	 */
	public UserModel() {
		this.userName = new SimpleStringProperty(""); 
		this.userPassword = new SimpleStringProperty("");
		this.isManager = new SimpleBooleanProperty(false);
		this.createdOrdersList = FXCollections.observableArrayList();
	}

	/**
	 *  _____GETTER & SETTER METHODS for ENCAPSULATION of USERDATA_____
	 */

	/**
	 * User name.
	 * @return the string property
	 */
	public StringProperty userName() {
		return userName;
	}

	/**
	 * Gets the user name.
	 * @return the user name
	 */
	public String getUserName() {
		return userName.get();
	}

	/**
	 * Sets the user name.
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	/**
	 * User password.
	 * @return the string property
	 */
	public StringProperty userPassword() {
		return userPassword;
	}


	/**
	 * Gets the user password.
	 * @return the user password
	 */
	public String getUserPassword() {
		return userPassword.get();
	}


	/**
	 * Sets the user password.
	 * @param userPassword the new user password
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword.set(userPassword);
	}
	/**
	 * Checks if is manager.
	 * @return the boolean property
	 */
	public BooleanProperty isManager() {
		return isManager;
	}


	/**
	 * Gets the checks if is manager.
	 * @return the checks if is manager
	 */
	public boolean getIsManager() {
		return isManager.get();
	}

	/**
	 * Sets the checks if is manager.
	 *
	 * @param isManager the new checks if is manager
	 */
	public void setIsManager(boolean isManager) {
		this.isManager.set(isManager);
	}

	/** Reference to createdOrderList for employeeActivity
	 * Created orders list.
	 * @return the observable list
	 */
	public ObservableList<OrderModel> createdOrdersList() {
		return this.createdOrdersList;
	}
}
