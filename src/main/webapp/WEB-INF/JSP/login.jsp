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

	<div id="login-box">
	
		<h3 align=center><strong>Please login</strong></h3>
		
		<form:form class="form-horizontal" method="POST" action="${context }/login" commandName="loginForm" >
			<p class="errors" align=center><strong><form:errors path="*"/></strong></p>
			<div class="form-group">
				<form:label for="mail" path="email" class="col-md-offset-2 col-md-2 control-label">Email:</form:label>
				<div class="col-md-4">
					<form:input type="email" id="mail" class="form-control" path="email" />
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
		</form:form>
	</div>
