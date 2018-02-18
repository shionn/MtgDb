<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="deck" type="tcg.db.dbo.Deck" %>
<header>
	<ul>
		<li><a href='<spring:url value="/d/table/${deck.id}"/>'><spring:message code="DECK_NAV_TABLE"/></a></li>
		<li><a href="<spring:url value="/d/flat/${deck.id}"/>"><spring:message code="DECK_NAV_FLAT"/></a></li>
		<li><a href="<spring:url value="/d/cube/${deck.id}"/>"><spring:message code="DECK_NAV_CUBE"/></a></li>
		<li><a href="<spring:url value="/d/price/${deck.id}"/>"><spring:message code="DECK_NAV_PRICE"/></a></li>
		<li><a href="<spring:url value="/d/history/${deck.id}"/>"><spring:message code="DECK_NAV_HISTORY"/></a></li>
		<li><a href="<spring:url value="/d/stat/${deck.id}"/>"><spring:message code="DECK_NAV_STAT"/></a></li>
	</ul>
</header>
