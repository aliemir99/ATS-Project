/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

// <editor-fold desc="Used Imports">
import BusinessServices.ITaskService;
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
 * Purpose: this servlet only used to create a task
 */
public class ProcessCreateTask extends HttpServlet {

    // <editor-fold desc="Local Scope Variables">
    private ITask createdTask;
    private ITaskService taskService;
    // </editor-fold>

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * DOPost: it gets the task information in the request and then it inserts it to the database through task service object
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        taskService = TaskServiceFactory.createInstance();
        
        createdTask = InputViewModel.getTaskFromInputRequest(request);
        taskService.insertTask(createdTask);
         
         response.sendRedirect("ProcessTaskList");
    }

}
