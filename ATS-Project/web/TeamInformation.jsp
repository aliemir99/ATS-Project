<%-- 
    Document   : TeamInformation
    Created on : Nov 14, 2020, 8:05:57 AM
    Author     : jber5
    Purpose: this Jsp page is for Showing Individual Team Information
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
        <title>Team Information</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/Header.jspf"%>  
        <div class="container">
            <div class="main-body">
                <div class="row gutters-sm">
                    <div class="col-md-4 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <p><b>Team Information</b></p>
                                <div class="d-flex flex-column align-items-center text-center">
                                    <img class="card-img-top" src="https://www.flaticon.com/svg/static/icons/svg/2405/2405310.svg" alt="" style="width:50%;height:50%;"  alt="" >
                                    <div class="mt-3">
                                        <h4><jsp:getProperty name="team" property="name"/></h4>
                                        <p>Updated: <b><jsp:getProperty name="team" property="dateCreated"/></b></p>
                                        <c:if test="${team.teamStatus == true}">
                                            <p class="text-success">Status: <b>ACTIVE</b><p>
                                            </c:if>

                                            <c:if test="${team.teamStatus == false}">  
                                            <p class="text-warning">Status: <b>ARCHIVED</b><p>
                                            </c:if>

                                        <form action="ShowTeamInformation" method="POST">      
                                            <c:if test="${team.onCall == false && canSetTeamOnCall == true && team.teamStatus == true}">  
                                                <input type="submit" class="btn btn-success" name="setOnCall" value="SET ON CALL"/> 
                                            </c:if>

                                            <c:if test="${team.onCall == true && team.teamStatus == true}">  
                                                <input type="submit" class="btn btn-danger" name="discardOnCall" value="DISCARD ON CALL"/> 
                                            </c:if>
                                        </form>

                                        <br>
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
                                        <form action="ShowTeamInformation" method="POST">                                
                                            <c:if test="${errors != null}">
                                                <c:forEach items="${errors}" var="error">
                                                    <p class="text-danger"><c:out value="${error}"/><p>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${team.teamStatus == true}">
                                                    <input type="submit" class="btn btn-danger" data-toggle="tooltip"
                                                           data-placement="top" title="Archive Team" name="archiveTeamBtn" value="ARCHIVE"/>       
                                                </c:if>
                                                <c:if test="${team.teamStatus == false}">   
                                                    <input type="submit" class="btn btn-danger" name="deleteTeamBtn" value="DELETE"/>       
                                                </c:if>  
                                        </form>    
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-6">
                                        <ul class="list-group">
                                            <li class="list-group-item active"><b>Team Members</b></li>
                                                <c:forEach items="${team.teamMembers}" var="member">
                                                <li class="list-group-item">
                                                    <a href="<%=request.getContextPath()%>/ProcessEmployeeInformation?employeeID=<c:out value="${member.employeeID}"/>">
                                                        <p class="text-primary"><c:out value="${member.fullName}"/></p></a></li>
                                                    </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <ul class="list-group">
                                            <li class="list-group-item active"><b>Team Skills</b></li>
                                                <c:forEach items="${team.teamSkills}" var="kvp">  
                                                <li class="list-group-item">
                                                    <a href="<%=request.getContextPath()%>/ProcessTaskInformation?taskID=<c:out value="${kvp.value.taskID}"/>">
                                                        <p class="text-danger"><c:out value="${kvp.value.name}"/></p></a></li>
                                                    </c:forEach>
                                        </ul>
                                    </div>
                                    <div class="col-6">
                                        <ul class="list-group">
                                            <li class="list-group-item active"><b>Associated Jobs</b></li>
                                                <c:forEach items="${team.teamJobs}" var="job">
                                                <li class="list-group-item">
                                                    <a href="<%=request.getContextPath()%>/ProcessJobInformation?jobId=<c:out value="${job.jobID}"/>">
                                                        <p class="text-info"><c:out value="${job.clientName}"/></p></a></li>
                                                    </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                </div>
                            </div>
                        </div>
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