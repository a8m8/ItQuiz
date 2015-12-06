<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl }" method="POST">
    <button type="submit" id="logout" class="btn btn-success">Log Out</button>
    <sec:csrfInput/>
</form>

<div id="content">
    <ul id="main-nav" class="admin">
        <li id="firsttab">
            <a href="${context }/admin/myaccount">My account</a>
        <li id="secondtab">
            <a href="${context }/admin/accounts/page/1">All accounts</a>
        <li id="thirdtab">
            <a href="${context }/admin/add-user">Add user</a>
    </ul>

    <h3>Edit User</h3>

    <c:if test="${not empty errorMessage}">
        <h4 id="error-message">${errorMessage }</h4>
    </c:if>

    <input type="hidden" name="id" value="${user.idAccount }"/>

    <form:form id="admin-edit-user-form" class="form-horizontal" method="POST" action="${context }/admin/edit-user"
               commandName="adminUserForm">
        <div class="form-group">
            <form:label for="mail" path="email" class="col-md-offset-2 col-md-2 control-label">Email:</form:label>
            <div class="col-md-4">
                <form:input type="text" id="mail" class="form-control" path="email" value="${user.email}"
                            readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <form:label for="pass" path="password" class="col-md-offset-2 col-md-2 control-label">Password:</form:label>
            <div class="col-md-4">
                <form:input id="pass" class="form-control" path="password" value="${user.password }"/>
            </div>
        </div>
        <div class="form-group">
            <form:label for="pass2" path="password2"
                        class="col-md-offset-2 col-md-2 control-label">Confirm password:</form:label>
            <div class="col-md-4">
                <form:input id="pass2" class="form-control" path="password2" value="${user.password }"/>
            </div>
        </div>
        <div class="form-group">
            <form:label for="log" path="login" class="col-md-offset-2 col-md-2 control-label">Login:</form:label>
            <div class="col-md-4">
                <form:input id="log" class="form-control" path="login" value="${user.login }"/>
            </div>
        </div>
        <div class="form-group">
            <form:label for="name" path="fio" class="col-md-offset-2 col-md-2 control-label">Name:</form:label>
            <div class="col-md-4">
                <form:input id="name" class="form-control" path="fio" value="${user.fio }"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-4 col-md-4">
                <div class="col-md-6">
                    <label id="checkbox-label" class="checkbox-inline">
                        <form:checkbox path="active"/>Active
                    </label>
                </div>
                <div class="col-md-6">
                    <label id="checkbox-label" class="checkbox-inline">
                        <form:checkbox path="confirmed"/>Confirmed
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-4 col-md-4">
                <h4>Roles</h4>

                <div class="col-md-6">
                    <label id="checkbox-label" class="checkbox-inline">
                        <form:checkbox path="administrator"/>Administrator
                    </label>
                </div>
                <div class="col-md-6">
                    <label id="checkbox-label" class="checkbox-inline">
                        <form:checkbox path="advancedTutor"/>Advanced Tutor
                    </label>
                </div>
                <div class="col-md-6">
                    <label id="checkbox-label" class="checkbox-inline">
                        <form:checkbox path="tutor"/>Tutor
                    </label>
                </div>
                <div class="col-md-6">
                    <label id="checkbox-label" class="checkbox-inline">
                        <form:checkbox path="student"/>Student
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-4 col-md-4">
                <button type="submit" class="btn btn-warning btn-block">Save</button>
            </div>
        </div>
    </form:form>
</div>