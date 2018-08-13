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
<section class="deck-title">
	<div>
		<spring:message code="DECK_TABLE_TITLE">
			<spring:argument>${deck.name}</spring:argument>
			<spring:argument><spring:message code="DeckType.${deck.type}"/></spring:argument>
			<spring:argument>${deck.count('main')}<c:if test="${deck.type == 'commander'}"> + ${deck.count('commander')}</c:if></spring:argument>
		</spring:message>
	</div>
	<c:if test="${deck.user == user.user}">
		<div class="btn-select">
			<button type="button" class="secondary"><spring:message code="DECK_ACTION"/></button>
			<ul>
				<li><a class="modal" href='<spring:url value="/d/edit/${deck.id}"/>'><spring:message code="DECK_ACTION_EDIT"/></a></li>
				<li><a class="modal" href='<spring:url value="/d/export/${deck.id}"/>'><spring:message code="DECK_ACTION_EXPORT"/></a></li>
				<li><a class="modal" href='<spring:url value="/d/import/${deck.id}"/>'><spring:message code="DECK_ACTION_IMPORT"/></a></li>
			</ul>
		</div>
	</c:if>
</section>
