/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Task;

import Model.Employee.IEmployee;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Ali Emir Samlioglu
 */
public interface ITask {
    
public String getTaskID();

public void setTaskID(String taskID);

public String getName();

public void setName(String name);

public String getDescription();

public void setDescription(String description);

public int getDuration();

public void setDuration(int duration);

public LocalDate getDateCreated();

public void setDateCreated(LocalDate dateCreated);

public void setSkillHoldersList(ArrayList<IEmployee> employees);

public ArrayList<IEmployee> getSkillHolders();
           
}
