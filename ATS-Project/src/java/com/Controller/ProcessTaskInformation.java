/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

// <editor-fold desc="Used Imports">
import BusinessServices.ITaskService;
import HelperClasses.HelperMethods;
import Model.Task.ITask;
import BusinessServices.TaskServiceFactory;
import HelperClasses.InputViewModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

/**
 * @author aliem
 * Purpose: this servlet is used to grab an individual Task Information 
 * to be displayed in the View
 */
public class ProcessTaskInformation extends HttpServlet {
    
    // <editor-fold desc="Local Scope Variables">
    private ITask requestedTask;
    private ITaskService taskService;
    // </editor-fold>

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * DOGet: it gets the corresponding Task by the Id from the request
     * and it grabs the required information for the view 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        taskService = TaskServiceFactory.createInstance();
        int taskID = Integer.parseInt(request.getParameter("taskID"));
        requestedTask = taskService.getTaksByID(taskID);
        taskService.setSkillHolders(requestedTask);

        setRequestObjectsAttributes(request);
        HelperMethods.forwardTo(request, response, "TaskInformation.jsp");
    }

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * DOPost: it's used for Tasks operations (UPDATE,DELETE)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("updateTaskBtn") != null) {
            
            String tmpId = requestedTask.getTaskID();
            
            requestedTask = InputViewModel.getTaskFromInputRequest(request);
            requestedTask.setTaskID(tmpId);
            
            taskService.updateTask(requestedTask);
            taskService.setSkillHolders(requestedTask); 
            
            setRequestObjectsAttributes(request);
            HelperMethods.forwardTo(request, response, "TaskInformation.jsp");
        }

        if (request.getParameter("deleteTaskBtn") != null) {
            if (!taskService.deleteTask(requestedTask)) {
                setRequestObjectsAttributes(request);
                HelperMethods.forwardTo(request, response, "TaskInformation.jsp");
                return;
            }
            response.sendRedirect("ProcessTaskList");
        }
    }
    
    
    /**
     * 
     * @param request 
     * Method to set the required request attributes to display the information in the view
     */
    private void setRequestObjectsAttributes(HttpServletRequest request){
        request.setAttribute("errorMessage", taskService.getErrors());
        request.setAttribute("task", requestedTask);
    }
    
    
}
