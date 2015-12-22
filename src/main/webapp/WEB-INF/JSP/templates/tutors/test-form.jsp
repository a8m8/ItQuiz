<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div class="form-group">
    <form:label path="title" class="col-md-offset-2 col-md-2 control-label">Title:</form:label>
    <div class="col-md-4">
        <form:input type="text" class="form-control" path="title"/>
    </div>
</div>
<div class="form-group">
    <form:label path="description" class="col-md-offset-2 col-md-2 control-label">Description:</form:label>
    <div class="col-md-4">
        <form:textarea type="text" rows="4" class="form-control" path="description"/>
    </div>
</div>
<div class="form-group">
    <form:label path="timePerQuestion" class="col-md-offset-2 col-md-2 control-label">Time per
        question:</form:label>
    <div class="col-md-1">
        <form:input type="text" class="form-control" path="timePerQuestion"/>
    </div>
</div>
<div class="form-group">
    <div class="col-md-offset-4 col-md-4">
        <label id="checkbox-label" class="checkbox-inline">
            <form:checkbox path="active"/>Active
        </label>
    </div>
</div>
