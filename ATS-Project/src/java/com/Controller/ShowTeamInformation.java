/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

// <editor-fold desc="Used Imports">
import BusinessServices.ITeamService;
import HelperClasses.HelperMethods;
import Model.Team.ITeam;
import BusinessServices.TeamServiceFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

/**
 *
 * @author jber5
 * 
 * Purpose: this servlet is for process an individual Team information
 */
public class ShowTeamInformation extends HttpServlet {

    // <editor-fold desc="Local Scope Variables">
    private ITeam currentTeam;
    private boolean canSetTeamOnCall;
    private ITeamService teamService;
    // </editor-fold>
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * 
     * DOGet: it gets the corresponding team by the Id from the request
     * and it grabs the required information for the view 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        teamService = TeamServiceFactory.createInstance();
        String teamReq = request.getParameter("teamId");
        currentTeam = teamService.getTeamByID(teamReq);
        currentTeam.setTeamJobs(teamService.getTeamJobs(currentTeam.getTeamID()));
        teamService.setAlltMemberSkills(currentTeam);
        
        canSetTeamOnCall = false;

        //If team on Call is not found
        if ((teamService.getTeamOnCall().getTeamID()).equals("")) {
            canSetTeamOnCall = true;
        }

        setRequestObjectsAttributes(request);
        HelperMethods.forwardTo(request, response, "TeamInformation.jsp");
    }

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * DOPost: it's used for Team operations (ARCHIVE,DELETE,SET ON CALL,DISCARD ON CALL)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Archive Team Btn
        if (request.getParameter("archiveTeamBtn") != null) {
            teamService.archiveTeam(currentTeam);
            setRequestObjectsAttributes(request);
            HelperMethods.forwardTo(request, response, "TeamInformation.jsp");
        }

        //Delete Team Btn
        if (request.getParameter("deleteTeamBtn") != null) {
            if (!teamService.deleteTeam(currentTeam)) {
                setRequestObjectsAttributes(request);
                HelperMethods.forwardTo(request, response, "TeamInformation.jsp");
            } else {
                response.sendRedirect("ProcessCreateTeam");
            }
        }

        //Set onCall status
        if (request.getParameter("setOnCall") != null) {

            teamService.setTeamOnCall(currentTeam);
            setRequestObjectsAttributes(request);
            HelperMethods.forwardTo(request, response, "TeamInformation.jsp");
        }

        //remove onCall status
        if (request.getParameter("discardOnCall") != null) {

            teamService.removeTeamOnCall(currentTeam);
            setRequestObjectsAttributes(request);
            HelperMethods.forwardTo(request, response, "TeamInformation.jsp");
        }

    }

    /**
     * @param request 
     * Method to set the required request attributes to display the information in the view
     */
    private void setRequestObjectsAttributes(HttpServletRequest request) {
        request.setAttribute("canSetTeamOnCall", canSetTeamOnCall);
        request.setAttribute("errors", teamService.getErrors());
        request.setAttribute("team", currentTeam);
    }

}
