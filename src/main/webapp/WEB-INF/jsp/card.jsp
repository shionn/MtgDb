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
						<li<c:if test="${card.id == p.id}"> class="active"</c:if>><a href='<spring:url value="/c/${p.id}"/>' title="${p.edition.name}"><i class="ss ss-${p.edition.icon}"></i></a></li>
					</c:forEach>
				</ul>
			</section>
			<section class="main">
				<article class="image portlet">
					<header>${card.artist}</header>
					<section>
						<img src='<spring:url value="/c/img/${card.id}.jpg"/>'/>
					</section>
					<c:if test="${card.flip}">
						<section>
							<a href='<spring:url value="/c/${card.linkCard.id}"/>'>Other Face</a>
						</section>
					</c:if>
				</article>
				<article class="text portlet">
					<header>
						<span class="mtgsymbol type">
							<c:if test="${card.creature}"><i class="mi mi-creature" title="creature"></i></c:if>
							<c:if test="${card.enchantment}"><i class="mi mi-enchantment" title="enchantment"></i></c:if>
							<c:if test="${card.planeswalker}"><i class="mi mi-planeswalker" title="planeswalker"></i></c:if>
							<c:if test="${card.instant}"><i class="mi mi-instant" title="instant"></i></c:if>
							<c:if test="${card.sorcery}"><i class="mi mi-sorcery" title="sorcery"></i></c:if>
							<c:if test="${card.land}"><i class="mi mi-land" title="land"></i></c:if>
							<c:if test="${card.artifact}"><i class="mi mi-artifact" title="artifact"></i></c:if>
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
							<p>${card.power}/${card.toughness}</p>
						</c:if>
						<c:if test="${card.planeswalker}">
							<p>${card.loyalty}</p>
						</c:if>
					</section>
					<c:if test="${card.flip}">
						<article class="portlet left">
							<header>Other Face</header>
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
							<header>Original</header>
							<section>
								<p>${card.originalType}</p>
								<p>${card.originalText}</p>
							</section>
						</article>
					</c:if>
				</article>
				<article class="portlet prices">
					<header>Prices</header>
					<section>
						<ul>
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
				</article>
			</section>
			<section>
				<article class="portlet rules">
					<header>Rules</header>
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
</t:template>