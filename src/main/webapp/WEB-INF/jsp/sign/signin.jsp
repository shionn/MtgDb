<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">Sign in</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container signin">
			<section>
				<article class="portlet">
					<header><spring:message code="SIGNIN"/></header>
					<section>
						<form method="POST" action='<spring:url value="/signin"/>' class="row">
							<div>
								<label for="username"><spring:message code="SIGNIN_EMAIL"/></label>
								<input name="username" type="email" />
							</div>
							<div>
								<label for="password"><spring:message code="SIGNIN_PASSWORD"/></label>
								<input name="password" type="password" />
							</div>
							<div>
								<input type="submit" value="<spring:message code="SIGNIN_SUBMIT"/>" />
							</div>
						</form>
					</section>
					<footer>
						<a class="button grey" href='<spring:url value="/signup"/>'>
							<spring:message code="SIGNIN_SIGNUP"/>
						</a>
						<a class="button grey" href='<spring:url value="/recover"/>'>
							<spring:message code="SIGNIN_RECOVER"/>
						</a>
					</footer>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>