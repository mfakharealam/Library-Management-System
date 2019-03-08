/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooada1;

import java.util.Date;

/**
 *
 * @author Muhammad Fakhar
 */
public class requestsForHold {
    private Borrower borrower;
    private Book book;
    Date dateOfRequest;

    public requestsForHold(Borrower borrower, Book book, Date dateOfRequest) {
        this.borrower = borrower;
        this.book = book;
        this.dateOfRequest = dateOfRequest;
    }
    
    public void holdRequestsInfo()
    {
        System.out.println("The User " + borrower.getName() + " Requested Book: " + book.getTitle() + " On: " + dateOfRequest);
    }
    
        /////////////////////////// GETTERS & SETTERS /////////////////////////////////

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }
    
}
