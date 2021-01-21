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
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
// </editor-fold>


/**
 * @author jber5
 * Purpose: Class is used to Hit the database for Job related information
 */
public class SqlJob implements ISqlJob {

    @Override
    public ArrayList<IJob> getJobs() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<IJob> jobList = new ArrayList();

        try {
            PreparedStatement st = con.prepareStatement("CALL GET_JOBS");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                jobList.add(getJobFromRS(rs));
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return jobList;
    }

    @Override
    public IJob getJobByID(String jobId) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_JOB_BY_ID(?)");
            st.setString(1, jobId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                return getJobFromRS(rs);
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return null;
    }

    @Override
    public ArrayList<IJob> getJobsByDate(LocalDate ld) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<IJob> jobList = new ArrayList();

        try {
            PreparedStatement st = con.prepareStatement("CALL GET_JOBS_BY_DATE(?)");
            st.setString(1, ld.toString());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                jobList.add(getJobFromRS(rs));
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return jobList;
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

    @Override
    public boolean insertJob(IJob job) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL INSERT_JOB(?,?,?,?,?)");

            st.setString(1, job.getDescription());
            st.setString(2, job.getClientName());
            st.setString(3, job.getAssignedTeam().getTeamID());
            st.setString(4, job.getDateCreated().toString());
            st.setBoolean(5, job.isEmeregencyJob());
            int rowAffected = st.executeUpdate();

            job.setJobID(getLastInsertedJobID());
            insertIndividualJobTasks(job);
            insertJobFinancials(job);
            scheduleTeamJob(job, job.getAssignedTeam().getTeamID());

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public boolean deleteJob(IJob job) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st
                    = con.prepareStatement("CALL DELETE_JOB(?)");

            st.setString(1, job.getJobID());
            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;

    }

    @Override
    public boolean insertJobTask(ITask task, String jobId) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;

        try {
            PreparedStatement st = con.prepareStatement("CALL INSERT_JOB_TASKS(?,?)");
            st.setString(1, jobId);
            st.setString(2, task.getTaskID());

            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public void insertIndividualJobTasks(IJob job) throws SQLException {
        for (ITask task : job.getJobTasks()) {
            insertJobTask(task, job.getJobID());
        }
    }

    @Override
    public boolean insertJobFinancials(IJob job) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        boolean result;
        try {
            PreparedStatement st = con.prepareStatement("CALL INSERT_JOB_FINANCIALS(?,?,?)");
            st.setString(1, job.getJobID());
            st.setDouble(2, job.getJobCost());
            st.setDouble(3, job.getJobRevenue());

            int rowAffected = st.executeUpdate();

            result = (rowAffected > 0);

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return result;
    }

    @Override
    public String getLastInsertedJobID() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_LAST_INSERTED_JOBID");
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("jobID");
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return null;
    }

    @Override
    public ArrayList<ITeam> getAvailableTeams(IJob job) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<ITeam> availableTeams = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_AVAILABLE_TEAMS(?,?,?)");
            st.setString(1, job.getStartDate().toString());
            st.setTime(2, Time.valueOf(job.getStartTime()));
            st.setTime(3, Time.valueOf(job.getEndTime()));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                availableTeams.add(getTeamFromRs(rs));
            }
            for (ITeam team : availableTeams) {
                for (IEmployee emp : getTeamMembers(team.getTeamID())) {
                    team.setTeamMember(emp);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getCause().toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return availableTeams;
    }

    @Override
    public boolean checkIfTeamAvailable(ITeam team, IJob job) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<ITeam> availableTeams = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_AVAILABLE_TEAM(?,?,?,?)");
            st.setString(1, job.getStartDate().toString());
            st.setTime(2, Time.valueOf(job.getStartTime()));
            st.setTime(3, Time.valueOf(job.getEndTime()));
            st.setString(4, team.getTeamID());
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getCause().toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
    }

    @Override
    public IJob getJobFromRS(ResultSet rs) throws SQLException {
        IJob job = JobFactory.createInstance();
        job.setJobID(rs.getString("JobID"));
        job.setDescription(rs.getString("Description"));
        job.setClientName(rs.getString("Clientname"));
        job.setAssignedTeam(getJobTeamByID(rs.getString("teamID")));
        job.setDateCreated(LocalDate.parse(rs.getString("dateCreated")));
        job.setIsEmergencyJob(rs.getBoolean("IsEmergencyBooking"));
        job.setJobCost(rs.getDouble("Cost"));
        job.setJobRevenue(rs.getDouble("Revenue"));
        job.setStartDate(LocalDate.parse(rs.getString("jobDate")));
        job.setStartTime(LocalTime.parse(rs.getString("startTime")));
        job.setEndTime(LocalTime.parse(rs.getString("endTime")));
        job.setJobTasks(getJobTasks(job.getJobID()));

        return job;
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
    public ITeam getJobTeamByID(String id) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ITeam resultedTeam = null;
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_TEAM_ByID(?)");
            st.setString(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                resultedTeam = getTeamFromRs(rs);
            }
            setIndividualTeamMembers(resultedTeam);
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return resultedTeam;
    }

    @Override
    public ITeam getTeamFromRs(ResultSet rs) throws SQLException {
        ITeam team = TeamFactory.createInstance();
        team.setTeamID(rs.getString("TeamID"));
        team.setName(rs.getString("TeamName"));
        team.setOnCall(rs.getBoolean("OnCALL"));
        team.setTeamStatus(rs.getBoolean("Status"));
        team.setDateCreated(LocalDate.parse(rs.getString("DateCreated")));

        return team;
    }

    @Override
    public IEmployee getEmployeeFromRs(ResultSet rs) throws SQLException {
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
    public ArrayList<IEmployee> getTeamMembers(String teamID) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ArrayList<IEmployee> teamsMembersList = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_TEAM_MEMBERS(?)");
            st.setString(1, teamID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                teamsMembersList.add(getEmployeeFromRs(rs));
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return teamsMembersList;
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
            return false;
        }
        return result;
    }

    @Override
    public int getCurrentDayJobCount() throws SQLException {
        int jobCount = 0;
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_CURR_DAY_JOB_COUNT");
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                jobCount = rs.getInt("RESULT");
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return jobCount;
    }

    @Override
    public double getCurrentMonthJobCost() throws SQLException {
        double monthJobCost = 0;
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_CURR_MONTH_COST");
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                monthJobCost = rs.getDouble("CurrMonthCost");
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return monthJobCost;
    }

    @Override
    public double getCurrentMonthJobRevenue() throws SQLException {
        double monthJobRevenue = 0;
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_CURR_MONTH_REVENUE");
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                monthJobRevenue = rs.getDouble("CurrMonthRevenue");
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return monthJobRevenue;
    }

    @Override
    public SortedMap<Integer,List<Double>> getAnnualReport(LocalDate ld) throws SQLException {
        SortedMap<Integer,List<Double>> yearlyReport = new TreeMap<>();
        Connection con = ConnectionFactory.getConnection();

        try {
            PreparedStatement st = con.prepareStatement("CALL GET_ANNUAL_REPORT(?)");
            st.setInt(1,ld.getYear());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                List<Double> monthCostRevenue = new ArrayList<>();
                monthCostRevenue.add(rs.getDouble("monthRevenue"));
                monthCostRevenue.add(rs.getDouble("monthCost"));
                yearlyReport.put(rs.getInt("Month"),monthCostRevenue);  
            }

        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return yearlyReport;
    }

    @Override
    public double getCurrentYearCost(LocalDate ld) throws SQLException {
          double yearCost = 0;
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_YEAR_COST(?)");
            st.setInt(1,ld.getYear());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                yearCost = rs.getDouble("yearlyCost");
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return yearCost;
    }

    @Override
    public double getcurrentYearRevenue(LocalDate ld) throws SQLException {
          double yearRevenue = 0;
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("CALL GET_YEAR_REVENUE(?)");
            st.setInt(1,ld.getYear());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                yearRevenue = rs.getDouble("yearlyRevenue");
            }
        } catch (SQLException e) {
            System.out.print(e.toString());
            throw new SQLException("Something wwent wrong in processing the request");
        }
        return yearRevenue;
    }

}
