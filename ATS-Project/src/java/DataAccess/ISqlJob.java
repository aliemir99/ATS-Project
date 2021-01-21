/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Model.Employee.IEmployee;
import Model.Job.IJob;
import Model.Task.ITask;
import Model.Team.ITeam;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 *
 * @author jber5
 */
public interface ISqlJob {
     public ArrayList<IJob> getJobs() throws SQLException;
     
     public IJob getJobByID(String jobId) throws SQLException ;
     
     public ArrayList<IJob> getJobsByDate(LocalDate ld) throws SQLException;
     
     public ArrayList<ITask> getJobTasks(String jobId) throws SQLException;
     
     public boolean insertJob(IJob job) throws SQLException;
     
     public boolean deleteJob(IJob job) throws SQLException;
     
     public boolean insertJobTask(ITask task, String jobId) throws SQLException;
     
     public void insertIndividualJobTasks(IJob job) throws SQLException;
     
     public boolean insertJobFinancials(IJob job) throws SQLException;
     
     public String getLastInsertedJobID() throws SQLException;
     
     public ArrayList<ITeam> getAvailableTeams(IJob job) throws SQLException;
     
     public boolean checkIfTeamAvailable(ITeam team, IJob job) throws SQLException;
     
     public IJob getJobFromRS(ResultSet rs) throws SQLException;
     
     public ITask getTaskFromRs(ResultSet rs) throws SQLException;
     
     public ITeam getTeamFromRs(ResultSet rs) throws SQLException;
     
     public IEmployee getEmployeeFromRs(ResultSet rs) throws  SQLException;
     
     public ITeam getJobTeamByID(String id) throws SQLException;
     
     public void setIndividualTeamMembers(ITeam team) throws SQLException; 
     
     public void setTeamsMembers(ArrayList<ITeam> teamList) throws SQLException;
     
     public ArrayList<IEmployee> getTeamMembers(String teamID) throws SQLException;
     
     public boolean scheduleTeamJob(IJob job, String teamID) throws SQLException;
     
     public int getCurrentDayJobCount() throws SQLException;
     
     public double getCurrentMonthJobCost() throws SQLException;
     
     public double getCurrentMonthJobRevenue() throws SQLException;
     
     public double getCurrentYearCost(LocalDate ld) throws SQLException;
     
     public double getcurrentYearRevenue(LocalDate ld) throws SQLException;
     
     public SortedMap<Integer,List<Double>> getAnnualReport(LocalDate ld) throws SQLException;
     
    
}
