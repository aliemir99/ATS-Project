<%-- 
    Document   : home
    Created on : Nov 5, 2020, 2:23:50 AM
    Author     : jber5
    Purpose: this Jsp page is for Showing home page (Dashboard) Information
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="errors.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="ATStags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link rel="stylesheet" href="WEB-INF/StyleSheets/ATS_Entities_Creation.css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <<title>Dahsboard</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/Header.jspf"%>  
        <div id="wrapper">
            <br>

            <div class="col-sm-9">
                <div class="well">
                    <h1>Dashboard</h1>
                    <h4>Welcome to ATS Admin Dashboard</h4>
                </div>
                <div id="firstRow">
                    <div class="row">
                        <div class="col-6">
                            <div class="card border-left-danger shadow h-100 py-2">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col mr-2">
                                            <a href="#"><img class="card-img-top" src="https://www.flaticon.com/premium-icon/icons/svg/3101/3101839.svg" alt="" style="width:20%;height:20%;" ></a>
                                            <div class=" font-weight-bold text-danger">
                                                Team On Call</div><hr>

                                            <a href="<%=request.getContextPath()%>/ShowTeamInformation?teamId=<c:out value="${dashModel.currentOnCallTeam.teamID}"/>">
                                                <div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value="${dashModel.currentOnCallTeam.name}"/> </div></a><br>

                                            <c:forEach items="${dashModel.currentOnCallTeam.teamMembers}" var="member"> 
                                                <div class="h5 mb-0 font-weight-bold text-info"><c:out value="${member.fullName}"/></div>
                                            </c:forEach>                                        
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="card border-left-warning shadow h-100 py-2">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col mr-2">
                                            <a href="#"><img class="card-img-top" src="https://www.flaticon.com/premium-icon/icons/svg/562/562674.svg" alt="" style="width:20%;height:20%;" ></a>
                                            <div class="text-lg font-weight-bold text-warning text-uppercase mb-1">
                                                Booked Jobs</div><hr>
                                            <div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value="${dashModel.currentDayJobCount}"/></div>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-book-open fa-2x text-gray-300"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-3">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-lg font-weight-bold text-primary text-uppercase mb-1">
                                            Cost (Monthly)</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value="${dashModel.currentMonthJobCost}"/></div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-funnel-dollar fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-3">
                        <div class="card border-left-success shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-lg font-weight-bold text-success text-uppercase mb-1">
                                            Revenue (Monthly)</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value="${dashModel.currentMonthJobRevenue}"/></div>

                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-file-invoice-dollar fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-3">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-lg font-weight-bold text-primary text-uppercase mb-1">
                                            Cost (Annual)</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value="${dashModel.annualJobCost}"/></div>
                                        <hr>
                                        <p><c:out value="${dashModel.yearToShow.getYear()}"/></p>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-funnel-dollar fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-3">
                        <div class="card border-left-success shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-lg font-weight-bold text-success text-uppercase mb-1">
                                            Revenue (Annual)</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value="${dashModel.annualJobRevenue}"/></div>
                                        <hr>
                                        <p><c:out value="${dashModel.yearToShow.getYear()}"/></p>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-file-invoice-dollar fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div><br>
                <div class="row">
                    <div class="col-12">
                        <form action="ProcessDashboardInformation" method="POST">
                            <input type="submit" class="btn btn-dark" name="lastYearReportBtn" value="Last Year Report"/> 
                            <input type="submit" class="btn btn-success" name="currentYearReportBtn" value="Current Year Report"/> 
                        </form>
                        <br>
                        <table class="table table-dark table-bordered">
                            <thead>
                                <tr>
                                    <th>Month</th>
                                    <th>Revenue</th>
                                    <th>Cost</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${dashModel.financialReport}" var="reportKvp">
                                    <tr>
                                        <td class="bg-light text-danger"><c:out value="${reportKvp.key}"/></td>
                                        <td class="bg-info">$<c:out value="${reportKvp.value[0]}"/></td>
                                        <td class="bg-primary">$<c:out value="${reportKvp.value[1]}"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <br><br>
            </body>
            <%@include file="WEB-INF/jspf/footer.jspf"%>  
            </html>


            <style>
                /* On small screens, set height to 'auto' for the grid */
                @media screen and (max-width: 767px) {
                    .row.content {
                        height: auto;
                    }
                }

                .col-sm-9 {
                    color: black;
                    text-align: center;
                    margin-left: auto;
                    margin-right: auto;
                    width: 100%;
                    font-size: 20px;
                }

                #wrapper {
                    margin-top: 10px;
                    margin-left: auto;
                    margin-right: auto;
                    width: 80%;
                }
                table{
                    width: 90%;

                }
            </style>
