<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="deck" type="tcg.db.dbo.Deck" %>
<%@ attribute name="user" type="tcg.security.User" %>
<%@ attribute name="entries" type="java.util.List" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<c:forEach items="${entries}" var="e">
	<tr>
		<td>
			${e.qty}
			<c:if test="${deck.user == user.user}">
				<a class="ajax" data-update="table"
						href='<spring:url value="/d/rm/${e.deck}/1/${e.card.id}/${e.category}/${e.foil}"/>'>
					<i class="fa fa-minus-circle"></i>
				</a>
			</c:if>
			<c:if test="${deck.user == user.user}">
				<a class="ajax" data-update="table"
						 href='<spring:url value="/d/add/${e.deck}/1/${e.card.id}/${e.category}/${e.foil}"/>'>
					<i class="fa fa-plus-circle"></i>
				</a>
			</c:if>
		</td>
		<td><a href='<spring:url value="/c/${e.card.id}"/>'>${e.card.name}</a></td>
		<td><t:card-type-symbole card="${e.card}"/><span>${e.card.type}</span></td>
		<td>
			<i class="ss ss-fw ss-grad ss-${e.card.rarity.ss} ss-${e.card.edition.icon}"></i>
			<c:if test="${deck.user == user.user}">
				<a href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>' class="modal"><i class="fa fa-cog"></i></a>
			</c:if>
		</td>
		<td>
			<c:if test="${e.foil}">
				<img src='<spring:url value="/img/foil.gif"/>'>
			</c:if>
		</td>
		<td>${e.tag}</td>
		<td>${e.card.manaCost}</td>
		<td>
			<c:if test="${deck.user == user.user}">
				<a class="modal"
						href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>'>
					<i class="fa fa-cog"></i>
				</a>
			</c:if>
		</td>
	</tr>
</c:forEach>
