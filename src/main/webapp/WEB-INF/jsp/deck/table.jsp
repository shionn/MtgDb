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
					<section>
						<table style="margin-top:5px" class="deck">
							<thead>
								<tr>
									<th></th>
									<th><spring:message code="DECK_TABLE_NAME"/></th>
									<th><span><spring:message code="DECK_TABLE_TYPE"/></span></th>
									<th colspan="2"><span><spring:message code="DECK_TABLE_EDITION"/></span></th>
									<th><spring:message code="DECK_TABLE_TAG"/></th>
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
										<td colspan="8"><p><spring:message code="DECK_TABLE_MAIN_EMPTY"/></p></td>
									</tr>
								</c:if>
								<t:deck-table-line deck="${deck}" entries="${deck.mains}" user="${user}"/>
							</tbody>
							<c:if test="${not empty deck.sides}">
								<thead>
									<tr>
										<th colspan="8"><spring:message code="DECK_TABLE_SIDEBOARD_TITLE" arguments="${deck.count('side')}"/></th>
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