<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<table>
	<c:forEach items="${items}" var="e">
		<tr data-card="${e.card.id}" data-category="${e.category}">
			<td data-type="qty">${e.qty}</td>
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
					<button type="button" class="secondary">Action</button>
					<ul>
						<li><a class="ajax add-one" href='<spring:url value="/d/add/1/${e.card.id}/${e.category}/${e.foil}"/>'>Add One</a></li>
						<li><a class="ajax rm-one" href='<spring:url value="/d/rm/1/${e.card.id}/${e.category}/${e.foil}"/>'>Remove One</a></li>
						<li><a class="ajax rm-all" href='<spring:url value="/d/rm/all/${e.card.id}/${e.category}/${e.foil}"/>'>Remove All</a></li>
						<li><a class="ajax mv" href='<spring:url value="/d/mv/1/${e.card.id}/${e.category}/${e.foil}/side"/>'>Move one to Side</a></li>
						<li><a class="ajax" href="/todo">Move all Side</a></li>
						<!-- <li><a class="ajax" href="/todo">Change edition</a></li> -->
						<!-- <li><a class="ajax" href="/todo">Set foiled</a></li> -->
	<!-- 													<li><a href="/todo">Move one to Main</a></li> -->
	<!-- 													<li><a href="/todo">Move all Main</a></li> -->
					</ul>
				</div>
			</td>
		</tr>
	</c:forEach>
</table>