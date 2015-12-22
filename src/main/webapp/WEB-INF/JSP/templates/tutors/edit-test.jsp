<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../message.jsp"/>
<jsp:include page="../error-message.jsp"/>

<h3>Edit test</h3>

<form:form id="edit-test-form" class="form-horizontal" method="POST" action="${context }/${role}/${pageName}/edit-test"
           commandName="testForm">
    <jsp:include page="test-form.jsp"/>
    <div class="form-group">
        <div class="col-md-offset-4 col-md-4">
            <button type="submit" class="btn-func">Save</button>
        </div>
    </div>
</form:form>

<form:form class="form-horizontal" method="GET" action="${context }/${role}/${pageName}/test/questions/page/1">
    <div class="form-group">
        <div class="col-md-offset-4 col-md-4">
            <button type="submit" class="btn-func">Test questions</button>
        </div>
    </div>
</form:form>



