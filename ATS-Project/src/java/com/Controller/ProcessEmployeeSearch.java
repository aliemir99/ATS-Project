/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

// <editor-fold desc="Used Imports">
import HelperClasses.HelperMethods;
import Model.Employee.IEmployee;
import BusinessServices.EmployeeServiceFactory;
import BusinessServices.IEmployeeService;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//</editor-fold>

/**
 * @author jber5
 * Purpose: is to search for employees 
 */
public class ProcessEmployeeSearch extends HttpServlet {

    // <editor-fold desc="Local Scope Variables">
    private ArrayList<IEmployee> employeeSearchResult;
    private IEmployeeService employeeService = 
            EmployeeServiceFactory.createInstance();;
    private String searchType;
    private String searchInput;
    //</editor-fold>


    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * DoPost: Check if the search button is clicked
     * it grabs the searchInput from the user and
     * the search type (SIN,Last Name)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("searchBtn") != null) {
            searchInput = request.getParameter("searchUserInput");
            searchType = request.getParameter("searchFilter");
            
            employeeSearchResult = 
                   employeeService.getEmployeeAccordingSearch(searchType, searchInput);
                        
            request.setAttribute("empSearchResult", employeeSearchResult);
            HelperMethods.forwardTo(request, response, "EmployeeSearch.jsp");
        }
    }

}
