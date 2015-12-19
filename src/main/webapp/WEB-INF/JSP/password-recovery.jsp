<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<jsp:include page="templates/login-nav.jsp"/>

<h3>Please, enter your email</h3>

<jsp:include page="templates/error-message.jsp"/>

<form:form id="password-recovery-form" class="form-horizontal" method="POST" action="${context }/password-recovery"
           commandName="passwordRecoveryForm">
    <div class="form-group">
        <form:label path="email" class="col-md-offset-2 col-md-2 control-label">Email:</form:label>
        <div class="col-md-4">
            <form:input class="form-control" path="email"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-offset-4 col-md-4">
            <button type="submit" class="btn-func">Confirm</button>
        </div>
    </div>
</form:form>
