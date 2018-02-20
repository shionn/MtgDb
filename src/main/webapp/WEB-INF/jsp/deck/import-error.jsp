<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">Import errors</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container">
			<section>
				<article class="portlet">
					<header><spring:message code="DECK_IMPORT_ERROR"/></header>
					<section>
						<c:forEach items="${errors}" var="e">
							<p>${e}</p>
						</c:forEach>
						<p><spring:message code="DECK_IMPORT_OTHERS"/></p>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>