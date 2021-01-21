<%-- 
    Document   : JobInformation
    Created on : Nov 26, 2020, 1:18:04 PM
    Author     : jber5
    Purpose: this Jsp page is for Showing Individual Job Information
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
        <title>Job Information</title>
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
                                    <img class="card-img-top" src="https://www.flaticon.com/premium-icon/icons/svg/2936/2936736.svg" alt="" style="width:50%;height:50%;"  alt="" >
                                    <div class="mt-3">
                                        <div class="alert alert-primary" role="alert">
                                            <h4>Client Name: <jsp:getProperty name="currentJob" property="clientName"/></h4>
                                            <p><b>Created On: <jsp:getProperty name="currentJob" property="dateCreated"/></b></p>
                                            <form action="ProcessJobInformation" method="POST">
                                                <c:if test="${canDeleteJob == true}">  
                                                    <input  type="submit" name="deleteJobBtn" class="btn btn-danger" value="Delete Job"/>
                                                </c:if>

                                                <c:if test="${currentJob.isEmeregencyJob()}">
                                                    <p class="text-warning"><b>Emergency Job</b></p>
                                                </c:if>

                                                <c:if test="${!currentJob.isEmeregencyJob()}">
                                                    <p><b>Casual Job</b></p>
                                                </c:if>
                                            </form>
                                            <br>
                                        </div>
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
                                            <legend>Job Information</legend><br>
                                            <div class="col-6">
                                                <div class="alert alert-light" role="alert">
                                                    <fieldset>
                                                        <p>Start Date: <b class="text-danger"><c:out value="${currentJob.startDate}"/></b></p>
                                                        <p>Start Time: <b class="text-danger"><c:out value="${currentJob.startTime}"/></b></p>
                                                        <p>End Time:   <b class="text-danger"><c:out value="${currentJob.endTime}"/></b></p>
                                                    </fieldset>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <div class="alert alert-dark" role="alert">
                                                    <fieldset>
                                                        <p>Description: <b class="text-danger"><c:out value="${currentJob.description}"/></b></p>
                                                        <p>Cost: <b class="text-danger"><c:out value="${currentJob.jobCost}"/>$</b></p>
                                                        <p>Revenue: <b class="text-danger"><c:out value="${currentJob.jobRevenue}"/>$</b></p>
                                                    </fieldset>
                                                </div>
                                            </div>
                                        </div> 
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <h4 class="text-danger"><c:out value="${currentJob.assignedTeam.name}"/></h4></a>
                                        <c:forEach items="${currentJob.assignedTeam.teamMembers}" var="member">  
                                            <a href="<%=request.getContextPath()%>/ProcessEmployeeInformation?employeeID=<c:out value="${member.employeeID}"/>">
                                                <p class="text-info"><c:out value="${member.fullName}"/></p></a>
                                            </c:forEach>
                                    </div>
                                    <div class="col-sm-6">
                                        <legend class="">Job Tasks</legend>
                                        <ul class="list-group">
                                            <c:forEach items="${jobTasksMap}" var="task"> 
                                                <li class="list-group-item d-flex justify-content-between align-items-center">
                                                    <a href="<%=request.getContextPath()%>/ProcessTaskInformation?taskID=<c:out value="${task.key.taskID}"/>">
                                                        <p class="text-primary"><c:out value="${task.key.name}"/></p></a>
                                                    <span class="badge badge-primary badge-pill"><c:out value="${task.value}"/></span>
                                                </li>
                                            </c:forEach>
                                    </div>
                                </div>
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