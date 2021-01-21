<%-- 
    Document   : CreateTeam
    Created on : Nov 5, 2020, 4:48:35 PM
    Author     : jber5
    Purpose: this Jsp page is for Showing the Team list and also Create Team form
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
        <title>Create Team</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/Header.jspf"%>  
        <div class="container register">
            <div class="row">
                <div class="col-md-3 register-left">
                    <img src="https://cdnb.artstation.com/p/assets/images/images/015/133/897/large/celia-calderon-ats-2.jpg?1547174170" alt=""/>
                    <h3>Welcome to ATS Admin Dashboard</h3>
                </div>
                <div class="col-md-9 register-right">
                    <div class="tab-content" id="myTabContent">
                        <div class="tab-pane fade show active" id="team" role="tabpanel" aria-labelledby="team-tab">
                            <h3  class="register-heading">Establish a Team</h3>
                            <div class=" row register-form">
                                <div class="col-md-6">
                                    <form action="ProcessCreateTeam" method="POST">
                                        <div class="row">
                                            <div class="col-5">
                                                <fieldset>
                                                    <c:if test="${isMembersSubmitted}">   
                                                        <h2 class="fs-title">Create a Team</h2>
                                                        <input type="text" class="form-control" name="teamName" placeholder="Team Name *" required/>
                                                    </c:if>  
                                                    <c:if test="${teamToBeCreated.teamMembers.size() < 2}">     
                                                        <p><b>Assign Members</b></p>
                                                        <p>Select 2 employees</p>
                                                        <select name="employees">
                                                            <c:forEach items="${employeeList}" var="employee">
                                                                <option value="${employee.employeeID}"><c:out value="${employee.fullName}"/></option>
                                                            </c:forEach>  
                                                        </select>
                                                        <br>
                                                        <button class="btn btn-success" name="addTeamBtn">Add member</button>          
                                                    </c:if>
                                                </fieldset>  
                                            </div>

                                            <div class="col-4">
                                                <fieldset>
                                                    <p>Selected Members</p>
                                                    <hr>
                                                    <c:if test="${!isMembersSubmitted}">
                                                        <select name="discardMemberId">
                                                            <c:forEach items="${teamToBeCreated.teamMembers}" var="member"> 
                                                                <option value="${member.employeeID}"><c:out value="${member.fullName}"/></option>
                                                            </c:forEach>
                                                        </select>
                                                    </c:if>

                                                    <c:if test="${isMembersSubmitted}">
                                                        <c:forEach items="${teamToBeCreated.teamMembers}" var="member"> 
                                                            <p class="text-success"><c:out value="${member.fullName}"/></p>
                                                        </c:forEach>
                                                    </c:if>

                                                    <c:if test="${!isMembersSubmitted && teamToBeCreated.teamMembers.size() >= 1}">
                                                        <button class="btn btn-danger" name="discardMember">Discard</button><hr>
                                                    </c:if>
                                                    <c:if test="${!isMembersSubmitted && teamToBeCreated.teamMembers.size() >= 2}">   
                                                        <button class="btn btn-success" name="submitMembersBtn">Submit Members</button><hr> 
                                                    </c:if>
                                                </fieldset>
                                            </div>
                                        </div>
                                        <c:if test="${teamToBeCreated.teamMembers != null && teamToBeCreated.teamMembers.size() >= 2}">   
                                            <button class="btnRegister" name="createTeam">Register</button>
                                        </c:if>  
                                    </form>   
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <h1 style="text-align:center;">Teams</h1>
        <hr>
        <form action="ProcessCreateTeam" method="POST">
            <input style="width:150px;height:50px; " type="submit" class="btn btn-success" name="showActiveTeamsBtn" value="Active Teams"/>       
            <input style="width:150px;height:50px; " type="submit" class="btn btn-danger" name="showArchivedTeamsBtn" value="Archived Teams"/>    
        </form>
        <div class="col-lg-12">  
            <c:if test="${isActive == true}"> 
                <div class="row">
                    <c:forEach items="${activeTeamList}" var="team"> 
                        <div class="col-lg-3">
                            <div class="card h-90" style="background-color: #1C6262; margin-bottom: 50px;">
                                <a href="#"><img class="card-img-top" src="https://www.flaticon.com/svg/static/icons/svg/2405/2405310.svg" alt="" style="width:20%;height:20%;" ></a>
                                <div class="card-body">
                                    <h4 class="card-title">
                                        <a href="<%=request.getContextPath()%>/ShowTeamInformation?teamId=<c:out value="${team.teamID}"/>">
                                            <p class="text-warning"><c:out value="${team.name}"/></p></a>
                                        <p><b>Members</b></p>
                                        <c:forEach items="${team.teamMembers}" var="member">  
                                            <a href="<%=request.getContextPath()%>/ProcessEmployeeInformation?employeeID=<c:out value="${member.employeeID}"/>">
                                                <p class="text-info"><c:out value="${member.fullName}"/></p></a>
                                            </c:forEach>
                                        <hr>
                                        <c:if test="${team.onCall == true}">  
                                            <div class="alert alert-success" role="alert">
                                                <b>On Call Team*</b>
                                            </div>
                                        </c:if>
                                    </h4>
                                </div>
                            </div>
                        </div>          
                    </c:forEach>             
                </div>
            </c:if>
            <c:if test="${isArchive == true}">
                <div class="row">
                    <c:forEach items="${archivedTeamList}" var="team"> 
                        <div class="col-lg-3">
                            <div class="card h-90" style="background-color: #1C6262; margin-bottom: 50px;">
                                <a href="#"><img class="card-img-top" src="https://www.flaticon.com/svg/static/icons/svg/2405/2405310.svg" alt="" style="width:20%;height:20%;" ></a>
                                <div class="card-body">
                                    <h4 class="card-title">
                                        <a href="<%=request.getContextPath()%>/ShowTeamInformation?teamId=<c:out value="${team.teamID}"/>">
                                            <p class="text-warning"><c:out value="${team.name}"/></p></a>
                                        <p><b>Members</b></p>
                                        <c:forEach items="${team.teamMembers}" var="member">  
                                            <a href="<%=request.getContextPath()%>/ProcessEmployeeInformation?employeeID=<c:out value="${member.employeeID}"/>">
                                                <p class="text-info"><c:out value="${member.fullName}"/></p></a>
                                            </c:forEach>
                                    </h4>
                                </div>
                            </div>
                        </div>          
                    </c:forEach>             
                </div>
            </c:if>
        </div>
    </body>
    <%@include file="WEB-INF/jspf/footer.jspf"%>  
</html>


<style>
    <%@include file="WEB-INF/StyleSheets/Team_Form_and_List.css"%>
</style>
