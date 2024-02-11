package main;

import interfaces.List;

/*@author Juan Iranzo <juan.iranzo@upr.edu>
 * 
 * This class represents a user of the library or its books, and defines the user's behaviors.
 * 
*/

public class User {
	private int id;
	private String name;
	private List<Book> checkedOutList;

	/*This is the constructor for the User object
	 * @param the constructor takes in an integer variable 'id', a string variable 'name'
	 * and a List of checked-out books containing the books ID
	 * @return empty
	*/
	public User(int id, String name, List<Book> checkedOutList) {
		this.id=id;
		this.name=name;
		this.checkedOutList=checkedOutList;
	}
	
	/*This function returns the specified user's 'id' parameter from its constructor
	 * @param this function does not receive any parameters
	 * @return id, returns the integer variable 'id' from the user's constructor
	*/
	public int getId() {
		return this.id;
	}
	
	/*This function assigns a new integer value 'id' to a specified user object
	 * @param id, receives an integer variable 'id'
	 * @return This function has no return value
	*/
	public void setId(int id) {
		this.id=id;
	}

	/*This function return the specified user's 'name' parameter from its constructor
	 * @param This function receives no parameters
	 * @return name, This function returns a String variable 'name' from the user constructor
	*/
	public String getName() {
		return this.name;
	}

	/*This function assigns a new String value 'name' to the specified user object
	 * @param name, This function receives a String variable 'name'
	 * @return This function has no return value
	*/
	public void setName(String name) {
		this.name=name;
	}

	/*This function returns the specified user's List of checked-out books 'checkedOutList'
	 * @param This function receives no parameters
	 * @return checkedOutList, returns the List of checked-out books 'checkedOutList' from the user constructor
	*/
	public List<Book> getCheckedOutList() {
		return this.checkedOutList;
	}

	/*This function assigns a List of checked-out books 'checkedOutList' to the specified user object
	 * @param checkedOutList, receives a List<Book> 'checkedOutList'
	 * @return This function has no return value
	*/
	public void setCheckedOutList(List<Book> checkedOutList) {
		this.checkedOutList=checkedOutList;
	}
	
}
