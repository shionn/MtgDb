<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<article class="portlet alter-deck-entry">
	<header><spring:message code="DECK_ALTER_ENTRY_EDITION" /></header>
	<section class="editions">
		<div>
			<ul>
				<c:forEach items="${entry.card.printings}" var="p">
					<li<c:if test="${entry.card.id == p.id}"> class="active"</c:if>>
						<a href='<spring:url value="/d/printing/${entry.deck}/${entry.qty}/${entry.card.id}/${p.id}/${entry.category}/${entry.foil}"/>'
								class="ajax closeModal"
								data-update="section.cube li[data-card=${entry.card.card}],table.deck-table"
								title="${p.edition.name}">
							<i class="ss ss-${p.edition.icon}"></i>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</section>
	<header><spring:message code="DECK_ALTER_ENTRY_FOIL" /></header>
	<section>
		<p>TODO</p>
<%-- 		<img src='<spring:url value="/img/foil.gif"/>'> --%>
<!-- 		<a>Oui</a> -->
<!-- 		<a>Non</a> -->
	</section>
	<header><spring:message code="DECK_ALTER_ENTRY_TAGS" /></header>
	<section>
		<div>
			<c:forEach items="${entry.tags}" var="tag">
				<a class="button grey" 
						data-update=".deck-table table"
						href="<spring:url value="/d/tag/${entry.deck}/${entry.card.id}/${entry.category}/${entry.foil}/${tag}"/>">${tag}</a>
			</c:forEach>
		</div>
	</section>
	<section>
		<spring:url value="/d/tag/${entry.deck}/${entry.card.id}/${entry.category}/${entry.foil}" var="action"/>
		<form:form action="${action}" class="ajax" 
				data-update=".deck-table table"
				method="GET">
			<input type="text" required="required" name="tag"
				placeholder='<spring:message code="DECK_ALTER_ENTRY_ADD_TAG_PLACEHOLDER"/>'>
			<input type="submit" class="closeModal" value='<spring:message code="DECK_ALTER_ENTRY_ADD_TAG_BUTTON"/>'>
		</form:form>
	</section>
</article>