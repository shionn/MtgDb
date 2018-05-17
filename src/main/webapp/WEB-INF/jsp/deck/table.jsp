<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">deck ${deck.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container deck-table">
			<section>
				<article class="portlet nav">
					<t:deck-nav deck="${deck}"/>
					<section class="deck-title">
						<div>
							<spring:message code="DECK_TABLE_TITLE">
								<spring:argument>${deck.name}</spring:argument>
								<spring:argument><spring:message code="DeckType.${deck.type}"/></spring:argument>
								<spring:argument>${deck.count('main')}</spring:argument>
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
					<section>
						<table style="margin-top:5px" class="deck">
							<thead>
								<tr>
									<th></th>
									<th><spring:message code="DECK_TABLE_NAME"/></th>
									<th><spring:message code="DECK_TABLE_TYPE"/></th>
									<th colspan="2"><spring:message code="DECK_TABLE_EDITION"/></th>
									<th><spring:message code="DECK_TABLE_MANACOST"/></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty deck.commanders}">
									<t:deck-table-line deck="${deck}" entries="${deck.commanders}" user="${user}"/>
								</c:if>
								<c:if test="${empty deck.mains}">
									<tr>
										<td colspan="7"><p><spring:message code="DECK_TABLE_MAIN_EMPTY"/></p></td>
									</tr>
								</c:if>
								<t:deck-table-line deck="${deck}" entries="${deck.mains}" user="${user}"/>
							</tbody>
							<c:if test="${not empty deck.sides}">
								<thead>
									<tr>
										<th colspan="7"><spring:message code="DECK_TABLE_SIDEBOARD_TITLE" arguments="${deck.count('side')}"/></th>
									</tr>
								</thead>
								<tbody>
									<t:deck-table-line deck="${deck}" entries="${deck.sides}" user="${user}"/>
								</tbody>
							</c:if>
						</table>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>