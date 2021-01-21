/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessServices;

import Model.Employee.IEmployee;
import Model.Job.IJob;
import Model.Task.ITask;
import Model.Team.ITeam;
import java.util.ArrayList;

/**
 *
 * @author jber5
 */
public interface ITeamService {
    public  void addTeamMember(ArrayList<IEmployee> employeeList,
            String memberToAddID, ITeam teamToBeCreated);

    public  boolean isMemberAdded(String memberToAddID, ITeam teamToBeCreated);
    
    public  void removeMember(String employeeID,ITeam teamToBeCreated);

    public  boolean insertTeam(ITeam team) ;

    public  void setAllTeamMembers(ArrayList<IEmployee> memberList, ITeam team);

    public  boolean insertTeamMember(IEmployee member, ITeam team);
    public  ITeam getTeamOnCall();

    public  ArrayList<ITeam> getActiveTeams() ;

    public  ArrayList<ITeam> getArchivedTeams() ;

    public  ITeam getTeamByID(String id);
    
    public  ArrayList<IJob> getTeamJobs(String id) ;

    public  void setAlltMemberSkills(ITeam currentTeam);
    
    public  ArrayList<ITask> getMemberSkill(IEmployee member);

    public  boolean canArchiveTeam(String id);

    public  boolean archiveTeam(ITeam currentTeam);
    
    public  boolean canDeleteTeam(String id);

    public  boolean deleteTeam(ITeam currentTeam);

    public  void setTeamOnCall(ITeam currentTeam);
    
    public void removeTeamOnCall(ITeam currentTeam);
    
    public ArrayList<String> getErrors();
}
