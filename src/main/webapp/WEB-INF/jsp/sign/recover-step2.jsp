<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">account recover</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container signup">
			<section>
				<article class="portlet">
					<header><spring:message code="SIGN_RECOVER_CONFIRM"/></header>
					<section><spring:message code="SIGN_RECOVER_CONFIRM_TEXT"/></section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>