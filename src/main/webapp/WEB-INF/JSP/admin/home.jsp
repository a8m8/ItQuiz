<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<ul id="admin-nav">
		<li id="firsttab">	
			<a href="${context}/profile"><strong>Profile</strong></a>
		<li id="secondtab" class="active">
			<a href="${context}/all-accounts"><strong>All accounts</strong></a>
		<li id="thirdtab">
			<a href="${context}/add-user"><strong>Add user</strong></a>
		<li id="fourthtab">
			<a href="${context}/login"><strong>Exit</strong></a>
	</ul>

<h3 align=center><strong>Accounts management</strong></h3>

<div id="table">
	<table class="table table-striped">
		<thead><tr>
			<td width="15%">Login</td>
			<td width="20%">Email</td>
			<td width="10%">Roles</td>
			<td width="10%">Created</td>
			<td width="10%">Updated</td>
			<td width="10%">Status</td>
			<td width="10%">Verified</td>
			<td width="7.5%"></td>
			<td width="7.5%"></td>			
			
		</tr></thead>
		<c:forEach items="${accounts}" var="accounts">
		<tr>
			<td class="overflowable">${accounts.login}</td>
			<td class="overflowable"><a href="mailto:${accounts.email}">${accounts.email}</a></td>
			<td><c:forEach items="${accounts.accountRoles }" var="accountRoles">${accountRoles.role.idRole }</c:forEach></td>
			<td><fmt:formatDate value="${accounts.created }" pattern="dd-MM-yyyy"/></td>
			<td><fmt:formatDate value="${accounts.updated }" pattern="dd-MM-yyyy"/></td>
			<td>${accounts.active ? "Activated" : "Deactivated" }</td>
			<td>${accounts.confirmed ? "Yes" : "No" }</td>
			<td><a href="<c:url value="/admin/edit-account?id=${accounts.idAccount }"/>">Edit</a></td>
			<td><a href="<c:url value="/admin/delete-account?id=${accounts.idAccount }"/>">Delete</a></td>
		</tr>
		</c:forEach>
	</table>
</div>
