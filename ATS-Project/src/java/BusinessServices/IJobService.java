/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessServices;

import Model.Job.IJob;
import Model.Task.ITask;
import Model.Team.ITeam;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 *
 * @author jber5
 */
public interface IJobService {

    public ArrayList<IJob> getJobs();

    public boolean insertJob(IJob jobCreated, String teamId, boolean isEmergencyJob);

    public void setJobFinancials(IJob jobCreated, boolean isEmergency);

    public LocalTime getClaculatedJobEndTime(IJob job);

    public int getMinutes(double minutes);

    public double getcalculatedJobCost(IJob job);

    public double getCalculatedJobRevenue(IJob job, int rate);

    public boolean isDateValid(LocalDate ld);

    public boolean isTimeValid(LocalTime lc, LocalDate ld);

    public boolean isJobTaskValid(ArrayList<ITask> tasks);

    public ArrayList<ITeam> getEligibleTeams(IJob jobCreated, ArrayList<ITeam> universalTeamList);

    public ArrayList<ITeam> filterEligibleTeams(ArrayList<ITeam> eligibleTeams, IJob jobCreated);

    public ArrayList<ITeam> getJobFittingTeams(ArrayList<ITeam> eligibleTeams, IJob jobCreated, ArrayList<ITeam> universalTeamList);

    public boolean isJobTimeIncluded(LocalTime currentTime, IJob currentJob);

    public SortedMap<LocalTime, ArrayList<IJob>> getBusinessHoursEmptySchedule();
    
    public SortedMap<LocalTime, ArrayList<IJob>> getFullDayEmptySchedule();

    public ArrayList<IJob> getJobsByDateList(LocalDate jobsDate);

    public SortedMap<LocalTime, ArrayList<IJob>> getBusinessHoursJobSchedulesByDate(LocalDate ld);
    
    public SortedMap<LocalTime, ArrayList<IJob>> getFullDayJobSchedulesByDate(LocalDate ld);

    public ITeam getAssignedTeam(String teamId);

    public IJob getJobByID(String jobId);

    public boolean deleteJob(IJob job);

    public int getCurrentDayJobCount();

    public double getCurrentMonthJobCost();

    public double getCurrentMonthJobRevenue();

    public double getCurrentYearRevenue(LocalDate ld);

    public double getCurrentYearCost(LocalDate ld);

    public SortedMap<Integer, List<Double>> getEmptyReport();

    public void filterYearlyReportResults(SortedMap<Integer, List<Double>> yearlyReport,
            SortedMap<Integer, List<Double>> resultedReport);

    public SortedMap<Integer, List<Double>> getAnnualReport(LocalDate ld);

    public ArrayList<String> getErrors();

    public JOB_TYPE getJobType();
    
    public void setJobType(JOB_TYPE type);
}
