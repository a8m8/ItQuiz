<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../templates/tutor-nav.jsp"/>

<h3>My tests</h3>

<jsp:include page="../templates/message.jsp"/>
<jsp:include page="../templates/error-message.jsp"/>

<div id="table-box">
    <table class="table table-striped">
        <thead>
        <tr>
            <td width="15%">Title</td>
            <td width="15%">Time p/q</td>
            <td width="15%">Created</td>
            <td width="15%">Updated</td>
            <td width="10%">Active</td>
            <td width="15%"></td>
            <td width="15%"></td>
        </tr>
        </thead>
        <c:forEach items="${tests}" var="test">
            <tr>
                <td>${test.title}</td>
                <td>${test.timePerQuestion} sec</td>
                <td><fmt:formatDate value="${test.created }" pattern="dd-MM-yyyy"/></td>
                <td><fmt:formatDate value="${test.updated }" pattern="dd-MM-yyyy"/></td>
                <td>${test.active ? "Activated" : "Deactivated" }</td>
                <td><a href="<c:url value="/tutor/mytests/edit-test?id=${test.idTest }"/>">Edit</a></td>
                <td><a href="<c:url value="/tutor/mytests/delete?id=${test.idTest }"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>

<jsp:include page="../templates/pagination.jsp"/>