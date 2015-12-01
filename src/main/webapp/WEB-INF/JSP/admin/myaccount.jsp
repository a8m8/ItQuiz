<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<ul id="admin-nav">
	<li id="firsttab" class="active">	
		<a href="${context }/admin/myaccount"><strong>My account</strong></a>
	<li id="secondtab">
		<a href="${context }/admin/all-accounts"><strong>All accounts</strong></a>
	<li id="thirdtab">
		<a href="${context }/admin/add-user"><strong>Add user</strong></a>
	<li id="fourthtab">
		<a href="${context }/logout"><strong>Exit</strong></a>
</ul>
	
<h3 align="center"><strong>My Account</strong></h3>

<c:if test="${not empty message}">
	<h4 id="message" align="center">${message }</h4>
</c:if>

<c:if test="${not empty errorMessage}">
	<h4 id="error-message" align="center">${errorMessage }</h4>
</c:if>


<form:form id="myaccount-form" class="form-horizontal" method="POST" action="${context}/admin/myaccount" commandName="personalInfoForm">
	<div class="form-group">
			<form:label for="mail" path="email" class="col-md-offset-2 col-md-2 control-label">Email:</form:label>
			<div class="col-md-4">
				<form:input type="text" id="mail" class="form-control" path="email" value="${account.email}" readonly="true"/>
			</div>
	</div>
	<div class="form-group">
		<form:label for="pass" path="password" class="col-md-offset-2 col-md-2 control-label">Password:</form:label>
		<div class="col-md-4">
			<form:input id="pass" class="form-control" path="password" value="${account.password }"/>
		</div>
	</div>
	<div class="form-group">
		<form:label for="pass2" path="password2" class="col-md-offset-2 col-md-2 control-label">Confirm password:</form:label>
		<div class="col-md-4">
			<form:input id="pass2" class="form-control" path="password2" value="${account.password }"/>
		</div>
	</div>
	<div class="form-group">
		<form:label for="log" path="login" class="col-md-offset-2 col-md-2 control-label">Login:</form:label>
		<div class="col-md-4">
			<form:input id="log" class="form-control" path="login" value="${account.login }"/>
		</div>
	</div>
	<div class="form-group">
		<form:label for="name" path="fio" class="col-md-offset-2 col-md-2 control-label" >Your name:</form:label>
		<div class="col-md-4">
			<form:input id="name" class="form-control" path="fio" value="${account.fio }"/>
		</div>
	</div>
	<div class="form-group">
		<div class="col-md-offset-4 col-md-4">
			<button type="submit" class="btn btn-warning btn-block">Save</button>
		</div>
	</div>
</form:form>