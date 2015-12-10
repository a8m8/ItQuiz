<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<ul id="main-nav" class="advanced-tutor">
    <li id="firsttab">
        <a href="${context }/advanced-tutor/myaccount">My account</a>
    <li id="secondtab">
        <a href="${context }/advanced-tutor/mytests">My tests</a>
    <li id="thirdtab" class="active">
        <a href="${context }/advanced-tutor/alltests">All tests</a>
    <li id="fourthtab">
        <a href="${context }/advanced-tutor/create-test">Create test</a>
</ul>

<h3>All tests</h3>