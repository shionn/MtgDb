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
		<nav class="container">
			<ul >
				<li>
					<form action='<spring:url value="/c"/>'>
						<span class="autocomplete">
							<input type="text" name="name" data-source="<spring:url value="/s"/>"
								data-href='<spring:url value="/c/"/>'
								autocomplete="off"/>
						</span>
					</form>
				</li>
			</ul>
		</nav>
	</header>
	<jsp:invoke fragment="content" />
	<footer>
	</footer>
	<script type="text/javascript" src='<spring:url value="/js/jquery-3.1.0.min.js"/>'></script>
	<script type="text/javascript" src='<spring:url value="/js/scripts.js"/>'></script>
	<jsp:invoke fragment="scripts" />
</body>
</html>
