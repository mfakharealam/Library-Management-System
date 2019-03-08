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
public class Loan {
    private Borrower borrower;
    private Date issueDate;
    private Date returnDate;
    private Members bookIssuer;
    private Members bookReceiver;
    private Book book;
    //private Item itemObj;

    public Loan(Borrower borrower, Date issueDate, Date returnDate, Members bookIssuer, Members bookReceiver, Book book) {
        this.borrower = borrower;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.bookIssuer = bookIssuer;
        this.bookReceiver = bookReceiver;
        this.book = book;
    }
/////////////////////////////////////// GETTERS & SETTERS /////////////////////////////////////////////
    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Members getBookIssuer() {
        return bookIssuer;
    }

    public void setBookIssuer(Members bookIssuer) {
        this.bookIssuer = bookIssuer;
    }

    public Members getBookReceiver() {
        return bookReceiver;
    }

    public void setBookReceiver(Members bookReceiver) {
        this.bookReceiver = bookReceiver;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

  ///////////////////////////////////////////////////////////////////////////////////////////////

    // extending loan date
    public void extendIssueDate(Date newIssueDate) // renewing the issue date
    {
        this.issueDate = newIssueDate;
    }
}
