<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<ul id="log-nav">
	<li id="firsttab" class="active">
		<a href="${context}/singup"><strong>SingUp</strong></a>
	<li id="secondtab">
		<a href="${context}/login"><strong>Welcome</strong></a>
	<li id="thirdtab">
		<a href="${context}/password-recovery"><strong>Password recovery</strong></a>
</ul>

<h3 align=center><strong>Creating new user</strong></h3>

<c:if test="${not empty errorMessage}">
	<h4 id="error-message" align="center">${errorMessage }</h4>
</c:if>

<form:form id="singup-form" class="form-horizontal" method="POST" action="${context }/singup" commandName="singUpForm" >
	<div class="form-group">
		<form:label for="mail" path="email" class="col-md-offset-2 col-md-2 control-label">Email:</form:label>
		<div class="col-md-4">
			<form:input id="mail" class="form-control" path="email"/>
		</div>
	</div>
	<div class="form-group">
		<form:label for="pass" path="password" class="col-md-offset-2 col-md-2 control-label">Password:</form:label>
		<div class="col-md-4">
			<form:input id="pass" class="form-control" path="password"/>
		</div>
	</div>
	<div class="form-group">
		<form:label for="pass2" path="password2" class="col-md-offset-2 col-md-2 control-label">Confirm password:</form:label>
		<div class="col-md-4">
			<form:input id="pass2" class="form-control" path="password2"/>
		</div>
	</div>
	<div class="form-group">
		<form:label for="log" path="login" class="col-md-offset-2 col-md-2 control-label">Login:</form:label>
		<div class="col-md-4">
			<form:input id="log" class="form-control" path="login"/>
		</div>
	</div>
	<div class="form-group">
		<form:label for="name" path="fio" class="col-md-offset-2 col-md-2 control-label" >Your name:</form:label>
		<div class="col-md-4">
			<form:input id="name" class="form-control" path="fio"/>
		</div>
	</div>
	<div class="form-group">
		<div class="col-md-offset-4 col-md-4">
			<button type="submit" class="btn btn-warning btn-block">Create user</button>
		</div>
	</div>
</form:form>	