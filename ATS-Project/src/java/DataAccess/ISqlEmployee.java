/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Model.Employee.IEmployee;
import Model.Task.ITask;
import Model.Team.ITeam;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author jber5
 */
public interface ISqlEmployee {
    public ArrayList<IEmployee> getEmployees() throws SQLException;
    public ArrayList<IEmployee> getActiveEmployees() throws SQLException;
    public ArrayList<IEmployee> getArchivedEmployees() throws SQLException;
    public ArrayList<IEmployee> getEmployeesBySinSearch(String sinSearch) throws SQLException;
    public ArrayList<IEmployee> getEmployeesByLNameSearch(String lNameSearch) throws SQLException;
    public IEmployee getEmployeeByID(String employeeID) throws SQLException ;
    public boolean insertEmployee(IEmployee employee) throws SQLException;
    public boolean archiveEmployee(IEmployee employee) throws SQLException;
    public boolean deleteEmployee(IEmployee employee) throws SQLException;        
    public boolean restoreEmployee(IEmployee employee) throws SQLException;
    public boolean insertEmployeeSkill(IEmployee employee, ITask task) throws SQLException;
    public boolean updateEmployeeInofrmation(IEmployee employee) throws SQLException;
    public boolean deleteEmployeeSkill(IEmployee employee, ITask task) throws SQLException;
    public ITeam getEmployeeAssignedTeam(IEmployee employee) throws SQLException;
    public ArrayList<IEmployee> getUnassignedEmployees() throws SQLException;
    public ArrayList<ITask> getEmployeeAssignedSkills(IEmployee employee) throws SQLException;
    public ArrayList<ITask> getEmployeeUnassignedSkills(IEmployee employee) throws SQLException;
    public IEmployee getEmployeeFromRS(ResultSet rs) throws SQLException;
    public ITask getTaskFromRs(ResultSet rs) throws SQLException;
    public ITeam getTeamFromRs(ResultSet rs) throws SQLException;
    public boolean  searchSin(String sin) throws SQLException;
}
