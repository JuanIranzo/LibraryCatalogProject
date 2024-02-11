package main;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import data_structures.ArrayList;

/*@author Juan Iranzo <juan.iranzo@upr.edu>
 * This class represents an object called 'book', and defines its parameters and behaviors
 * 
*/
public class Book {
	private int id;
	private String title;
	private String author;
	private String genre;
	private LocalDate lastCheckOut;
	private boolean isCheckedOut;
	
	/*This is the constructor for the book object
	 * 
	 * @param 'id, 'title', 'author', 'genre', 'lastCheckOut', 'isCheckedOut'. This constructor receives an integer value
	 * called 'id' which represents the identifier for the book, a String 'author' which represents the name of the book's author. 
	 * It also receives a String 'genre' which specifies the genre of the book, and a 'lastCheckOut' LocalDate object
	 * which represents the last date the book was checked out on. Finally it receives a boolean variable 'isCheckedOut', which
	 * sets the book's check-out status.
	*/
	Book(int id, String title, String author, String genre, LocalDate lastCheckOut, boolean isCheckedOut){
		this.id=id;
		this.title=title;
		this.author=author;
		this.genre=genre;
		this.lastCheckOut=lastCheckOut;
		this.isCheckedOut=isCheckedOut;
	}

	/*Returns the current book's ID parameter
	 * 
	 * @param empty
	 * @return int id, returns the id of the book as an integer
	*/
	public int getId() {
		return this.id;
	}
	/*Assigns a specific ID to current book or new book 
	 * @param id, receives an integer value "id"
	 * @return void
		*/
	public void setId(int id) {
		this.id=id;	
	}

	/*Returns current book's title parameter
	 * @param empty
	 * @return title, returns a String "title" assigned to the current book
	*/
	public String getTitle() {
		return this.title;
	}

	/*Assigns a title to a book
	 *@param title, Receives a String variable "title"
	 *@return void
	*/
	public void setTitle(String title) {
		this.title=title;
	}

	/*Returns the specified book's author parameter
	 * @param empty, this function receives nothing
	 * @return author, returns the book's String "author" parameter from its constructor
	*/
	public String getAuthor() {
		return this.author;
	}

	/*Assigns a new author to the specified book
	 * @param author, receives a String variable "author"
	 * @return void 
	*/
	public void setAuthor(String author) {
		this.author=author;
	}

	/*This function returns a book object's genre parameter
	 * @param This function does not receive any parameters
	 * @return genre, returns the book's String variable "genre" from its constructor
	*/
	public String getGenre() {
		return this.genre;
	}

	/*This function assigns a new genre to the specified book
	 * @param genre, This function receives a String variable "genre"
	 * @return void
	*/
	public void setGenre(String genre) {
		this.genre=genre;
	}

	/*This function returns a book object's lastCheckOut parameter
	 * @param This function does not receive any parameters
	 * @return lastCheckOut, returns the book's LocalDate object "lastCheckOut" from its constructor 
	*/
	public LocalDate getLastCheckOut() {
		return this.lastCheckOut;
	}

	/*This function assigns a new LocalDate object lastCheckOut to the book
	 * @param lastCheckOut, this function receives a LocalDate object lastCheckOut
	 * @return void
	*/
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut=lastCheckOut;
	}

	/*This functions returns a book object's isCheckedOut boolean variable
	 * @param This function does not receive any parameters
	 * @return isCheckedOut, returns boolean variable isCheckedOut from book object's constructor 
	*/
	public boolean isCheckedOut() {
		return this.isCheckedOut;
	}

	/*Assigns a new status to the book's boolean variable isCheckedOut
	 * @param checkedOut, receives a boolean variable "checkedOut"\
	 * @return void
	*/
	public void setCheckedOut(boolean checkedOut) {
		this.isCheckedOut=checkedOut;
	}

	/*This function searches for a book based on its id parameter and returns it
	 * @param id, receives an integer variable "id"
	 * @return returns book object with matching id parameter
	*/
	public Book getBook(int id) {
		//Book book= new Book(id, author, author, author, lastCheckOut, isCheckedOut);
		if(this.getId()==id) {
			return this;
		}
		else
			return null;
	}

	/*This function returns a String in uppercase with a book object's title and author
	 * @param This function does not receive parameters
	 * @return String, this function returns a String variable with the book's title and author
	*/
	@Override
	public String toString() {
		/*
		 * This is supposed to follow the format
		 * 
		 * {TITLE} By {AUTHOR}
		 * 
		 * Both the title and author are in uppercase.
		 */
		return this.getTitle().toUpperCase() + " BY " + this.getAuthor().toUpperCase();
	}

	/*This function calculates fees for a book that is checked out
	 * 
	 * The function initializes a LocalDate object variable with the date as September 15, 2023. After this,
	 * it saves the specified book's lastCheckOut LocalDate object in a variable and calculates the days between
	 * the lastCheckOut date and the current date as a float value, using the until() method of LocalDate. After
	 * this, it calculates the fee based on the specifications. A base fee of $10 is applied for the first 31 days checked out,
	 * and an extra $1.50 will be charged for every extra day checked out after the first 31 days.
	 * 
	 *@param This function does not receive any parameters
	 *@return fee, this function returns a float variable 'fee' 
	*/
	public float calculateFees() {
		/*
		 * fee (if applicable) = base fee + 1.5 per additional day
		 */
		LocalDate currentDate= LocalDate.of(2023, 9, 15);
		float fee;
		LocalDate checkOutDateDays= this.getLastCheckOut();
		float daysCheckedOut= checkOutDateDays.until(currentDate, ChronoUnit.DAYS);
		if(this.isCheckedOut()==false) {
			return 0;
		}
		else if(daysCheckedOut<31) {
			fee=0;

		}else {
			fee=(float) (10.00+(1.50*(daysCheckedOut-31)));
		}
		
		return fee;
		
	}
}
