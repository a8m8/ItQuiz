<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="test-passing-box">
    <div class="panel panel-default">
        <div class="panel panel-heading">${question.content}</div>
        <div id="timer-box">
            <span id="timer">${timePerQuestion}</span>
        </div>
        <div class="panel-body">
            <form:form id="passing-test-form" class="form-horizontal" method="POST"
                       action="${context }/student/passing-test">
                <c:forEach items="${question.answers}" var="answer">
                    <c:if test="${answer.active}">
                        <div class="form-group">
                            <div class="col-md-offset-1 col-md-11">
                                <label id="checkbox-label" class="checkbox-inline">
                                    <input type="checkbox" name="answer" value="${answer.idAnswer}"/>${answer.content}
                                </label>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
                <div class="col-md-offset-4 col-md-4">
                    <button type="submit" class="btn-func">Next</button>
                </div>
                <input type="hidden" value="${question.idQuestion}" name="id"/>
            </form:form>
        </div>
    </div>
</div>


