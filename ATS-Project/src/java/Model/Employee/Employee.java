/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Employee;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Ali Emir Samlioglu
 */
public class Employee implements IEmployee, Serializable{
    private String employeeID;
    private String firstName;
    private String lastName;
    private String SIN;
    private double payRate;
    private LocalDate dateCreated;
    private boolean employeeStatus;
    
    public Employee(){
        
    }

    @Override
    public String getEmployeeID() {
        return employeeID;
    }

    @Override
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    
    
    @Override
    public String getFirstName() {
        return firstName;
    }
    
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    @Override
    public String getLastName() {
        return lastName;
    }
    
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public String getSIN() {
        return SIN;
    }
    
    @Override
    public void setSIN(String SIN) {
        this.SIN = SIN;
    }
    
    @Override
    public double getPayRate() {
        return payRate;
    }
    
    @Override
    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }
    
    @Override
    public LocalDate getDateCreated() {
        return dateCreated;
    }
    
    @Override
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    
    public String getFullName(){
        return this.firstName +' ' + this.lastName;
    }

    @Override
    public Boolean getEmployeeStatus() {
       return this.employeeStatus;
    }

    @Override
    public void setEmployeeStatus(boolean status) {
        this.employeeStatus = status;
    }
}
