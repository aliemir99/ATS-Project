/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

// <editor-fold desc="Used Imports">
import Model.Employee.EmployeeFactory;
import Model.Employee.IEmployee;
import Model.Task.ITask;
import Model.Task.TaskFactory;
import Model.Team.ITeam;
import Model.Team.TeamFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
// </editor-fold>

/**
 * @author jber5
 * Purpose: Class is used to Hit the database for Employee related information
 */
public class SqlEmployee implements ISqlEmployee {

    @Override
    public ArrayList<IEmployee> getEmployees() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<IEmployee> employeesList = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_EMPLOYEES");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                employeesList.add(getEmployeeFromRS(rs));
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return employeesList;
    }

    @Override
    public ArrayList<IEmployee> getActiveEmployees() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<IEmployee> employeesList = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_ACTIVE_EMPLOYEES");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                employeesList.add(getEmployeeFromRS(rs));
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return employeesList;
    }

    @Override
    public ArrayList<IEmployee> getArchivedEmployees() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<IEmployee> employeesList = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_ARCHIVED_EMPLOYEES");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                employeesList.add(getEmployeeFromRS(rs));
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return employeesList;
    }

    @Override
    public ArrayList<IEmployee> getEmployeesBySinSearch(String sinSearch) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<IEmployee> employeesSearchResult = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL SEARCH_EMPLOYEES_BY_SIN(?)");
            st.setString(1, sinSearch);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                employeesSearchResult.add(getEmployeeFromRS(rs));
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return employeesSearchResult;
    }

    @Override
    public ArrayList<IEmployee> getEmployeesByLNameSearch(String lNameSearch) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<IEmployee> employeesSearchResult = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL SEARCH_EMPLOYEES_BY_LNAME(?)");
            st.setString(1, lNameSearch);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                employeesSearchResult.add(getEmployeeFromRS(rs));
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return employeesSearchResult;
    }

    @Override
    public IEmployee getEmployeeByID(String employeeID) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_EMPLOYEE_BY_ID(?)");
            st.setString(1, employeeID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                return getEmployeeFromRS(rs);
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");

        }
        return null;
    }

    @Override
    public boolean insertEmployee(IEmployee employee) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL CREATE_EMPLOYEE (?,?,?,?,?,?)");
            st.setString(1, employee.getSIN());
            st.setString(2, employee.getFirstName());
            st.setString(3, employee.getLastName());
            st.setDouble(4, employee.getPayRate());
            st.setString(5, employee.getDateCreated().toString());
            st.setBoolean(6, employee.getEmployeeStatus());
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public boolean archiveEmployee(IEmployee employee) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL ARCHIVE_EMPLOYEE (?,?)");
            st.setString(1, employee.getEmployeeID());
            st.setString(2, employee.getDateCreated().toString());
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public boolean deleteEmployee(IEmployee employee) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;

        try {
            PreparedStatement st = con.prepareStatement("CALL DELETE_EMPLOYEE(?)");
            st.setString(1, employee.getEmployeeID());
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;

    }

    @Override
    public boolean restoreEmployee(IEmployee employee) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL RESTORE_ARCHIVED_EMPLOYEE (?,?)");
            st.setString(1, employee.getEmployeeID());
            st.setString(2, employee.getDateCreated().toString());
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public boolean insertEmployeeSkill(IEmployee employee, ITask task) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL INSERT_EMPLOYEE_SKILL (?,?)");
            st.setString(1, employee.getEmployeeID());
            st.setString(2, task.getTaskID());
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public boolean updateEmployeeInofrmation(IEmployee employee) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL UPDATE_EMPLOYEE(?,?,?,?,?,?)");
            st.setString(1, employee.getEmployeeID());
            st.setString(2, employee.getSIN());
            st.setString(3, employee.getFirstName());
            st.setString(4, employee.getLastName());
            st.setDouble(5, employee.getPayRate());
            st.setString(6, employee.getDateCreated().toString());
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public boolean deleteEmployeeSkill(IEmployee employee, ITask task) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL DELETE_EMPLOYEE_SKILL(?,?)");
            st.setString(1, employee.getEmployeeID());
            st.setString(2, task.getTaskID());
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public ITeam getEmployeeAssignedTeam(IEmployee employee) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_EMPLOYEE_ASSIGNED_TEAMS(?)");
            st.setString(1, employee.getEmployeeID());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return getTeamFromRs(rs);
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return TeamFactory.createInstance();
    }

    //Gets all employees that don't belong to a team
    @Override
    public ArrayList<IEmployee> getUnassignedEmployees() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<IEmployee> employeesList = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_UNASSIGNED_EMPLOYEES");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                employeesList.add(getEmployeeFromRS(rs));
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return employeesList;
    }

    @Override
    public ArrayList<ITask> getEmployeeAssignedSkills(IEmployee employee) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<ITask> employeeSkillList = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_EMPLOYEE_SKILLS(?)");
            st.setString(1, employee.getEmployeeID());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                employeeSkillList.add(getTaskFromRs(rs));
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return employeeSkillList;
    }

    @Override
    public ArrayList<ITask> getEmployeeUnassignedSkills(IEmployee employee) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<ITask> unassignedSkillList = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_EMPLOYEE_UNASSIGNED_SKILLS(?)");
            st.setString(1, employee.getEmployeeID());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                unassignedSkillList.add(getTaskFromRs(rs));
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return unassignedSkillList;
    }

    @Override
    public IEmployee getEmployeeFromRS(ResultSet rs) throws SQLException {
        IEmployee employee = EmployeeFactory.createInstance();
        employee.setEmployeeID(rs.getString("EmployeeID"));
        employee.setSIN(rs.getString("SIN"));
        employee.setFirstName(rs.getString("firstName"));
        employee.setLastName(rs.getString("lastName"));
        employee.setDateCreated(LocalDate.parse(rs.getString("DateCreated")));
        employee.setPayRate(rs.getInt("payRatePerHr"));
        employee.setEmployeeStatus(rs.getBoolean("IsArchived"));
        return employee;
    }

    @Override
    public ITask getTaskFromRs(ResultSet rs) throws SQLException {
        ITask task = TaskFactory.createInstance();
        task.setTaskID(rs.getString("TaskID"));
        task.setName(rs.getString("TaskName"));
        task.setDescription(rs.getString("Description"));
        task.setDuration(rs.getInt("Duration"));
        task.setDateCreated(LocalDate.parse(rs.getString("DateCreated")));

        return task;
    }

    @Override
    public ITeam getTeamFromRs(ResultSet rs) throws SQLException {
        ITeam team = TeamFactory.createInstance();
        team.setTeamID(rs.getString("TeamID"));
        team.setName(rs.getString("TeamName"));
        team.setOnCall(rs.getBoolean("OnCALL"));
        team.setTeamStatus(rs.getBoolean("Status"));
        team.setDateCreated(LocalDate.parse(rs.getString("DateCreated")));

        return team;
    }

    @Override
    public boolean searchSin(String sin) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        boolean result = false;
        try {
            PreparedStatement st = 
            con.prepareStatement("CALL SEARCH_EMPLOYEE_SIN(?)");
            st.setString(1, sin);
            ResultSet rs = st.executeQuery();
            result = rs.next();
            return result;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Something wwent wrong in processing the request");
        }
    }

}
