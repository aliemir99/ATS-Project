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
import java.util.ArrayList;

/**
 *
 * @author jber5
 */
public interface ISqlTeam {
    public ArrayList<ITeam> getTeams() throws SQLException;
    public ArrayList<ITeam> getActiveTeams() throws SQLException;
    public ArrayList<ITeam> getArchivedTeams() throws SQLException;
    public ITeam getTeamByID(String teamId) throws SQLException;
    public boolean insertTeam(ITeam team) throws SQLException;
    public String getLastInsertedTeamID() throws SQLException;
    public boolean insertTeamMembers(ITeam team, IEmployee employee) throws SQLException;
    public boolean deleteTeam(String teamId) throws SQLException;
    public boolean archiveTeam(String teamId) throws SQLException;
    public boolean setTeamOnCallStatus(String teamId) throws SQLException;
    public boolean removeTeamOnCallStatus(String teamId) throws SQLException;
    public ITeam getTeamOnCall() throws SQLException;
    public ITeam getTeamFromRS(ResultSet rs) throws SQLException;
    public ITask getTaskFromRs(ResultSet rs) throws SQLException;
    public IJob getJobFromRs(ResultSet rs) throws SQLException;
    public ArrayList<IEmployee> getTeamMembers(String teamID) throws SQLException;
    public void setIndividualTeamMembers(ITeam team) throws SQLException ;
    public void setTeamsMembers(ArrayList<ITeam> teamList) throws SQLException;
    public ArrayList<ArrayList<ITask>> getMembersSkills(ITeam team) throws SQLException;
    public boolean scheduleTeamJob(IJob job, String teamID) throws SQLException;
    public int getFutureTeamJobCount(String teamId) throws SQLException;
    public int getAllTeamJobCount(String teamId) throws SQLException;
    public ArrayList<IJob> getTeamAssociatedJobs(String teamID) throws SQLException;
    public IEmployee getEmployeeFromRS(ResultSet rs) throws SQLException;
    public ArrayList<ITask> getEmployeeAssignedSkills(IEmployee employee) throws SQLException;
    public ArrayList<ITask> getJobTasks(String jobId) throws SQLException;
}
