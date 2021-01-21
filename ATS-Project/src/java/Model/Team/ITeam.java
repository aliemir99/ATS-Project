/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Team;

import Model.Employee.IEmployee;
import Model.Job.IJob;
import Model.Task.ITask;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ali Emir Samlioglu
 */
public interface ITeam {
    
    public String getTeamID();
    
    public void setTeamID(String id);
    
     public String getName();
     
     public void setName(String Name);
     
     public boolean isOnCall();
     
     public void setOnCall(boolean onCall);
     
     public LocalDate getDateCreated();
     
     public void setDateCreated(LocalDate dateCreated) ;
     
     public ArrayList<IEmployee> getTeamMembers();
     
     public void setTeamMember(IEmployee teamMember);
     
     public boolean getTeamStatus();
     
     public void setTeamStatus(boolean status);
     
     public void setTeamSkills(HashMap<String, ITask> tasks);
     
     public void setTeamJobs(ArrayList<IJob> jobs);
     
     public ArrayList<IJob> getTeamJobs();
     
     public HashMap<String, ITask> getTeamSkills();
     
}
