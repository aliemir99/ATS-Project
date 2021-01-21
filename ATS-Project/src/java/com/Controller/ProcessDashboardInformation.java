/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

// <editor-fold desc="Used Imports">
import BusinessServices.JobService;
import BusinessServices.TeamService;
import HelperClasses.HelperMethods;
import Model.DashBoardView.DashboardInfoFactory;
import Model.DashBoardView.IDashboardInfo;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// </editor-fold>

/**
 * @author jber5
 * Purpose: This servlet to grab all the information required for the dashboard View
 */
public class ProcessDashboardInformation extends HttpServlet {
    
    // <editor-fold desc="Local Scope Variables">
    private IDashboardInfo dashBoardInormation;
    private TeamService teamService;
    private JobService jobService;
    // </editor-fold> 

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * DoGet: We initialize the objects and we set all the required details 
     * using the corresponding services
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        teamService = new TeamService();
        jobService = new JobService();
        dashBoardInormation = DashboardInfoFactory.createInstance();

        dashBoardInormation.setCurrentOnCallTeam(teamService.getTeamOnCall());
        dashBoardInormation.setCurrentDayJobCount(jobService.getCurrentDayJobCount());
        dashBoardInormation.setCurrentMonthJobCost(jobService.getCurrentMonthJobCost());
        dashBoardInormation.setCurrentMonthJobRevenue(jobService.getCurrentMonthJobRevenue());
        dashBoardInormation.setAnnualJobCost(jobService.getCurrentYearCost(LocalDate.now()));
        dashBoardInormation.setAnnualJobRevenue(jobService.getCurrentYearCost(LocalDate.now()));
        dashBoardInormation.setFinancialReport(jobService.getAnnualReport(LocalDate.now()));
        dashBoardInormation.setYearToShow(LocalDate.now());

        request.setAttribute("dashModel", dashBoardInormation);
        HelperMethods.forwardTo(request, response, "home.jsp");

    }

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException  
     * DOPost: all it does is to grab the current year or last year information 
     * when user request it.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("lastYearReportBtn") != null) {
            LocalDate ld = LocalDate.now().minusYears(1);

            dashBoardInormation.setAnnualJobCost(jobService.getCurrentYearCost(ld));
            dashBoardInormation.setAnnualJobRevenue(jobService.getCurrentYearCost(ld));
            dashBoardInormation.setFinancialReport(jobService.getAnnualReport(ld));
            dashBoardInormation.setYearToShow(ld);
        }

        if (request.getParameter("currentYearReportBtn") != null) {
            dashBoardInormation.setAnnualJobCost(jobService.getCurrentYearCost(LocalDate.now()));
            dashBoardInormation.setAnnualJobRevenue(jobService.getCurrentYearCost(LocalDate.now()));
            dashBoardInormation.setFinancialReport(jobService.getAnnualReport(LocalDate.now()));
            dashBoardInormation.setYearToShow(LocalDate.now());
        }
        request.setAttribute("dashModel", dashBoardInormation);
        HelperMethods.forwardTo(request, response, "home.jsp");

    }

}
