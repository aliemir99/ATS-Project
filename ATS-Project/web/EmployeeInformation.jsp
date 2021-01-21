<%-- 
    Document   : EmployeeInformation
    Created on : Nov 5, 2020, 3:20:42 PM
    Author     : jber5
    Purpose: this Jsp page is for Showing Individual Employee Information
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="errors.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="ATStags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <title>Employee Info</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/Header.jspf"%>  
        <div class="container">
            <div class="main-body">

                <div class="row gutters-sm">
                    <div class="col-md-4 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex flex-column align-items-center text-center">
                                    <img class="card-img-top" src="https://www.flaticon.com/premium-icon/icons/svg/3597/3597892.svg" alt="" style="width:50%;height:50%;"  alt="" >
                                    <div class="mt-3">
                                        <form action="ProcessEmployeeInformation" method="POST">
                                            <div class="alert alert-danger" role="alert">
                                                <h4><jsp:getProperty name="employee" property="fullName"/></h4><hr>
                                                <c:if test="${!isShowingSin}">  
                                                    <p>SIN: <b><c:out value="${maskedSin}"/></b></p>
                                                    <input type="submit" class="btn btn-success" name="showSinBtn" value="SHOW SIN"/>
                                                </c:if>
                                                <c:if test="${isShowingSin}">
                                                    <p>SIN: <b><jsp:getProperty name="employee" property="SIN"/></b></p>
                                                </c:if>
                                                <hr>
                                                <p>Pay Rate: <b><jsp:getProperty name="employee" property="payRate"/></b></p>
                                                <p>Updated: <b><jsp:getProperty name="employee" property="dateCreated"/></b></p>
                                                <c:if test="${employee.employeeStatus == true}">
                                                    <p class="text-success">Status: <b>ACTIVE</b><p>
                                                    </c:if>

                                                    <c:if test="${employee.employeeStatus == false}">  
                                                    <p class="text-warning">Status: <b>ARCHIVED</b><p>
                                                    </c:if>
                                                    <br>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="card mb-3">
                            <div class="card-body">
                                <div class="row">
                                    <div class="card-body">
                                        <div class="row">
                                            <form action="ProcessEmployeeInformation" method="POST">
                                                <fieldset>
                                                    <c:if test="${isShowingSin}">
                                                        <div id="legend">
                                                            <legend class="">Employee Information</legend>
                                                        </div>
                                                        <label class="control-label"  for="fName">First Name</label>
                                                        <input type="text" id="username" name="firstName" value="<c:out value="${employee.firstName}"/>" class="input-xlarge" required>


                                                        <label class="control-label"  for="lName">Last Name</label>
                                                        <input type="text" id="username" name="lastName" value="<c:out value="${employee.lastName}"/>" class="input-xlarge" required>

                                                        <br>

                                                        <label class="control-label" for="sinNum">SIN number</label>
                                                        <input type="text" id="username" name="sin" value="<c:out value="${employee.SIN}"/>"
                                                               class="input-xlarge" required>

                                                        <label class="control-label"  for="payRateHr">Pay Rate (HOUR)</label>
                                                        <input type="text" id="username" name="payRate" value="<c:out value="${employee.payRate}"/>" class="input-xlarge" required>
                                                        <br><br><br>
                                                        <input type="submit" class="btn btn-warning" name="saveEmployeeBtn" value="UPDATE"/>
                                                    </c:if>
                                                        
                                                    <c:if test="${!isShowingSin}">
                                                        <ul class="list-group">
                                                            <li class="list-group-item active"><b>Employee Information</b></li>
                                                            <li class="list-group-item">First Name: <b><c:out value="${employee.firstName}"/></b></li>
                                                            <li class="list-group-item">Last Name: <b><c:out value="${employee.lastName}"/></b></li>
                                                            <li class="list-group-item">SIN: <b><c:out value="${maskedSin}"/></b></li>
                                                            <li class="list-group-item">Pay Rate: <b><c:out value="${employee.payRate}"/></b></li>
                                                        </ul>
                                                        <br>
                                                    </c:if>

                                                    <c:if test="${employee.employeeStatus == true}">    
                                                        <input type="submit" class="btn btn-danger" name="archiveEmployeeBtn" value="ARCHIVE"/>       
                                                    </c:if>
                                                        
                                                    <c:if test="${employee.employeeStatus == false}">    
                                                        <input type="submit" class="btn btn-success" name="restoreEmployeeBtn" value="RESTORE"/>       
                                                    </c:if>   

                                                    <c:if test="${employeeTeam.teamID < 1 && employee.employeeStatus == false}">   
                                                        <input type="submit" class="btn btn-danger" name="deleteEmployeeBtn" value="DELETE"/>       
                                                    </c:if>     
                                                    <c:forEach items="${inputError}" var="error">
                                                        <p class="text-danger"><c:out value="${error}"/></p>
                                                    </c:forEach>

                                                </fieldset>  
                                        </div> 
                                    </div>
                                </div>
                                <hr>
                                <legend class="">Assigned Team</legend>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <c:if test="${employeeTeam != null}"> 
                                            <a href="<%=request.getContextPath()%>/ShowTeamInformation?teamId=<c:out value="${employeeTeam.teamID}"/>">
                                                <p class="text-primary"><c:out value="${employeeTeam.name}"/></p></a>
                                            </c:if>
                                    </div>
                                    <div class="col-sm-9 text-secondary">

                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <p class="text-info">Add Skill</p>
                                        <select name="universalSkills">
                                            <c:forEach items="${universalSkills}" var="task">
                                                <option value="${task.taskID}"><c:out value="${task.name}"/></option>
                                            </c:forEach>  
                                        </select>
                                        <hr>
                                        <button class="btn btn-success" name="addSkill">Add Skill</button>          
                                    </div>
                                    <c:if test="${!employeeSkills.isEmpty()}">
                                        <div class="col-sm-6">
                                            <p class="text-success">Assigned Skills</p>
                                            <select name="employeeSkill">
                                                <c:forEach items="${employeeSkills}" var="task">
                                                    <option value="${task.taskID}"><c:out value="${task.name}"/></option>
                                                </c:forEach>  
                                            </select>
                                            <hr>
                                            <button class="btn btn-danger" name="removeSkill">Remove Skill</button>          
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                        </div>
                        </form>  
                    </div>

                </div>
            </div>
        </div>

    </body>
    <%@include file="WEB-INF/jspf/footer.jspf"%>  
</html>

<style>
    <%@ include file="WEB-INF/StyleSheets/Employee_Information_Page.css"%>  
</style>