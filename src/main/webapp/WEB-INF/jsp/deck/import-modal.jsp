<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<article class="portlet">
	<header><spring:message code="DECK_IMPORT_TITLE"/> ${deck.name}</header>
	<section>
		<spring:url value="/d/import/${deck.id}?${_csrf.parameterName}=${_csrf.token}" var="action" />
		<form:form action="${action}" method="POST" enctype="multipart/form-data" >
			<label><spring:message code="DECK_IMPORT_FORMAT"/></label>
			<div class="btn-select">
				<button type="button" class="secondary"><spring:message code="DeckExportFormat.${formats[0]}"/></button>
				<input type="hidden" name="format" value="${formats[0]}">
				<ul>
					<c:forEach items="${formats}" var="f">
						<li><a href="#${f}"><spring:message code="DeckExportFormat.${f}"/></a></li>
					</c:forEach>
				</ul>
			</div>
			<label><spring:message code="DECK_IMPORT_FILE"/></label>
			<div class="btn">
				<span><spring:message code="DECK_IMPORT_FILE_BUTTON"/></span>
				<input type="file" name="file">
			</div>
			<input type="submit" class="closeModal" value="<spring:message code="DECK_IMPORT_SUBMIT"/>"/>
		</form:form>
	</section>
	<section>
		TODO descriptif des format
	</section>
</article>