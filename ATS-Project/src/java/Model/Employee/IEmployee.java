/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Employee;
import java.time.LocalDate;

/**
 *
 * @author Ali Emir Samlioglu
 */
public interface IEmployee {
    
     public String getFirstName();
     
     public void setFirstName(String firstName);
     
     public void setEmployeeID(String id);
     
     public String getEmployeeID();
     
     public String getLastName();
     
     public void setLastName(String lastName);
     
     public String getFullName();
   
     public String getSIN();
     
     public void setSIN(String SIN);
     
     public double getPayRate();
     
     public void setPayRate(double payRate);
     
     public LocalDate getDateCreated();
     
     public void setDateCreated(LocalDate dateCreated);
     
     public Boolean getEmployeeStatus();
     
     public void setEmployeeStatus(boolean status);
     
}