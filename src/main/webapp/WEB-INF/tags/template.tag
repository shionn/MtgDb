<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="content" fragment="true"%>
<%@ attribute name="scripts" fragment="true"%>
<%@ attribute name="title" type="java.lang.String" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<link rel="shortcut icon" type="image/x-icon" href='<spring:url value="/img/favicon.ico"/>'/>
<link rel="stylesheet" href="<spring:url value="/css/style.css"/>" />
<link href="//cdn.jsdelivr.net/npm/keyrune@latest/css/keyrune.css" rel="stylesheet" type="text/css" />
<title>MtgDb - ${title} - by Shionn</title>
</head>
<body>
	<header>
		<nav class="container">
			<ul>
				<li>
					<form action='<spring:url value="/s"/>'>
						<span class="autocomplete">
							<input type="text" name="name" placeholder="<spring:message code="MAIN_MENU_QUICK_SEARCH"/>" data-source="<spring:url value="/s"/>" autocomplete="off" data-length="3"/>
						</span>
					</form>
				</li>
				<li>
					<a href='<spring:url value="/as"/>' class="as"><spring:message code="MAIN_MENU_ADVANCED_SEARCH"/></a>
				</li>
				<li>
					<a href='<spring:url value="/t"/>' class="t"><spring:message code="MAIN_MENU_TRADE"/></a>
				</li>
				<li>
					<a href='<spring:url value="/d"/>' class="d"><spring:message code="MAIN_MENU_DECK"/></a>
				</li>
				<c:if test="${user.currentDeck > 0}">
					<li>
						<a href='<spring:url value="/d/${user.currentDeck}"/>' class="cd">Deck : ${user.currentDeckName}</a>
					</li>
				</c:if>
				<li>
					<a href="?lg=fr_FR">fr</a>
				</li>
				<li>
					<a href="?lg=en_GB">en</a>
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
