<%-- 
    Document   : JobList
    Created on : Nov 21, 2020, 9:05:21 AM
    Author     : jber5
    Purpose: this Jsp page is for Showing Job List and Create Job Form
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="errors.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="ATStags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js" integrity="sha512-GDey37RZAxFkpFeJorEUwNoIbkTwsyC736KNSYucu1WJWFK9qTdzYub8ATxktr6Dwke7nbFaioypzbDOQykoRg==" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <title>Job List</title>
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
                    <div class="col-lg-9 register-right">
                        <form action="ProcessCreateJob" method="POST" style="text-align:center; padding-top:10px;">
                            <c:if test="${showTeamList == false}">  
                                <input  type="submit" name="emergencyJobBtn" class="btn btn-warning" value="Emergency Booking"/>
                                <input type="submit" name="normalJobBtn" class="btn btn-success" value="Normal Booking"/>
                            </c:if>
                        </form>
                        <h3 class="register-heading">Create a Job</h3>
                        <form class="row register-form" action="ProcessCreateJob" method="POST">
                            <div class="panel-body">
                                <c:if test="${isEmergencyBooking == false}">    
                                    <p class="text-info"><b>Normal Booking is Selected</b></p>
                                </c:if>

                                <c:if test="${isEmergencyBooking == true}">    
                                    <p class="text-warning"><b>Emergency Booking is Selected</b></p>
                                </c:if>
                                <c:if test="${showTeamList == false}">
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="form-group">
                                                <label class="control-label">Client Name</label>
                                                <input type="text" class="form-control" name="clientName" id="clientname" required>
                                            </div>
                                            <textarea name="jobDescription" rows="4" cols="50" placeholder="Job Description*" required></textarea>
                                        </div>
                                    </div>
                                    <br><hr> 
                                    <div class="row">
                                        <div class="col-12">
                                            <label class="control-label">Tasks</label><br>
                                            <c:forEach items="${universalTasks}" var="task">
                                                <label class="text-info" for="${task.taskID}"><c:out value="${task.name}"/></label>
                                                <input type="checkbox" name="tasksSelection" id="${task.taskID}" value="${task.taskID}">
                                                <input style="margin-left:20px;" for="${task.taskID}" type="number" min="1" max="10" onkeydown="return false" name="${task.taskID}" value="1"/><br>
                                            </c:forEach> 
                                        </div>
                                    </div>
                                    <br><hr>    
                                    <div class="row">
                                        <c:if test="${isEmergencyBooking == false}">
                                            <div class="col-md-6">
                                                <label class="control-label" >Time:(9:00 AM - 17:00 PM)</label>
                                                <input type="time" id="timePicker" min="09:00" max="17:00"  class="without_ampm" name="jobStartTime"
                                                       required/>
                                            </div>
                                        </c:if>

                                        <c:if test="${isEmergencyBooking == true}"> 
                                            <div class="col-md-6">
                                                <label class="control-label" >Time:(Emergency Times)</label>
                                                <input type="time" id="timePicker" class="without_ampm" name="jobStartTime"
                                                       required/>
                                            </div>
                                        </c:if>
                                        <div class="col-md-6">
                                            <label class="control-label" >Date:</label><br>
                                            <input type="date" id="datepicker" name="jobStartDate"
                                                   required>
                                        </div>
                                        <hr>
                                        <c:if test="${errorList != null && errorList.size() > 0}">  
                                            <ul>
                                                <c:forEach items="${errorList}" var="error">    
                                                    <li class="text-danger"><c:out value="${error}"/></li><br>
                                                </c:forEach>
                                            </ul>
                                        </c:if>
                                    </div>
                                    <br>
                                    <input style="float:right;" type="submit" name="nextBtn" class="btn btn-success" value="Next"/>
                                </c:if >
                                <c:if test="${showTeamList == true}">  
                                    <div class="row">
                                        <div class="col-md-12">
                                            <c:if test="${eligibleTeams.isEmpty()}">
                                                <p><b>No Available Teams Found</b></p>
                                            </c:if>

                                            <c:forEach items="${eligibleTeams}" var="team">  
                                                <label class="text-info" for="${team.teamID}"><c:out value="${team.name}"/></label>
                                                <input type="radio" name="teamSelection" id="${team.teamID}" value="${team.teamID}" required>
                                            </c:forEach>
                                            <br><br>
                                            <input style="float:right;" type="submit" name="createJobBtn" class="btn btn-success" value="Register Job"/>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <hr>
        <form action="ProcessCreateJob" method="POST" style="text-align:center;">
            <c:if test="${isShowingJobs == true}">  
                <input type="submit" name="showScheduleBtn" class="btn btn-info" value="Show Schedule"/>
            </c:if>

            <c:if test="${isShowingSchedule == true}">  
                <input  type="submit" name="showJobsBtn" class="btn btn-info" value="Show Jobs"/>
                <input  type="submit" name="prevDayScheduleBtn" class="btn btn-danger" value="Previous day"/>
                <input type="submit" name="nextDayScheduleBtn" class="btn btn-success" value="Next day"/>
                <input type="submit" name="allDayScheduleBtn" class="btn btn-success" value="24H Schedule"/>
                <input type="submit" name="busisnessHrsSchduleBtn" class="btn btn-success" value="Business Hours Schedule"/>
            </c:if>

        </form>
        <br>
        <div class="col-lg-12">
            <c:if test="${isShowingSchedule == true}">
                <h1>Job Schedule For : <c:out value="${currentDate}"/></h1>
                <div class="col-lg-3">
                    <div class="card h-90" style="background-color: #cc562f; text-align:center;">
                        <h3 class="text-light">Teams List</h3>
                        <div class="alert alert-danger" role="alert">
                            <c:forEach items="${universalTeamList}" var="team">  
                                <a href="<%=request.getContextPath()%>/ShowTeamInformation?teamId=<c:out value="${team.teamID}"/>">
                                    <h4 class="text-primary"><c:out value="${team.name}"/></h4></a>
                                </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <c:forEach items="${jobCalendarMap}" var="kvp">
                        <div class="col-lg-3">
                            <div class="card h-90" style="background-color: #2092A1; margin-bottom: 50px;">
                                <a href="#"><img class="card-img-top" src="https://www.flaticon.com/svg/static/icons/svg/850/850960.svg" alt="" style="width:20%;height:20%;" ></a>
                                <h4 class="text-danger"><c:out value="${kvp.key.toString()}"/></h4>
                                <div class="alert alert-info" role="alert">
                                    <c:if test="${!kvp.value.isEmpty()}">  
                                        <c:forEach items="${kvp.value}" var="job">
                                            <label>Team: </label>
                                            <a href="<%=request.getContextPath()%>/ShowTeamInformation?teamId=<c:out value="${job.assignedTeam.teamID}"/>">
                                                <p class="text-primary"><c:out value="${job.assignedTeam.name}"/></p></a>
                                            <label>Client: </label>
                                            <a href="<%=request.getContextPath()%>/ProcessJobInformation?jobId=<c:out value="${job.jobID}"/>">
                                                <p class="text-warning"><c:out value="${job.clientName}"/></p></a>
                                            <hr>

                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${kvp.value.isEmpty()}">  
                                        <p>No Job Showing</p>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>   
                </div>
            </c:if>
            <c:if test="${isShowingJobs == true}">  
                <h1>Jobs List</h1>
                <div class="row">
                    <c:forEach items="${universalJobsList}" var="job">
                        <div class="col-lg-3">
                            <div class="card h-90" style="background-color: #2092A1; margin-bottom: 50px;">
                                <a href="#"><img class="card-img-top" src="https://www.flaticon.com/premium-icon/icons/svg/2936/2936736.svg" alt="" style="width:20%;height:20%;" ></a>
                                <a href="<%=request.getContextPath()%>/ProcessJobInformation?jobId=<c:out value="${job.jobID}"/>">
                                    <h4 class="text-warning"><c:out value="${job.clientName}"/></h4></a>
                                <div class="alert alert-info" role="alert">
                                    <p>Job Date: <b class="text-danger"><c:out value="${job.startDate}"/></b></p>
                                    <label>Team</label>
                                    <a href="<%=request.getContextPath()%>/ShowTeamInformation?teamId=<c:out value="${job.assignedTeam.teamID}"/>">
                                        <p class="text-danger"><c:out value="${job.assignedTeam.name}"/></p></a>
                                        <c:if test="${job.isEmeregencyJob()}">
                                        <p class="text-warning">Emergency Job</p>
                                    </c:if>

                                    <c:if test="${!job.isEmeregencyJob()}">
                                        <p>Casual Job</p>
                                    </c:if>
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
    <%@include file="WEB-INF/StyleSheets/ATS_Entities_Creation.css"%>
    .without_ampm::-webkit-datetime-edit-ampm-field {
        display: none;
    }
    input[type=time]::-webkit-clear-button {
        -webkit-appearance: none;
        -moz-appearance: none;
        -o-appearance: none;
        -ms-appearance:none;
        appearance: none;
        margin: -10px; 
    }
</style>