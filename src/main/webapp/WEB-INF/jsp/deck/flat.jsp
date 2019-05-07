<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">deck ${deck.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container deck-flat">
			<section>
				<article class="portlet nav">
					<t:deck-nav deck="${deck}"/>
					<section class="deck-list">
						<c:if test="${not empty deck.commanders}">
							<div class="title"><spring:message code="DECK_TABLE_COMMANDER_TITLE"/></div>
							<t:deck-flat-cards deck="${deck}" entries="${deck.commanders}" user="${user}"/>
						</c:if>
						<div class="title"><spring:message code="DECK_TABLE_MAIN_TITLE"/></div>
						<t:deck-flat-cards deck="${deck}" entries="${deck.mains}" user="${user}"/>
						<div class="title"><spring:message code="DECK_TABLE_SIDEBOARD_TITLE" arguments="${deck.count('side')}"/></div>
						<t:deck-flat-cards deck="${deck}" entries="${deck.sides}" user="${user}"/>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>