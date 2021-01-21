/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelperClasses;

// <editor-fold desc="Used Imports">
import BusinessServices.IEmployeeService;
import BusinessServices.IJobService;
import BusinessServices.JOB_TYPE;
import Model.Employee.EmployeeFactory;
import Model.Employee.IEmployee;
import Model.Job.IJob;
import Model.Job.JobFactory;
import Model.Task.ITask;
import Model.Task.TaskFactory;
import Model.Team.ITeam;
import Model.Team.TeamFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
// </editor-fold>

/**
 * @author jber5 Purpose: get all Entity creation inputs from the View
 * (Task,Team,Job,Employee) and input validation
 */
public class InputViewModel {

    //----------Task related Method
    public static ITask getTaskFromInputRequest
        (HttpServletRequest request) {
        ITask task = TaskFactory.createInstance();
        LocalDate lc = LocalDate.now();
        task.setName(request.getParameter("taskName"));
        task.setDescription(request.getParameter("taskDescription"));
        task.setDuration(Integer.parseInt(request.getParameter("taskDuration")));
        task.setDateCreated(lc);

        return task;
    }

    //----------Team related Method
    public static ITeam getTeamFromInputRequest
        (HttpServletRequest request) {
        LocalDate lc = LocalDate.now();
        ITeam team = TeamFactory.createInstance();

        team.setDateCreated(lc);
        team.setName(request.getParameter("teamName"));
        team.setOnCall(false);
        team.setTeamStatus(true);
        return team;
    }

    //----------Employee related Methods
    public static IEmployee getNewEmployeeFromInputRequest
        (HttpServletRequest request, IEmployeeService employeeService) {
        IEmployee employee = EmployeeFactory.createInstance();
        validateNewEmployeeRequiredFeilds(request, employeeService);
        if (employeeService.isErrorListEmpty()) {
            LocalDate lc = LocalDate.now();
            employee.setFirstName(request.getParameter("firstName"));
            employee.setLastName(request.getParameter("lastName"));
            employee.setSIN(request.getParameter("sin"));
            employee.setPayRate(Double.parseDouble(request.getParameter("payRate")));
            employee.setDateCreated(lc);
            employee.setEmployeeStatus(true);
        }

        return employee;
    }

    public static IEmployee getExistingEmployeeFromInputRequest
        (HttpServletRequest request, IEmployeeService employeeService) {

        IEmployee employee = EmployeeFactory.createInstance();
        validateExistingEmployeeRequiredFields(request, employeeService);
        if (employeeService.isErrorListEmpty()) {
            LocalDate lc = LocalDate.now();
            employee.setFirstName(request.getParameter("firstName"));
            employee.setLastName(request.getParameter("lastName"));
            employee.setSIN(request.getParameter("sin"));
            employee.setPayRate(Double.parseDouble(request.getParameter("payRate")));
            employee.setDateCreated(lc);
            employee.setEmployeeStatus(true);
        }

        return employee;
    }

    public static void validateNewEmployeeRequiredFeilds
        (HttpServletRequest request, IEmployeeService employeeService) {
        employeeService.getErrors().clear();
        String payPerHour = request.getParameter("payRate");
        String sinString = request.getParameter("sin");
        employeeService.validatePayRateValue(payPerHour);
        employeeService.validateSIN(sinString);
    }

    public static void validateExistingEmployeeRequiredFields
        (HttpServletRequest request, IEmployeeService employeeService) {
        employeeService.getErrors().clear();
        String payPerHour = request.getParameter("payRate");
        String sinString = request.getParameter("sin");
        employeeService.validatePayRateValue(payPerHour);
        employeeService.validateSinInput(sinString);
    }

    //----------job related Methods    
    public static IJob getJobFromRequest
        (HttpServletRequest request, ArrayList<ITask> universalTasks,IJobService jobService, boolean isEmergencyBooking) {

        if (!isEmergencyBooking) {
            return getCasualJobFromRequest(request, universalTasks, jobService);
        } else {
            return getEmergencyJobFromRequest(request, universalTasks, jobService);
        }
    }

    public static IJob getCasualJobFromRequest
        (HttpServletRequest request, ArrayList<ITask> universalTasks,
            IJobService jobService) {
            
            
        jobService.setJobType(JOB_TYPE.CASUAL_JOB);

        validateJobFields(request, universalTasks, jobService);

        if (jobService.getErrors().isEmpty()) {
            LocalDate lc = LocalDate.now();
            IJob job = JobFactory.createInstance();
            job.setClientName(request.getParameter("clientName"));
            job.setDescription(request.getParameter("jobDescription"));
            job.setIsEmergencyJob(false);
            job.setJobTasks(getJobTasksFromRequest(request, universalTasks));
            job.setDateCreated(lc);

            job.setStartDate(getJobStartDateFromRequest(request));
            job.setStartTime(getJobStartTimeFromRequest(request));
            job.setEndTime(jobService.getClaculatedJobEndTime(job));
            return job;
        } else {
            return null;
        }

    }

    public static IJob getEmergencyJobFromRequest
        (HttpServletRequest request, ArrayList<ITask> universalTasks,IJobService jobService) {

        jobService.setJobType(JOB_TYPE.EMRGENCY_JOB);
        validateJobFields(request, universalTasks, jobService);

        if (jobService.getErrors().isEmpty()) {
            LocalDate lc = LocalDate.now();
            IJob job = JobFactory.createInstance();
            job.setClientName(request.getParameter("clientName"));
            job.setDescription(request.getParameter("jobDescription"));
            job.setIsEmergencyJob(true);
            job.setJobTasks(getJobTasksFromRequest(request, universalTasks));
            job.setDateCreated(lc);

            job.setStartDate(getJobStartDateFromRequest(request));
            job.setStartTime(getJobStartTimeFromRequest(request));
            job.setEndTime(jobService.getClaculatedJobEndTime(job));
            return job;
        } else {
            return null;
        }

    }

    public static void validateJobFields(HttpServletRequest request, ArrayList<ITask> universalTasks,
            IJobService jobService) {
        jobService.getErrors().clear();
        if (!jobService.isDateValid(getJobStartDateFromRequest(request))) {
            jobService.getErrors().add("Please provide valid Date");
        }
        if (!jobService.isTimeValid(getJobStartTimeFromRequest(request), getJobStartDateFromRequest(request))) {
            jobService.getErrors().add("Please provide valid Time");
        }
        if (!jobService.isJobTaskValid(getJobTasksFromRequest(request, universalTasks))) {
            jobService.getErrors().add("Please Provide a Task(s)");
        }
    }

    public static ArrayList<ITask> getJobTasksFromRequest(HttpServletRequest request, ArrayList<ITask> universalTasks) {
        ArrayList<ITask> jobTasks = new ArrayList<>();
        String[] taskSelections = request.getParameterValues("tasksSelection");

        if (taskSelections != null) {
            for (String taskSelection : taskSelections) {
                final String taskIdTarget = taskSelection;
                String taskAmount = request.getParameter(taskIdTarget);

                ITask tmpTask = universalTasks.stream()
                        .filter(task -> taskIdTarget.
                        equals(task.getTaskID())).findFirst().orElse(null);

                for (int m = 0; m < Integer.parseInt(taskAmount); m++) {
                    jobTasks.add(tmpTask);
                }
            }
        }
        return jobTasks;

    }

    public static LocalTime getJobStartTimeFromRequest(HttpServletRequest request) {
        LocalTime lt = LocalTime.parse(request.getParameter("jobStartTime"));
        return lt;
    }

    public static LocalDate getJobStartDateFromRequest(HttpServletRequest request) {
        String ld = request.getParameter("jobStartDate");
        LocalDate lc = LocalDate.parse(ld);

        return lc;
    }

}
