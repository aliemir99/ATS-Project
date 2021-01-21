<%-- 
    Document   : EmployeesList
    Created on : Nov 5, 2020, 10:59:03 AM
    Author     : jber5
    Purpose: this Jsp page is for Showing Employees list and Create Employee Form   
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="errors.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="ATStags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="createEmployeeErrors" scope="session" class="java.util.ArrayList"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

        <title>Employee List</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/Header.jspf"%>  
        <div class="container">
            <div class="container register">
                <div class="row">
                    <div class="col-md-3 register-left">
                        <img src="https://cdnb.artstation.com/p/assets/images/images/015/133/897/large/celia-calderon-ats-2.jpg?1547174170" alt=""/>
                        <h3>Welcome to ATS Admin Dashboard</h3>
                    </div>
                    <div class="col-md-9 register-right">
                        <div class="tab-content" id="myTabContent">
                            <div class=" tab-pane fade show active" id="employee" role="tabpanel" aria-labelledby="employee-tab">
                                <h3 class="register-heading">Create a new Employee</h3>
                                <form class="row register-form" action="EmployeeOperations" method="POST">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <input type="text" class="form-control" name="firstName" placeholder="First Name *" required/>
                                        </div>
                                        <div class="form-group">
                                            <input type="text" class="form-control" name="lastName" placeholder="Last Name *" required/>
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control" name="sin" placeholder="SIN *" required/>
                                        </div>
                                        <div class="form-group">
                                            <input type="text" class="form-control" name="payRate" placeholder="Hourly pay rate *" required/>
                                        </div>
                                        <input type="submit" class="btnRegister" name="createEmployee" value="Register"/>
                                    </div>
                                    <div class='colo-md-3'>
                                        <c:if test="${createEmployeeErrors != null}">  
                                            <ul>
                                                <c:forEach items="${createEmployeeErrors}" var="error">    
                                                    <li style="color:red;"><c:out value="${error}"/></li>
                                                    </c:forEach>
                                            </ul>
                                        </c:if>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <hr>
        <div class="col-lg-8" style="margin-left:16%">
            <form class="searchBar" action="ProcessEmployeeSearch" method="POST">
                <input type="text" placeholder="Search by SIN or Last name" id="table_filter" name="searchUserInput" required  >
                <button type="submit" name="searchBtn" style="margin-bottom:20px;" required ><i class="fa fa-search"></i></button>
                <div class="card" id="searchCard">
                    <ul>
                        <li>
                            <input type="radio" name="searchFilter" id="one" value="SIN" checked />
                            <label for="one">Search by SIN</label>
                            <div class="check"></div>
                        </li>
                        <li>
                            <input type="radio" name="searchFilter" value="LNAME" id="two" />
                            <label for="two">Search by Last Name</label>
                            <div class="check"></div>
                        </li>
                    </ul>
                </div>

            </form>
        </div>
        <br>
        <hr>
        <h1>Employees</h1><br>
        <form action="EmployeeOperations" method="POST">
            <input type="submit" class="btn btn-success" name="showActiveEmployeesBtn" value="Active Employees"/>       
            <input type="submit" class="btn btn-danger" name="showArchivedEmployeesBtn" value="Archived Employees"/>    
        </form>
        <br><br>
        <div class="col-lg-12">
            <div class="row">
                <c:if test="${isActive == true}"> 
                    <c:forEach items="${activeEmployees}" var="currEmployee">   
                        <div class="col-lg-3">
                            <div class="card h-90" style="background-color: #2092A1; margin-bottom: 50px;">
                                <a href="#"><img class="card-img-top" src="https://www.flaticon.com/premium-icon/icons/svg/3597/3597892.svg" alt="" style="width:20%;height:20%;" ></a>
                                <div class="card-body">
                                    <h4 class="card-title">
                                        <a href="<%=request.getContextPath()%>/ProcessEmployeeInformation?employeeID=${currEmployee.employeeID}">
                                            <p class="text-warning"><c:out value="${currEmployee.fullName}"/><p></a>
                                    </h4>
                                </div>
                            </div>
                        </div>          
                    </c:forEach>   
                </c:if>
                <c:if test="${isArchive == true}">
                    <c:forEach items="${archivedEmployees}" var="currEmployee">   
                        <div class="col-lg-3">
                            <div class="card h-90" style="background-color: #2092A1; margin-bottom: 50px;">
                                <a href="#"><img class="card-img-top" src="https://www.flaticon.com/premium-icon/icons/svg/3597/3597892.svg" alt="" style="width:20%;height:20%;" ></a>
                                <div class="card-body">
                                    <h4 class="card-title">
                                        <a href="<%=request.getContextPath()%>/ProcessEmployeeInformation?employeeID=${currEmployee.employeeID}">
                                            <p class="text-warning"><c:out value="${currEmployee.fullName}"/><p></a>
                                    </h4>
                                </div>
                            </div>
                        </div>          
                    </c:forEach>   
                </c:if>
            </div>
        </div>
    </body>
    <%@include file="WEB-INF/jspf/footer.jspf"%>  
</html>

<style>
    <%@include file="WEB-INF/StyleSheets/ATS_Entities_Creation.css"%>

    <%@include file="WEB-INF/StyleSheets/Employee_Search_Bar.css"%> 

</style>