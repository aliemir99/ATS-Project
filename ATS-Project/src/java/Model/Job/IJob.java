/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Job;

import Model.Task.ITask;
import Model.Team.ITeam;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author jber5
 */
public interface IJob {
    public String getJobID();

    public void setJobID(String jobID);

    public String getDescription();

    public void setDescription(String description);

    public String getClientName();

    public void setClientName(String clientName);

    public ITeam getAssignedTeam();

    public void setAssignedTeam(ITeam assignedTeam);

    public ArrayList<ITask> getJobTasks();

    public void setJobTasks(ArrayList<ITask> jobTasks);

    public LocalTime getStartTime();

    public void setStartTime(LocalTime startTime);

    public LocalTime getEndTime();

    public void setEndTime(LocalTime endTime);

    public LocalDate getDateCreated();

    public void setDateCreated(LocalDate dateCreated) ;
    
    public LocalDate getStartDate();
    
    public void setStartDate(LocalDate startDate);
    
    public double getJobCost();
    
    public void setJobCost(double cost);
    
    public double getJobRevenue();
    
    public void setJobRevenue(double revenue);
    
    public boolean isEmeregencyJob();
    
    public void setIsEmergencyJob(boolean isEmergency);
}
