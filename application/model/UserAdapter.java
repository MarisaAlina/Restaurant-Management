package uk.ac.ucl.cs.gc01.me.application.model;

import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The Class UserAdapter.
 * Makes sure that if an order is imported with the same userName as existing user
 * that these orders are added to the same User
 * 
 * http://stackoverflow.com/questions/7278406/serialize-a-jaxb-object-via-its-id
 * http://stackoverflow.com/questions/5319024/using-jaxb-to-cross-reference-xmlids-from-two-xml-files/5327425#5327425
 */

public class UserAdapter extends XmlAdapter<String, UserModel> {

	/** The all users. */
	public List<UserModel> allUsers;
	
	/**
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 * Catches if there is no user
	 * adds a new user if not yet in system
	 * v used as "container" variable to get new userName from XML file
	 */
	@Override
	public UserModel unmarshal(String v) throws Exception {
		for (UserModel user : this.allUsers) {
			if (user.getUserName().equals(v)) {
				return user;
			}
		}
		UserModel newUser = new UserModel (v, "", false);
		this.allUsers.add(newUser);
		return newUser;
	}

	/**
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal(UserModel v) throws Exception {
		return v.getUserName();
	}

}
