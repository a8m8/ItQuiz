<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<ul id="main-nav" class="student">
    <li id="firsttab" <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/myaccount')}">
        class="active"</c:if>>
        <a href="${context }/student/myaccount">My account</a>
    <li id="secondtab" <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/tests')}">
        class="active"</c:if>>
    <a href="${context }/student/tests/page/1">Tests</a>
    <li id="thirdtab" <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/test-results')}">
        class="active"</c:if>>
        <a href="${context }/student/test-results/page/1">Test results</a>
</ul>
