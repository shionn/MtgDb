<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">Sign up</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container signup">
			<section>
				<article class="portlet">
					<header>Sign Up</header>
					<section>
						<spring:url value="/signup" var="action"/>
						<form:form method="POST" action='${action}'>
							<div>
								<label for="email">Email :</label> <input name="email" type="email" />
							</div>
							<div>
								<label for="password">Password :</label> <input name="password" type="password" />
							</div>
							<div>
								<label for="confirm">Confirm :</label> <input name="confirm" type="password" />
							</div>
							<div>
								<input type="submit" value="Sign Up" />
							</div>
						</form:form>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>