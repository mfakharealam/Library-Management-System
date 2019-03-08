/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooada1;

import java.util.List;

/**
 *
 * @author Muhammad Fakhar
 */
abstract public class Person {
    private int id;
    private String Name, Address, PhoneNo, Password;
    static int currId = 0;
    public Person(int id, String Name, String Address, String PhoneNo, String password) {
        currId++;
        if(id == 0) // if id is not set already from the database
        {
                    this.id = currId;
        }
        else
        {
                    this.id = id;      
        }
        this.Name = Name;
        this.Address = Address;
        this.PhoneNo = PhoneNo;
        this.Password = password;
    }
    
    public void printInfo()
    {
        System.out.println("Personal Details: ");
        System.out.println("ID: " + id);
        System.out.println("Name: " + Name);
        System.out.println("Phone No: " + PhoneNo );
        System.out.println("Address: " + Address);
    }

        /////////////////////////// GETTERS & SETTERS /////////////////////////////////
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    
}
