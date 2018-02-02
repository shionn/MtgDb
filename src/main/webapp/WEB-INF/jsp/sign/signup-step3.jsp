<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">Sign up finish</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container signup">
			<section>
				<article class="portlet">
					<header><spring:message code="SIGNUP_ACTIVATE"/></header>
					<section><spring:message code="SIGNUP_ACTIVATE_TEXT"/></section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>