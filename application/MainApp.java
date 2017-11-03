package uk.ac.ucl.cs.gc01.me.application;
/**
 * The Class MainApp.
 * @author: Marisa Enhuber
 * MainApp launches app and coordinates data
 * @Main Reference:
 * http://code.makery.ch/library/javafx-8-tutorial/
 * @library JAXB for import/ export XML files
 * uses UserAdapter to append imported orders to correct users
 * @date (last update): 18/12/2016
 */
import java.io.File;
import java.time.LocalDateTime;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import uk.ac.ucl.cs.gc01.me.application.model.MenuItemModel;
import uk.ac.ucl.cs.gc01.me.application.model.OrderListWrapper;
import uk.ac.ucl.cs.gc01.me.application.model.OrderModel;
import uk.ac.ucl.cs.gc01.me.application.model.TableModel;
import uk.ac.ucl.cs.gc01.me.application.model.UserAdapter;
import uk.ac.ucl.cs.gc01.me.application.model.UserModel;
import uk.ac.ucl.cs.gc01.me.application.view.LoginController;
import uk.ac.ucl.cs.gc01.me.application.view.ManagerController;
import uk.ac.ucl.cs.gc01.me.application.view.OrderRegistrationController;
import uk.ac.ucl.cs.gc01.me.application.view.RestaurantController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MainApp extends Application {

	/** OBSERVABLE LIST FOR COLLECTING USERS */
	public ObservableList<UserModel> userList = FXCollections.observableArrayList();

	/** OBSERVABLE LIST FOR COLLECTING MENUITEMS */
	public ObservableList<MenuItemModel> menuList = FXCollections.observableArrayList();

	/** OBSERVABLE LIST FOR COLLECTING ORDERS */
	public ObservableList<OrderModel> orderList = FXCollections.observableArrayList();

	/** OBSERVABLE LIST FOR TABLES */
	public ObservableList<TableModel> tableList = FXCollections.observableArrayList();

	/**
	 * ============VARIABLES for PLACING an ORDER==========
	 * "Container" to add values of e.g employee name (reference of UserModel), current table number (reference from TableModel)
	 * needed to pass on to OrderRegistrationViewController where "filled"
	 * 
	 * ===========NextOrderID===========
	 * Declaring variable of type and assigning the initial value of 0 to start at 1
	 */

	/** The current user. */
	public UserModel currentUser;

	/** The current table. */
	public TableModel currentTable;

	/** The current total value. */
	public IntegerProperty currentTotalValue;

	/** The next order ID. */
	public IntegerProperty nextOrderID = new SimpleIntegerProperty(1); // starting to count orders from 1 rather than 0

	/**
	 * ============================THE MAIN APP CONSTRUCTOR=============================
	 * Instantiates a new main app.
	 * Creating reference to models
	 * with initial data 
	 * invoked from controllers via setApp(MainApp object) Method
	 */
	public MainApp() {
		this.tableList.add(new TableModel(1)); // Values "printed" on GUI - List start at 0
		this.tableList.add(new TableModel(2)); // 1
		this.tableList.add(new TableModel(3)); // 2
		this.tableList.add(new TableModel(4)); // 3

		this.userList.add(new UserModel("Betsy", "123", false));
		this.userList.add(new UserModel("Remy", "pass", true));

		this.menuList.add(new MenuItemModel ("Starter", "Waldorf Salad", 8));
		this.menuList.add(new MenuItemModel ("Starter", "Maroni Soup", 7));
		this.menuList.add(new MenuItemModel ("Starter", "Bruschetta", 6));
		this.menuList.add(new MenuItemModel ("Main Course", "Potato Fritters", 15));
		this.menuList.add(new MenuItemModel ("Main Course", "Curry with Rice", 20));
		this.menuList.add(new MenuItemModel ("Main Course", "Quiche", 20));
		this.menuList.add(new MenuItemModel ("Dessert", "Baiser", 5));
		this.menuList.add(new MenuItemModel ("Dessert", "Ice Cream", 4));
		this.menuList.add(new MenuItemModel ("Dessert", "Chocolate Cake", 4));
		this.menuList.add(new MenuItemModel ("Drink", "Riesling", 4));
		this.menuList.add(new MenuItemModel ("Drink", "Beetroot Juice", 3));
		this.menuList.add(new MenuItemModel ("Drink", "Ginger Beer", 2));

		// Dummy data for an existing order to test
		TableModel table = this.tableList.get(0);
		UserModel user = this.userList.get(0);
		OrderModel order = new OrderModel(
				1, 8, "No gluten", LocalDateTime.now(), table, user
				);
		order.dishList().add(this.menuList.get(0));
		this.orderList.add(order);
	}


	/** ============================THE PRIMARY STAGE and SCENES============================
	 * Basic Stage Settings: Title, disable to resize Stages
	 * Reference: http://www.java2s.com/Code/Java/JavaFX/NotresizableWindowScene.htm
	 */
	private Stage primaryStage; // starts "Login Scene (Screen)"

	/**@see javafx.application.Application#start(javafx.stage.Stage)*/
	@Override
	public void start(Stage primaryStage) { // is invoked by launch(); 
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Chez Remy");
		this.primaryStage.setResizable(false);

		this.showLogin();
	}

	/**Show login.*/
	public void showLogin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/LoginView.fxml"));
			Parent root = loader.load();

			// gets styles & components from Login FXML file
			Scene LoginScene = new Scene(root); // instantiating the Login window from Parent root defined above
			LoginScene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

			// give LoginController access to our data
			LoginController loginController = loader.getController();
			loginController.setApp(this);

			// show LoginScene
			primaryStage.setScene(LoginScene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**Show manager view.*/
	public void showManagerView() {
		try {
			// replaced Parent root = FXMLLoader.load(getClass().getResource("view/LoginView.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/ManagerView.fxml"));
			Parent root = loader.load();

			// gets styles & components from Login FXML file
			Scene scene = new Scene(root); // instantiating the Login window from Parent root defined above
			scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

			// give Controller access to our data
			ManagerController managerController = loader.getController();
			managerController.setApp(this);

			// show LoginScene
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**Show restaurant view. */
	public void showRestaurantView(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/RestaurantView.fxml"));
			Parent root = loader.load();

			// gets styles & components from Login FXML file
			Scene scene = new Scene(root); // instantiating the Login window from Parent root defined above
			scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

			// give Controller access to our data
			RestaurantController restaurantController = loader.getController();
			restaurantController.setApp(this);

			// show Scene
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show order registration.
	 * @param table the table
	 * Assigning a new IntProp object to set reference to tables and get table numbers
	 * from showOrder function call to get current value for table
	 */
	public void showOrderRegistration(TableModel table) {
		this.currentTable = table;

		try {
			// replaced from Parent root = FXMLLoader.load(getClass().getResource("view/XXXX.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/OrderRegistrationView.fxml"));
			Parent root = loader.load();

			// gets styles & components from Login FXML file
			Scene scene = new Scene(root); // instantiating the resp. window/scene from Parent root defined above
			scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

			// give Controller access to our data
			OrderRegistrationController orderRegistrationController = loader.getController();
			orderRegistrationController.setApp(this);

			// show Scene
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Exit programme.
	 */
	public void exitProgramme() {
		this.primaryStage.close(); 	
	}

	/**
	 * ============================XML IMPORT EXPORT========================
	 * uses JAXB library
	 * Reference: http://code.makery.ch/library/javafx-8-tutorial/part5/
	 */

	public void saveOrdersToFile() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter to save files in xml format
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(this.primaryStage);
		//		end if no file is created
		if (file == null) {
			return;
		}

		// Make sure file has the correct extension
		if (!file.getPath().endsWith(".xml")) {
			file = new File(file.getPath() + ".xml");
		}

		try {
			JAXBContext context = JAXBContext
					.newInstance(OrderListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			/**Declare + initialize new wrapper*/
			OrderListWrapper wrapper = new OrderListWrapper();
			/**content of wrapper is now order list of MainApp*/
			wrapper.setOrders(this.orderList);

			/**Marshalling = serializing data and saving XML to the file*/
			m.marshal(wrapper, file);
		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	/**
	 * Import orders from file.
	 * reverse logic of above
	 */
	public void importOrdersFromFile() {
		FileChooser fileChooser = new FileChooser();

		// Set XML extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show open file dialog
		File file = fileChooser.showOpenDialog(this.primaryStage);

		if (file == null) {
			return;
		}

		try {
			JAXBContext context = JAXBContext
					.newInstance(OrderListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			/** Make userList available to UserAdapter
			 * Unmarshaller uses object as container 
			 * to compare if user exists in userList
			 */
			UserAdapter userAdapter = new UserAdapter();
			userAdapter.allUsers = this.userList;
			um.setAdapter(userAdapter);

			/**Reading XML from the file and unmarshalling.*/
			OrderListWrapper wrapper = (OrderListWrapper) um.unmarshal(file);

			this.orderList.addAll(wrapper.getOrders());

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath() +"\nMake sure user exists!");

			alert.showAndWait();
			e.printStackTrace();
		}
	}

	/**
	 * The main method.
	 * method of the Application class
	 * setups the programme as JavaFX app 
	 * launch calls start
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args); 
	}
}
