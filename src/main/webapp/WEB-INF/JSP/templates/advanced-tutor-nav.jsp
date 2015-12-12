<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<ul id="main-nav" class="advanced-tutor">
    <li id="firsttab" <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/myaccount')}">
        class="active"</c:if>>
        <a href="${context }/advanced-tutor/myaccount">My account</a>
    <li id="secondtab" <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/mytests')}">
        class="active"</c:if>>
        <a href="${context }/advanced-tutor/mytests">My tests</a>
    <li id="thirdtab" <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/alltests')}">
        class="active"</c:if>>
        <a href="${context }/advanced-tutor/alltests">All tests</a>
    <li id="fourthtab" <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/create-test')}">
        class="active"</c:if>>
        <a href="${context }/advanced-tutor/create-test">Create test</a>
</ul>
