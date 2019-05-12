<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="content" fragment="true"%>
<%@ attribute name="scripts" fragment="true"%>
<%@ attribute name="title" type="java.lang.String" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta name="mobile-web-app-capable" content="yes" />
<link rel="shortcut icon" type="image/x-icon" href='<spring:url value="/img/favicon.ico"/>'/>
<link rel="stylesheet" href="<spring:url value="/css/style.css"/>" />
<link href="//cdn.jsdelivr.net/npm/keyrune@latest/css/keyrune.css" rel="stylesheet" type="text/css" />
<link href="//cdn.jsdelivr.net/npm/mana-font@latest/css/mana.css" rel="stylesheet" type="text/css" />
<title>MtgDb - ${title} - by Shionn</title>
</head>
<body>
	<header>
		<nav class="container menu">
			<ul>
				<li>
					<a href="#"><spring:message code="MAIN_MENU"/><span class="fa fa-2 fa-bars"></span></a>
				</li>
				<li>
					<form action='<spring:url value="/s"/>'>
						<span class="autocomplete">
							<input type="text" name="name"
									placeholder="<spring:message code="MAIN_MENU_QUICK_SEARCH"/>"
									data-source="<spring:url value="/s"/>" autocomplete="off" data-length="3"
									title="ctrl-s"/>
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
				<li>
					<a href='<spring:url value="/e"/>' class="e"><spring:message code="MAIN_MENU_EDITION"/></a>
				</li>
				<c:if test="${user.currentDeck > 0}">
					<li>
						<a href='<spring:url value="/d/${user.currentDeck}"/>' class="cd">Deck : ${user.currentDeckName}</a>
					</li>
				</c:if>
				<li>
					<c:if test="${user.locale.language == 'en'}">
						<a href="?lg=fr_FR"><span class="flag-icon flag-icon-fr"></span></a>
					</c:if>
					<c:if test="${user.locale.language == 'fr'}">
						<a href="?lg=en_GB"><span class="flag-icon flag-icon-gb"></span></a>
					</c:if>
				</li>
			</ul>
		</nav>
	</header>
	<jsp:invoke fragment="content" /><footer class="container">
		<div>MtgDb by <a href="shionn@gmail.com">shionn</a>
			<c:if test="${user.admin}">
				| <a href='<spring:url value="/adm"/>'>Admin</a>
			</c:if>
<%-- 			<c:if test="${user.login}"> --%>
<%-- 				| <a href='<spring:url value="/signout"/>'><spring:message code="SIGNOUT"/></a> --%>
<%-- 			</c:if> --%>
		</div>
		<div>
			<a href="mailto:shionn@gmail.com" title="Contact"><i class="fa fa-envelope" aria-hidden="true"></i></a> |
			<a href="https://github.com/shionn/MtgDb" target="_blank" title="Github"><i class="fa fa-github"></i></a> |
			<a href="http://fontawesome.io/" target="_blank" title="FontAwesome"><i class="fa fa-font-awesome"></i></a> |
			<a href="https://spring.io/" target="_blank" title="Spring"><i class="fa fa-leaf"></i></a> |
			<a href="http://blog.mybatis.org/" target="_blank" title="MyBatis"><i class="fa fa-twitter"></i></a> |
			<a href="http://lesscss.org/" target="_blank" title="Less">{<small>less</small>}</a> |
			<a href="http://alexandrearpin.com/mtg-font/" target="_blank" title="Magic the Gathering Font"><img src="http://alexandrearpin.com/favicon.ico"></a> |
			<a href="https://andrewgioia.github.io/Keyrune/" target="_blank" title="Keyrune"><i class="ss ss-leg"></i></a> |
			<a href="https://andrewgioia.github.io/Mana/" target="_blank" title="Mana"><i class="ms ms-g"></i> </a> |
			<a href="https://mtgjson.com/" target="_blank" title="MtgJson"><img src="https://mtgjson.com/favicon.ico"></a> |
			<a href="https://scryfall.com/" target="_blank" title="Scryfall"><img src="https://scryfall.com/favicon.ico"></a> |
			<a href="http://flag-icon-css.lip.is/" target="_blank" title="flag-icon-css"><img src="http://flag-icon-css.lip.is/favicon.ico"></a>
		</div>
	</footer>
	<div class="modal"></div>
	<script type="text/javascript" src='<spring:url value="/js/jquery-3.1.0.min.js"/>'></script>
	<script type="text/javascript" src='<spring:url value="/js/scripts.js"/>'></script>
	<jsp:invoke fragment="scripts" />
</body>
</html>
