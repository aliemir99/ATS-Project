/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessServices;

// <editor-fold desc="Used Imports">
import DataAccess.ISqlJob;
import DataAccess.ISqlTeam;
import DataAccess.SqlJobFactory;
import DataAccess.SqlTeamFactory;
import Model.Employee.IEmployee;
import Model.Job.IJob;
import Model.Job.JobFactory;
import Model.Task.ITask;
import Model.Team.ITeam;
import Model.Team.TeamFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
// </editor-fold> 

/**
  * @author jber5
  * Purpose: this class is for all job related operation
  * (CREATE,DELETE,UPDATE,READ) and also validation
  * and other business information (COST,REVENUE,REPORTS)
 */
public class JobService implements IJobService {

    // <editor-fold desc="Local Scope Variables">
    private ArrayList<String> erorrList = new ArrayList<>();
    private JOB_TYPE jobType;
    private final LocalTime CASUAL_JOB_MIN_TIME = LocalTime.of(8, 59, 00);
    private final LocalTime CASUAL_JOB_MAX_TIME = LocalTime.of(17, 01, 00);
    private final ISqlTeam teamRepo = SqlTeamFactory.createInstance();
    private final ISqlJob jobRepo = SqlJobFactory.createInstance();
    // </editor-fold> 

    /**
     * @return ArrayList<IJob>
     * Gets all the jobs from Job Repo
     */
    @Override
    public ArrayList<IJob> getJobs() {
        try {
            return jobRepo.getJobs();
        } catch (SQLException ex) {
           System.out.print(ex.toString());
        }
        return new ArrayList<>();
    }

    /**
     * @param jobCreated used to insert the job
     * @param teamId for adding the required team
     * @param isEmergencyJob used for job Financials calculations
     * @return boolean This method sets the assigned team and sets the job
     * Financials then it insert the job using job Repo
     */
    @Override
    public boolean insertJob(IJob jobCreated, String teamId, boolean isEmergencyJob) {
        jobCreated.setAssignedTeam(getAssignedTeam(teamId));
        setJobFinancials(jobCreated, isEmergencyJob);

        try {
            return jobRepo.insertJob(jobCreated);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return false;
    }

    /**
     * @param jobCreated used to set the job Financials
     * @param isEmergency type of job this method set the job cost and job
     * Revenue
     */
    @Override
    public void setJobFinancials(IJob jobCreated, boolean isEmergency) {
        jobCreated.setJobCost(getcalculatedJobCost(jobCreated));
        jobCreated.setJobRevenue(getCalculatedJobRevenue(jobCreated, isEmergency ? 4 : 3));
    }

    /**
     *
     * @param job used to get required time information
     * @return LocalTime this method accumulates all the durations of the job
     * tasks then adds 30M drive back time then we divide the tasksTime by
     * 60(HOUR) to get the duration in hours then we get the job tasks hours and
     * minutes and we add the value to our job start time and then we return the
     * LocalTime of job endTime
     *
     */
    @Override
    public LocalTime getClaculatedJobEndTime(IJob job) {
        final double HOUR = 60.d;
        final int DRIVE_BACK_TIME = 30;

        double jobTasksTime;
        int jobTasksHours;
        int jobTaskMinutes;
        int jobTasksDuration = 0;

        jobTasksDuration = job.getJobTasks().stream().map((jobTask)
                -> jobTask.getDuration()).reduce(jobTasksDuration, Integer::sum);

        jobTasksDuration += DRIVE_BACK_TIME;
        jobTasksTime = jobTasksDuration / HOUR;

        jobTasksHours = (int) jobTasksTime;
        jobTaskMinutes = getMinutes(jobTasksTime - jobTasksHours);

        LocalTime time = job.getStartTime();
        return time.plusHours(jobTasksHours).plusMinutes(jobTaskMinutes);
    }

    /**
     * @param minutes
     * @return the minutes for the parameter
     */
    @Override
    public int getMinutes(double minutes) {
        minutes *= 100;
        return (int) (minutes * 60) / 100;
    }

    /**
     *
     * @param job getting tasks duration and employees wages
     * @return THe job cost we accumulate the team wage and the task duration
     * then multiply the team wage with the job duration divided by 60(HOUR)
     */
    @Override
    public double getcalculatedJobCost(IJob job) {
        int teamWage = 0;
        int jobDuration = 0;
        double cost = 0.0;

        for (IEmployee emp : job.getAssignedTeam().getTeamMembers()) {
            teamWage += emp.getPayRate();
        }
        for (ITask tsk : job.getJobTasks()) {
            jobDuration += tsk.getDuration();
        }

        return teamWage * (jobDuration / 60.d);
    }

    /**
     * @param job getting employees payRate and tasks furation
     * @param rate
     * @return Double Job Revenue we accumulate the team wage and the task
     * duration then multiply the team wage with the job duration divided by
     * 60(HOUR) and multiply it with the rate
     */
    @Override
    public double getCalculatedJobRevenue(IJob job, int rate) {
        int teamWage = 0;
        int jobDuration = 0;
        double revenue = 0.0;

        for (IEmployee emp : job.getAssignedTeam().getTeamMembers()) {
            teamWage += emp.getPayRate();
        }
        for (ITask tsk : job.getJobTasks()) {
            jobDuration += tsk.getDuration();
        }
        revenue = teamWage * (jobDuration / 60) * (rate);

        return revenue;
    }

    /**
     * @param ld Date to check
     * @return boolean check if job date is valid
     */
    @Override
    public boolean isDateValid(LocalDate ld) {
        if (ld.isBefore(LocalDate.now())) {
            return false;
        }

        if (jobType == JOB_TYPE.CASUAL_JOB) {
            switch (ld.getDayOfWeek()) {
                case SATURDAY:
                    return false;
                case SUNDAY:
                    return false;
                default:
                    return true;
            }
        }
        return true;
    }

    /**
     * @param ld Date to check and localTime
     * @return boolean check if job date is valid and localTime is within the
     * bounds of casual Job allowed time
     */
    @Override
    public boolean isTimeValid(LocalTime lc, LocalDate ld) {
        if (lc.isBefore(LocalTime.now()) && ld.equals(LocalDate.now())) {
            return false;
        }

        if (jobType == JOB_TYPE.CASUAL_JOB) {
            return lc.isAfter(CASUAL_JOB_MIN_TIME)
                    && lc.isBefore(CASUAL_JOB_MAX_TIME);
        } else {
            return true;
        }
    }

    /**
     * @param tasks
     * @return Boolean check if job tasks list is not empty
     */
    @Override
    public boolean isJobTaskValid(ArrayList<ITask> tasks) {
        return !tasks.isEmpty();
    }

    /**
     * @param jobCreated
     * @param universalTeamList
     * @return Eligible teams that have all the required job tasks
     */
    @Override
    public ArrayList<ITeam> getEligibleTeams(IJob jobCreated, ArrayList<ITeam> universalTeamList) {
        HashMap<String, ITask> jobTasksMap = new HashMap<>();
        ArrayList<ITeam> eligibleTeams = new ArrayList<>();

        //Inserting task into the HashMap
        jobCreated.getJobTasks().forEach((jobTask) -> {
            jobTasksMap.put(jobTask.getTaskID(), jobTask);
        });

        //Getting Members skills
        //Then try to match the tasks between job and team skills
        for (ITeam team : universalTeamList) {
            boolean canAddTeam = true;
            ArrayList<ArrayList<ITask>> membersSkills = new ArrayList<>();
            HashMap<String, ITask> empSkills = new HashMap<>();

            try {
                //Getting member skills
                membersSkills
                        = teamRepo.getMembersSkills(team);
            } catch (SQLException ex) {
                System.out.print(ex.toString());
            }

            //Put all the skills ID and the SKill object
            membersSkills.forEach((arrTask) -> {
                arrTask.forEach((empSkill) -> {
                    empSkills.put(empSkill.getTaskID(), empSkill);
                });
            });

            //Check if the skills are found in the empSkills Map
            for (Map.Entry jobTaskEl : jobTasksMap.entrySet()) {
                if (!empSkills.containsKey(jobTaskEl.getKey())) {
                    canAddTeam = false;
                }
            }

            //Add team
            if (canAddTeam) {
                eligibleTeams.add(team);
            }

        }
        return eligibleTeams;
    }

    /**
     * @param eligibleTeams Teams that have the required skills
     * @param jobCreated Current job
     * @return List of teams available for the job(TIME)
     */
    @Override
    public ArrayList<ITeam> filterEligibleTeams(ArrayList<ITeam> eligibleTeams, IJob jobCreated) {
        ArrayList<ITeam> availableTeams = new ArrayList<>();
        try {
            for (ITeam currTeam : eligibleTeams) {
                if (jobRepo.checkIfTeamAvailable(currTeam, jobCreated)) {
                    availableTeams.add(currTeam);
                }
            }
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return availableTeams;
    }

    /**
     * @param eligibleTeams
     * @param jobCreated
     * @param universalTeamList
     * @return List of Teams whom are fitting for skills and Time
     */
    @Override
    public ArrayList<ITeam> getJobFittingTeams(ArrayList<ITeam> eligibleTeams, IJob jobCreated, ArrayList<ITeam> universalTeamList) {
        return filterEligibleTeams(getEligibleTeams(jobCreated, universalTeamList), jobCreated);
    }

    /**
     * @param currentTime Time to compare
     * @param currentJob Job start time and end time
     * @return if job start time and end time is within that specified time
     */
    @Override
    public boolean isJobTimeIncluded(LocalTime currentTime, IJob currentJob) {
        return currentTime.equals(currentJob.getStartTime())
                || currentTime.isAfter(currentJob.getStartTime())
                && currentJob.getEndTime().isAfter(currentTime);
    }

    /**
     * @return Sorted map with LocalTime slots as key and empty job list
     */
    @Override
    public SortedMap<LocalTime, ArrayList<IJob>> getBusinessHoursEmptySchedule() {
        SortedMap<LocalTime, ArrayList<IJob>> emptyCalendar = new TreeMap<>();

        //filling the map with times from 7 am - 8:00 pm with new instances of job lists.
        for (int i = 7; i < 20; i++) {
            emptyCalendar.put(LocalTime.of(i, 00, 00), JobFactory.createListInstance());
            emptyCalendar.put(LocalTime.of(i, 30, 00), JobFactory.createListInstance());
        }
        return emptyCalendar;
    }
    
     /**
     * @return Sorted map with LocalTime slots as key and empty job list
     */
    @Override
    public SortedMap<LocalTime, ArrayList<IJob>> getFullDayEmptySchedule() {
         SortedMap<LocalTime, ArrayList<IJob>> emptyCalendar = new TreeMap<>();

        //filling the map with times from 1 am - 23:30 pm with new instances of job lists.
        for (int i = 1; i < 24; i++) {
            emptyCalendar.put(LocalTime.of(i, 00, 00), JobFactory.createListInstance());
            emptyCalendar.put(LocalTime.of(i, 30, 00), JobFactory.createListInstance());
        }
        return emptyCalendar;
    }

    /**
     * @param jobsDate Takes date value
     * @return Job list on that specified date if there is any
     */
    @Override
    public ArrayList<IJob> getJobsByDateList(LocalDate jobsDate) {
        ArrayList<IJob> currDayJobList = new ArrayList<>();
        try {
            currDayJobList = jobRepo.getJobsByDate(jobsDate);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return currDayJobList;
    }

    /**
     * @param ld takes date value
     * @return a Sorted map with times slots a key and any associated jobs in
     * the values that have their job time in that time slot
     */
    @Override
    public SortedMap<LocalTime, ArrayList<IJob>> getBusinessHoursJobSchedulesByDate(LocalDate ld) {
        ArrayList<IJob> currentDayJobs = getJobsByDateList(ld);
        SortedMap<LocalTime, ArrayList<IJob>> jobCalendar = getBusinessHoursEmptySchedule();

        jobCalendar.entrySet().forEach((Map.Entry<LocalTime, ArrayList<IJob>> kvp) -> {
            currentDayJobs.stream().filter((currJob)
                    -> (isJobTimeIncluded(kvp.getKey(), currJob))).forEachOrdered((currJob) -> {
                kvp.getValue().add(currJob);
            });
        });

        return jobCalendar;
    }
    
    /**
     * @param ld takes date value
     * @return a Sorted map with times slots a key and any associated jobs in
     * the values that have their job time in that time slot
     */
    @Override
    public SortedMap<LocalTime, ArrayList<IJob>> getFullDayJobSchedulesByDate(LocalDate ld) {
         ArrayList<IJob> currentDayJobs = getJobsByDateList(ld);
        SortedMap<LocalTime, ArrayList<IJob>> jobCalendar = getFullDayEmptySchedule();

        jobCalendar.entrySet().forEach((Map.Entry<LocalTime, ArrayList<IJob>> kvp) -> {
            currentDayJobs.stream().filter((currJob)
                    -> (isJobTimeIncluded(kvp.getKey(), currJob))).forEachOrdered((currJob) -> {
                kvp.getValue().add(currJob);
            });
        });

        return jobCalendar;
    }

    /**
     *
     * @param teamId to retrieve the team
     * @return ITeam Object by ID
     */
    @Override
    public ITeam getAssignedTeam(String teamId) {
        try {
            return teamRepo.getTeamByID(teamId);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return TeamFactory.createInstance();
    }

    /**
     * @param jobId
     * @return IJob Object
     */
    @Override
    public IJob getJobByID(String jobId) {
        try {
            return jobRepo.getJobByID(jobId);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return JobFactory.createInstance();
    }

    /**
     *
     * @param job job to be deleted
     * @return Boolean
     */
    @Override
    public boolean deleteJob(IJob job) {
        try {
            return jobRepo.deleteJob(job);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return false;
    }

    /**
     *
     * @return a sorted map of Integer and list of Double (Company Yearly
     * Report) Gets a SortedMap from January-December as Key values and list
     * double as a mapping to the key
     */
    @Override
    public SortedMap<Integer, List<Double>> getEmptyReport() {
        final int START_MONTH = 1;
        final int END_MONTH = 12;
        SortedMap<Integer, List<Double>> emptyReport = new TreeMap<>();
        for (int i = START_MONTH; i <= END_MONTH; i++) {
            List<Double> tmplist = new ArrayList<>();
            tmplist.add(0.d);
            tmplist.add(0.d);
            emptyReport.put(i, tmplist);
        }
        return emptyReport;
    }

    /**
     * @param yearlyReport Empty report
     * @param resultedReport This method is to grab all the values in the
     * specified key and puts them in yearlyReport
     */
    @Override
    public void filterYearlyReportResults(SortedMap<Integer, List<Double>> yearlyReport,
            SortedMap<Integer, List<Double>> resultedReport) {

        for (Map.Entry<Integer, List<Double>> kvp : resultedReport.entrySet()) {
            yearlyReport.put(kvp.getKey(), kvp.getValue());
        }
    }

    /**
     * @param ld LocalDate for the Year the report is required
     * @return SortedMap<Integer, List<Double>> We get the annual report from
     * the repo then we Filter the report results
     */
    @Override
    public SortedMap<Integer, List<Double>> getAnnualReport(LocalDate ld) {
        SortedMap<Integer, List<Double>> yearlyReport = getEmptyReport();
        SortedMap<Integer, List<Double>> resultedReport = new TreeMap<>();
        try {
            resultedReport = jobRepo.getAnnualReport(ld);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        filterYearlyReportResults(yearlyReport, resultedReport);
        return yearlyReport;
    }

    @Override
    public ArrayList<String> getErrors() {
        return this.erorrList;
    }

    @Override
    public JOB_TYPE getJobType() {
        return this.jobType;
    }

    @Override
    public void setJobType(JOB_TYPE type) {
        this.jobType = type;
    }

    @Override
    public int getCurrentDayJobCount() {
        try {
            return jobRepo.getCurrentDayJobCount();
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return 0;
    }

    @Override
    public double getCurrentMonthJobCost() {
        try {
            return jobRepo.getCurrentMonthJobCost();
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return 0.d;
    }

    @Override
    public double getCurrentMonthJobRevenue() {
        try {
            return jobRepo.getCurrentMonthJobRevenue();
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }   
        return 0;
    }

    @Override
    public double getCurrentYearRevenue(LocalDate ld) {
        try {
            return jobRepo.getcurrentYearRevenue(ld);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return 0;
    }

    @Override
    public double getCurrentYearCost(LocalDate ld) {
        try {
            return jobRepo.getCurrentYearCost(ld);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return 0;
    }

}
