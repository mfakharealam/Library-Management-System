/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooada1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Muhammad Fakhar
 */
public class Borrower extends Person {
    
    private List<Loan> booksBorrowed; // books currently borrowed (Loans)
    private List<requestsForHold> holdRequests; // requests for put the book on hold by this user

    public Borrower(int id, String Name, String Address, String PhoneNo, String Password)
    {
        super(id, Name, Address, PhoneNo, Password);
        this.booksBorrowed = new ArrayList();
        this.holdRequests = new ArrayList();
    }

    ///////////////////////////////////// PRINTING FUNCTIONS ///////////////////////////////////////////
    public void printBorrowedBooks()
    {
        if (!booksBorrowed.isEmpty())
        { 
            System.out.println("Borrowed Books are: ");
                        
            for (int i = 0; i < booksBorrowed.size(); i++)
            {                      
                booksBorrowed.get(i).getBook().viewBookDetails();
            }
        }
        else
            System.out.println("No books have been borrowed yet.");  
    }
    
    public void printHoldRequests()
    {
         if (!holdRequests.isEmpty())
        { 
            System.out.println("Borrowed Books are: ");
                        
            for (int i = 0; i < holdRequests.size(); i++)
            {                      
                holdRequests.get(i).getBook().viewBookDetails();
            }
        }
        else
            System.out.println("No books have been borrowed yet.");  
    }
    public void printInfo()
    {
        super.printInfo();
        this.printBorrowedBooks();
        this.printHoldRequests();
    }
    //////////////////////////////////// ADD & REMOVE FUNCTIONS FOR BOOKS & REQUESTS ////////////////////////////
    public void borrowBook(Loan e) {
        booksBorrowed.add(e);
    }

    public void removeBorrowedBook(Loan i) 
    {
        booksBorrowed.remove(i);
    }
    
    
    public void addHoldRequest(requestsForHold e) {
        holdRequests.add(e);
    }

    public void removeHoldRequest(requestsForHold i) 
    {
        holdRequests.remove(i);
    }

    /////////////////////////// GETTERS & SETTERS /////////////////////////////////
    public List<Loan> getBooksBorrowed() {
        return booksBorrowed;
    }

    public void setBooksBorrowed(List<Loan> booksBorrowed) {
        this.booksBorrowed = booksBorrowed;
    }

    public List<requestsForHold> getHoldRequests() {
        return holdRequests;
    }

    public void setHoldRequests(List<requestsForHold> holdRequests) {
        this.holdRequests = holdRequests;
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////
    
    public void editBorrowerInformation() // updation
    {
        
    }
    public void addBooksBorrowed(Loan loan)
    {
        booksBorrowed.add(loan);
    }
     public void removeBooksBorrowed(int index) // by index
    {
        booksBorrowed.remove(index);
    }
      public void removeBooksBorrowed(Loan loan)
    {
        booksBorrowed.remove(loan);
    }
    
}