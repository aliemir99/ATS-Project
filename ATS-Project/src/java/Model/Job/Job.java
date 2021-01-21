/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Job;

import Model.Task.ITask;
import Model.Team.ITeam;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author jber5
 */
public class Job implements IJob, Serializable {
    private String jobID;
    private String description;
    private String clientName;
    private ITeam assignedTeam;
    private double jobCost;
    private double jobRevenue;
    private ArrayList<ITask> jobTasks;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate dateCreated;
    private boolean isEmergency;

    public Job() {
        jobTasks = new ArrayList<>();
    }

    @Override
    public String getJobID() {
        return jobID;
    }

    @Override
    public void setJobID(String jobID) {
        this.jobID = jobID;
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
    public String getClientName() {
        return clientName;
    }

    @Override
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public ITeam getAssignedTeam() {
        return assignedTeam;
    }

    @Override
    public void setAssignedTeam(ITeam assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    @Override
    public ArrayList<ITask> getJobTasks() {
        return jobTasks;
    }

    @Override
    public void setJobTasks(ArrayList<ITask> jobTasks) {
        this.jobTasks = jobTasks;
    }

    @Override
    public LocalTime getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
    public LocalDate getStartDate() {
        return this.startDate;
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public double getJobCost() {
        return this.jobCost;
    }

    @Override
    public void setJobCost(double cost) {
        this.jobCost = cost;
    }

    @Override
    public double getJobRevenue() {
        return this.jobRevenue;
    }

    @Override
    public void setJobRevenue(double revenue) {
        this.jobRevenue = revenue;
    }

    @Override
    public boolean isEmeregencyJob() {
        return this.isEmergency;
    }

    @Override
    public void setIsEmergencyJob(boolean isEmergency) {
       this.isEmergency = isEmergency;
    }
   
}
