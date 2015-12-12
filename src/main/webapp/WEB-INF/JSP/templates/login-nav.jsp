<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<ul id="main-nav" class="login">
    <li id="firsttab"
        <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/signup')}">class="active"</c:if>>
        <a href="${context}/signup">SignUp</a>
    <li id="secondtab"
        <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/login')}">class="active"</c:if>>
        <a href="${context}/login">Welcome</a>
    <li id="thirdtab" <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/password-recovery')}">
        class="active"</c:if>>
        <a href="${context}/password-recovery">Password recovery</a>
</ul>

<h3>${pageContext.request.contextPath}</h3>
