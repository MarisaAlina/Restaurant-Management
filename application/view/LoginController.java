 package uk.ac.ucl.cs.gc01.me.application.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import uk.ac.ucl.cs.gc01.me.application.MainApp;
import uk.ac.ucl.cs.gc01.me.application.model.UserModel;

/**
 * The Class LoginController.
 * =============================LOGIN=============================
 *  Login with if-else logic:
 *  if employee login successful, show Restaurant Scene (Primary stage)
 *  else (manager), show Manager Scene 
 *  
 * ==========================GET CURRENT USER=====================
 *  Enhanced for loop: declaring a new reference variable of type UserModel
 *  that iterates through the values of userlist via the main app 
 *   "path": this.app
 *  assigning new user variable to currentUser to connect with model 
 *  "fill" currentUser variable with data from main.app Constructor
 *  if-else logic to compare and verify entered text with default values of set in main app constructor
 *  single return statement to "stop" iterating and move to print Status if login failed
 *  Reference: https://www.youtube.com/watch?v=mokD1I7hl-o
 */
public class LoginController {

	/** The app. Reset to 0 to launch when started */
	private MainApp app = null;

	/**
	 * Sets the app.
	 * @param input the new app
	 */
	public void setApp(MainApp input) {
		this.app = input;
	}

	/** The statusLabel. 
	 * "Wiring up" FMXL component (linked in SceneBuilder) with variable
	 */
	@FXML private Label lblStatus; 

	/** The txt user name. */
	@FXML private TextField txtUserName;

	/** The txt password. */
	@FXML private TextField txtPassword;

	/** The btn login. */
	@FXML private Button btnLogin;

	/**
	 * Login.
	 *
	 * @param event the Login event
	 * @throws IOException Signals that an I/O exception has occurred.
	 * User reference points to the position in userList & assigns values to variable
	 * Update label with Status warning if login failed
	 */
	public void Login (ActionEvent event) throws IOException {

		String userName = txtUserName.getText();
		String userPassword = txtPassword.getText();

		for (UserModel user : this.app.userList) {
			//content of textfield == variable content from mainapp => txtUserName.getText() == getUserName()
			if (userName.equals(user.getUserName()) && userPassword.equals(user.getUserPassword())) {
				this.app.currentUser = user; 	//"pointing" to position in userList & assigns values to variable
				if (user.getIsManager() == true) {
					this.app.showManagerView();
				} else {
					this.app.showRestaurantView();
				}
			return;
			}
		}
		lblStatus.setText("Login Failed. Try again");
	}
}
