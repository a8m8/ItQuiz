<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../templates/student/student-nav.jsp"/>

<h3>Test results</h3>

<div id="table-box">
    <table class="table table-striped">
        <thead>
        <tr>
            <td width="30%">Test</td>
            <td width="20%">Passed</td>
            <td width="25%">Right Answers</td>
            <td width="25%">All Questions</td>
        </tr>
        </thead>
        <c:forEach items="${testResults}" var="testResult">
            <tr>
                <td>${testResult.testTitle}</td>
                <td><fmt:formatDate value="${testResult.created }" pattern="dd-MM-yyyy HH:mm"/></td>
                <td>${testResult.correctCount}</td>
                <td>${testResult.allQuestionsCount}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<jsp:include page="../templates/pagination.jsp"/>

