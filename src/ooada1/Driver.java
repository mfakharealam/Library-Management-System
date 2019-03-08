/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooada1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 *
 * @author Muhammad Fakhar
 */
////////////////////// Some Assumptions ///////////////////////////////
// made some assumptions like only borrower can borrow or reserve the book! Similarly as described in book, only
// borrower's Personal Information can be viewed in Personal Info GUI.
// also have all the references in each class but since there were no constraints in this assignment decided to this way.
// Only used book instead of items.
public class Driver {
    public static void main(String[] args) throws  SQLException, IOException 
    {
       Library library = new Library();
       DBConnection db = new DBConnection(library);
       library.setNameOfLibrary("NUCES Library");
       library = db.populateLibrary(library);
       db.closeConnection(); // close the connection
       if (library == null) 
       {
           System.out.println("Connection Failed");
       }
       LoginGUI startApp = new LoginGUI(library);
       startApp.setVisible(true);
    }
}
