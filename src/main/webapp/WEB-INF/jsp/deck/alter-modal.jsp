<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<article class="portlet alter-deck-entry">
	<header><spring:message code="DECK_ALTER_ENTRY_EDITION" /></header>
	<section class="editions">
		<ul>
			<c:forEach items="${entry.card.printings}" var="p">
				<li<c:if test="${entry.card.id == p.id}"> class="active"</c:if>>
					<a href='<spring:url value="/d/printing/${entry.deck}/${entry.qty}/${entry.card.id}/${p.id}/${entry.category}/${entry.foil}"/>'
							class="ajax closeModal" data-update="section.cube li[data-card=${entry.card.card}]"
							title="${p.edition.name}">
						<i class="ss ss-${p.edition.icon}"></i>
					</a>
				</li>
			</c:forEach>
		</ul>
	</section>
	<header><spring:message code="DECK_ALTER_ENTRY_FOIL" /></header>
	<section>TODO

<%-- 		<img src='<spring:url value="/img/foil.gif"/>'> --%>
<!-- 		<a>Oui</a> -->
<!-- 		<a>Non</a> -->
	</section>
	<header><spring:message code="DECK_ALTER_ENTRY_TAGS" /></header>
		<spring:url value="/d/add-tag/${entry.deck}/${entry.card.id}/${entry.category}/${entry.foil}" var="action"/>
		<form:form action="${action}">
			<input type="text" required="required" name="tag"
				placeholder='<spring:message code="DECK_ALTER_ENTRY_ADD_TAG_PLACEHOLDER"/>'>
			<input type="submit" value='<spring:message code="DECK_ALTER_ENTRY_ADD_TAG_BUTTON"/>'>
		</form:form>
	<section>
	</section>
</article>