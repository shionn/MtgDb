<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">deck list</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container deck-list">
			<section>
				<article class="portlet left">
					<header><spring:message code="DECK_LIST_CREATE"/></header>
					<section>
						<form:form method="POST">
							<input type="text" placeholder="<spring:message code="DECK_LIST_CREATE_NAME"/>" name="name">
							<div class="btn-select">
								<button type="button" class="secondary"><spring:message code="DECK_LIST_CREATE_FORMAT"/></button>
								<input type="hidden" name="type" value="none">
								<ul>
									<c:forEach items="${types}" var="type">
										<li><a href="#${type}"><spring:message code="DeckType.${type}"/></a></li>
									</c:forEach>
								</ul>
							</div>
							<input type="submit" value="<spring:message code="DECK_LIST_CREATE_SUBMIT"/>"/>
						</form:form>
					</section>
				</article>
			</section>
			<section>
				<article class="portlet">
					<section>
						<table>
							<thead>
								<tr>
									<th><spring:message code="DECK_LIST_NAME"/></th>
									<th><spring:message code="DECK_LIST_TYPE"/></th>
									<th>#</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${decks}" var="deck">
									<tr>
										<td><a href='<spring:url value="/d/${deck.id}"/>'>${deck.name}</a></td>
										<td><spring:message code="DeckType.${deck.type}"/></td>
										<td>
											<div class="btn-select">
												<button type="button" class="secondary"><spring:message code="DECK_LIST_ACTION"/></button>
												<ul>
													<li><a href="#todo">Duplicate</a></li>
													<li><a href="#todo">Delete</a></li>
													<li><a class="modal" href='<spring:url value="/d/export/${deck.id}"/>'>Export</a></li>
													<li><a class="modal" href='<spring:url value="/d/import/${deck.id}"/>'>Import</a></li>
													<li><a href="#todo">Update Prices</a></li>
												</ul>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>