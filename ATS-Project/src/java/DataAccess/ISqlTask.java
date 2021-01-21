/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;
import Model.Employee.IEmployee;
import Model.Task.ITask;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jber5
 */
public interface ISqlTask {
    public ArrayList<ITask> getTasks() throws SQLException;
    public ITask getTaskByID(int taskID) throws SQLException;
    public boolean insertTask(ITask task) throws SQLException;
    public boolean deleteTask(String taskID) throws SQLException;
    public int getLinkedTaskCount(String taskID) throws SQLException;
    public boolean updateTask(ITask task) throws SQLException;
    public ArrayList<IEmployee> getEmployeesToTask(String taskID) throws SQLException;
    public ITask getTaskromRS(ResultSet rs) throws SQLException;
    public IEmployee getEmployeeFromRs(ResultSet rs) throws SQLException;
}
