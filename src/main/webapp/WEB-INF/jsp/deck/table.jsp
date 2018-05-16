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
						<table style="margin-top:5px" class="deck-table">
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
								<c:if test="${empty deck.mains}">
									<tr>
										<td colspan="7"><p><spring:message code="DECK_TABLE_MAIN_EMPTY"/></p></td>
									</tr>
								</c:if>
								<c:forEach items="${deck.mains}" var="e">
									<tr>
										<td data-type="qty">${e.qty}</td>
										<td><a href='<spring:url value="/c/${e.card.id}"/>'>${e.card.name}</a></td>
										<td><t:card-type-symbole card="${e.card}"/> ${e.card.type}</td>
										<td>
											<i class="ss ss-fw ss-grad ss-${e.card.rarity.ss} ss-${e.card.edition.icon}"></i>
											<a href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>' class="modal"><i class="fa fa-cog"></i></a>
										</td>
										<td>
											<c:if test="${e.foil}">
												<img src='<spring:url value="/img/foil.gif"/>'>
											</c:if>
										</td>
										<td>${e.card.manaCost}</td>
										<td>
											<c:if test="${deck.user == user.user}">
												<div class="btn-select">
													<button type="button" class="secondary"><spring:message code="DECK_TABLE_ACTION"/></button>
													<ul>
														<li><a class="ajax" href='<spring:url value="/d/add/${e.deck}/1/${e.card.id}/${e.category}/${e.foil}"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_ADD_ONE"/></a></li>
														<li><a class="ajax" href='<spring:url value="/d/rm/${e.deck}/1/${e.card.id}/${e.category}/${e.foil}"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_RM_ONE"/></a></li>
														<li><a class="ajax" href='<spring:url value="/d/rm/${e.deck}/${e.qty}/${e.card.id}/${e.category}/${e.foil}"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_RM_ALL"/></a></li>
														<li><a class="ajax" href='<spring:url value="/d/mv/${e.deck}/1/${e.card.id}/${e.category}/${e.foil}/side"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_MV_ONE_SIDE"/></a></li>
														<li><a class="ajax" href='<spring:url value="/d/mv/${e.deck}/${e.qty}/${e.card.id}/${e.category}/${e.foil}/side"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_MV_ALL_SIDE"/></a></li>
													</ul>
												</div>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<thead>
								<tr>
									<th colspan="7"><spring:message code="DECK_TABLE_SIDEBOARD_TITLE" arguments="${deck.count('side')}"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${deck.sides}" var="e">
									<tr>
										<td>${e.qty}</td>
										<td><a href='<spring:url value="/c/${e.card.id}"/>'>${e.card.name}</a></td>
										<td><t:card-type-symbole card="${e.card}"/> ${e.card.type}</td>
										<td>
											<i class="ss ss-fw ss-grad ss-${e.card.rarity.ss} ss-${e.card.edition.icon}"></i>
											<a href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>' class="modal"><i class="fa fa-cog"></i></a>
										</td>
										<td>
											<c:if test="${e.foil}">
												<img src='<spring:url value="/img/foil.gif"/>'>
											</c:if>
										</td>
										<td>${e.card.manaCost}</td>
										<td>
											<c:if test="${deck.user == user.user}">
												<div class="btn-select">
													<button type="button" class="secondary"><spring:message code="DECK_TABLE_ACTION"/></button>
													<ul>
														<li><a class="ajax" href='<spring:url value="/d/add/${e.deck}/1/${e.card.id}/${e.category}/${e.foil}"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_ADD_ONE"/></a></li>
														<li><a class="ajax" href='<spring:url value="/d/rm/${e.deck}/1/${e.card.id}/${e.category}/${e.foil}"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_RM_ONE"/></a></li>
														<li><a class="ajax" href='<spring:url value="/d/rm/${e.deck}/${e.qty}/${e.card.id}/${e.category}/${e.foil}"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_RM_ALL"/></a></li>
														<li><a class="ajax" href='<spring:url value="/d/mv/${e.deck}/1/${e.card.id}/${e.category}/${e.foil}/main"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_MV_ONE_MAIN"/></a></li>
														<li><a class="ajax" href='<spring:url value="/d/mv/${e.deck}/${e.qty}/${e.card.id}/${e.category}/${e.foil}/main"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_MV_ALL_MAIN"/></a></li>
													</ul>
												</div>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>