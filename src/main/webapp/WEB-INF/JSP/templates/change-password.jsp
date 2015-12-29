<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<h3>New password</h3>

<jsp:include page="../templates/error-message.jsp"/>

<form:form id="password-change-form" class="form-horizontal" method="POST"
           action="${context}/${role}/${pageName}/change-password" commandName="passwordForm">
    <input type="hidden" name="id" value="${idAccount}">
    <c:if test="${pageName ne 'accounts/edit-account'}">
        <div class="form-group">
            <form:label path="oldPassword" class="col-md-offset-2 col-md-2 control-label">Old password:</form:label>
            <div class="col-md-4">
                <form:input type="password" class="form-control" path="oldPassword"/>
            </div>
        </div>
    </c:if>
    <div class="form-group">
        <form:label path="password" class="col-md-offset-2 col-md-2 control-label">Password
            :</form:label>
        <div class="col-md-4">
            <form:input type="password" class="form-control" path="password"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="passwordConfirmed" class="col-md-offset-2 col-md-2 control-label">Confirm
            password:</form:label>
        <div class="col-md-4">
            <form:input type="password" class="form-control" path="passwordConfirmed"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-offset-4 col-md-4">
            <button type="submit" class="btn-func">Save</button>
        </div>
    </div>
</form:form>
