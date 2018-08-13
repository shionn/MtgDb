<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">deck ${deck.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container deck-price">
			<section>
				<article class="portlet nav">
					<t:deck-nav deck="${deck}"/>
					<section>
						<table style="margin-top:5px" class="deck">
							<thead>
								<tr class="total">
									<th></th>
									<th><spring:message code="DECK_TABLE_NAME"/></th>
									<th><img src='<spring:url value="/img/mkm.gif"/>'> ${deck.mkmPrice.price} ${deck.mkmPrice.source.currency}</th>
									<th><img src='<spring:url value="/img/MtgGoldFish.gif"/>'> ${deck.mtgGoldFishPrice.price} ${deck.mtgGoldFishPrice.source.currency}</th>
									<th><img src='<spring:url value="/img/MtgGoldFish.gif"/>'> ${deck.mtgGoldFishTxPrice.price} ${deck.mtgGoldFishTxPrice.source.currency}</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${deck.commanders}" var="e" varStatus="i">
									<tr data-line="${e.category}${i.index}">
										<td>${e.qty}</td>
										<td><a href='<spring:url value="/c/${e.card.id}"/>'>${e.card.name}</a></td>
										<td>${e.mkmPrice.price} ${e.mkmPrice.source.currency}</td>
										<td>${e.mtgGoldFishPrice.price} ${e.mtgGoldFishPrice.source.currency}</td>
										<td>${e.mtgGoldFishTxPrice.price} ${e.mtgGoldFishTxPrice.source.currency}</td>
										<td>
											<c:if test="${e.card.oldPrice}">
												<a href='<spring:url value="/d/price/${deck.id}/refresh/${e.card.id}"/>'
														class="ajax hide-on-click"
														data-update="tr[data-line=${e.category}${i.index}],tr.total"><i class="fa fa-refresh"></i></a>
											</c:if>
											<fmt:formatDate value="${e.card.lastPriceDate}"/>
										</td>
									</tr>
								</c:forEach>
								<c:forEach items="${deck.mains}" var="e" varStatus="i">
									<tr data-line="${e.category}${i.index}">
										<td>${e.qty}</td>
										<td><a href='<spring:url value="/c/${e.card.id}"/>'>${e.card.name}</a></td>
										<td>${e.mkmPrice.price} ${e.mkmPrice.source.currency}</td>
										<td>${e.mtgGoldFishPrice.price} ${e.mtgGoldFishPrice.source.currency}</td>
										<td>${e.mtgGoldFishTxPrice.price} ${e.mtgGoldFishTxPrice.source.currency}</td>
										<td>
											<c:if test="${e.card.oldPrice}">
												<a href='<spring:url value="/d/price/${deck.id}/refresh/${e.card.id}"/>'
														class="ajax hide-on-click"
														data-update="tr[data-line=${e.category}${i.index}],tr.total"><i class="fa fa-refresh"></i></a>
											</c:if>
											<fmt:formatDate value="${e.card.lastPriceDate}"/>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<c:if test="${not empty deck.sides}">
								<thead>
									<tr>
										<th colspan="6"><spring:message code="DECK_TABLE_SIDEBOARD_TITLE" arguments="${deck.count('side')}"/></th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${deck.sides}" var="e" varStatus="i">
									<tr data-line="${e.category}${i.index}">
										<td>${e.qty}</td>
										<td><a href='<spring:url value="/c/${e.card.id}"/>'>${e.card.name}</a></td>
										<td>${e.mkmPrice.price} ${e.mkmPrice.source.currency}</td>
										<td>${e.mtgGoldFishPrice.price} ${e.mtgGoldFishPrice.source.currency}</td>
										<td>${e.mtgGoldFishTxPrice.price} ${e.mtgGoldFishTxPrice.source.currency}</td>
										<td>
											<c:if test="${e.card.oldPrice}">
												<a href='<spring:url value="/d/price/${deck.id}/refresh/${e.card.id}"/>'
														class="ajax hide-on-click"
														data-update="tr[data-line=${e.category}${i.index}],tr.total"><i class="fa fa-refresh"></i></a>
											</c:if>
											<fmt:formatDate value="${e.card.lastPriceDate}"/>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</c:if>
						</table>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>