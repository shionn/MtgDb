<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<article class="portlet small">
	<header><spring:message code="DECK_EDIT_TITLE"/> ${deck.name}</header>
	<section>
		<spring:url value="/d/edit/${deck.id}" var="action" />
		<form:form action="${action}" method="POST" class="row">
			<div>
				<label><spring:message code="DECK_EDIT_NAME"/></label>
				<input type="text" name="name" value="${deck.name}">
			</div>

			<div>
				<label><spring:message code="DECK_EDIT_FORMAT"/></label>
				<div class="btn-select">
					<button type="button" class="secondary"><spring:message code="DeckType.${deck.type}"/></button>
					<input type="hidden" name="type" value="${deck.type}">
					<ul>
						<c:forEach items="${types}" var="type">
							<li><a href="#${type}"><spring:message code="DeckType.${type}"/></a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div>
				<input type="submit" class="closeModal" value="<spring:message code="DECK_EDIT_SUBMIT"/>"/>
			</div>
		</form:form>
	</section>
</article>