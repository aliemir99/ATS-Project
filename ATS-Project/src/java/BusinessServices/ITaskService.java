/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessServices;

import Model.Task.ITask;
import java.util.ArrayList;

/**
 *
 * @author jber5
 */
public interface ITaskService {

    public boolean updateTask(ITask task);

    public boolean deleteTask(ITask task);

    public boolean canDeleteTask(String id);

    public ArrayList<ITask> getTasks();

    public boolean insertTask(ITask task);

    public ITask getTaksByID(int id);

    public void setSkillHolders(ITask task);
    
    public ArrayList<String> getErrors();
}
