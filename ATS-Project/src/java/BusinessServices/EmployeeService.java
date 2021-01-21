/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessServices;

// <editor-fold desc="Used Imports">
import DataAccess.ISqlEmployee;
import DataAccess.SqlEmployeeFactory;
import HelperClasses.HelperMethods;
import Model.Employee.EmployeeFactory;
import Model.Employee.IEmployee;
import Model.Task.ITask;
import Model.Team.ITeam;
import Model.Team.TeamFactory;
import java.sql.SQLException;
import java.util.ArrayList;
// </editor-fold>


/**
 * @author jber5 Purpose: this class is for all Employee related operation
 * (CREATE,DELETE,UPDATE,READ) and also validation
 */
public class EmployeeService implements IEmployeeService {

    // <editor-fold desc="Local Scope Variables">
    private ISqlEmployee empRepo = SqlEmployeeFactory.createInstance();
    public ArrayList<String> errorList;
    // </editor-fold>

    public EmployeeService() {
        errorList = new ArrayList<>();
    }

    @Override
    public boolean insertEmployee(IEmployee emp) {
        if (errorList.isEmpty()) {
            try {
                return empRepo.insertEmployee(emp);
            } catch (SQLException ex) {
                System.out.print(ex.toString());
            }
        }
        return false;
    }

    @Override
    public boolean validateSinInput(String sinNum) {
        if (sinNum.length() != 9) {
            errorList.add("SIN length has to be exactly 9 Digit number");
            return false;
        }

        char[] slicedSin = sinNum.toCharArray();
        for (char ch : slicedSin) {
            if (!Character.isDigit(ch)) {
                errorList.add("Wrong SIN input");
                return false;
            }
        }
        return true;

    }

    @Override
    public boolean isSinExists(String sin) {
        try {
            if (empRepo.searchSin(sin)) {
                errorList.add("SIN already exists in the records");
            }
        } catch (SQLException ex) {

        }
        return true;
    }

    @Override
    public void validateSIN(String sin) {
        if (validateSinInput(sin) == true) {
            isSinExists(sin);
        }
    }

    @Override
    public boolean validatePayRateValue(String val) {
        if (!HelperMethods.checkNumeric(val)) {
            errorList.add("Value is not Numeric");
            return false;

        } else {
            if (Double.parseDouble(val) <= 0) {
                errorList.add("Value Should be bigger than zero");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isErrorListEmpty() {
        return errorList.isEmpty();
    }

    @Override
    public ArrayList<IEmployee> getEmployeeAccordingSearch(String searchType, String searchInput) {
        if (searchType.equals("SIN")) {
            return getEmployeesBySin(searchInput);
        } else {
            return getEmployeesByLastName(searchInput);
        }
    }

    @Override
    public ArrayList<IEmployee> getEmployeesBySin(String searchInput) {
        ArrayList<IEmployee> employeesList = new ArrayList<>();

        try {
            employeesList = empRepo.getEmployeesBySinSearch(searchInput);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return employeesList;
    }

    @Override
    public ArrayList<IEmployee> getEmployeesByLastName(String searchInput) {
        ArrayList<IEmployee> employeesList = new ArrayList<>();
        try {
            employeesList = empRepo.getEmployeesByLNameSearch(searchInput);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return employeesList;
    }

    @Override
    public ArrayList<IEmployee> getUnassignedTeamEmployees() {
        ArrayList<IEmployee> employeesList = new ArrayList<>();

        try {
            employeesList = empRepo.getUnassignedEmployees();
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return employeesList;
    }

    @Override
    public ArrayList<IEmployee> getActiveEmployees() {
        ArrayList<IEmployee> activeEmployeeList = new ArrayList<>();
        try {
            return empRepo.getActiveEmployees();
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return activeEmployeeList;
    }

    @Override
    public ArrayList<IEmployee> getArchivedEmployees() {
        ArrayList<IEmployee> archivedEmployeeList = new ArrayList<>();
        try {
            return empRepo.getArchivedEmployees();
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return archivedEmployeeList;
    }

    @Override
    public IEmployee getEmployeeByID(String id) {
        try {
            return empRepo.getEmployeeByID(id);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return EmployeeFactory.createInstance();
    }

    @Override
    public ITeam getEmployeeAssignedTeam(IEmployee emp) {
        try {
            return empRepo.getEmployeeAssignedTeam(emp);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return TeamFactory.createInstance();
    }

    @Override
    public ArrayList<ITask> getEmployeeUnAssignedSkills(IEmployee emp) {
        ArrayList<ITask> employeeSkillsList = new ArrayList<>();
        try {
            employeeSkillsList = empRepo.getEmployeeUnassignedSkills(emp);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return employeeSkillsList;
    }

    @Override
    public ArrayList<ITask> getEmployeeAssignedSkills(IEmployee emp) {
        ArrayList<ITask> employeeSkillsList = new ArrayList<>();
        try {
            employeeSkillsList = empRepo.getEmployeeAssignedSkills(emp);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return employeeSkillsList;
    }

    @Override
    public boolean insertEmployeeSkill(IEmployee emp, ITask task) {
        try {
            return empRepo.insertEmployeeSkill(emp, task);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return false;
    }

    @Override
    public boolean deleteEmployeeSkill(IEmployee emp, ITask task) {
        try {
            return empRepo.deleteEmployeeSkill(emp, task);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return false;
    }

    @Override
    public boolean updateEmployeeInformation(IEmployee emp) {
        try {
            return empRepo.updateEmployeeInofrmation(emp);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return false;
    }

    @Override
    public boolean archiveEmployee(IEmployee emp) {
        try {
            //If employee not part of a team
            if ((getEmployeeAssignedTeam(emp).getTeamID()).equals("")) {
                empRepo.archiveEmployee(emp);
                emp.setEmployeeStatus(false);
                emp.setDateCreated(HelperMethods.getCurrentDate());
                return true;
            } else {
                errorList.clear();
                this.errorList.add("Couldn't archive Employee. "
                        + "Employee is part of an active team");
                return false;
            }

        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return false;
    }

    @Override
    public boolean restoreEmployee(IEmployee emp) {
        try {
            empRepo.restoreEmployee(emp);
            emp.setDateCreated(HelperMethods.getCurrentDate());
            emp.setEmployeeStatus(true);
            return true;
        } catch (SQLException ex) {
            System.out.print(ex.toString());

        }
        return false;
    }

    @Override
    public boolean deleteEmployee(IEmployee emp) {
        try {
            if ((getEmployeeAssignedTeam(emp).getTeamID()).equals("")) {
                empRepo.deleteEmployee(emp);
                return true;
            }
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return false;
    }

    @Override
    public ArrayList<String> getErrors() {
        return this.errorList;
    }

    @Override
    public String getMaskedSin(IEmployee emp) {
        String maskedSin = "******";
        maskedSin += emp.getSIN().substring(6, emp.getSIN().length());
        return maskedSin;
    }

}
