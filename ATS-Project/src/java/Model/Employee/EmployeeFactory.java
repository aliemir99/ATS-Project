/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Employee;

import java.util.ArrayList;

/**
 *
 * @author Ali Emir Samlioglu
 */
public abstract class EmployeeFactory {
    public static IEmployee createInstance(){
        return new Employee();
    }
    public static ArrayList<IEmployee> createListInstance(){
            return new ArrayList<IEmployee>();
    }
}
