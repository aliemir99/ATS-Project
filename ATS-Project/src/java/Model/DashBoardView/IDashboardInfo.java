/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DashBoardView;

import Model.Team.ITeam;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

/**
 *
 * @author jber5
 */
public interface IDashboardInfo {
    
    public LocalDate getYearToShow();

    public void setYearToShow(LocalDate yearToShow);
    
    public SortedMap<Integer,List<Double>> getFinancialReport();

    public void setFinancialReport(SortedMap<Integer,List<Double>> financialReport);

    public ITeam getCurrentOnCallTeam();

    public void setCurrentOnCallTeam(ITeam currentOnCallTeam);

    public int getCurrentDayJobCount();

    public void setCurrentDayJobCount(int currentDayJobCount);

    public double getCurrentMonthJobCost();

    public void setCurrentMonthJobCost(double currentMonthJobCost);

    public double getCurrentMonthJobRevenue();

    public void setCurrentMonthJobRevenue(double currentMonthJobRevenue);

    public double getAnnualJobRevenue();

    public void setAnnualJobRevenue(double annualJobRevenue);

    public double getAnnualJobCost();
    
    public void setAnnualJobCost(double annualJobCost);
}
