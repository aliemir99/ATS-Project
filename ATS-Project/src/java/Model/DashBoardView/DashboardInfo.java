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
import java.util.TreeMap;

/**
 *
 * @author jber5
 * 
 * Dashboard View Model
 */
public class DashboardInfo implements IDashboardInfo{
    private ITeam currentOnCallTeam;
    private int currentDayJobCount;
    private double currentMonthJobCost;
    private double currentMonthJobRevenue;
    private double annualJobRevenue;
    private double annualJobCost;
    private SortedMap<Integer,List<Double>> financialReport;
    private LocalDate yearToShow;

    @Override
    public LocalDate getYearToShow() {
        return yearToShow;
    }
    
    @Override
    public void setYearToShow(LocalDate yearToShow) {
        this.yearToShow = yearToShow;
    }
    
    public DashboardInfo(){
        financialReport = new TreeMap<>();
        
    }

    @Override
    public SortedMap<Integer,List<Double>> getFinancialReport() {
        return financialReport;
    }

    @Override
    public void setFinancialReport(SortedMap<Integer,List<Double>> financialReport) {
        this.financialReport = financialReport;
    }

    @Override
    public ITeam getCurrentOnCallTeam() {
        return currentOnCallTeam;
    }
    
    @Override
    public void setCurrentOnCallTeam(ITeam currentOnCallTeam) {
        this.currentOnCallTeam = currentOnCallTeam;
    }
    
    @Override
    public int getCurrentDayJobCount() {
        return currentDayJobCount;
    }
    
    @Override
    public void setCurrentDayJobCount(int currentDayJobCount) {
        this.currentDayJobCount = currentDayJobCount;
    }
    
    @Override
    public double getCurrentMonthJobCost() {
        return currentMonthJobCost;
    }
    
    @Override
    public void setCurrentMonthJobCost(double currentMonthJobCost) {
        this.currentMonthJobCost = currentMonthJobCost;
    }
    
    @Override
    public double getCurrentMonthJobRevenue() {
        return currentMonthJobRevenue;
    }
    
    @Override
    public void setCurrentMonthJobRevenue(double currentMonthJobRevenue) {
        this.currentMonthJobRevenue = currentMonthJobRevenue;
    }
    
    @Override
    public double getAnnualJobRevenue() {
        return annualJobRevenue;
    }
    
    @Override
    public void setAnnualJobRevenue(double annualJobRevenue) {
        this.annualJobRevenue = annualJobRevenue;
    }
    
    @Override
    public double getAnnualJobCost() {
        return annualJobCost;
    }
    @Override
    public void setAnnualJobCost(double annualJobCost) {
        this.annualJobCost = annualJobCost;
    }
    
    
}
