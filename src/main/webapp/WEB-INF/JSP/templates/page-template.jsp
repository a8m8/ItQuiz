<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7 "> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8 br-ie7"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9 br-ie8"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>ItQuiz</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<link rel="stylesheet" type="text/css" href="${context}/resources/css/normalize.css?v=${CSS_JS_VERSION}" />
	<link rel="stylesheet" type="text/css" href="${context}/resources/css/styles.css?v=${CSS_JS_VERSION}" />
</head>

<body>
	<div class="container" id="header">
			<div id="logo" class="col-md-offset-1 col-md-3">
				<h1>LOGO HERE</h1>
			</div>
			<div id="welcome" class="col-md-6">
				<h1>ItQuiz</h1>	
			</div>
	</div>
	
	<div id="wrapper">	

	  	<section id="content">
			<decorator:body />
		</section>
		
	</div>
		
	<div class="container" id="footer">
		<div class="col-md-offset-2 col-md-3">
			<p>Support email: <a href="mailto: itquiz@gmail.com">itquiz@gmail.com</a><p>
		</div>
		<div class="col-md-offset-2 col-md-3">
			<p>&copy; 2015 Artur Meshcheriakov</p>
		</div>
	</div>
		
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="${context}/resources/js/jquery-1.10.2.js?v=${CSS_JS_VERSION}"></script>
	<script>
		$(document).ready(function(){
	
		});
	</script>

</body>
</html>