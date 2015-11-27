<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<ul id="log-nav">
	<li id="firsttab">
		<a href="${context}/singup"><strong>SingUp</strong></a>
	<li id="secondtab">
		<a href="${context}/login"><strong>Welcome</strong></a>
	<li id="thirdtab" class="active">
		<a href="${context}/password-recovery"><strong>Password recovery</strong></a>
</ul>
	
<div id="password-recovery-box">

	<h3 align=center><strong>Please, enter your login</strong></h3>
	
	<form:form class="form-horizontal" method="POST" action="${context }/password-recovery" commandName="passwordRecoveryForm" >
	<p class="errors" align=center><strong><form:errors path="*"/></strong></p>
		<div class="form-group">
			<form:label for="log" path="login" class="col-md-offset-2 col-md-2 control-label">Login:</form:label>
			<div class="col-md-4">
				<form:input id="log" class="form-control" path="login" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-offset-4 col-md-4">
				<button type="submit" class="btn btn-warning btn-block">Confirm</button>
			</div>
		</div>
	</form:form>
</div>