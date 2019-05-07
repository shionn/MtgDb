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
							<ul>
								<c:forEach items="${deck.commanders}" var="e">
									<li data-card="${e.card.card}">
										<c:forEach begin="1" end="${e.qty}" var="i">
											<c:if test="${i==1}">
												<a href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>' class="modal"><i class="fa fa-cog"></i></a>
											</c:if>
											<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
										</c:forEach>
									</li>
								</c:forEach>
							</ul>
						</c:if>
						<div class="title"><spring:message code="DECK_TABLE_MAIN_TITLE"/></div>
						<ul>
							<c:forEach items="${deck.mains}" var="e">
								<li data-card="${e.card.card}">
									<c:forEach begin="1" end="${e.qty}" var="i">
										<c:if test="${i==1}">
											<a href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>' class="modal"><i class="fa fa-cog"></i></a>
										</c:if>
										<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
									</c:forEach>
								</li>
							</c:forEach>
						</ul>
						<div class="title"><spring:message code="DECK_TABLE_SIDEBOARD_TITLE" arguments="${deck.count('side')}"/></div>
						<ul>
							<c:forEach items="${deck.sides}" var="e">
								<li data-card="${e.card.card}">
									<c:forEach begin="1" end="${e.qty}" var="i">
										<c:if test="${i==1}">
											<a href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>' class="modal"><i class="fa fa-cog"></i></a>
										</c:if>
										<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
									</c:forEach>
								</li>
							</c:forEach>
						</ul>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>