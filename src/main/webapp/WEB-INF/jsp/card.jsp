<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">${card.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container card">
			<section class="editions">
				<ul>
					<c:forEach items="${card.printings}" var="p">
						<li<c:if test="${card.id == p.id}"> class="active"</c:if>>
							<a href='<spring:url value="/c/${p.id}"/>' title="${p.edition.code} - ${p.edition.name}"><i class="${p.edition.iconClass}"></i></a>
						</li>
					</c:forEach>
				</ul>
			</section>
			<section class="main">
				<article class="image portlet">
					<header><i class="ms ms-artist-brush"></i> ${card.artist}</header>
					<section>
						<img src='<spring:url value="/c/img/${card.id}.jpg"/>'/>
					</section>
					<c:if test="${card.haveLinkCard()}">
						<section>
							<a href='<spring:url value="/c/${card.linkCard.id}"/>'><spring:message code="CARD_OTHER_FACE"/></a>
						</section>
					</c:if>
				</article>
				<article class="text portlet">
					<header>
						<span class="mtgsymbol type">
							<t:card-type-symbole card="${card}"/>
						</span>
						<span>${card.name} - ${card.lang('fr').name}</span>
						<span class="mtgsymbol cost">${card.manaCost}</span>
					</header>
					<section>
						<p>${card.type}<i class="ss ss-fw ss-grad ss-${card.rarity.ss} ss-${card.edition.icon}"></i></p>
						<p>${card.text}</p>
						<c:if test="${card.flavor !=null}">
							<p class="flavor">${card.flavor}</p>
						</c:if>
						<c:if test="${card.creature}">
							<p><i class="ms ms-power"></i> ${card.power} / <i class="ms ms-toughness"></i> ${card.toughness}</p>
						</c:if>
						<c:if test="${card.planeswalker}">
							<p><i class="ms ms-loyalty-start ms-loyalty-${card.loyalty}"></i></p>
						</c:if>
					</section>
					<c:if test="${card.flip}">
						<article class="portlet left">
							<header><spring:message code="CARD_OTHER_FACE"/></header>
							<section>
								<p>${card.linkCard.type}</p>
								<p>${card.linkCard.text}</p>
								<c:if test="${card.linkCard.creature}">
									<p>${card.linkCard.power}/${card.linkCard.toughness}</p>
								</c:if>
								<c:if test="${card.linkCard.planeswalker}">
									<p>${card.linkCard.loyalty}</p>
								</c:if>
							</section>
						</article>
					</c:if>
					<c:if test="${card.displayOriginal}">
						<article class="portlet left">
							<header><spring:message code="CARD_ORIGINAL"/></header>
							<section>
								<p>${card.originalType}</p>
								<p>${card.originalText}</p>
							</section>
						</article>
					</c:if>
					<article class="portlet left">
						<header><spring:message code="CARD_LEGALITIES"/></header>
						<section>
							<c:forEach items="${card.legalities}" var="l">
								<p>${l.format} : <spring:message code="GameLegality.${l.legality}"/></p>
							</c:forEach>
						</section>
					</article>
				</article>
				<article class="portlet prices">
					<header><spring:message code="CARD_PRICE"/></header>
					<section>
						<ul>
							<c:if test="${empty card.prices}">
								<li><em><spring:message code="CARD_NO_PRICE_FOUND"/></em></li>
							</c:if>
							<c:forEach items="${card.prices}" var="p">
								<c:if test="${p.price != null}">
									<li>
										<a href="${p.link}" target="_blank">
											<img src='<spring:url value="/img/${p.source.icon}"/>' title='<fmt:formatDate value="${p.priceDate}"/>'>
											<c:if test="${p.source.foil}">
												<img src='<spring:url value="/img/foil.gif"/>'>
											</c:if>
										</a>
										${p.price} ${p.source.currency}
									</li>
								</c:if>
							</c:forEach>
						</ul>
						<c:if test="${priceupdate}">
							<div class="priceloading" data-source='<spring:url value="/p/${card.id}"/>'>
								<span></span>
							</div>
						</c:if>
					</section>
					<header<c:if test="${priceupdate}"> class="hide"</c:if>><spring:message code="CARD_TRADE"/></header>
					<section<c:if test="${priceupdate}"> class="hide"</c:if>>
						<a class="button" href='<spring:url value="/t/add/${card.id}"/>'><spring:message code="CARD_TRADE_ADD"/></a>
						<a class="button" href='<spring:url value="/t/add-f/${card.id}"/>'><spring:message code="CARD_TRADE_FOIL_ADD"/></a>
					</section>
					<c:if test="${user.currentDeck != 0}">
						<header><spring:message code="CARD_DECK"/></header>
						<section class="deck">
							<a class="button ajax add-main" href='<spring:url value="/d/add/${user.currentDeck}/1/${card.id}/main/false"/>' data-update="section.deck" title="Ctrl +">
								<spring:message code="CARD_DECK_ADD"/><span>${deckMainQty}</span>
							</a>
							<a class="button ajax" href='<spring:url value="/d/add/${user.currentDeck}/4/${card.id}/main/false"/>' data-update="section.deck">
								<spring:message code="CARD_DECK_PLAYSET_ADD"/><span>${deckMainQty}</span>
							</a>
							<a class="button ajax add-side" href='<spring:url value="/d/add/${user.currentDeck}/1/${card.id}/side/false"/>' data-update="section.deck" title="Ctrl Shift +">
								<spring:message code="CARD_DECK_SIDE_ADD"/><span>${deckSideQty}</span>
							</a>
						</section>
					</c:if>
					<c:if test="${user.admin}">
						<header><spring:message code="CARD_ADMIN"/></header>
						<section>
							<a class="button" href='<spring:url value="/adm/card/${card.id}/drop"/>'><spring:message code="CARD_ADMIN_DROP"/></a>
						</section>
					</c:if>
				</article>
			</section>
			<section>
				<article class="portlet rules">
					<header><spring:message code="CARD_RULES"/></header>
					<section>
						<ul>
							<c:forEach items="${card.rules}" var="r">
								<li>${r.rule}</li>
							</c:forEach>
						</ul>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
	<jsp:attribute name="scripts">
		<script type="text/javascript" src='<spring:url value="/js/cards.js"/>'></script>
	</jsp:attribute>
</t:template>