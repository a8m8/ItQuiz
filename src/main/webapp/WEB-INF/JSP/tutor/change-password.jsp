<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<ul id="main-nav" class="tutor">
    <li id="firsttab">
        <a href="${context }/tutor/myaccount">My account</a>
    <li id="secondtab">
        <a href="${context }/tutor/mytests">My tests</a>
    <li id="fourthtab">
        <a href="${context }/tutor/create-test">Create test</a>
</ul>

<jsp:include page="../templates/change-password.jsp"/>
