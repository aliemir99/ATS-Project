/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

// <editor-fold desc="Used Imports">
import Model.Employee.EmployeeFactory;
import Model.Employee.IEmployee;
import Model.Job.IJob;
import Model.Job.JobFactory;
import Model.Task.ITask;
import Model.Task.TaskFactory;
import Model.Team.ITeam;
import Model.Team.TeamFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
// </editor-fold>

/**
 * @author jber5
 * Purpose: Class is used to Hit the database for Team related information
 */
public class SqlTeam implements ISqlTeam {
    
    @Override
    public ArrayList<ITeam> getTeams() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<ITeam> teamsList = new ArrayList();

        try {
            PreparedStatement st = con.prepareStatement("CALL GET_TEAMS");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                teamsList.add(getTeamFromRS(rs));
            }

            setTeamsMembers(teamsList);

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }

        return teamsList;
    }

    @Override
    public ArrayList<ITeam> getActiveTeams() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<ITeam> teamsList = new ArrayList();

        try {
            PreparedStatement st = con.prepareStatement("CALL GET_ACTIVE_TEAMS");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                teamsList.add(getTeamFromRS(rs));
            }

            setTeamsMembers(teamsList);

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }

        return teamsList;
    }

    @Override
    public ArrayList<ITeam> getArchivedTeams() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<ITeam> teamsList = new ArrayList();

        try {
            PreparedStatement st = con.prepareStatement("CALL GET_ARCHIVED_TEAMS");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                teamsList.add(getTeamFromRS(rs));
            }

            setTeamsMembers(teamsList);

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }

        return teamsList;
    }

    @Override
    public ITeam getTeamByID(String teamId) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ITeam resultedTeam = null;
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_TEAM_ByID(?)");
            st.setString(1, teamId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                resultedTeam = getTeamFromRS(rs);
            }
            setIndividualTeamMembers(resultedTeam);
        } catch (SQLException e) {
           System.out.print(e.toString());
           throw new SQLException("Something wwent wrong in processing the request");
        }
        return resultedTeam;
    }

    @Override
    public boolean insertTeam(ITeam team) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL CREATE_TEAM (?,?,?,?)");
            st.setString(1, team.getName());
            st.setBoolean(2, team.isOnCall());
            st.setString(3, team.getDateCreated().toString());
            st.setBoolean(4, team.getTeamStatus());
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public String getLastInsertedTeamID() throws SQLException {
        String teamID = "";
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_LAST_INSERTED_TEAMID");
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                teamID = rs.getString("TeamID");
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return teamID;
    }

    @Override
    public boolean insertTeamMembers(ITeam team, IEmployee employee) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL INSERT_TEAM_MEMBERS (?,?,?)");
            st.setString(1, team.getTeamID());
            st.setInt(2, Integer.parseInt(employee.getEmployeeID()));
            st.setString(3, team.getDateCreated().toString());

            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (NumberFormatException | SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public boolean deleteTeam(String teamId) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;

        if (getAllTeamJobCount(teamId) == 0) {
            try {
                PreparedStatement st = con.prepareStatement("CALL DELETE_TEAM (?)");
                st.setString(1, teamId);
                int rowAffected = st.executeUpdate();

                result = (rowAffected > 0);
            } catch (SQLException e) {
                System.out.print(e.toString());
                throw new SQLException("Something wwent wrong in processing the request");
            }
            return result;
        }
        return false;
    }

    @Override
    public boolean archiveTeam(String teamId) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;

        if (getFutureTeamJobCount(teamId) == 0) {
            try {
                PreparedStatement st = con.prepareStatement("CALL ARCHIVE_TEAM (?)");
                st.setString(1, teamId);
                int rowAffected = st.executeUpdate();

                result = (rowAffected > 0);
            } catch (SQLException e) {
                System.out.print(e.toString());
                throw new SQLException("Something wwent wrong in processing the request");
            }
            return result;
        }
        return false;
    }

    @Override
    public boolean setTeamOnCallStatus(String teamId) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;

        try {
            PreparedStatement st = con.prepareStatement("CALL SET_TEAM_ON_CALL(?)");
            st.setString(1, teamId);
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;

    }

    @Override
    public boolean removeTeamOnCallStatus(String teamId) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;

        try {
            PreparedStatement st = con.prepareStatement("CALL DISCARD_TEAM_ON_CALL(?)");
            st.setString(1, teamId);
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public ITeam getTeamOnCall() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ITeam teamOnCall = TeamFactory.createInstance();

        try {
             PreparedStatement st = con.prepareStatement("CALL GET_TEAM_ON_CALL");
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                teamOnCall = getTeamFromRS(rs);
                setIndividualTeamMembers(teamOnCall);
            }
           
            return teamOnCall;
            
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }

        return teamOnCall;
    }

    @Override
    public ITeam getTeamFromRS(ResultSet rs) throws SQLException {
        ITeam team = TeamFactory.createInstance();
        team.setTeamID(rs.getString("TeamID"));
        team.setName(rs.getString("TeamName"));
        team.setOnCall(rs.getBoolean("OnCALL"));
        team.setTeamStatus(rs.getBoolean("Status"));
        team.setDateCreated(LocalDate.parse(rs.getString("DateCreated")));

        return team;
    }

    @Override
    public ArrayList<IEmployee> getTeamMembers(String teamID) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<IEmployee> teamsMembersList = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_TEAM_MEMBERS(?)");
            st.setString(1, teamID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                teamsMembersList.add(getEmployeeFromRS(rs));
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return teamsMembersList;
    }

    @Override
    public void setIndividualTeamMembers(ITeam team) throws SQLException {
        ArrayList<IEmployee> tmpTeamMem = new ArrayList<>();
        tmpTeamMem = getTeamMembers(team.getTeamID());
        for (IEmployee employee : tmpTeamMem) {
            team.setTeamMember(employee);
        }
    }

    @Override
    public void setTeamsMembers(ArrayList<ITeam> teamList) throws SQLException {
        for (ITeam team : teamList) {
            ArrayList<IEmployee> tmpTeamMem = new ArrayList<>();
            tmpTeamMem = getTeamMembers(team.getTeamID());
            for (IEmployee employee : tmpTeamMem) {
                team.setTeamMember(employee);
            }
        }

    }

    @Override
    public ArrayList<ArrayList<ITask>> getMembersSkills(ITeam team) throws SQLException {
        ArrayList<ArrayList<ITask>> membersSkills = new ArrayList<>();
        for (IEmployee emp : team.getTeamMembers()) {
            ArrayList<ITask> tmpTask = new ArrayList<>();
            tmpTask = getEmployeeAssignedSkills(emp);
            membersSkills.add(tmpTask);
        }
        return membersSkills;
    }

    @Override
    public boolean scheduleTeamJob(IJob job, String teamID) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL SCHEDULE_TEAM_JOB (?,?,?,?,?)");
            st.setString(1, teamID);
            st.setString(2, job.getJobID());
            st.setString(3, job.getStartDate().toString());
            st.setTime(4, Time.valueOf(job.getStartTime()));
            st.setTime(5, Time.valueOf(job.getEndTime()));

            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public int getFutureTeamJobCount(String teamId) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st
                    = con.prepareStatement("CALL COUNT_TEAM_FUTURE_JOB(?)");

            st.setString(1, teamId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("JOBS");
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return -1;
    }

    @Override
    public int getAllTeamJobCount(String teamId) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st
                    = con.prepareStatement("CALL COUNT_ALL_TEAM_JOB(?)");

            st.setString(1, teamId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("JOBS");
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return -1;
    }

    @Override
    public ArrayList<IJob> getTeamAssociatedJobs(String teamID) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<IJob> teamJobList = new ArrayList();

        try {
            PreparedStatement st = con.prepareStatement("CALL GET_TEAM_JOBS(?)");
            st.setString(1, teamID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                teamJobList.add(getJobFromRs(rs));
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return teamJobList;
    }

    @Override
    public IEmployee getEmployeeFromRS(ResultSet rs) throws SQLException {
        IEmployee employee = EmployeeFactory.createInstance();
        employee.setEmployeeID(rs.getString("EmployeeID"));
        employee.setSIN(rs.getString("SIN"));
        employee.setFirstName(rs.getString("firstName"));
        employee.setLastName(rs.getString("lastName"));
        employee.setDateCreated(LocalDate.parse(rs.getString("DateCreated")));
        employee.setPayRate(rs.getInt("payRatePerHr"));
        employee.setEmployeeStatus(rs.getBoolean("IsArchived"));
        return employee;
    }

    @Override
    public ArrayList<ITask> getEmployeeAssignedSkills(IEmployee employee) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<ITask> employeeSkillList = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_EMPLOYEE_SKILLS(?)");
            st.setString(1, employee.getEmployeeID());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                employeeSkillList.add(getTaskFromRs(rs));
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return employeeSkillList;
    }

    @Override
    public ITask getTaskFromRs(ResultSet rs) throws SQLException {
        ITask task = TaskFactory.createInstance();
        task.setTaskID(rs.getString("TaskID"));
        task.setName(rs.getString("TaskName"));
        task.setDescription(rs.getString("Description"));
        task.setDuration(rs.getInt("Duration"));
        task.setDateCreated(LocalDate.parse(rs.getString("DateCreated")));
        
        return task;
    }

    @Override
    public IJob getJobFromRs(ResultSet rs) throws SQLException {
         IJob job = JobFactory.createInstance();
        job.setJobID(rs.getString("JobID"));
        job.setDescription(rs.getString("Description"));
        job.setClientName(rs.getString("Clientname"));
        job.setAssignedTeam(getTeamByID(rs.getString("teamID")));
        job.setDateCreated(LocalDate.parse(rs.getString("dateCreated")));
        job.setJobCost(rs.getDouble("Cost"));
        job.setJobRevenue(rs.getDouble("Revenue"));
        job.setStartDate(LocalDate.parse(rs.getString("jobDate")));
        job.setStartTime(LocalTime.parse(rs.getString("startTime")));
        job.setEndTime(LocalTime.parse(rs.getString("endTime")));
        job.setJobTasks(getJobTasks(job.getJobID()));

        return job;
    }
    
    @Override
    public ArrayList<ITask> getJobTasks(String jobId) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<ITask> jobTask = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_JOB_TASKS(?)");
            st.setString(1, jobId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                jobTask.add(getTaskFromRs(rs));
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return jobTask;
    }
    
}
