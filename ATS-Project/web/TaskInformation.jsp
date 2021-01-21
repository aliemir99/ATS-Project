<%-- 
    Document   : TaskInformation
    Created on : 14-Nov-2020, 10:58:55 AM
    Author     : aliem
    Purpose: this Jsp page is for Showing Individual Task Information
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="errors.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="ATStags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <title>Task Info</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/Header.jspf"%>  
        <div class="container">
            <div class="main-body">
                <div class="row gutters-sm">
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex flex-column align-items-center text-center">
                                    <img class="card-img-top" src="https://www.flaticon.com/svg/static/icons/svg/762/762686.svg" alt="" style="width:50%;height:50%;"  alt="" >
                                    <div class="mt-3">
                                        <h4><jsp:getProperty name="task" property="name"/></h4>
                                        <p>Task Duration: <b><jsp:getProperty name="task" property="duration"/></b></p>
                                        <p>Updated: <b><jsp:getProperty name="task" property="dateCreated"/></b></p>
                                        <br>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-9">
                        <div class="card mb-9">
                            <div class="card-body">
                                <div class="row">
                                    <div class="card-body">
                                        <div class="row">
                                            <legend class="">Task Information</legend>
                                            <div class="col-6">
                                            <form action="ProcessTaskInformation" method="POST">
                                                <c:forEach items="${errorMessage}" var="err">
                                                    <p class="text-danger">${err}</p>
                                                </c:forEach>
                                                <ul class="list-group">
                                                    <li class="list-group-item active">Skill Holders</li>
                                                        <c:forEach items="${task.skillHolders}" var="skillHolder">                                                    
                                                        <li class="list-group-item">
                                                            <a href="<%=request.getContextPath()%>/ProcessEmployeeInformation?employeeID=<c:out value="${skillHolder.employeeID}"/>">
                                                                <h5 class="text-dark"><c:out value="${skillHolder.fullName}"/></h5></a></li>
                                                        </c:forEach> 
                                                </ul>
                                            </div>
                                            <div class="col-6">
                                                <label class="control-label"  for="fName"><b>Name:</b></label>
                                                <input type="text" id="username" name="taskName" value="<c:out value="${task.name}"/>" class="input-xlarge" required>

                                                <br><br>                                
                                                <textarea name="taskDescription" rows="4" cols="50" placeholder="Task Description*" required><c:out value="${task.description}"/></textarea>
                                            </div>
                                        </div>   
                                        <hr>
                                        <fieldset>
                                            <br><br>
                                            <label>Task Duration in <b>Minutes</b></label>
                                            <div class="d-flex justify-content-center my-4">
                                                <div class="w-75">
                                                    <input type="range" class="custom-range" id="customRange11" value="${task.duration}" name="taskDuration" step="30" min="30" max="120">
                                                </div>
                                                <span class="font-weight-bold text-primary ml-2 valueSpan2"></span>
                                            </div>
                                            <br>
                                            <input type="submit" class="btn btn-warning" name="updateTaskBtn" value="UPDATE"/>
                                            <input type="submit" class="btn btn-danger" name="deleteTaskBtn" value="DELETE"/>
                                        </fieldset>
                                        </form>    
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
