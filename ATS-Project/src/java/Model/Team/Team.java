/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Team;

import java.util.ArrayList;
import java.io.Serializable;
import Model.Employee.IEmployee;
import Model.Job.IJob;
import Model.Task.ITask;
import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author Ali Emir Samlioglu
 */
public class Team implements ITeam, Serializable {

    private String teamID;
    private String name;
    private boolean onCall;
    private boolean teamStatus;
    private LocalDate dateCreated;
    ArrayList<IEmployee> teamMembers;
    HashMap<String, ITask>teamSkills;//For unique Values usifn a Map
    ArrayList<IJob> teamJobs;

    public Team() {
        this.teamMembers = new ArrayList<>();
        this.teamSkills = new HashMap<>();
        this.teamJobs = new ArrayList<>();
        this.teamID ="";
    }

    @Override
    public String getTeamID() {
        return teamID;
    }

    @Override
    public void setTeamID(String id) {
        this.teamID = id;
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
    public boolean isOnCall() {
        return onCall;
    }

    @Override
    public void setOnCall(boolean onCall) {
        this.onCall = onCall;
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
    public ArrayList<IEmployee> getTeamMembers() {
        return teamMembers;
    }

    @Override
    public void setTeamMember(IEmployee teamMember) {
        this.teamMembers.add(teamMember);
    }

    @Override
    public boolean getTeamStatus() {
        return this.teamStatus;
    }

    @Override
    public void setTeamStatus(boolean status) {
        this.teamStatus = status;
    }

    @Override
    public void setTeamJobs(ArrayList<IJob> jobs) {
        this.teamJobs = jobs;
    }

    @Override
    public ArrayList<IJob> getTeamJobs() {
        return this.teamJobs;
    }

    @Override
    public HashMap<String, ITask> getTeamSkills() {
        return this.teamSkills;
    }

    @Override
    public void setTeamSkills(HashMap<String, ITask> tasks) {
        this.teamSkills = tasks;
    }

}
