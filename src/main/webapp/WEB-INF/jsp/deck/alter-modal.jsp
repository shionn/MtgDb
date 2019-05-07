<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<article class="portlet alter-deck-entry">
	<header><spring:message code="DECK_ALTER_ENTRY_MOVE" /></header>
	<section>
		<p style="text-align: center">
			<a class="ajax button closeModal" data-update="table.deck,section.deck-title,section.deck-list"
					href='<spring:url value="/d/add/${entry.deck}/1/${entry.card.id}/${entry.category}/${entry.foil}"/>'>
				<spring:message code="DECK_ALTER_ENTRY_ADD_ONE"/>
			</a>
			<a class="ajax button closeModal" data-update="table.deck,section.deck-title,section.deck-list"
					href='<spring:url value="/d/rm/${entry.deck}/1/${entry.card.id}/${entry.category}/${entry.foil}"/>'>
				<spring:message code="DECK_ALTER_ENTRY_RM_ONE"/>
			</a>
			<a class="ajax button closeModal" data-update="table.deck,section.deck-title,section.deck-list"
					href='<spring:url value="/d/rm/${entry.deck}/${entry.qty}/${entry.card.id}/${entry.category}/${entry.foil}"/>'>
				<spring:message code="DECK_ALTER_ENTRY_RM_ALL"/>
			</a>
		</p>
		<c:if test='${entry.category != "side"}'>
			<p style="text-align: center">
				<a class="ajax button closeModal" data-update="table.deck,section.deck-title,section.deck-list"
						href='<spring:url value="/d/mv/${entry.deck}/1/${entry.card.id}/${entry.category}/${entry.foil}/side"/>'>
					<spring:message code="DECK_ALTER_ENTRY_MV_ONE_SIDE"/>
				</a>
				<a class="ajax button closeModal" data-update="table.deck,section.deck-title,section.deck-list"
						href='<spring:url value="/d/mv/${entry.deck}/${entry.qty}/${entry.card.id}/${entry.category}/${entry.foil}/side"/>'>
					<spring:message code="DECK_ALTER_ENTRY_MV_ALL_SIDE"/>
				</a>
			</p>
		</c:if>
		<c:if test='${entry.category != "main"}'>
			<p style="text-align: center">
				<a class="ajax button closeModal" data-update="table.deck,section.deck-title,section.deck-list"
						href='<spring:url value="/d/mv/${entry.deck}/1/${entry.card.id}/${entry.category}/${entry.foil}/main"/>'>
					<spring:message code="DECK_ALTER_ENTRY_MV_ONE_MAIN"/>
				</a>
				<a class="ajax button closeModal" data-update="table.deck,section.deck-title,section.deck-list"
						href='<spring:url value="/d/mv/${entry.deck}/${entry.qty}/${entry.card.id}/${entry.category}/${entry.foil}/main"/>'>
					<spring:message code="DECK_ALTER_ENTRY_MV_ALL_MAIN"/>
				</a>
			</p>
		</c:if>
		<c:if test='${entry.category != "commander" && deck.type == "commander"}'>
			<p style="text-align: center">
				<a class="ajax button closeModal" data-update="table.deck,section.deck-title,section.deck-list"
						href='<spring:url value="/d/mv/${entry.deck}/${entry.qty}/${entry.card.id}/${entry.category}/${entry.foil}/commander"/>'>
					<spring:message code="DECK_ALTER_ENTRY_MV_COMMANDER"/>
				</a>
			</p>
		</c:if>
	</section>
	<header><spring:message code="DECK_ALTER_ENTRY_EDITION" /></header>
	<section class="editions">
		<div>
			<ul>
				<c:forEach items="${entry.card.printings}" var="p">
					<li<c:if test="${entry.card.id == p.id}"> class="active"</c:if>>
						<a href='<spring:url value="/d/printing/${entry.deck}/${entry.qty}/${entry.card.id}/${p.id}/${entry.category}/${entry.foil}"/>'
								class="ajax closeModal"
								data-update="section.cube li[data-card=${entry.card.card}],table.deck,section.deck-title,section.deck-list"
								title="${p.edition.name}">
							<i class="${p.edition.iconClass}"></i>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</section>
	<header><spring:message code="DECK_ALTER_ENTRY_FOIL" /></header>
	<section>
		<p style="text-align: center">
			<c:if test="${entry.foil}">
				<a href='<spring:url value="/d/foil/${entry.deck}/1/${entry.card.id}/${entry.category}/${entry.foil}"/>'
						class="ajax closeModal button"
						data-update="section.cube li[data-card=${entry.card.card}],table.deck,section.deck-title,section.deck-list">
					<spring:message code="DECK_ALTER_ENTRY_RM_ONE_FOIL"/>
				</a>
			</c:if>
			<c:if test="${not entry.foil}">
				<a href='<spring:url value="/d/foil/${entry.deck}/1/${entry.card.id}/${entry.category}/${entry.foil}"/>'
						class="ajax closeModal button"
						data-update="section.cube li[data-card=${entry.card.card}],table.deck,section.deck-title,section.deck-list">
					<spring:message code="DECK_ALTER_ENTRY_ADD_ONE_FOIL"/>
				</a>
			</c:if>
		</p>
	</section>
	<header><spring:message code="DECK_ALTER_ENTRY_TAGS" /></header>
	<section>
		<div style="text-align: center;">
			<c:forEach items="${entry.tags}" var="tag">
				<a class="button grey"
						data-update="table.deck"
						href="<spring:url value="/d/tag/${entry.deck}/${entry.card.id}/${entry.category}/${entry.foil}/${tag}"/>">${tag}</a>
			</c:forEach>
		</div>
	</section>
	<section style="text-align: center;">
		<spring:url value="/d/tag/${entry.deck}/${entry.card.id}/${entry.category}/${entry.foil}" var="action"/>
		<form:form action="${action}" class="ajax"
				data-update="table.deck"
				method="GET">
			<input type="text" required="required" name="tag"
				placeholder='<spring:message code="DECK_ALTER_ENTRY_ADD_TAG_PLACEHOLDER"/>'>
			<input type="submit" class="closeModal" value='<spring:message code="DECK_ALTER_ENTRY_ADD_TAG_BUTTON"/>'>
		</form:form>
	</section>
</article>