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
                <td class="overflowable">${account.login}</td>
                <td class="overflowable"><a href="mailto:${account.email}">${account.email}</a></td>
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

<c:url var="firstUrl" value="/admin/accounts/page/1"/>
<c:url var="lastUrl" value="/admin/accounts/page/${maximum}"/>
<c:url var="prevUrl" value="/admin/accounts/page/${currentIndex - 1}"/>
<c:url var="nextUrl" value="/admin/accounts/page/${currentIndex + 1}"/>

<nav id="pagination">
    <ul class="pagination">
        <c:choose>
            <c:when test="${currentIndex == 1}">
                <li class="disabled"><a href="#">&lt;&lt;</a></li>
                <li class="disabled"><a href="#">&lt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${firstUrl}">&lt;&lt;</a></li>
                <li><a href="${prevUrl}">&lt;</a></li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:url var="pageUrl" value="/admin/accounts/page/${i}"/>
            <c:choose>
                <c:when test="${i == currentIndex}">
                    <li class="active"><a href="${pageUrl}"><c:out value="${i}"/></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageUrl}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${currentIndex == maximum}">
                <li class="disabled"><a href="#">&gt;</a></li>
                <li class="disabled"><a href="#">&gt;&gt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${nextUrl}">&gt;</a></li>
                <li><a href="${lastUrl}">&gt;&gt;</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</nav>
