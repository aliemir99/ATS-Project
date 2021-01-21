/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelperClasses;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jber5
 */
public class HelperMethods {

    //public static ArrayList<String> errorList;

    public static boolean checkNumeric(String numVal) {
        try {
            double tryVal = Double.parseDouble(numVal);
        } catch (Exception ex) {
            //errorList.add("Value is not a numeric Number");
            return false;
        }
        return true;
    }
    
    private String checkRequiredField(String value, String fieldName,ArrayList<String>errors) {
        if (value.equals("")) {
            errors.add(fieldName + " is required");
        }
        return value;
    }
    
    public static boolean checkifNull(String val){
        return val == null || val.equals(" ");
    }

    public static java.sql.Date getCurrDateTime(LocalDate lc) {
        return new java.sql.Date(lc.getYear(),
                lc.getMonthValue(),
                lc.getDayOfMonth());
    }

    public static void forwardTo(HttpServletRequest request, HttpServletResponse response, String URL)
            throws ServletException, IOException {

        RequestDispatcher rds = request.getRequestDispatcher(URL);
        rds.forward(request, response);
    }
    
    public static LocalDate getCurrentDate(){
        return LocalDate.now();
    }
}
