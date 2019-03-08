/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooada1;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.io.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Muhammad Fakhar
 */
public class Library {
    private String nameOfLibrary;
    private Librarian librarian; // assuming only one librarian to look after the library
    private List<Person> persons;
    private List<Book> books; // books and DVDs
    private List<Loan> loans; // history of all the items issued

    public Library()   // default constructor
    {
        nameOfLibrary = null;
        librarian = null;
        persons = new ArrayList();
        books = new ArrayList();
        loans = new ArrayList();
    }
    public Library(String nameOfLibrary, Librarian librarian, List<Person> persons, List<Book> books, List<Loan> loans) {
        this.nameOfLibrary = nameOfLibrary;
        this.librarian = librarian;
        this.persons = persons;
        this.books = books;
        this.loans = loans;
    }

    ///////////////////////////////////////////////// GETTERS & SETTERS /////////////////////////////////////////////////
    public String getNameOfLibrary() {
        return nameOfLibrary;
    }

    public void setNameOfLibrary(String nameOfLibrary) {
        this.nameOfLibrary = nameOfLibrary;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public List<Person> getPersons() {
        return this.persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Loan> getLoans() {
        return this.loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    ///  SEARCH FUNCTIONS
    
    public List<Book> searchBookByTitle(String title)
    {
        List<Book> booksList = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) 
        {
            if (books.get(i).getTitle().startsWith(title) && books.get(i).getTitle().contains(title) || books.get(i).getTitle().equalsIgnoreCase(title)) 
            {
                booksList.add(books.get(i));
            }
        }
        return booksList;
    }
    
    public List<Book> searchBookByAuthor(String author)
    {
        List<Book> booksList = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) 
        {
            if (books.get(i).getAuthor().startsWith(author) && books.get(i).getAuthor().contains(author) || books.get(i).getAuthor().equalsIgnoreCase(author))
            {
                booksList.add(books.get(i));
            }
        }
        return booksList;
    }
    
    public List<Book> searchBookBySubject(String subj)
    {
        List<Book> booksList = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) 
        {
            if (books.get(i).getSubject().startsWith(subj) && books.get(i).getSubject().contains(subj) || books.get(i).getSubject().equalsIgnoreCase(subj))
            {
                booksList.add(books.get(i));
            }
        }
        return booksList;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    public void addBook(Book book)
    {
        this.books.add(book);
    }
     public void addClerk(checkoutClerk clerk)
    {
        this.persons.add(clerk);
    }
     public void addLibrarian(Librarian librarian)
    {
        this.persons.add(librarian);
    }
     public void addBorrower(Borrower borrower)
    {
        this.persons.add(borrower);
    }
     public void addLoan(Loan loan)
    {
        this.loans.add(loan);
    }
     
     
     
     
     
     
     
     
    public Library fillDataFromDB(Connection con) throws SQLException, IOException
    {
        Library libraryObj = new Library();
        Statement stmt = con.createStatement();
        
        //////////////////////////////// getting books Data //////////////////////////////////////
        
        ResultSet rs = stmt.executeQuery("select * from Book"); // SQL Query
        if(!rs.next())
        {
           //System.out.println("No Books Found in Library"); 
        }
        else
        {
            do
            {
                  int id = rs.getInt("bookId"); // 1
                  String title = rs.getString("bTitle"); // 2
                  String author = rs.getString("bAuthor"); //3
                  String subj = rs.getString("bSubject"); //4
                  boolean issueStat = rs.getBoolean("issueStatus"); //5
                  Book book = new Book(id, title, author, subj, issueStat);
                  libraryObj.addBook(book);
            }while(rs.next());     
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
         //////////////////////////////// getting Persons Data //////////////////////////////////////
        /// CLERK ///
        
        rs = stmt.executeQuery("SELECT personId, pName, pAddress, pPhoneNo, password, memberSalary FROM PERSON "
                + "INNER JOIN CLERK ON personId = clerkId INNER JOIN Members ON memberId = clerkId"); // SQL Query
        if(!rs.next())
        {
           //System.out.println("No Books Found in Library"); 
        }
        else
        {
            do
            {
                int id = rs.getInt("personId"); // 1
                String name = rs.getString("pName"); // 2
                String addr = rs.getString("pAddress"); //3
                String phone = rs.getString("pPhoneNo"); //4
                String pass = rs.getString("password"); //5
                float sal = rs.getFloat("memberSalary");
                checkoutClerk clerk = new checkoutClerk(sal, id, name, addr, phone, pass); //(float salary, int id, String Name, String Address, String PhoneNo, String password)
                libraryObj.addClerk(clerk);
            }while(rs.next());  
        }
          /// Librarian ///
        
        rs = stmt.executeQuery("SELECT personId, pName, pAddress, pPhoneNo, password, memberSalary FROM Person "
                + "INNER JOIN Librarian ON personId = librarianId INNER JOIN Members ON memberId = librarianId"); // SQL Query
        if(!rs.next())
        {
           //System.out.println("No Books Found in Library"); 
        }
        else
        {
            do
            {
                int id = rs.getInt("personId"); // 1
                String name = rs.getString("pName"); // 2
                String addr = rs.getString("pAddress"); //3
                String phone = rs.getString("pPhoneNo"); //4
                String pass = rs.getString("password"); //5
                float sal = rs.getFloat("memberSalary");
                Librarian librarian = new Librarian(sal, id, name, addr, phone, pass); //(float salary, int id, String Name, String Address, String PhoneNo, String password)
                libraryObj.addLibrarian(librarian);
            }while(rs.next());     
        }
          /// Borrower ///
        
        rs = stmt.executeQuery("SELECT personId, pName, pAddress, pPhoneNo, password FROM Person "
                + "INNER JOIN Borrower ON personId = borrowerId"); // SQL Query
        if(!rs.next())
        {
           //System.out.println("No Books Found in Library"); 
        }
        else
        {
            do
            {
                int id = rs.getInt("personId"); // 1
                String name = rs.getString("pName"); // 2
                String addr = rs.getString("pAddress"); //3
                String phone = rs.getString("pPhoneNo"); //4
                String pass = rs.getString("password"); //5
                Borrower borrower = new Borrower(id, name, addr, phone, pass); //(float salary, int id, String Name, String Address, String PhoneNo, String password)
                libraryObj.addBorrower(borrower);
            }while(rs.next());     
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        ////////////////////////////////////// getting Loan data //////////////////////////////////////////
        
        rs = stmt.executeQuery("SELECT * from Loan"); // SQL Query
        if(!rs.next())
        {
           //System.out.println("No Books Found in Library"); 
        }
        else
        {
            do
            {
                int borrowerId = rs.getInt("borrowerId");
                int bookId = rs.getInt("bookId"); 
                int issuerId = rs.getInt("issuerId"); 
                Date issueDate = rs.getDate("issueDate");
                 int receiverId = rs.getInt("receiverId"); 
                Date receiveDate = rs.getDate("receiveDate"); 
                //Borrower borrower, Date issueDate, Date returnDate, Members bookIssuer, Members bookReceiver, Book book
                
                Borrower borrower = null;
                for (int i = 0; i < libraryObj.getPersons().size() ; i++) {
                    if (libraryObj.getPersons().get(i).getId() == borrowerId) 
                    {
                        borrower = (Borrower) libraryObj.getPersons().get(i);
                        break;
                    }
                }
                Members Issuer = null, receiver = null;
                for (int i = 0; i < libraryObj.getPersons().size() ; i++)
                {
                    if (libraryObj.getPersons().get(i).getId() == issuerId && libraryObj.getPersons().get(i).getClass().getSimpleName().equals("Librarian") ) 
                    {
                        Issuer = (Librarian) libraryObj.getPersons().get(i);
                        break;
                    }
                    else if (libraryObj.getPersons().get(i).getId() == issuerId && libraryObj.getPersons().get(i).getClass().getSimpleName().equals("checkoutClerk") ) 
                    {
                        Issuer = (checkoutClerk) libraryObj.getPersons().get(i);
                        break;
                    }
                }
                 if (receiverId != 0) {
                    for (int i = 0; i < libraryObj.getPersons().size() ; i++) 
                    {
                        if (libraryObj.getPersons().get(i).getId() == receiverId && libraryObj.getPersons().get(i).getClass().getSimpleName().equals("Librarian") ) 
                       {
                           receiver = (Librarian) libraryObj.getPersons().get(i);
                           break;
                       }
                       else if (libraryObj.getPersons().get(i).getId() == receiverId && libraryObj.getPersons().get(i).getClass().getSimpleName().equals("checkoutClerk") ) 
                       {
                           receiver = (checkoutClerk) libraryObj.getPersons().get(i);
                           break;
                       }
                    }
                }
                 else // not received yet
                 {
                     receiver = null;
                     receiveDate = null;
                 }
                 Book book = null;
                 for (int i = 0; i < libraryObj.getBooks().size(); i++) 
                 {
                     if (libraryObj.getBooks().get(i).getId() == bookId)
                     {
                         book = libraryObj.getBooks().get(i);
                         break;
                     }
                }
                 Loan loan = new Loan(borrower, issueDate, receiveDate, Issuer, receiver, book);
                 libraryObj.addLoan(loan);
            }while(rs.next());     // end of while loop
        } //end of else 
        
       ////////////////////////////////////////////////////////////////////////////////////////////////////
        
       ////////////////////////////////// getting RequestsForHold data ////////////////////////////////////
       
        rs = stmt.executeQuery("SELECT * from RequestsForHold"); // SQL Query
        if(!rs.next())
        {
           //System.out.println("No Books Found in Library"); 
        }
        else
        {
            do
            {
                int borrowerId = rs.getInt("borrowerId");
                int bookId = rs.getInt("bookId"); 
                Date requestDate = rs.getDate("dateOfRequest");
                Borrower borrower = null;
                List<Person> personsList = libraryObj.getPersons();
                for (int i = 0; i < personsList.size() ; i++)
                {
                    if (personsList.get(i).getId() == borrowerId) 
                    {
                        borrower = (Borrower)personsList.get(i);
                        break;
                    }
                }
                Book book = null;
                 List<Book> booksList = libraryObj.getBooks();
                for (int i = 0; i < booksList.size() ; i++)
                {
                    if (booksList.get(i).getId() == bookId) 
                    {
                        book = (Book)booksList.get(i);
                        requestsForHold request = new requestsForHold(borrower, book, requestDate);
                        libraryObj.getBooks().get(i).addHoldRequest(request); // to that particular book
                        request.getBorrower().addHoldRequest(request); // to the borrower's list of requests
                        break;
                    }
                }
                
            }while(rs.next()); //end of while     
        } // end of else
        
       /////////////////////////////////////////////////////////////////////////////////////////////////// 
        
       //////////////////////////////////// adding borrowed books data //////////////////////////////////////
      
        rs = stmt.executeQuery("SELECT Borrower.borrowerId, borrowedBId from Person INNER JOIN Borrower on Borrower.borrowerId = personId "
                + "INNER JOIN BorrowedBook on Borrower.borrowerId = BorrowedBook.borrowerId"); // SQL Query
        if(!rs.next())
        {
           //System.out.println("No Books Found in Library"); 
        }
        else
        {
            do
            {
                int borrowerId = rs.getInt("borrowerId");
                int bookId = rs.getInt("borrowedBId");
                Borrower borrower = null;
                for (int i = 0; i < libraryObj.getPersons().size() ; i++)
                {
                    if (libraryObj.getPersons().get(i).getClass().getSimpleName().equals("Borrower"))
                    {
                        if (libraryObj.getPersons().get(i).getId() == borrowerId)
                        {
                            borrower = (Borrower) libraryObj.getPersons().get(i);
                            break;
                        }
                    }
                }
                List<Loan> loanedBooks = libraryObj.getLoans();
                for (int i = 0; i < loanedBooks.size() ; i++)
                {
                    if (loanedBooks.get(i).getBook().getId() == bookId)
                    {
                        Loan borrowedBooks = new Loan(borrower, loanedBooks.get(i).getIssueDate(), loanedBooks.get(i).getReturnDate(),
                        loanedBooks.get(i).getBookIssuer(), loanedBooks.get(i).getBookReceiver(), loanedBooks.get(i).getBook());
                        borrowedBooks.getBorrower().addBooksBorrowed(borrowedBooks); // the borrower we just added
                        break;
                    }
                }
            }while(rs.next()); // end of while
        } // end of else
       
       /////////////////////////////////////////////////////////////////////////////////////////////////// 
       
       return libraryObj;
    } // end of fillDataFromDB
    
    ////////////////////////////////////// Finders ///////////////////////////////////////////////////
    public Borrower findBorrower(int Id)
    {
        for (int i = 0; i < this.getPersons().size() ; i++) 
        {
            if (this.getPersons().get(i).getClass().getSimpleName().equals("Borrower") && this.getPersons().get(i).getId() == Id) {
                return (Borrower)this.getPersons().get(i);
            }
        }
        return null;
    }
    public Book findBook(int id) // by bookID
    {
        for (int i = 0; i < this.getBooks().size() ; i++) {
            if (this.getBooks().get(i).getId() == id) {
                return this.getBooks().get(i);
            }
        }
        return null;
    }
    public Book findBook(String Title)
    {
        for (int i = 0; i < this.getBooks().size() ; i++) {
            if (this.getBooks().get(i).getTitle().equals(Title)) {
                return this.getBooks().get(i);
            }
        }
        return null;
    }
    public Loan findLoan(int bookId) // since for each book there is only one loan to each borrower at a time
    {
        for (int i = 0; i < this.getLoans().size() ; i++) {
            if (this.getLoans().get(i).getBook().getId() == bookId) {
                return this.getLoans().get(i);
            }
        }
        return null;
    }
    public void removeBook(Book book)
    {
        if (!books.isEmpty()) {
            books.remove(book);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
} // end of Class
