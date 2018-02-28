<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">deck ${deck.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container deck-cube">
			<section>
				<article class="portlet nav">
					<t:deck-nav deck="${deck}"/>
					<section class="deck-title">
						<p style="text-align: center">TODO</p>
					</section>
					<section class="cube">
						<c:forEach items="${colors}" var="color">
							<ul>
								<li class="title"><spring:message code="DECK_CUBE_COLOR_TITLE.${color}"/></li>
								<c:forEach items="${deck.cubeColor(color)}" var="e">
									<li data-card="${e.card.card}">
										<a href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>' class="modal"><i class="fa fa-cog"></i></a>
										<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
									</li>
								</c:forEach>
							</ul>
						</c:forEach>
						<ul>
							<li class="title"><spring:message code="DECK_CUBE_COLOR_TITLE.gold"/></li>
							<c:forEach items="${guilds}" var="color">
								<li class="title"><spring:message code="DECK_CUBE_COLOR_TITLE.${color}"/></li>
								<c:forEach items="${deck.cubeColor(color)}" var="e">
									<li data-card="${e.card.card}">
										<a href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>' class="modal"><i class="fa fa-cog"></i></a>
										<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
									</li>
								</c:forEach>
							</c:forEach>
						</ul>
						<ul>
							<li class="title"><spring:message code="DECK_CUBE_COLOR_TITLE.none"/></li>
							<c:forEach items="${deck.cubeColor('none')}" var="e">
								<li data-card="${e.card.card}">
									<a href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>' class="modal"><i class="fa fa-cog"></i></a>
									<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
								</li>
							</c:forEach>
						</ul>
						<ul>
							<li class="title"><spring:message code="DECK_CUBE_COLOR_TITLE.land"/></li>
							<c:forEach items="${deck.cubeLand()}" var="e">
								<li data-card="${e.card.card}">
									<a href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>' class="modal"><i class="fa fa-cog"></i></a>
									<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
								</li>
							</c:forEach>
						</ul>
					</section>
					<section>
						<table>
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
										<td><i class="ss ss-fw ss-grad ss-${e.card.rarity.ss} ss-${e.card.edition.icon}"></i></td>
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