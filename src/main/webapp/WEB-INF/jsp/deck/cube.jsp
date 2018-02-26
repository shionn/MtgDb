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
								<li class="title">${color}</li>
								<c:forEach items="${deck.cubeColor(color)}" var="e">
									<li>
										<a href="#"><i class="fa fa-cog"></i></a>
										<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
									</li>
								</c:forEach>
							</ul>
						</c:forEach>
						<ul>
							<li class="title">Golds</li>
							<c:forEach items="${guilds}" var="color">
								<li class="title">${color}</li>
								<c:forEach items="${deck.cubeColor(color)}" var="e">
									<li>
										<a href="#"><i class="fa fa-cog"></i></a>
										<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
									</li>
								</c:forEach>
							</c:forEach>
						</ul>
						<ul>
							<li class="title">Colorless</li>
							<c:forEach items="${deck.cubeColor('none')}" var="e">
								<li>
									<a href="#"><i class="fa fa-cog"></i></a>
									<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
								</li>
							</c:forEach>
						</ul>
						<ul>
							<li class="title">Lands</li>
							<c:forEach items="${deck.cubeLand()}" var="e">
								<li>
									<a href="#"><i class="fa fa-cog"></i></a>
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
											<div class="btn-select">
												<button type="button" class="secondary"><spring:message code="DECK_TABLE_ACTION"/></button>
												<ul>
													<li><a class="ajax" href='<spring:url value="/d/add/1/${e.card.id}/${e.category}/${e.foil}"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_ADD_ONE"/></a></li>
													<li><a class="ajax" href='<spring:url value="/d/rm/1/${e.card.id}/${e.category}/${e.foil}"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_RM_ONE"/></a></li>
													<li><a class="ajax" href='<spring:url value="/d/rm/all/${e.card.id}/${e.category}/${e.foil}"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_RM_ALL"/></a></li>
													<li><a class="ajax" href='<spring:url value="/d/mv/1/${e.card.id}/${e.category}/${e.foil}/main"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_MV_ONE_MAIN"/></a></li>
													<li><a class="ajax" href='<spring:url value="/d/mv/${e.qty}/${e.card.id}/${e.category}/${e.foil}/main"/>' data-update="table,section.deck-title"><spring:message code="DECK_TABLE_MV_ALL_MAIN"/></a></li>
												</ul>
											</div>
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