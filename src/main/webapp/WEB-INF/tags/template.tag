<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="content" fragment="true"%>
<%@ attribute name="scripts" fragment="true"%>
<%@ attribute name="title" type="java.lang.String" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<link rel="stylesheet" href="<spring:url value="/css/style.css"/>" />
<title>${title}</title>
</head>
<body>
	<header>
		<nav>
			<a href="#">root<span class="fa fa-2 fa-bars"></span></a>
		</nav>
	</header>
	<div class="main container">
		<jsp:invoke fragment="content" />
	</div>
	<footer>
	</footer>
	<jsp:invoke fragment="scripts" />
</body>
</html>
