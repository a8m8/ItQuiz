<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="form-group">
    <div class="col-md-offset-1 col-md-1">
        <a href="${context}/${role}/${pageName}/test/questions/page/1">&#9668; Back</a>
    </div>
    <form:label path="content" class="col-md-2 control-label">Answer content:</form:label>
    <div class="col-md-4">
        <form:textarea id="answer-content" type="text" rows="4" class="form-control" path="content"/>
    </div>
</div>
<div class="form-group">
    <div class="col-md-offset-4 col-md-4">
        <label id="checkbox-label" class="checkbox-inline">
            <form:checkbox path="correct"/>Correct
        </label>
    </div>
</div>
<div class="form-group">
    <div class="col-md-offset-4 col-md-4">
        <label id="checkbox-label" class="checkbox-inline">
            <form:checkbox path="active"/>Active
        </label>
    </div>
</div>
