/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Task;

import Model.Employee.IEmployee;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 *
 * @author Ali Emir Samlioglu
 */
public class Task implements ITask, Serializable{
    private String taskID;
    private String name;
    private String description;
    private int duration;
    private LocalDate dateCreated;
    private ArrayList<IEmployee> skillHolders;

    @Override
    public String getTaskID() {
        return taskID;
    }

    @Override
    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public int getDuration() {
        return duration;
    }
    
    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    @Override
    public LocalDate getDateCreated() {
        return dateCreated;
    }
    
    @Override
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public void setSkillHoldersList(ArrayList<IEmployee> employees) {
        this.skillHolders = employees;
    }

    @Override
    public ArrayList<IEmployee> getSkillHolders() {
        return this.skillHolders;
    }
   
    
}
