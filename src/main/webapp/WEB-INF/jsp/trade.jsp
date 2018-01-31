<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="bundle" var="bundle"/>
<t:template>
	<jsp:attribute name="title">Trade</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container trade">
			<section>
				<article>
					<section>
						<table>
							<thead>
								<tr>
									<th><fmt:message bundle="${bundle}" key="TRADE_CARD"/></th>
									<th><img src='<spring:url value="/img/mkm.gif"/>'></th>
									<th><img src='<spring:url value="/img/MtgGoldFish.gif"/>'></th>
									<th><img src='<spring:url value="/img/MtgGoldFish.gif"/>'></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${cards1}" var="t">
									<tr>
										<td><a href='<spring:url value="/c/${t.card.id}"/>'>${t.card.name}</a> <c:if test="${t.foil}"><img src='<spring:url value="/img/foil-inline.gif"/>'></c:if></td>
										<td>${t.mkmPrice.price} ${t.mkmPrice.source.currency}</td>
										<td>${t.mtgGoldFishPrice.price} ${t.mtgGoldFishPrice.source.currency}</td>
										<td>${t.mtgGoldFishTxPrice.price} ${t.mtgGoldFishTxPrice.source.currency}</td>
										<td>
											<a href='<spring:url value="/t/rm/${t.card.id}"/>'><i class="fa fa-times" aria-hidden="true"></i></a>
											<a href='<spring:url value="/t/right/${t.card.id}"/>'><i class="fa fa-arrow-circle-right" aria-hidden="true"></i></a>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<td><fmt:message bundle="${bundle}" key="TRADE_TOTAL"/></td>
									<td>${total1.mkm}</td>
									<td>${total1.mtgGoldFish}</td>
									<td>${total1.mtgGoldFishTx}</td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</section>
				</article>
				<article>
					<section>
						<table>
							<thead>
								<tr>
									<th></th>
									<th>Card</th>
									<th><img src='<spring:url value="/img/mkm.gif"/>'></th>
									<th><img src='<spring:url value="/img/MtgGoldFish.gif"/>'></th>
									<th><img src='<spring:url value="/img/MtgGoldFish.gif"/>'></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${cards2}" var="t">
									<tr>
										<td>
											<a href='<spring:url value="/t/left/${t.card.id}"/>'><i class="fa fa-arrow-circle-left" aria-hidden="true"></i></a>
											<a href='<spring:url value="/t/rm/${t.card.id}"/>'><i class="fa fa-times" aria-hidden="true"></i></a>
										</td>
										<td><a href='<spring:url value="/c/${t.card.id}"/>'>${t.card.name}</a> <c:if test="${t.foil}"><img src='<spring:url value="/img/foil-inline.gif"/>'></c:if></td>
										<td>${t.mkmPrice.price} ${t.mkmPrice.source.currency}</td>
										<td>${t.mtgGoldFishPrice.price} ${t.mtgGoldFishPrice.source.currency}</td>
										<td>${t.mtgGoldFishTxPrice.price} ${t.mtgGoldFishTxPrice.source.currency}</td>
									</tr>
								</c:forEach>
								<tr>
									<td></td>
									<td>Total</td>
									<td>${total2.mkm}</td>
									<td>${total2.mtgGoldFish}</td>
									<td>${total2.mtgGoldFishTx}</td>
								</tr>
							</tbody>
						</table>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>