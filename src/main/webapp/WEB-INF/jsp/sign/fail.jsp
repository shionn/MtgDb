<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">fail</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container signup">
			<section>
				<article class="portlet">
					<header><spring:message code="SIGN_FAIL"/></header>
					<section><spring:message code="SIGN_FAIL_TEXT"/></section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>