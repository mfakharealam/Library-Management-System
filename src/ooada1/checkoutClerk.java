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
public class checkoutClerk extends Members {

    public checkoutClerk(float memberSalary, int id, String Name, String Address, String PhoneNo, String password) {
        super(memberSalary, id, Name, Address, PhoneNo, password);
    }
    
    @Override
    public void printInfo()
    {
        super.printInfo();
        System.out.println("ID of Checkout Clerk is: " + super.getId() );         
    }
    
}
