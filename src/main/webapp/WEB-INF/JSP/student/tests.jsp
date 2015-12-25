<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../templates/student/student-nav.jsp"/>

<h3>Available tests</h3>

<c:choose>
    <c:when test="${not empty tests}">
        <c:forEach items="${tests}" var="test">
            <div class="panel panel-default">
                <div class="panel panel-heading" id="tests-heading">${test.title}</div>
                <div class="panel-body">
                    <div class="col-md-3">
                        <p class="important">Description: </p>
                    </div>
                    <div class="col-md-9">
                        <c:choose>
                            <c:when test="${not empty test.description}">
                                <p>${test.description}</p>
                            </c:when>
                            <c:otherwise>
                                <p>No description</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-3">
                        <p class="important">Time per question: </p>
                    </div>
                    <div class="col-md-9">
                        <p>${test.timePerQuestion}</p>
                    </div>
                    <div class="col-md-offset-8 col-md-3">
                        <form:form class="form-horizontal" method="GET"
                                   action="${context }/student/pass-test">
                            <input type="hidden" value="${test.idTest}" name="id"/>

                            <div class="form-group">
                                <button type="submit" class="btn-func">Start</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </c:forEach>

        <jsp:include page="../templates/pagination.jsp"/>

    </c:when>
    <c:otherwise>
        <h4>No available tests at this moment</h4>
    </c:otherwise>
</c:choose>