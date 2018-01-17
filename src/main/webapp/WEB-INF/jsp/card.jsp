<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">MtgDb by Shionn - ${card.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container card">
<%-- 			<header>${card.name}</header> --%>
			<section class="editions">
				<ul>
					<c:forEach items="${card.printings}" var="p">
						<li<c:if test="${card.id == p.id}"> class="active"</c:if>><a href='<spring:url value="/c/${p.id}"/>'>${p.edition}</a></li>
					</c:forEach>
				</ul>
			</section>
			<section class="main">
				<article class="image portlet">
					<header>${card.name}</header>
					<section>
						<img src='<spring:url value="/c/img/${card.id}.jpg"/>'/>
					</section>
					<footer></footer>
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
						<span>${card.name}</span>
						<span class="mtgsymbol cost">${card.manaCost}</span>
					</header>
					<section><p>${card.text}</p></section>
				</article>
				<article class="prices portlet">
					<header>prix</header>
					<section>
						<ul>
							<c:forEach items="${card.prices}" var="p">
								<li><img src='<spring:url value="/img/${p.source.icon}"/>'>${p.price} ${p.source.currency}</li>
							</c:forEach>
						</ul>
					</section>
					<footer></footer>
				</article>
			</section>
			<footer>
				<ul>
					<li>rules</li>
				</ul>
			</footer>
		</article>
	</jsp:attribute>
</t:template>