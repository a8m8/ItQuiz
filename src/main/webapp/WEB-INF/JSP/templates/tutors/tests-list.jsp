<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../message.jsp"/>
<jsp:include page="../error-message.jsp"/>

<c:choose>
    <c:when test="${not empty tests}">
        <div id="table-box">
            <table class="table table-striped">
                <thead>
                <tr>
                    <c:if test="${location eq '/advanced-tutor/alltests'}">
                        <td width="15%">Created by</td>
                    </c:if>
                    <td width="15%">Title</td>
                    <td width="15%">Time p/q</td>
                    <td width="15%">Created</td>
                    <td width="15%">Updated</td>
                    <td width="10%">Active</td>
                    <td width="7.5%"></td>
                    <td width="7.5%"></td>
                </tr>
                </thead>
                <c:forEach items="${tests}" var="test">
                    <tr>
                        <c:if test="${location eq '/advanced-tutor/alltests'}">
                            <td>${test.account.login}</td>
                        </c:if>
                        <td>${test.title}</td>
                        <td>${test.timePerQuestion} sec</td>
                        <td><fmt:formatDate value="${test.created }" pattern="dd-MM-yyyy HH:mm"/></td>
                        <td><fmt:formatDate value="${test.updated }" pattern="dd-MM-yyyy HH:mm"/></td>
                        <td>${test.active ? "Activated" : "Deactivated" }</td>
                        <td><a href="<c:url value="${location}/edit-test?id=${test.idTest }"/>">Edit</a></td>
                        <td><a href="<c:url value="${location}/delete?id=${test.idTest }"/>">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <jsp:include page="../pagination.jsp"/>
    </c:when>
    <c:otherwise>
        <h4>No created tests</h4>
    </c:otherwise>
</c:choose>