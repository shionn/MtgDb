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
						<table class="deck">
							<thead>
								<tr>
									<th colspan="8"><spring:message code="DECK_TABLE_SIDEBOARD_TITLE" arguments="${deck.count('side')}"/></th>
								</tr>
							</thead>
							<tbody>
								<t:deck-table-line deck="${deck}" entries="${deck.sides}" user="${user}"></t:deck-table-line>
							</tbody>
						</table>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>