<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<ul id="log-nav">
	<li id="firsttab">
		<a href="${context}/singup"><strong>SingUp</strong></a>
	<li id="secondtab" class="active">
		<a href="${context}/login"><strong>Welcome</strong></a>
	<li id="thirdtab">
		<a href="${context}/password-recovery"><strong>Password recovery</strong></a>
</ul>

<h3 align=center><strong>Please login</strong></h3>

<c:if test="${not empty message}">
	<h4 id="message" align="center">${message }</h4>
</c:if>

<c:if test="${not empty errorMessage}">
	<h4 id="error-message" align="center">${errorMessage }</h4>
</c:if>

<form:form id="login-form" class="form-horizontal" action="${context }/login" method="POST" commandName="loginForm" >
	<div class="form-group">
		<form:label for="mail" path="email" class="col-md-offset-2 col-md-2 control-label">Email:</form:label>
		<div class="col-md-4">
			<form:input id="mail" class="form-control" path="email" />
		</div>
	</div>
	<div class="form-group">
		<form:label for="pass" path="password" class="col-md-offset-2 col-md-2 control-label">Password:</form:label>
		<div class="col-md-4">
			<form:input type="password" id="pass" class="form-control" path="password"/>
		</div>
	</div>
	<div class="form-group">
		<form:label for="rol" path="idRole" class="col-md-offset-2 col-md-2 control-label">Role:</form:label>
		<div class="col-md-4">
			<form:select path="idRole" class="form-control" id="rol">
				<form:option value="0" label="--- Select ---"/>
				<form:options items="${roles}" itemLabel="title" itemValue="idRole" />
			</form:select>
		</div>
	</div>
	<div class="form-group">
		<div class="col-md-offset-4 col-md-4">
			<button type="submit" class="btn btn-warning btn-block">Login</button>
		</div>
	</div>
	<div class="form-group">
		<div class="col-md-offset-4 col-md-4" id="fblink">
			<a href="${context }/fbLogin">
				<img alt="fbLogin" src="${context }/resources/images/login-facebook.png"/>
			</a>
		</div>
	</div>
</form:form>
	