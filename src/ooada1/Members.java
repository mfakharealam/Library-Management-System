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
public class Members extends Person{
    private double memberSalary;

    public Members(float memberSalary, int id, String Name, String Address, String PhoneNo, String password) {
        super(id, Name, Address, PhoneNo, password);
        this.memberSalary = memberSalary;
    }
//////////////////////////// GETTER ///////////////////////////////
    public double getMemberSalary() {
        return memberSalary;
    }
///////////////////////////////////////////////////////////////////////////////
    @Override
    public void printInfo()
    {
        super.printInfo();
        System.out.println("Salary of the Member: " + memberSalary);
    }
}
