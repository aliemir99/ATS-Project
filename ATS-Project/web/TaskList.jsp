<%-- 
    Document   : CreateTask
    Created on : 8-Nov-2020, 1:33:43 PM
    Author     : aliem
    Purpose: this Jsp page is for Showing Task List and Create Task Form
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="errors.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="ATStags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <title>Create Task</title>
    </head>
    <body>

        <%@include file="WEB-INF/jspf/Header.jspf"%>  
        <div class="container">
            <div class="col-lg-6">
            </div>
            <div class="container register">
                <div class="row">
                    <div class="col-md-3 register-left">
                        <img src="https://cdnb.artstation.com/p/assets/images/images/015/133/897/large/celia-calderon-ats-2.jpg?1547174170" alt=""/>
                        <h3>Welcome to ATS Admin Dashboard</h3>
                    </div>
                    <div class="col-md-9 register-right">
                        <div class="tab-content" id="myTabContent">
                            <div class=" tab-pane fade show active" id="task" role="tabpanel" aria-labelledby="task-tab">
                                <form action="ProcessCreateTask" method="POST">
                                    <fieldset>
                                        <div id="legend">
                                            <legend class="">Create Task</legend>
                                        </div>
                                        <input type="text" id="username" name="taskName" placeholder="Task Name*" class="input-xlarge" required>
                                        <br><br>                                
                                        <textarea name="taskDescription" rows="4" cols="50" placeholder="Task Description*" required></textarea>
                                        <br><br>
                                        <label>Task Duration in <b>Minutes</b></label>
                                        <div class="d-flex justify-content-center my-4">
                                            <div class="w-75">
                                                <input type="range" class="custom-range" id="customRange11" name="taskDuration" step="30" min="30" max="120">
                                            </div>
                                            <span class="font-weight-bold text-primary ml-2 valueSpan2"></span>
                                        </div>
                                        <br>
                                        <button class="btn btn-success" name="submitBtn">Register</button>
                                    </fieldset>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <hr>
        <h1>Tasks</h1>
        <div class="col-lg-12">
            <div class="row">
                <c:forEach items="${tasks}" var="currTask">   
                    <div class="col-lg-3">
                        <div class="card h-90" style="background-color: #093857; margin-bottom: 50px;">
                            <a href="#"><img class="card-img-top" src="https://www.flaticon.com/svg/static/icons/svg/762/762686.svg" alt="" style="width:20%;height:20%;" ></a>
                            <div class="card-body">
                                <h4 class="card-title">
                                    <a href="<%=request.getContextPath()%>/ProcessTaskInformation?taskID=<c:out value="${currTask.taskID}"/>">
                                        <p class="text-warning"><c:out value="${currTask.name}"/></p></a>
                                    <hr>
                                </h4>
                                <div class="alert alert-danger" role="alert">
                                    <c:out value="${currTask.description}"/>
                                </div>
                                
                            </div>
                        </div>
                    </div>           
                </c:forEach>              
            </div>
        </div>
    </body>

    <%@include file="WEB-INF/jspf/footer.jspf"%>  

</html>

<style>
    <%@include file="WEB-INF/StyleSheets/ATS_Entities_Creation.css"%>
    .tab-content{
        margin-top: 70px;
        margin-bottom: 70px;
        text-align:center;
    }
</style>

<script>
    $(document).ready(function () {

        const $valueSpan = $('.valueSpan2');
        const $value = $('#customRange11');
        $valueSpan.html($value.val());
        $value.on('input change', () => {

            $valueSpan.html($value.val());
        });
    });

</script>