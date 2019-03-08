/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooada1;

/**
 *
 * @author Muhammad Fakhar
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class DBConnection
{
    Connection con;
    Statement stmt;
    Library library;
    DBConnection(Library lib) //cons
    {
        library = lib;
        try
        {
            String s = "jdbc:sqlserver://MFA:1433;databaseName=LMS";
            con=DriverManager.getConnection(s,"Fakhar","123");

            //library = new Library();
           // library = library.fillDataFromDB(con); // fill the data from the DB
            stmt = con.createStatement();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public Library populateLibrary(Library lib)
    {
        try
        {
            String s = "jdbc:sqlserver://MFA:1433;databaseName=LMS";
            con=DriverManager.getConnection(s,"Fakhar","123");

           // library = new Library();
            lib = lib.fillDataFromDB(con); // fill the data from the DB
            return lib;
          //  stmt = con.createStatement();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
    public Borrower getBorrower(int id) // returns the borrower with id in the param
    {
        for (int i = 0; i < getPersons().size(); i++) {
            if (getPersonIds().get(i).equals(id) && getPersons().get(i).getClass().getSimpleName().equals("Borrower")) 
            {
                return (Borrower)getPersons().get(i);
            }
        }
        return null;
    }
    public List<String> getPersonPasswords()
    {
        List<String> pass = new ArrayList<>();
        for (int i = 0; i < library.getPersons().size(); i++) 
        {
            pass.add(library.getPersons().get(i).getPassword()); // adding passwords of each person
        }
        return pass;
    }
    public List<Integer> getPersonIds()
    {
        List<Integer> pass = new ArrayList<>();
        for (int i = 0; i < library.getPersons().size(); i++) 
        {
            pass.add(library.getPersons().get(i).getId()); // adding passwords of each person
        }
        return pass;
    }
      public List<Person> getPersons()
    {
       return library.getPersons();
    }
      public int getReqId()
      {
        try
           {
                ResultSet rs = stmt.executeQuery("select max(requestId) Id from RequestsForHold");
                while(rs.next())
                 {   
                    return rs.getInt(1);
                 }
           }
           catch(Exception e)
           {
                System.out.println(e);
           }   
        return 0;
      }
    public void displayAll()
    {
        try
        {
             ResultSet rs=stmt.executeQuery("select * from Book");
             while(rs.next())
             {   
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
             }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void insertIntoHoldRequests(int id, int borrowerId, int bookId, Date date)
    {
        String sql = "INSERT INTO RequestsForHold VALUES(?,?,?,?)";
 
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.setInt(2, borrowerId);
                pstmt.setInt(3, bookId);
                pstmt.setDate(4, date);

                pstmt.executeUpdate();
            }
        catch (SQLException e)
        {
                System.out.println(e.getMessage());
        }
    }
    public List<Book> searchBooksByTitle(String title)
    {
        List<Book> searched = new ArrayList<>();
        searched = library.searchBookByTitle(title); // calling library function
        return searched;
    }
     public List<Book> searchBooksByAuthor(String author)
    {
        List<Book> searched = new ArrayList<>();
        searched = library.searchBookByAuthor(author);
        return searched;
    }
      public List<Book> searchBooksBySubject(String subj)
    {
        List<Book> searched = new ArrayList<>();
        searched = library.searchBookBySubject(subj);       
        return searched;
    }
    public void checkThisBookInSQL(Borrower borrower, Loan loan, Members member)
    {
         try
        {
            stmt.executeUpdate("Update Loan set receiverId = " + member.getId() + ", receiveDate = " + "GETDATE()"
                    + "where borrowerId = " + borrower.getId());               
            stmt.executeUpdate("Update Book set issueStatus = 0 where bookId = " + loan.getBook().getId());
            stmt.executeUpdate("delete from BorrowedBook where borrowedBId = " +  loan.getBook().getId());
                     
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
     public int getLoanMaxId()
      {
        try
           {
                ResultSet rs = stmt.executeQuery("select max(loanId) Id from Loan");
                while(rs.next())
                 {   
                    return rs.getInt(1);
                 }
           }
           catch(Exception e)
           {
                System.out.println(e);
           }   
        return 0;
      }
    public void checkThisBookOutInSQL(Borrower borrower, Loan loan, Book book, Members member)
    {
        int loanId = getLoanMaxId();
        loanId++;
        try
        {
            PreparedStatement pstmt = con.prepareStatement("Insert into Loan values(" + loanId + ", " + borrower.getId() + ", " + book.getId() + ", " +
                    member.getId() + ", GETDATE(), " + null + ", " + null +  ")");
            pstmt.executeUpdate();
            stmt.executeUpdate("Update Book set issueStatus = 1 where bookId = " + loan.getBook().getId()); // book is issued
            pstmt = con.prepareStatement("Insert into BorrowedBook values(" + book.getId() + ", " + borrower.getId() + ")");        
            pstmt.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public int renewBookDate(int bookId, Date date)
    {
        Book book = null;
        for (int i = 0; i < library.getBooks().size(); i++) {
            if (library.getBooks().get(i).getId() == bookId) {
                book = library.getBooks().get(i);
            }
        }
        if (book != null)
        {
            Loan loan = library.findLoan(bookId);
            if (book.getIssueStatus() && loan != null && loan.getBookReceiver() == null) {
                loan.setIssueDate(date);
                return 3; // successful
            }
           return 2; // book not issued
        }
        return 1; // means could not find book
    }
    public void renewDateInSQL(int borrowerId, int bookId, Date date)
    {
        try
        {
            stmt.executeUpdate("Update Loan set issueDate = " + "'" + date + "'" + " where bookId = " + bookId + " and borrowerId = " + borrowerId); // renewed
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
     public int getMaxBorrowerId()
      {
        try
           {
                ResultSet rs = stmt.executeQuery("select max(personId) from Person");
                while(rs.next())
                 {   
                    return rs.getInt(1);
                 }
           }
           catch(Exception e)
           {
                System.out.println(e);
           }   
        return 0;
      }
    public void addBorrowerInLibrary(String name, String addr, String phone, String pass)
    {
        int id = getMaxBorrowerId() + 1;
        Borrower borrower = new Borrower(id, name, addr, phone, pass);
        library.addBorrower(borrower); // added in class
        try
        {
            PreparedStatement pstmt = con.prepareStatement("Insert into Person values(" + id + ", " + "'" + name + "'" +  ", " + "'" + addr + "'" + ", " +
                    "'" + phone + "'" + ", " + "'" + pass + "'" + ")");
            pstmt.executeUpdate(); // inserted in Person Table
            pstmt = con.prepareStatement("Insert into Borrower values(" + id + ")");   // inserted fk personId, which is borrower's Id into Borrower Table     
            pstmt.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public int updateBorrowerInLibrary(int id, String name, String addr, String phone)
    {
        Borrower borrower = null;
        for (int i = 0; i < library.getPersons().size(); i++) {
            if (id == library.getPersons().get(i).getId() && library.getPersons().get(i).getClass().getSimpleName() == "Borrower") {
                borrower = (Borrower)library.getPersons().get(i);
            }
        }
        if (borrower == null) {
            return 1; // no borrower found!
        }
        borrower.setName(name);
        borrower.setAddress(addr);
        borrower.setPhoneNo(phone);
        return 2; // success
    }
    public void updateBorrowerInSQL(int id, String name, String addr, String phone)
    {
        try
        {
            stmt.executeUpdate("Update Person set pName = " + "'" + name + "'" +  ", pAddress = " + "'" + addr + "'" + ", pPhoneNo = " +
                    "'" + phone + "'" + "where personId = " + id); // updated
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
     public int getMaxBookId()
      {
        try
           {
                ResultSet rs = stmt.executeQuery("select max(bookId) from Book");
                while(rs.next())
                 {   
                    return rs.getInt(1);
                 }
           }
           catch(Exception e)
           {
                System.out.println(e);
           }   
        return 0;
      }
    public void addBookInLibrary(String name,String subj, String author)
    {
        int bookId = getMaxBookId() + 1;
        Book book = new Book(bookId, name, author, subj, false);
        library.addBook(book);
        String sql = "INSERT INTO Book VALUES(?, ?, ?, ?, ?)";
 
        try (
                PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, bookId);
                pstmt.setString(2, name); // title
                pstmt.setString(3, subj);
                pstmt.setString(4, author);
                pstmt.setBoolean(5, false);
                pstmt.executeUpdate();
            }
        catch (SQLException e)
        {
                System.out.println(e.getMessage());
        }
    }
    public int updateBookInLibrary(int id, String title, String author, String subj)
    {
        Book book = null;
        for (int i = 0; i < library.getBooks().size(); i++) 
        {
            if (id == library.getBooks().get(i).getId()) 
            {
                book = (Book)library.getBooks().get(i);
            }
        }
        if (book == null) {
            return 1; // no borrower found!
        }
        book.setTitle(title);
        book.setAuthor(author);
        book.setSubject(subj);
        return 2; // success
    }
     public void updateBookInSQL(int id, String title, String author, String subj)
    {
        try
        {
            stmt.executeUpdate("Update Book set bTitle = " + "'" + title + "'" +  ", bAuthor = " + "'" + author + "'" + ", bSubject = " + "'" + subj + "'" + " where bookId = " + id); // updated   
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
     public Person checkLogin(String id, String pass)
     {
        List<Person> persons = getPersons();
        Person lib = null;
        for (int i = 0; i < persons.size(); i++) 
        {
                if (Integer.valueOf(id).equals(persons.get(i).getId()) && pass.equals(persons.get(i).getPassword())) 
                {
                    lib = persons.get(i);
                    break;
                }          
        }     
        return lib;
     }
     public int deleteBookFromLibrary(int id)
     {
         Book book = library.findBook(id);
         if (book == null) { // case 1
             return 1; // no book of this ID is in the DB
         }
         else if (book.getIssueStatus() == true) // case 2, book is issued
         {
             return 2; 
         }
         else if (book.getOnHoldRequests().size() > 0) // case 3, there are some on hold requests on that particular book
         {
             return 3;
         }
         else // can now delete the book
         {
             library.removeBook(book); // removes book corresponding to id
         }
         return 4;
     }
     public void deleteBookFromDB(int id)
     {
         try
        {
            stmt.executeUpdate("Delete from Book where bookId = " + id); // updated   
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
     }
      
    public void closeConnection()
    {
        try
        {
            con.close();
        }
         catch(Exception e)
        {
            System.out.println(e);
        }      
    }
    
}










