/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessServices;

import Model.Employee.IEmployee;
import Model.Task.ITask;
import Model.Team.ITeam;
import java.util.ArrayList;

/**
 *
 * @author jber5
 */
public interface IEmployeeService {
    public boolean insertEmployee(IEmployee emp);
    
    public boolean validateSinInput(String sinNum);
    
    public boolean validatePayRateValue(String val);
    
    public void validateSIN(String sin);
    
    public boolean isSinExists(String sin);

    public boolean isErrorListEmpty();
    
    public ArrayList<String> getErrors();
            
    public ArrayList<IEmployee> getEmployeeAccordingSearch(String searchType,String searchInput);
    
    public ArrayList<IEmployee> getEmployeesBySin(String searchInput);

    public ArrayList<IEmployee> getEmployeesByLastName(String searchInput);

    public ArrayList<IEmployee> getUnassignedTeamEmployees();

    public ArrayList<IEmployee> getActiveEmployees();

    public ArrayList<IEmployee> getArchivedEmployees();

    public IEmployee getEmployeeByID(String id);

    public ITeam getEmployeeAssignedTeam(IEmployee emp);

    public ArrayList<ITask> getEmployeeUnAssignedSkills(IEmployee emp);

    public ArrayList<ITask> getEmployeeAssignedSkills(IEmployee emp);

    public boolean insertEmployeeSkill(IEmployee emp, ITask task);

    public boolean deleteEmployeeSkill(IEmployee emp, ITask task);

    public boolean updateEmployeeInformation(IEmployee emp);

    public boolean archiveEmployee(IEmployee emp) ;

    public boolean restoreEmployee(IEmployee emp);

    public boolean deleteEmployee(IEmployee emp);
    
    public String getMaskedSin(IEmployee emp);
    
    
}
