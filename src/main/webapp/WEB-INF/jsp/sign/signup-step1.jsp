<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">Sign up</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container signup">
			<section>
				<article class="portlet">
					<header><spring:message code="SIGNUP"/></header>
					<section>
						<spring:url value="/signup" var="action"/>
						<form:form method="POST" action="${action}" class="row">
							<div>
								<label for="email"><spring:message code="SIGNUP_EMAIL"/></label>
								<input name="email" type="email" value="${email}"/>
							</div>
							<c:if test="${error == 'emailAlreadyTaken' }">
								<div class="error"><spring:message code="SignUpError.${error}"/></div>
							</c:if>
							<div>
								<label for="password"><spring:message code="SIGNUP_PASSWORD"/></label>
								<input name="password" type="password" min="5" required="required"/>
							</div>
							<div>
								<label for="confirm"><spring:message code="SIGNUP_PASSWORD_CONFIRM"/></label>
								<input name="confirm" type="password" min="5" required="required"/>
							</div>
							<c:if test="${error == 'passwordDontMatch' }">
								<div class="error"><spring:message code="SignUpError.${error}"/></div>
							</c:if>
							<div>
								<input type="submit" value="<spring:message code="SIGNUP_SUBMIT"/>" />
							</div>
						</form:form>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>