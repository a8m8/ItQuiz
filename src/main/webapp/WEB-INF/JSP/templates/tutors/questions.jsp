<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<h3>Questions</h3>

<jsp:include page="../message.jsp"/>
<jsp:include page="../error-message.jsp"/>

<form:form class="form-horizontal" method="GET" action="${context }/${role}/${pageName}/test/questions/add-question">
    <div class="form-group">
        <div class="col-md-offset-4 col-md-4">
            <button type="submit" class="btn-func">Add question</button>
        </div>
    </div>
</form:form>

<c:choose>
    <c:when test="${not empty questions}">
        <div id="table-box">
            <table class="table table-striped">
                <thead>
                <tr>
                    <td width="45%">Question</td>
                    <td width="15%">Created</td>
                    <td width="15%">Updated</td>
                    <td width="10%">Status</td>
                    <td width="7.5%"></td>
                    <td width="7.5%"></td>
                </tr>
                </thead>
                <c:forEach items="${questions}" var="question">
                    <tr>
                        <td>${question.content}</td>
                        <td><fmt:formatDate value="${question.created }" pattern="dd-MM-yyyy"/></td>
                        <td><fmt:formatDate value="${question.updated }" pattern="dd-MM-yyyy"/></td>
                        <td>${question.active ? "Activated" : "Deactivated" }</td>
                        <td><a href="<c:url value=
                        "/${role}/${pageName}/test/questions/edit-question?id=${question.idQuestion}"/>">
                            Edit</a></td>
                        <td><a href="<c:url value=
                        "/${role}/${pageName}/test/questions/delete?id=${question.idQuestion}"/>">
                            Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <jsp:include page="../pagination.jsp"/>
    </c:when>
    <c:otherwise>
        <h4>No created question</h4>
    </c:otherwise>
</c:choose>