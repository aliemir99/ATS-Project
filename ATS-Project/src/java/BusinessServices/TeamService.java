/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessServices;

// <editor-fold desc="Used Imports">
import DataAccess.ISqlEmployee;
import DataAccess.ISqlTeam;
import DataAccess.SqlEmployeeFactory;
import DataAccess.SqlTeamFactory;
import HelperClasses.HelperMethods;
import Model.Employee.IEmployee;
import Model.Job.IJob;
import Model.Task.ITask;
import Model.Team.ITeam;
import Model.Team.TeamFactory;
import java.sql.SQLException;
import java.util.ArrayList;
// </editor-fold>

/**
 * @author jber5
 * Purpose: this class is for all Team operations and validation
 * (CREATE,READ,UPDATE,DELETE)
 */
public class TeamService implements ITeamService {

    // <editor-fold desc="Local Scope Variables">
    private final ISqlTeam teamRepo = SqlTeamFactory.createInstance();
    private final ISqlEmployee empRepo = SqlEmployeeFactory.createInstance();
    private ArrayList<String> errors = new ArrayList<>();
    // </editor-fold>

    /**
     * @param employeeList
     * @param memberToAddID
     * @param teamToBeCreated Adds team member to the current team
     */
    @Override
    public void addTeamMember(ArrayList<IEmployee> employeeList,
            String memberToAddID, ITeam teamToBeCreated) {

        if (!isMemberAdded(memberToAddID, teamToBeCreated)) {
            employeeList.stream().filter((emp)
                    -> (emp.getEmployeeID().equals(memberToAddID))).forEachOrdered((emp) -> {
                teamToBeCreated.setTeamMember(emp);
            });
        }
    }

    /**
     * @param memberToAddID
     * @param teamToBeCreated
     * @return boolean check if member has been added to the team
     */
    @Override
    public boolean isMemberAdded(String memberToAddID, ITeam teamToBeCreated) {
        if (!teamToBeCreated.getTeamMembers().isEmpty()) {
            for (IEmployee emp : teamToBeCreated.getTeamMembers()) {
                if (emp.getEmployeeID().equals(memberToAddID)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param employeeID
     * @param teamToBeCreated remove team member from team members list
     */
    @Override
    public void removeMember(String employeeID, ITeam teamToBeCreated) {
        for (IEmployee emp : teamToBeCreated.getTeamMembers()) {
            if (emp.getEmployeeID().equals(employeeID)) {
                teamToBeCreated.getTeamMembers().remove(emp);
                return;
            }
        }
    }

    /**
     * @param team
     * @return boolean inserts the team and sets the id to our team object then
     * we set all the members of the team
     */
    @Override
    public boolean insertTeam(ITeam team) {
        try {
            teamRepo.insertTeam(team);
            team.setTeamID(teamRepo.getLastInsertedTeamID());
            setAllTeamMembers(team.getTeamMembers(), team);
            return true;
        } catch (SQLException ex) {

        }
        return false;
    }

    /**
     * @param memberList
     * @param team Iterates over the members list and insets the members to the
     * database
     */
    @Override
    public void setAllTeamMembers(ArrayList<IEmployee> memberList, ITeam team) {
        memberList.forEach((member) -> {
            insertTeamMember(member, team);
        });
    }

    /**
     * @param member
     * @param team
     * @return boolean insert a team member to the team using teamRepo
     */
    @Override
    public boolean insertTeamMember(IEmployee member, ITeam team) {
        try {
            return teamRepo.insertTeamMembers(team, member);
        } catch (SQLException ex) {

        }
        return false;
    }

    @Override
    public ITeam getTeamOnCall() {
        try {
            return teamRepo.getTeamOnCall();
        } catch (SQLException ex) {
        }
        return null;
    }

    @Override
    public ArrayList<ITeam> getActiveTeams() {
        ArrayList<ITeam> activeTeams = new ArrayList<>();
        try {
            activeTeams = teamRepo.getActiveTeams();
        } catch (SQLException ex) {

        }
        return activeTeams;
    }

    @Override
    public ArrayList<ITeam> getArchivedTeams() {
        ArrayList<ITeam> activeTeams = new ArrayList<>();
        try {
            activeTeams = teamRepo.getArchivedTeams();
        } catch (SQLException ex) {

        }
        return activeTeams;
    }

    @Override
    public ITeam getTeamByID(String id) {
        try {
            return teamRepo.getTeamByID(id);
        } catch (SQLException ex) {

        }
        return TeamFactory.createInstance();
    }

    @Override
    public ArrayList<IJob> getTeamJobs(String id) {
        ArrayList<IJob> teamJobs = new ArrayList<>();
        try {
            teamJobs = teamRepo.getTeamAssociatedJobs(id);
        } catch (SQLException ex) {

        }
        return teamJobs;
    }

    /**
     * @param currentTeam Sets all team member skills
     */
    @Override
    public void setAlltMemberSkills(ITeam currentTeam) {
        currentTeam.getTeamMembers().stream()
                .map((member)
                        -> getMemberSkill(member)).forEachOrdered((tmpEmpSkills) -> {
            tmpEmpSkills.forEach((task) -> {
                currentTeam.getTeamSkills().put(task.getTaskID(), task);
            });
        });
    }

    /**
     * @param member
     * @return ArrayList<ITask>
     * gets all team member skills
     */
    @Override
    public ArrayList<ITask> getMemberSkill(IEmployee member) {
        ArrayList<ITask> memberSkills = new ArrayList<>();
        try {
            memberSkills = empRepo.getEmployeeAssignedSkills(member);
        } catch (SQLException ex) {

        }
        return memberSkills;
    }

    /**
     * @param id
     * @return boolean check if a team can be archived
     */
    @Override
    public boolean canArchiveTeam(String id) {
        try {
            boolean isOnCall = true;
            ITeam tmpTeam = teamRepo.getTeamOnCall();
            
            if(tmpTeam == null){
                isOnCall = false;
                
            }else{
               isOnCall = tmpTeam.getTeamID().equals(id);
               
            }
            
           
            return teamRepo.getFutureTeamJobCount(id) == 0
                    && !isOnCall;
        } catch (SQLException ex) {

        }
        return false;
    }

    /**
     *
     * @param currentTeam
     * @return boolean if canArchiveTeam returns true we update date and set the
     * team status to false and we archive the team in the database using
     * teamRepo
     */
    @Override
    public boolean archiveTeam(ITeam currentTeam) {
        if (canArchiveTeam(currentTeam.getTeamID())) {
            try {
                currentTeam.setDateCreated(HelperMethods.getCurrentDate());
                currentTeam.setTeamStatus(false);
                return teamRepo.archiveTeam(currentTeam.getTeamID());
            } catch (SQLException ex) {

            }
        } else {
            errors.clear();
            errors.add("Team has Jobs assigned to it");
            return false;
        }

        return false;
    }

    /**
     * @param id
     * @return boolean check if a team can be deleted
     */
    @Override
    public boolean canDeleteTeam(String id) {
        try {
            return teamRepo.getAllTeamJobCount(id) == 0;
        } catch (SQLException ex) {

        }
        return false;
    }

    /**
     *
     * @param currentTeam
     * @return boolean if canDeleteTeam returns true we call deleteTeam from
     * teamRepo
     */
    @Override
    public boolean deleteTeam(ITeam currentTeam) {
        if (canDeleteTeam(currentTeam.getTeamID())) {
            try {
                return teamRepo.deleteTeam(currentTeam.getTeamID());
            } catch (SQLException ex) {

            }
        } else {
            errors.clear();
            errors.add("Team can't be deleted. Jobs has been done in the past");
            return false;
        }
        return false;
    }

    /**
     * @param currentTeam if can set team on call (no other team is on call) we
     * set the team status on Call
     */
    @Override
    public void setTeamOnCall(ITeam currentTeam) {
        try {
            if (teamRepo.setTeamOnCallStatus(currentTeam.getTeamID())) {
                currentTeam.setDateCreated(HelperMethods.getCurrentDate());
                currentTeam.setOnCall(true);
            }
        } catch (SQLException ex) {

        }
    }

    @Override
    public void removeTeamOnCall(ITeam currentTeam) {
        try {
            if (teamRepo.removeTeamOnCallStatus(currentTeam.getTeamID())) {
                currentTeam.setDateCreated(HelperMethods.getCurrentDate());
                currentTeam.setOnCall(false);
            }
        } catch (SQLException ex) {

        }
    }

    @Override
    public ArrayList<String> getErrors() {
        return this.errors;
    }

}
