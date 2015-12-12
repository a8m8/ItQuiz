<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="templates/login-nav.jsp"/>

<h3>Please login</h3>

<jsp:include page="templates/message.jsp"/>

<c:if test="${param.logout != null}">
    <h4 id="message">You have been logged out successfully.</h4>
</c:if>

<c:if test="${param.error != null}">
    <h4 id="error-message">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message }</h4>
</c:if>

<c:url var="loginUrl" value="/login"/>
<form id="login-form" class="form-horizontal" action="${loginUrl }" method="POST">
    <div class="form-group">
        <label for="name" class="col-md-offset-2 col-md-2 control-label">Email:</label>

        <div class="col-md-4">
            <input id="name" type="text" class="form-control" name="username"/>
        </div>
    </div>
    <div class="form-group">
        <label for="pass" class="col-md-offset-2 col-md-2 control-label">Password:</label>

        <div class="col-md-4">
            <input type="password" id="pass" class="form-control" name="password"/>
        </div>
    </div>
    <div class="form-group">
        <label for="rol" class="col-md-offset-2 col-md-2 control-label">Role:</label>

        <div class="col-md-4">
            <select name="idRole" class="form-control" id="rol">
                <option value="0" label="--- Select ---"/>
                <c:forEach var="role" items="${roles }">
                    <option value="${role.idRole }">${role.title }</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-offset-4 col-md-4">
            <label id="checkbox-label" class="checkbox-inline">
                <input type="checkbox" id="remember-me" name="remember-me"/>Remember me
            </label>
        </div>
    </div>
    <sec:csrfInput/>
    <div class="form-group">
        <div class="col-md-offset-4 col-md-4">
            <button type="submit" value="Login" class="btn btn-warning btn-block">Login</button>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-offset-4 col-md-4" id="fblink">
            <a href="${context }/fbLogin">
                <img alt="fbLogin" src="${context }/resources/images/login-facebook.png"/>
            </a>
        </div>
    </div>
</form>