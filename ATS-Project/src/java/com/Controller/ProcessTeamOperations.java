/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

// <editor-fold desc="Used Imports">
import HelperClasses.HelperMethods;
import Model.Employee.IEmployee;
import Model.Team.ITeam;
import Model.Team.TeamFactory;
import BusinessServices.EmployeeServiceFactory;
import BusinessServices.IEmployeeService;
import BusinessServices.ITeamService;
import BusinessServices.TeamServiceFactory;
import HelperClasses.InputViewModel;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

/**
 * @author jber5
 * Purpose: This servlet is used to process the creation of a team
 * and also to view current list of Teams
 */
public class ProcessTeamOperations extends HttpServlet {

    // <editor-fold desc="Local Scope Variables">
    private boolean viewArchive;
    private boolean viewActive;
    private ArrayList<ITeam> activeTeamList;
    private ArrayList<ITeam> archivedTeamList;
    private boolean isMembersSubmitted;
    private ArrayList<IEmployee> employeeList;
    private ArrayList<IEmployee> addedMembers;
    private String addedEmployeeID;
    private ITeam teamCreated;
    private IEmployeeService employeeService;
    private ITeamService teamService;
    // </editor-fold>

    
    /** 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * DoGet: IS used to load the Team list (Active,Archived)
     * then it sets the required attributes for the view
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        employeeService = EmployeeServiceFactory.createInstance();
        teamService = TeamServiceFactory.createInstance();
        teamCreated = TeamFactory.createInstance();
        isMembersSubmitted = false;
        viewActive = true;
        viewArchive = false;

        
        if (addedMembers == null) {
            addedMembers = new ArrayList<>();
        }

        employeeList = employeeService.getUnassignedTeamEmployees();
        activeTeamList = teamService.getActiveTeams();
        archivedTeamList = teamService.getArchivedTeams();

        setRequestObjectsAttributes(request);
        HelperMethods.forwardTo(request, response, "TeamList.jsp");

    }

    /** 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * DOPost: is used for Team creation submission and also
     * operation like adding, removing members (in team creation process only)
     * Also to process the view for Archived and Active Teams upon user request. 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("createTeam") != null) {
            ArrayList<IEmployee> tmpMemberList = teamCreated.getTeamMembers();
            teamCreated = InputViewModel.getTeamFromInputRequest(request);
            
            //Adding the memebrs again since teamcreated member values changed
            tmpMemberList.forEach((member) -> {
                teamCreated.setTeamMember(member);
            });
            
            //Insert Team
            teamService.insertTeam(teamCreated);

            addedMembers.clear();
            activeTeamList = teamService.getActiveTeams();
            employeeList = employeeService.getUnassignedTeamEmployees();
            isMembersSubmitted = false;
            
            response.sendRedirect("ProcessCreateTeam");
            return;
        }

        if (request.getParameter("submitMembersBtn") != null) {
            isMembersSubmitted = true;
        }

        if (request.getParameter("addTeamBtn") != null) {
            addedEmployeeID = request.getParameter("employees");
            teamService.addTeamMember(employeeList, addedEmployeeID, teamCreated);
        }

        if (request.getParameter("discardMember") != null) {
            String memberToRemoveID = request.getParameter("discardMemberId");
            teamService.removeMember(memberToRemoveID, teamCreated);
        }

        if (request.getParameter("showActiveTeamsBtn") != null) {
            viewActive = true;
            viewArchive = false;
        }

        if (request.getParameter("showArchivedTeamsBtn") != null) {
            viewActive = false;
            viewArchive = true;
        }

        setRequestObjectsAttributes(request);
        HelperMethods.forwardTo(request, response, "TeamList.jsp");
    }

    /**
     * @param request 
     * Method to set the required request attributes to display the information in the view
     */
    private void setRequestObjectsAttributes(HttpServletRequest request){
        request.setAttribute("isMembersSubmitted", isMembersSubmitted);
        request.setAttribute("employeeList", employeeList);
        request.setAttribute("addedMembers", addedMembers);
        request.setAttribute("activeTeamList", activeTeamList);
        request.setAttribute("archivedTeamList", archivedTeamList);
        request.setAttribute("isActive", viewActive);
        request.setAttribute("isArchive", viewArchive);
        request.setAttribute("teamToBeCreated", teamCreated);
    }
}
