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
        <li id="firsttab" class="active">
            <a href="${context }/admin/myaccount">My account</a>
        <li id="secondtab">
            <a href="${context }/admin/accounts/page/1">All accounts</a>
        <li id="thirdtab">
            <a href="${context }/admin/add-user">Add user</a>
    </ul>

    <h3>My Account</h3>

    <c:if test="${not empty message}">
        <h4 id="message">${message }</h4>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <h4 id="error-message">${errorMessage }</h4>
    </c:if>


    <form:form id="myaccount-form" class="form-horizontal" method="POST" action="${context}/admin/myaccount"
               commandName="personalInfoForm">
        <div class="form-group">
            <form:label path="email" class="col-md-offset-2 col-md-2 control-label">Email:</form:label>
            <div class="col-md-4">
                <form:input type="text" class="form-control" path="email" value="${account.email}" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="login" class="col-md-offset-2 col-md-2 control-label">Login:</form:label>
            <div class="col-md-4">
                <form:input class="form-control" path="login" value="${account.login }"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="fio" class="col-md-offset-2 col-md-2 control-label">Your name:</form:label>
            <div class="col-md-4">
                <form:input class="form-control" path="fio" value="${account.fio }"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-4 col-md-4">
                <a href="${context}/admin/myaccount/change-password">Change password</a>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-4 col-md-4">
                <button type="submit" class="btn btn-warning btn-block">Save</button>
            </div>
        </div>
    </form:form>
</div>