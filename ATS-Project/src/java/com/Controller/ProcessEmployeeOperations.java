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
import HelperClasses.InputViewModel;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
// </editor-fold>

/**
 * @author jber5
 * Purpose: This servlet is used to process the creation of an employee
 * and also to view current list of employees
 */
public class ProcessEmployeeOperations extends HttpServlet {

    // <editor-fold desc="Local Scope Variables">
    private HttpSession session;
    private ArrayList<IEmployee> activeEmployeeList;
    private ArrayList<IEmployee> archivedEmployeeList;
    private boolean viewArchive;
    private boolean viewActive;
    private IEmployeeService employeeService;
    private IEmployee createdEmployee;
    // </editor-fold>

    
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * DoGet: IS used to load the employees list (Active,Archived)
     * then it sets the required attributes for the view
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        viewActive = true;
        viewArchive = false;
        session = request.getSession();
        employeeService = EmployeeServiceFactory.createInstance();

        activeEmployeeList = employeeService.getActiveEmployees();
        archivedEmployeeList = employeeService.getArchivedEmployees();

        setRequestObjectsAttributes(request);
        session.setAttribute("createEmployeeErrors", null);
        HelperMethods.forwardTo(request, response, "EmployeeList.jsp");
    }

    /** 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * 
     * DOPost: it's used to process Employee Creation request
     * also it triggers the view between active employees and archived employee
     * upon the user's request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("createEmployee") != null) {
            createdEmployee = InputViewModel.getNewEmployeeFromInputRequest(request, employeeService);
            employeeService.insertEmployee(createdEmployee);
            session.setAttribute("createEmployeeErrors", employeeService.getErrors());
        }

        if (request.getParameter("showActiveEmployeesBtn") != null) {
            viewActive = true;
            viewArchive = false;
        }
        if (request.getParameter("showArchivedEmployeesBtn") != null) {
            viewActive = false;
            viewArchive = true;
        }

        setRequestObjectsAttributes(request);
        HelperMethods.forwardTo(request, response, "EmployeeList.jsp");
    }

    /**
     * @param request 
     * Method to set the required request attributes to display the information in the view
     */
    private void setRequestObjectsAttributes(HttpServletRequest request) {
        request.setAttribute("isActive", viewActive);
        request.setAttribute("isArchive", viewArchive);
        request.setAttribute("activeEmployees", activeEmployeeList);
        request.setAttribute("archivedEmployees", archivedEmployeeList);
    }
}
