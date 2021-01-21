/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessServices;

// <editor-fold desc="Used Imports">
import DataAccess.ISqlTask;
import DataAccess.SqlTaskFactory;
import Model.Task.TaskFactory;
import Model.Task.ITask;
import java.sql.SQLException;
import java.util.ArrayList;
// </editor-fold>

/**
 * @author aliem
 * Purpose: this class is for all Task related information
 * (CREATE,READ,UPDATE,DELETE)
 */
public class TaskService implements ITaskService {

    // <editor-fold desc="Local Scope Variables">
    private final ISqlTask taskRepo = SqlTaskFactory.createInstance();
    private ArrayList<String> errors = new ArrayList<>();
    // </editor-fold>

    @Override
    public boolean updateTask(ITask task) {
        try {
            return taskRepo.updateTask(task);
        } catch (SQLException ex) {

        }
        return false;
    }

    @Override
    public boolean deleteTask(ITask task) {
        if (canDeleteTask(task.getTaskID())) {
            try {
                return taskRepo.deleteTask(task.getTaskID());
            } catch (SQLException ex) {

            }
        } else {
            errors.clear();
            errors.add("Task can't be deleted. It's already assigned");
            return false;
        }
        return false;

    }

    @Override
    public boolean canDeleteTask(String id) {
        try {
            return taskRepo.getLinkedTaskCount(id) == 0;
        } catch (SQLException ex) {

        }
        return false;
    }

    @Override
    public ArrayList<ITask> getTasks() {
        try {
            return taskRepo.getTasks();
        } catch (SQLException ex) {

        }
        return new ArrayList<>();
    }

    @Override
    public boolean insertTask(ITask task) {
        try {
            return taskRepo.insertTask(task);
        } catch (SQLException ex) {

        }
        return false;
    }

    @Override
    public ITask getTaksByID(int id) {
        try {
            return taskRepo.getTaskByID(id);
        } catch (SQLException ex) {

        }
        return TaskFactory.createInstance();
    }

    @Override
    public void setSkillHolders(ITask task) {
        try {
            task.setSkillHoldersList(taskRepo.getEmployeesToTask(task.getTaskID()));
        } catch (SQLException ex) {

        }
    }

    @Override
    public ArrayList<String> getErrors() {
        return this.errors;
    }
}
