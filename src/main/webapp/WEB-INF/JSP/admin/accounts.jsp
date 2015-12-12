<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<ul id="main-nav" class="admin">
    <li id="firsttab">
        <a href="${context }/admin/myaccount">My account</a>
    <li id="secondtab" class="active">
        <a href="${context }/admin/accounts/page/1">All accounts</a>
    <li id="thirdtab">
        <a href="${context }/admin/add-user">Add user</a>
</ul>

<h3>Accounts management</h3>
<c:if test="${not empty message}">
    <h4 id="message">${message }</h4>
</c:if>
<c:if test="${not empty errorMessage}">
    <h4 id="error-message">${errorMessage }</h4>
</c:if>

<div id="table-box">
    <table class="table table-striped">
        <thead>
        <tr>
            <td width="15%">Login</td>
            <td width="20%">Email</td>
            <td width="10%">Roles</td>
            <td width="10%">Created</td>
            <td width="10%">Updated</td>
            <td width="10%">Status</td>
            <td width="10%">Verified</td>
            <td width="7.5%"></td>
            <td width="7.5%"></td>

        </tr>
        </thead>
        <c:forEach items="${accounts}" var="account">
            <tr>
                <td>${account.login}</td>
                <td><a href="mailto:${account.email}">${account.email}</a></td>
                <td><c:forEach items="${account.accountRoles }"
                               var="accountRoles">${accountRoles.role.idRole }</c:forEach></td>
                <td><fmt:formatDate value="${account.created }" pattern="dd-MM-yyyy"/></td>
                <td><fmt:formatDate value="${account.updated }" pattern="dd-MM-yyyy"/></td>
                <td>${account.active ? "Activated" : "Deactivated" }</td>
                <td>${account.confirmed ? "Yes" : "No" }</td>
                <td><a href="<c:url value="/admin/edit-account?id=${account.idAccount }"/>">Edit</a></td>
                <td><a href="<c:url value="/admin/delete?id=${account.idAccount }"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>

<jsp:include page="../templates/pagination.jsp"/>
