package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;
public class LibraryCatalog {
	
	/*@author Juan Iranzo <juan.iranzo@upr.edu>
	 * This class uses ArrayLists for its variable because of its random access properties. Since most methods
	 * work by retrieving certain information of certain objects, then this implementation
	 * proves more useful.
	*/
private List<Book> catalog= new ArrayList<Book>();
private List<User> userList= new ArrayList<User>();

	/*This is the constructor for the LibraryCatalog class, it calls the getBooksFromFiles() and 
	 *getUsersFromFiles() methods to initialize the catalog and userList variables
	 */
	public LibraryCatalog() throws IOException {
		catalog=this.getBooksFromFiles();
		userList=this.getUsersFromFiles();
	}

	/*This is the method for populating the List<Book> 'catalog'. 
	 * It reads from the catalog.csv file using a BufferedReader object. It initializes an array of Strings named param. It splits the line
	 * that the BufferedReader is currently reading, every time it finds a ',' and adds the elements found every time it splits
	 * to the param array. It parses the string in the param position 0 to an integer. It creates another String array called date,
	 * and splits the element in the position [4] of the param array every time it finds a '-'. It then parses the elements added to the
	 * date array to integers and stores them in variables named year, month and day. After this, it creates the checkoutDate using the LocalDate.of()
	 * method. It then continues reading the line and parses the element in position 5 of the param array into a boolean variable.
	 * It creates the user using the constructor and passes the stored variables of the param array as parameters. Finally, it adds
	 * the book to the catalog List
	 * 
	 * @param This function does not receive any parameters
	 * @return List<Book>. Returns a List containing book objects
	*/
	private List<Book> getBooksFromFiles() throws IOException {
		String file="data/catalog.csv";
		ArrayList<Book> catalog= new ArrayList<Book>();
		String line;
		try(BufferedReader buffReader= new BufferedReader(new FileReader(file))){
			buffReader.readLine();
			while((line=buffReader.readLine())!=null) {
				String[] param = line.split(",");
				int id=Integer.parseInt(param[0].trim());
				String title=param[1].trim();
				String author=param[2].trim();
				String genre=param[3].trim();
				String[]date = param[4].split("-");
				int month = Integer.parseInt(date[1]);
				int day = Integer.parseInt(date[2]);
				int year = Integer.parseInt(date[0]);
				LocalDate lastCheckOut=LocalDate.of(year, month, day);
				//Need to add a way to check if it received a date or not, if not then assign null or 0
				boolean isCheckedOut=Boolean.parseBoolean(param[5].trim());
				Book newBook= new Book(id, title, author, genre, lastCheckOut,isCheckedOut);
				catalog.add(newBook);
			}
		}
		catch(IOException e) {
			System.out.println(e);
		}
		return catalog;
	}
	
	/*This is the method for populating the List<User> 'userList'. 
	 * The function reads from the user.csv file using a BufferedReader object. It initializes an array of Strings named param. It splits the line
	 * that the BufferedReader is currently reading, every time it finds a ',' and adds the elements found every time it splits
	 * to the param array. It parses the string in the param position 0 to an integer 'id'. It creates an ArrayList called 'checkedOutList'
	 * that holds Book objects. It then checks if the param array has a length of more than 2 and if the element at position 2 in the param array has a length greater than 0.
	 * If it does, then it it creates another string array called 'BookIDs', replaces any '{' , or '}' with an empty space and splits the element in
	 * the position 2 of the param array everytime it finds a whitespace. It iterates through the 'bookIDs' array and parses the elements to integer types and stores them in a 
	 * 'BookIDNumber' variable. After this, it iterates through the 'catalog' ArrayList and compares the current book's 'id' to the 'BookIDNumber' variable value. If the values match
	 * it adds the corresponding object to the 'checkedOutList' ArrayList and breaks out of the loop. It creates a new User using the User constructor, passing in the values stored in the
	 * param array and the 'checkedOutList'. Finally, it adds this new user to the userList array.
	 * 
	 * @param This function does not receive any parameters
	 * @return List<User>. Returns a list containing all the user objects
	*/
	private List<User> getUsersFromFiles() throws IOException {
		String file="data/user.csv";
		ArrayList<User> userList = new ArrayList<User>();
		String line;
		try(BufferedReader buffReader= new BufferedReader(new FileReader(file))){
			buffReader.readLine();
			while((line=buffReader.readLine())!=null){
				String param[]=line.split(",");
				int id=Integer.parseInt(param[0].trim());
				String name= param[1].trim();
				List<Book>checkedOutList = new ArrayList<Book>();
				if(param.length>2 && param[2].length()>0)  {
				String[]bookIDs = param[2].replace("{","").replace("}","").split("\\s+");
					for(int i=0; i<bookIDs.length;i++) {
							int bookIDNumber = Integer.parseInt(bookIDs[i].trim());
							for(int j=0;j<this.catalog.size();j++) {
								if(this.catalog.get(j).getId()==bookIDNumber) {
									checkedOutList.add(this.catalog.get(j));
									break;
								}
							}
						}
				}
				User user = new User(id,name,checkedOutList);
				userList.add(user);
			}
		}
		return userList;
	}
	
	/*This function returns the private List 'catalog' from the LibraryCatalog class
	 *@param This function does not receive any parameters
	 *@return 'catalog', Returns the catalog ArrayList 
	 * 
		*/
	public List<Book> getBookCatalog() {
		return this.catalog;
	}
	
	/*This function returns the private List 'userList' from the LibraryCatalog class
	 *@param This function does not receive any parameters
	 *@return 'userList', Returns the userList ArrayList 
	*/
	public List<User> getUsers() {
		return this.userList;
	}
	
	/*This function adds a book object to the catalog 
	 * 
	 * The function initializes a LocalDate object 'currDate' using the LocalDate.of() method, and a boolean
	 * variable 'checkedOut' to false. It then creates a new Book object using the constructor, passing in the 
	 * 'title', 'author', and 'genre' parameters that it receives, the 'currDate' and 'checkedOut' variables 
	 * it defines, and setting the id to the size of the catalog+1. Finally, it adds the newly created book
	 * to the catalog using the ArrayList add() method.
	 * 
	 *@param title, author, genre. The function receives 3 String variables 'title','author','genre'
	 *@return This function has no return value
	*/
	public void addBook(String title, String author, String genre) {
		LocalDate currDate= LocalDate.of(2023, 9, 15);
		boolean checkedOut=false;
		Book bookToAdd=new Book(this.catalog.size()+1,title,author,genre,currDate,checkedOut);
		catalog.add(bookToAdd);
		return;
	}

	/*This function removes a book from the 'catalog' 
	 * 
	 * The function iterates through the 'catalog' ArrayList, comparing the current element's
	 * ID value to the parameter ID that the function receives. If the ID values match, it calls
	 * on the ArrayList remove() method to remove the found book from the 'catalog' list.
	 * 
	 *@param id. Receives an integer value 'id' 
	 *@return This function has no return value
	*/
	public void removeBook(int id) {
		for(int i=0;i<this.catalog.size();i++) {
			if(this.catalog.get(i).getId()==id) {
				this.catalog.remove(i);
			}
		}
		return;
	}	
	
	/*This function checks out a book from the 'catalog'
	 * 
	 *The function initializes a new LocalDate object 'currCheckOutDate' with the date September 15,2023. 
	 *It then iterates through the 'catalog', checking if the current book's 'id' value matches the 'id' parameter received. 
	 *If it does, it checks if the book's 'checkedOut' value is set to 'false', and if it is, it calls on the setLastCheckout()
	 *method of the book class and sets the check-out date to the previously initialized 'currCheckoutDate' variable.
	 *Then, it calls the setCheckedOut() method of the book class and sets the book's 'checkedOut' value to 'true'. 
	 * 
	 *@param id. Receives an integer value 'id'
	 *@return boolean. Returns false if the book is already checked out, true if it was checked out successfully.
	*/
	public boolean checkOutBook(int id) {
		LocalDate currCheckOutDate = LocalDate.of(2023, 9, 15);
		for(int i=0; i<this.catalog.size();i++) {
			if(this.catalog.get(i).getId()==id) {
				Book book=this.catalog.get(i);
				if(book.isCheckedOut()==false) {
					book.setLastCheckOut(currCheckOutDate);
					book.setCheckedOut(true);
					return true;
					}
				}
			}
		return false;
		}
		
		/*This function returns a book to the library if it is checked out
		 * 
		 *The method iterates through the catalog, getting the current book's 'id' value and checking if it
		 *matches the 'id' received as parameter. If the 'id' values match, the function checks if the book's
		 *'ischeckedOut' value is false, if it is, the function returns 'false'. If the 'isCheckedOut' value is set to
		 *'true', it calls on the setCheckedOut() method and sets the 'isCheckedOut' value to 'false' and returns 'true'.
		 * 
		 *@param id. Receives an integer value 'id'
		 *@return boolean. Returns true if the book was checked-out and it was successfully returned. 
		 *Returns false if it was not checked-out
		*/
	public boolean returnBook(int id) {
		for(int i=0; i<this.catalog.size();i++) {
			if(this.catalog.get(i).getId()==id) {
				if(this.catalog.get(i).isCheckedOut()==false) {
					return false;
				}
				else {
					this.catalog.get(i).setCheckedOut(false);
					return true;
				}
			}
		}
		return false;
	}
	
	/*This function checks if a book is available for check-out.
	 * 
	 *The function iterates through the 'catalog' list and compares the current element's 'id' value to the
	 *'id' parameter received. If the 'id' value match, it checks the element's check-out status. If the isCheckedOut()
	 *method returns false, then the function will return true. If the book's check-out status is set to true,
	 *then the function will return false.
	 * 
	 *@param 'id'. Receives an integer value 'id'
	 *@return boolean. Returns true if book is available for check-out, and false if it is not
	*/
	public boolean getBookAvailability(int id) {
		for(int i=0; i<this.catalog.size();i++) {
			if(this.catalog.get(i).getId()==id) {
				if(this.catalog.get(i).isCheckedOut()==false) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*This function counts how many books with the same title are present in the 'catalog'
	 * 
	 *The function initializes an integer variable 'counter' to 0, and iterates through the 'catalog'
	 *The function calls the getTitle() method of the book class on the current element compares the 'title'
	 *with the parameter 'title' received using the ArrayList's equals() method. If the 'titles' match,
	 *the counter will increase. When it finishes iterating through the 'catalog', it returns the 'counter' variable. 
	 *
	 * 
	 *@param 'title'. Receives a String variable 'title'
	 *@return 'counter'. Returns the number of books with the specified 'title' present in the 'catalog'
	*/
	public int bookCount(String title) {
		int counter=0;
		for(int i=0; i<this.catalog.size();i++) {
			if(this.catalog.get(i).getTitle().equals(title)) {
				counter++;
			}
		}
		return counter;
	}
	
	/*This function counts how many books with the specified 'genre' are present in the 'catalog'
	 * 
	 *The function initializes an integer variable 'counter' to 0, and iterates through the 'catalog'.
	 *It calls the getGenre() method of the book class on the current element and calls the ArrayList's
	 *equalsIgnoreCase() method. It calls this second method to compare the current book's 'genre', and 
	 *the 'genre' passed as parameter, ignoring if the 'genre' was passed in uppercase or lowercase.
	 *If the 'genre' values match, the 'counter' variable is incremented. When the function finishes
	 *iterating, it returns the 'counter' variable.
	 * 
	 *@param 'genre'. Receives a String value 'genre'.
	 *@return 'counter'. Returns the number of books with the specified genre
	*/
	public int searchBookGenre(String genre) {
		int counter=0;
		for(int i=0;i<this.catalog.size();i++) {
			if(this.catalog.get(i).getGenre().equalsIgnoreCase(genre)) {
				counter++;
			}
		}
		return counter;
	}
	
	/*This function counts how many books from the 'catalog' are currently checked out
	 *
	 *The function initializes an integer variable 'counter' to 0, and iterates through the 'catalog'.
	 *It calls the isCheckedOut() method from the book class on the current element, and checks if the 
	 *'isCheckedOut' variable is set to true. If it is set to true, the counter increments by 1. 
	 *When it finishes iterating, it returns the counter. 
	 * 
	 *@param This function does not receive any parameters.
	 *@return 'counter'. Returns the number of books that are checked out, using the 'counter' variable
	*/
	public int totalCheckedOutBooks() {
		int counter=0;
		for(int i = 0; i<this.catalog.size();i++) {
			if(this.catalog.get(i).isCheckedOut()==true) {
				counter++;
			}
		}
		return counter;
	}
	
	/*This function calculates the total fee owed to the library
	 * 
	 *The function initializes an integer variable 'fee' to 0, and iterates through the 'catalog'.
	 *It calls the isCheckedOut() method from the book class on the current element, and checks if the 
	 *'isCheckedOut' variable is set to true. If it is, it calls the calculateFees() method of the book class
	 *to calculate the fee owed for the current book, and adds it to the 'fee' variable. When it finishes iterating;
	 *it returns the total fees owed.
	 * 
	 * @param This function receives no parameters
	 * @return 'fee'. Returns how much money is owed in late fees to the library.
	*/
	public float totalFee() {
		float fee=0;
		for(int i = 0; i<this.catalog.size(); i++) {
			if(this.catalog.get(i).isCheckedOut()) {
					fee+=this.catalog.get(i).calculateFees();
				}
			}
		return fee;
	}
	
	/*This function generates a report of all the books that the library owns, which ones are checked out,
	 * how many of each genre there are, which users owe late fees to the library and how much the fee is, the 
	 * total amount of money owed.
	 * 
	 * The function creates a String variable named 'output', looks for the amount of books of the specified genres, adds
	 * these values to the string. It also looks for the total amount of books that the library owns and adds it to the 
	 * 'output'. It also looks for every book that is checked out and adds these books and their information to the 'output'
	 * String. It calculates the amount of books currently checked out by calling on the totalCheckedOutBooks() method of the 
	 * LibraryCatalog class. It adds the returned value to the 'output' variable. It then, iterates through the 'userList' to get the users that have checked out books. Then it calculates
	 * the fees owed for each checked out book by the user and adds the user name and fees owed to the 'output' String. It then adds all 
	 * these fees to get the total amount of money owed to the library and adds this to the String. It finally calls on a BufferedWriter object
	 * to generate a text file with all the information the 'output' String contains (it also prints the same information to the terminal). 
	 * 
	 * @param Does not receive any parameter values
	 * @return Does not have a return value
	*/
	public void generateReport() throws IOException {
		String adventure = "Adventure";
		String fiction = "Fiction";
		String classics = "Classics";
		String mystery = "Mystery";
		String scifi = "Science Fiction";
		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		output += "Adventure\t\t\t\t\t" + (this.searchBookGenre(adventure)) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (this.searchBookGenre(fiction)) + "\n";
		output += "Classics\t\t\t\t\t" + (this.searchBookGenre(classics)) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (this.searchBookGenre(mystery)) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (this.searchBookGenre(scifi)) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + this.catalog.size() + "\n\n";
		
		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */
		for(int i=0; i<this.catalog.size();i++) {
			if(this.catalog.get(i).isCheckedOut()) {
				
				output+=this.catalog.get(i).toString() + "\n";
			}
		}
		
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (this.totalCheckedOutBooks()) + "\n\n";
		
		
		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */
		 for(int i=0;i<this.userList.size();i++) {
			 float fees=0;
			 if(this.userList.get(i).getCheckedOutList().size()>0) {
			 for(int j=0; j<this.userList.get(i).getCheckedOutList().size();j++) {
				 fees+=this.userList.get(i).getCheckedOutList().get(j).calculateFees();
			 }
			 if(fees>0) {
				 String finalFeeFormat=String.format("%.2f", fees);
				 output+=this.userList.get(i).getName()+ "\t\t\t\t\t$" + finalFeeFormat +"\n";
				 
			 	}
			 }
		 }
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + (this.totalFee()) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		try {
			BufferedWriter outputWriter = new BufferedWriter(new FileWriter("report/FinalReport.txt"));
			outputWriter.write(output);
			outputWriter.close();
		} 
		catch(IOException e) {
			System.out.print(e);
		}
	}
	
	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		return null;
	}
	
	public List<User> searchForUsers(FilterFunction<User> func) {
		return null;
	}
	

	
	}
	

