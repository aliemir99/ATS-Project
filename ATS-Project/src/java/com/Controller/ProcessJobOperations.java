/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

// <editor-fold desc="Used Imports">
import BusinessServices.IJobService;
import BusinessServices.ITaskService;
import BusinessServices.ITeamService;
import Model.Job.IJob;
import Model.Task.ITask;
import Model.Team.ITeam;
import BusinessServices.JobServiceFactory;
import BusinessServices.TaskServiceFactory;
import BusinessServices.TeamServiceFactory;
import HelperClasses.InputViewModel;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold> 

/**
 * @author jber5
 * Purpose: this where we display the job schedule and job list
 * and Job creation processing
 */
public class ProcessJobOperations extends HttpServlet {
    
    // <editor-fold desc="Local Scope Variables">
    private ArrayList<ITask> universalTaskList;
    private ArrayList<ITeam> universalTeamList;
    private ArrayList<ITeam> eligibleTeams;
    private IJob jobCreated;
    private boolean showTeamsSelection;
    private boolean isEmergencyBooking;
    private boolean isShowingSchedule;
    private boolean isShowingJobs;
    private LocalDate currentViewingDate;
    private ArrayList<IJob> universalJobsList;
    private SortedMap<LocalTime, ArrayList<IJob>> jobCal;
    private ITaskService taskService;
    private ITeamService teamService;
    private IJobService jobService;
    // </editor-fold>   
    
    
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * 
     * DoGet: In here we instantiate or Services
     * and initialize our local Scope variables
     * Then we set the required request attributes then we forward to the View
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        taskService = TaskServiceFactory.createInstance();
        teamService = TeamServiceFactory.createInstance();
        jobService = JobServiceFactory.createInstance();

        currentViewingDate = LocalDate.now();
        jobCal = new TreeMap<>();
        isEmergencyBooking = false;
        showTeamsSelection = false;
        isShowingSchedule = false;
        isShowingJobs = true;
        eligibleTeams = new ArrayList<>();
        universalJobsList = new ArrayList<>();
        universalTeamList = teamService.getActiveTeams();
        universalTaskList = taskService.getTasks();
        universalJobsList = jobService.getJobs();
        jobCal = jobService.getBusinessHoursJobSchedulesByDate(currentViewingDate);

        setRequestObjectsAttributes(request);
        HelperClasses.HelperMethods.forwardTo(request, response, "JobList.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        getViewButtonsRequest(request, response);
        getJobFormCommandButtonsRequest(request, response);
    }

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * 
     * This method specific for View operations
     */
    private void getViewButtonsRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("emergencyJobBtn") != null) {
            isEmergencyBooking = true;
            setRequestObjectsAttributes(request);
            HelperClasses.HelperMethods.forwardTo(request, response, "JobList.jsp");
        }

        if (request.getParameter("normalJobBtn") != null) {
            isEmergencyBooking = false;
            setRequestObjectsAttributes(request);
            HelperClasses.HelperMethods.forwardTo(request, response, "JobList.jsp");
        }

        if (request.getParameter("showJobsBtn") != null) {
            isShowingJobs = true;
            isShowingSchedule = false;
            setRequestObjectsAttributes(request);
            HelperClasses.HelperMethods.forwardTo(request, response, "JobList.jsp");
        }

        if (request.getParameter("showScheduleBtn") != null) {
            isShowingJobs = false;
            isShowingSchedule = true;
            setRequestObjectsAttributes(request);
            HelperClasses.HelperMethods.forwardTo(request, response, "JobList.jsp");
        }

        if (request.getParameter("prevDayScheduleBtn") != null) {
            currentViewingDate = currentViewingDate.minusDays(1);
            jobCal = jobService.getBusinessHoursJobSchedulesByDate(currentViewingDate);
            setRequestObjectsAttributes(request);
            HelperClasses.HelperMethods.forwardTo(request, response, "JobList.jsp");
        }

        if (request.getParameter("nextDayScheduleBtn") != null) {
            currentViewingDate = currentViewingDate.plusDays(1);
            jobCal = jobService.getBusinessHoursJobSchedulesByDate(currentViewingDate);
            setRequestObjectsAttributes(request);
            HelperClasses.HelperMethods.forwardTo(request, response, "JobList.jsp");
        }
        
        if(request.getParameter("busisnessHrsSchduleBtn") != null){
            jobCal = jobService.getBusinessHoursJobSchedulesByDate(currentViewingDate);
            setRequestObjectsAttributes(request);
            HelperClasses.HelperMethods.forwardTo(request, response, "JobList.jsp");
        }
        
        if(request.getParameter("allDayScheduleBtn") != null){
            jobCal = jobService.getFullDayJobSchedulesByDate(currentViewingDate);
            setRequestObjectsAttributes(request);
            HelperClasses.HelperMethods.forwardTo(request, response, "JobList.jsp");
        }
        
    }

    private void getJobFormCommandButtonsRequest(HttpServletRequest request, HttpServletResponse response) 
           throws IOException, ServletException {
        
        getJobFormSpecificationsRequest(request, response);
        getCreateJobFormRequest(request, response);

    }

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * 
     * This Method is for the first step of job creation
     * we grab all the required information for the job
     * then we get the job details from the request then we prepare the eligible teams
     * for the job, then we forward to the View
     */
    private void getJobFormSpecificationsRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("nextBtn") != null) {
            jobCreated = InputViewModel.
                    getJobFromRequest(request, universalTaskList, jobService, isEmergencyBooking);
            
            if (jobCreated != null) {
                if (!jobCreated.isEmeregencyJob()) {
                    eligibleTeams = jobService.
                            getJobFittingTeams(eligibleTeams, jobCreated, universalTeamList);
                } else {
                    eligibleTeams.add(teamService.getTeamOnCall());
                }
                
                showTeamsSelection = true;
            }
            setRequestObjectsAttributes(request);
            HelperClasses.HelperMethods.forwardTo(request, response, "JobList.jsp");
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @throws IOException 
     * 
     * After job specifications are submitted and user chose a team
     * we call Insert job from job service to process the insertion to the Database
     * then we redirect to kob list page
     */
    private void getCreateJobFormRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("createJobBtn") != null) {
            String teamId = request.getParameter("teamSelection");
            jobService.insertJob(jobCreated, teamId, isEmergencyBooking);
            response.sendRedirect("ProcessCreateJob");
        }
    }

    /**
     * @param request 
     * Method to set the required request attributes to display the information in the view
     */
    private void setRequestObjectsAttributes(HttpServletRequest request) {
        request.setAttribute("isShowingSchedule", isShowingSchedule);
        request.setAttribute("isShowingJobs", isShowingJobs);
        request.setAttribute("universalJobsList", universalJobsList);
        request.setAttribute("currentDate", currentViewingDate);
        request.setAttribute("jobCalendarMap", jobCal);
        request.setAttribute("isEmergencyBooking", isEmergencyBooking);
        request.setAttribute("universalTasks", universalTaskList);
        request.setAttribute("universalTeamList", universalTeamList);
        request.setAttribute("showTeamList", showTeamsSelection);
        request.setAttribute("errorList", jobService.getErrors());
        request.setAttribute("eligibleTeams", eligibleTeams);
    }
}
