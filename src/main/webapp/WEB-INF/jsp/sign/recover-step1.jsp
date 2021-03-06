<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">account recover</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container signup">
			<section>
				<article class="portlet">
					<header><spring:message code="SIGN_RECOVER"/></header>
					<section>
						<spring:url value="/recover" var="action"/>
						<form:form method="POST" action='${action}' class="row">
							<div>
								<label for="email"><spring:message code="SIGN_RECOVER_EMAIL"/></label>
								<input name="email" type="email" value="${email}"/>
							</div>
							<c:if test="${error == 'emailNotUsed' }">
								<div class="error"><spring:message code="SignUpError.${error}"/></div>
							</c:if>
							<div>
								<input type="submit" value="<spring:message code="SIGN_RECOVER_SUBMIT"/>" />
							</div>
						</form:form>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>