<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<jsp:include page="../templates/admin/admin-nav.jsp"/>

<h3>Edit User</h3>

<jsp:include page="../templates/message.jsp"/>
<jsp:include page="../templates/error-message.jsp"/>

<form:form id="admin-edit-user-form" class="form-horizontal" method="POST"
           action="${context }/admin/accounts/edit-account"
           commandName="adminUserForm">
    <input type="hidden" name="id" value="${idAccount }"/>

    <div class="form-group">
        <form:label path="email" class="col-md-offset-2 col-md-2 control-label">Email:</form:label>
        <div class="col-md-4">
            <form:input type="text" class="form-control" path="email" readonly="true"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="login" class="col-md-offset-2 col-md-2 control-label">Login:</form:label>
        <div class="col-md-4">
            <form:input class="form-control" path="login"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="fio" class="col-md-offset-2 col-md-2 control-label">Name:</form:label>
        <div class="col-md-4">
            <form:input class="form-control" path="fio"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-offset-4 col-md-4">
            <a href="${context}/admin/accounts/edit-account/change-password?id=${idAccount }">Change password</a>
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
            <button type="submit" class="btn-func">Save</button>
        </div>
    </div>
</form:form>
