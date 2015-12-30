<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<h3>Edit question</h3>

<jsp:include page="../message.jsp"/>
<jsp:include page="../error-message.jsp"/>

<c:url var="editQuestion" value="/${role}/${pageName}/test/questions/edit-question"/>
<form:form id="add-question-form" class="form-horizontal" method="POST" action="${editQuestion}"
           commandName="questionForm">

    <jsp:include page="question-form.jsp"/>

    <div class="form-group">
        <div class="col-md-offset-4 col-md-4">
            <button type="submit" class="btn-func">Save</button>
        </div>
    </div>

</form:form>

<c:url var="addAnswer" value="/${role}/${pageName}/test/questions/question/add-answer"/>
<form:form class="form-horizontal" method="GET" action="${addAnswer }">
    <div class="form-group">
        <div class="col-md-offset-4 col-md-4">
            <button type="submit" class="btn-func">Add answer</button>
        </div>
    </div>
</form:form>

<h3>Answers</h3>

<c:choose>
    <c:when test="${not empty question.answers}">
        <div id="table-box">
            <table class="table table-striped">
                <thead>
                <tr>
                    <td width="35%">Answer</td>
                    <td width="10%">Correct</td>
                    <td width="15%">Created</td>
                    <td width="15%">Updated</td>
                    <td width="10%">Status</td>
                    <td width="7.5%"></td>
                    <td width="7.5%"></td>

                </tr>
                </thead>
                <c:forEach items="${question.answers}" var="answer">
                    <tr>
                        <td>${answer.content}</td>
                        <td>${answer.correct ? "Yes" : "No"}</td>
                        <td><fmt:formatDate value="${answer.created }" pattern="dd-MM-yyyy"/></td>
                        <td><fmt:formatDate value="${answer.updated }" pattern="dd-MM-yyyy"/></td>
                        <td>${answer.active ? "Activated" : "Deactivated" }</td>
                        <td><a href="<c:url value=
                        "/${role}/${pageName}/test/questions/question/edit-answer?id=${answer.idAnswer }"/>">Edit
                        </a></td>
                        <td><a href="<c:url value=
                        "/${role}/${pageName}/test/questions/question/answer/delete?id=${answer.idAnswer }"/>">Delete
                        </a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:when>
    <c:otherwise>
        <h4>No created answers</h4>
    </c:otherwise>
</c:choose>