<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title"><spring:message code="PAGE_EDITION_ADM"/></jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container">
			<section>
				<article class="portlet">
					<header><spring:message code="ADM_EDITION_DROP_TITLE"/></header>
					<section>
						<spring:url value="/adm/edition/drop" var="url"/>
						<form:form method="POST" action="${url}" class="row">
							<spring:message code="ADM_EDITION_DROP_CHOICE"/>
							<select name="deleted">
								<c:forEach items="${editiondeletable}" var="e">
									<option value="${e.code}">${e.code} ${e.name}</option>
								</c:forEach>
							</select><br>
							<spring:message code="ADM_EDITION_DROP_REPLACE"/>
							<select name="replaced">
								<c:forEach items="${editionreplacable}" var="e">
									<option value="${e.code}">${e.code} ${e.name}</option>
								</c:forEach>
							</select>
							<input type="submit">
						</form:form>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>