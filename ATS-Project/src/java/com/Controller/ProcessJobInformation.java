/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

// <editor-fold desc="Used Imports">
import BusinessServices.IJobService;
import HelperClasses.HelperMethods;
import Model.Job.IJob;
import BusinessServices.JobServiceFactory;
import Model.Task.ITask;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

/**
 * @author jber5
 * Purpose: This servlet for showing an Individual Job Information
 */
public class ProcessJobInformation extends HttpServlet {
    
     // <editor-fold desc="Local Scope Variables">
    private IJob currentJob;
    private boolean canDeleteJob;
    private IJobService jobService;
    private HashMap<ITask,Integer> accumelatedJobTasks; //Using HashMap to not display duplicate tasks
    // </editor-fold> 
    
    
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * 
     * DoGet: first it gets the job by it's ID then we check if job can be deleted to show the option in UI
     * Also we get the accumulated Tasks using getUniqueJobTasks method
     * then we set the request attributes and forward to the corresponding page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        jobService = JobServiceFactory.createInstance();
                
        String tmpJobID = request.getParameter("jobId");
        accumelatedJobTasks = new HashMap<>();
        currentJob = jobService.getJobByID(tmpJobID);
        canDeleteJob = currentJob.getStartDate().isAfter(LocalDate.now());
        
        accumelatedJobTasks = getUniqueJobTasks();

        request.setAttribute("canDeleteJob", canDeleteJob);
        request.setAttribute("currentJob", currentJob);
        request.setAttribute("jobTasksMap", accumelatedJobTasks);
        HelperMethods.forwardTo(request, response, "JobInformation.jsp");
    }

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * DoPost: if user wants to delete job we call deleteJob from Job service
     * then we redirect to job list and job creation form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("deleteJobBtn") != null) {
            jobService.deleteJob(currentJob);
            response.sendRedirect("ProcessCreateJob");
        }

    }
    
    
    //Function to get all the unique tasks and counts the number of occurences
    //Returns a HashMap with key of Task that maps to an integer(number of occurences)
    //Only used in this scope
    private HashMap<ITask,Integer> getUniqueJobTasks(){
        HashMap<ITask,Integer> uniqueJobTasks = new HashMap<>();
        HashMap<String,Integer> taskIDsMap = new HashMap<>();
        for(ITask tmpTask : currentJob.getJobTasks()){
            if(taskIDsMap.containsKey(tmpTask.getTaskID())){
                int taskCount = taskIDsMap.get(tmpTask.getTaskID());
                taskIDsMap.put(tmpTask.getTaskID(),taskCount+1);
            }else{
                taskIDsMap.put(tmpTask.getTaskID(), 1);
            }
        }
        
        for(Map.Entry kvp: taskIDsMap.entrySet()){
            for(ITask tmpTask : currentJob.getJobTasks()){
                if(tmpTask.getTaskID().equals(kvp.getKey())){
                    uniqueJobTasks.put(tmpTask,(Integer)kvp.getValue());
                    break;
                }
            }
        }
       
        return uniqueJobTasks;
    }

}
