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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
// </editor-fold>

/**
 *
 * @author jber5
 * Purpose: Class is used to Hit the database for Task related information
 */
public class SqlTask implements ISqlTask {
       
    @Override
    public ArrayList<ITask> getTasks() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<ITask> tasksList = new ArrayList();

        try {
            PreparedStatement st = con.prepareStatement("CALL GET_TASKS");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                tasksList.add(getTaskromRS(rs));
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return tasksList;
    }

    @Override
    public ITask getTaskByID(int taskID) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_TASK_BY_ID(?)");
            st.setInt(1, taskID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                return getTaskromRS(rs);
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return null;
    }

    @Override
    public boolean insertTask(ITask task) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL CREATE_TASK (?,?,?,?)");

            st.setString(1, task.getName());
            st.setString(2, task.getDescription());
            st.setInt(3, task.getDuration());
            st.setString(4, task.getDateCreated().toString());
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }
    
    @Override
    public boolean deleteTask(String taskID) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;

        try {
            PreparedStatement st = con.prepareStatement("CALL DELETE_TASK(?)");
            st.setString(1, taskID);
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public int getLinkedTaskCount(String taskID) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
         try{
             PreparedStatement st = con.prepareStatement
            ("CALL COUNT_ASSIGNED_EMPLOYEE_SKILL(?)");
             st.setString(1, taskID);
             ResultSet rs = st.executeQuery();
             if(rs.next()){
                return rs.getInt("CountRes");
             }
              
         }catch(SQLException e){
             System.out.print(e.toString());
             throw new SQLException("Something wwent wrong in processing the request");
         }
         return -1;
    }

    @Override
    public boolean updateTask(ITask task) throws SQLException{
          Connection con = ConnectionFactory.getConnection();
        boolean result;
         try{
             PreparedStatement st = con.prepareStatement
            ("CALL UPDATE_TASK (?,?,?,?,?)");
             st.setString(1, task.getTaskID());
             st.setString(2, task.getName());
             st.setString(3, task.getDescription());
             st.setInt(4, task.getDuration());
             st.setString(5, task.getDateCreated().toString());
             
             int rowAffected = st.executeUpdate();
             
            result = (rowAffected > 0);
         }catch(SQLException e){
             System.out.print(e.toString());
             throw new SQLException("Something wwent wrong in processing the request");
         }
         return result;
    }

    @Override
    public ArrayList<IEmployee> getEmployeesToTask(String taskID) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
         ArrayList<IEmployee> employeesList = new ArrayList();
         try{
             PreparedStatement st = con.prepareStatement("CALL GET_EMPLOYEES_TO_TASK(?)");
             st.setString(1, taskID);
             ResultSet rs = st.executeQuery();  
             while(rs.next()){
                 employeesList.add(getEmployeeFromRs(rs));
             }             
        }catch(SQLException e){
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return employeesList;
    }

    @Override
    public ITask getTaskromRS(ResultSet rs) throws SQLException{
        ITask task = TaskFactory.createInstance();
        task.setTaskID(rs.getString("TaskID"));
        task.setName(rs.getString("TaskName"));
        task.setDescription(rs.getString("Description"));
        task.setDuration(rs.getInt("Duration"));
        task.setDateCreated(LocalDate.parse(rs.getString("DateCreated")));
        
        return task;
    }

    @Override
    public IEmployee getEmployeeFromRs(ResultSet rs) throws SQLException {
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

}
