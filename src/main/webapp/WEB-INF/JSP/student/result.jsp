<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../templates/student/student-nav.jsp"/>

<h3>Congratulations</h3>

<div id="result">
    <p>You have passed test named "${testResult.testTitle}"</p>

    <p>You have answered right on ${testResult.correctCount}
        ${testResult.correctCount > 1 ? "questions" : "question"} of ${testResult.allQuestionsCount}</p>

    <p>All you test results statistic you can find in "test results" menu</p>
</div>
