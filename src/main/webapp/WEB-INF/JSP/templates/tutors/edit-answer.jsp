<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<h3>Edit answer</h3>

<jsp:include page="../error-message.jsp"/>

<c:url var="editAnswer" value="/${role}/${pageName}/test/questions/question/edit-answer"/>
<form:form id="add-question-form" class="form-horizontal" method="POST" action="${editAnswer}"
           commandName="answerForm">
    <input type="hidden" name="id" value="${idAnswer }"/>

    <jsp:include page="answer-form.jsp"/>

    <div class="form-group">
        <div class="col-md-offset-4 col-md-4">
            <button type="submit" class="btn-func">Save</button>
        </div>
    </div>

</form:form>

