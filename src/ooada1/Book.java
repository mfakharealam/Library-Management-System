/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooada1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Muhammad Fakhar
 */
public class Book extends Item{
    private int id;
    private String title;
    private String author;
    private String subject;
    private Boolean issueStatus;  //tells if the book is issued to someone or not
    private List<requestsForHold> onHoldRequests; // all the hold requests for this book
    //int curridNo = 0;  // unqiue for everybook
    Book(int id, String title, String author, String subj, Boolean issued)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.subject = subj;
        this.issueStatus = issued;
        onHoldRequests = new ArrayList<>();
    }
    public void viewBookDetails()
    {
        System.out.println("Book ID is: " + this.id);
        System.out.println("Book Title is: " + this.title);
        System.out.println("Book Subject is: " + this.subject);
        System.out.println("Author of the Book is: " + this.author);
    }
        /////////////////////////// GETTERS & SETTERS /////////////////////////////////

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(Boolean issueStatus) {
        this.issueStatus = issueStatus;
    }

    public List<requestsForHold> getOnHoldRequests() {
        return onHoldRequests;
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    public void addHoldRequest(requestsForHold req)
    {
        this.onHoldRequests.add(req);
    }
    public void removeHoldRequest(int index)
    {
        if(!onHoldRequests.isEmpty())
        {
            onHoldRequests.remove(index);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    public void placeThisBookOnHold(Borrower borrower, Date date)
    {
        requestsForHold req = new requestsForHold(borrower, this , date);
        addHoldRequest(req);        // add req to this book as it is on loan to somebody else
        borrower.addHoldRequest(req);      // add this request to that borrower's req list also, as he has requested to reserve it
    }
    //// before placing request
    public int processHoldRequestForBook(Borrower borrower)
    {
        //case 1
        for(int i = 0;i < borrower.getBooksBorrowed().size(); i++)
        {
            if(borrower.getBooksBorrowed().get(i).getBook() == this)
            {
                return 1; // 1 means already borrowed that book                
            }
        }
        // case 2
        for (int i = 0; i < this.onHoldRequests.size(); i++)
        {
            if ((this.onHoldRequests.get(i).getBorrower() == borrower))
            {
                return 2; // 2 means he already have requested this book
            }
        }
        // case 4
        if (this.issueStatus == false) { // means if it is not issued!
            return 4;
        }
        // case 3
        this.placeThisBookOnHold(borrower, new Date());
        return 3; // means he is allowed
    }
    public void checkInThisBook(Borrower borrower, Loan loan, Members member) // receive
    {
        loan.setBookReceiver(member);
        loan.setReturnDate(new Date());
        loan.getBook().setIssueStatus(false);
        borrower.removeBorrowedBook(loan);
    }
    public int checkOutThisBook(Borrower borrower) // issue
    {
        if (this.issueStatus) // case 1
        {
            return 1; // book is already issued. Try Reservation
        }
        else
        {
            if (!onHoldRequests.isEmpty()) // checking if the current borrower is first in queue of reservation
            {
                boolean requested = false;
                for (int i = 0; i < onHoldRequests.size() ; i++)
                {
                    if (onHoldRequests.get(i).getBorrower().getId() == borrower.getId()) {
                        requested = true;
                    }
                }
                if (requested == true)
                {
                    if (onHoldRequests.get(0).getBorrower().getId() == borrower.getId())  // first in queue or not
                    {
                        borrower.removeHoldRequest(onHoldRequests.get(0));
                        removeHoldRequest(0); // remove as now he is borrowing it
                    }
                    else // case 2
                    {
                        return 2; //some other borrower has requested earlier than you
                    }
                }
                else// case 3
                {
                    return 3; // you did not request, some other users have requested first
                }
            }
            // case 4 
            // he can get the book issued
            setIssueStatus(true);
            return 4;
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }
}
