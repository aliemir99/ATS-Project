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
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

/**
 * @author aliem
 * Purpose: This servlet is used to process all the tasks
 * and set the attributes for the view
 */
public class ProcessTaskList extends HttpServlet {

    // <editor-fold desc="Local Scope Variables">
    private ArrayList<ITask> taskList;
    private ITaskService taskService;
    // </editor-fold>

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * DoGet: it gets all tasks and set the attributes then it forwards it to the view 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        taskService = TaskServiceFactory.createInstance();
        taskList = taskService.getTasks();

        request.setAttribute("tasks", taskList);
        HelperMethods.forwardTo(request, response, "TaskList.jsp");
    }

}
