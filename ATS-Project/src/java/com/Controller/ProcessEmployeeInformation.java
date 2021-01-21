/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

// <editor-fold desc="Used Imports">
import HelperClasses.HelperMethods;
import Model.Employee.IEmployee;
import Model.Task.ITask;
import Model.Task.TaskFactory;
import Model.Team.ITeam;
import BusinessServices.EmployeeServiceFactory;
import BusinessServices.IEmployeeService;
import HelperClasses.InputViewModel;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

/**
 * @author jber5
 * Purpose: this servlet is used to grab an individual Employee Information to be  displayed in the View
 */
public class ProcessEmployeeInformation extends HttpServlet {
    
    // <editor-fold desc="Local Scope Variables">
    private IEmployee requestedEmployee;
    private String maskedSinNum;
    private ITeam employeeAssignedTeam;
    private ArrayList<ITask> universalSkillList;
    private ArrayList<ITask> employeeSkillList;
    private ITask tmpTask;
    private IEmployeeService employeeService;
    private boolean isShowingSin;
    // </editor-fold> 

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * DOGet: it gets the requested employee by ID
     * We set the required objects and attributes for the view
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        employeeService = EmployeeServiceFactory.createInstance();

        String id = request.getParameter("employeeID");

        requestedEmployee = employeeService.getEmployeeByID(id);
        employeeAssignedTeam = employeeService.getEmployeeAssignedTeam(requestedEmployee);

        universalSkillList = employeeService.getEmployeeUnAssignedSkills(requestedEmployee);
        employeeSkillList = employeeService.getEmployeeAssignedSkills(requestedEmployee);
        
        maskedSinNum = employeeService.getMaskedSin(requestedEmployee);
        isShowingSin = false;

        setRequestObjectsAttributes(request);
        HelperMethods.forwardTo(request, response, "EmployeeInformation.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Check for any Skill operations (REMOVE,ADD)
        processEmployeeSkillRequest(request, response);

        //Check for any explicit employee operation (UPDATE,DELETE,ARCHIVE,RESTORE)
        processEmployeeOperationsRequest(request, response);

    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * This method handles and employee skill operation (REMOVE,ADD)
     */
    private void processEmployeeSkillRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("addSkill") != null) {
            String taskID = request.getParameter("universalSkills");
            tmpTask = TaskFactory.createInstance();
            tmpTask.setTaskID(taskID);

            employeeService.insertEmployeeSkill(requestedEmployee, tmpTask);

            universalSkillList = employeeService.getEmployeeUnAssignedSkills(requestedEmployee);
            employeeSkillList = employeeService.getEmployeeAssignedSkills(requestedEmployee);

            setRequestObjectsAttributes(request);
            HelperMethods.forwardTo(request, response, "EmployeeInformation.jsp");

        }

        if (request.getParameter("removeSkill") != null) {
            String taskID = request.getParameter("employeeSkill");
            tmpTask = TaskFactory.createInstance();
            tmpTask.setTaskID(taskID);

            employeeService.deleteEmployeeSkill(requestedEmployee, tmpTask);
            universalSkillList = employeeService.getEmployeeUnAssignedSkills(requestedEmployee);
            employeeSkillList = employeeService.getEmployeeAssignedSkills(requestedEmployee);

            setRequestObjectsAttributes(request);
            HelperMethods.forwardTo(request, response, "EmployeeInformation.jsp");
        }

    }

    
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * This Method handles Employee Entity operations (SAVE,ARCHIVE,RESTORE,DELETE,SHOW SIN number)
     */
    private void processEmployeeOperationsRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        if (request.getParameter("saveEmployeeBtn") != null) {
            IEmployee tmpEmployee = requestedEmployee;
            String tmpID = requestedEmployee.getEmployeeID();
            tmpEmployee = InputViewModel.
                    getExistingEmployeeFromInputRequest(request, employeeService);
            if (employeeService.isErrorListEmpty()) {
                requestedEmployee = tmpEmployee;
                requestedEmployee.setEmployeeID(tmpID);
                employeeService.updateEmployeeInformation(requestedEmployee); 
            } else {
                request.setAttribute("inputError", employeeService.getErrors());
            }
            setRequestObjectsAttributes(request);
            HelperMethods.forwardTo(request, response, "EmployeeInformation.jsp");
        }

        
        if (request.getParameter("archiveEmployeeBtn") != null) {
            employeeService.archiveEmployee(requestedEmployee);
            setRequestObjectsAttributes(request);
            request.setAttribute("inputError", employeeService.getErrors());
            HelperMethods.forwardTo(request, response, "EmployeeInformation.jsp");
        }

        if (request.getParameter("restoreEmployeeBtn") != null) {
            employeeService.restoreEmployee(requestedEmployee);
            setRequestObjectsAttributes(request);
            HelperMethods.forwardTo(request, response, "EmployeeInformation.jsp");
        }

        if (request.getParameter("deleteEmployeeBtn") != null) {
            employeeService.deleteEmployee(requestedEmployee);
            response.sendRedirect("EmployeeOperations");
        }
        
        if(request.getParameter("showSinBtn") != null){
            isShowingSin = true;
            setRequestObjectsAttributes(request);
            HelperMethods.forwardTo(request, response, "EmployeeInformation.jsp");
        }
        
        
    }

    /**
     * @param request
     * Method to set the required request attributes to display the information in the view
     */
    private void setRequestObjectsAttributes(HttpServletRequest request) {
        request.setAttribute("employee", requestedEmployee);
        request.setAttribute("employeeTeam", employeeAssignedTeam);
        request.setAttribute("universalSkills", universalSkillList);
        request.setAttribute("employeeSkills", employeeSkillList);
        request.setAttribute("maskedSin", maskedSinNum);
        request.setAttribute("isShowingSin", isShowingSin);
    }
}
