<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7 "> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8 br-ie7"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9 br-ie8"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <sec:csrfMetaTags/>
    <title>ItQuiz</title>
    <link rel="stylesheet" type="text/css" href="${context}/resources/css/bootstrap.min.css?v=${CSS_JS_VERSION}">
    <link rel="stylesheet" type="text/css" href="${context}/resources/css/bootstrap-theme.min.css?v=${CSS_JS_VERSION}">
    <link rel="stylesheet" type="text/css" href="${context}/resources/css/normalize.css?v=${CSS_JS_VERSION}"/>
    <link rel="stylesheet" type="text/css" href="${context}/resources/css/styles.css?v=${CSS_JS_VERSION}"/>
</head>

<body>
<header>
    <div id="logo" class="col-xs-offset-1 col-xs-3">
        <h1>LOGO HERE</h1>
    </div>
    <div id="welcome" class="col-xs-5">
        <h1>ItQuiz</h1>
    </div>
    <sec:authorize access="isAuthenticated()">
        <div class="col-xs-offset-1 col-xs-1">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl }" method="POST">
                <button type="submit" id="logout" class="btn btn-success">Log Out</button>
                <sec:csrfInput/>
            </form>
        </div>
    </sec:authorize>
</header>

<div id="wrapper">

    <section id="content">
        <decorator:body/>
    </section>

</div>
<footer>
    <div class="col-xs-offset-2 col-xs-3">
        <p>Support email: <a href="mailto: itquiz@gmail.com">itquiz@gmail.com</a></p>
    </div>
    <div class="col-xs-offset-2 col-xs-3">
        <p>&copy; 2015 Artur Meshcheriakov</p>
    </div>
</footer>

<script type="text/javascript" src="${context}/resources/js/jquery-1.11.3.min.js?v=${CSS_JS_VERSION}"></script>
<script type="text/javascript" src="${context}/resources/js/bootstrap.min.js?v=${CSS_JS_VERSION}"></script>
<script type="text/javascript" src="${context}/resources/js/jquery.validate.min.js?v=${CSS_JS_VERSION}"></script>
<script type="text/javascript" src="${context}/resources/js/jquery.form.min.js?v=${CSS_JS_VERSION}"></script>
<script type="text/javascript" src="${context}/resources/js/validation-scripts.js?v=${CSS_JS_VERSION}"></script>

</body>
</html>