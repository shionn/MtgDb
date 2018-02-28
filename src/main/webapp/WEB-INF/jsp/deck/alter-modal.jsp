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
	<section>
		TODO
	</section>
</article>