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
					<header>Account Recover</header>
					<section>
						<spring:url value="/recover/${email}/${key}" var="action"/>
						<form:form method="POST" action='${action}'>
							<input type="hidden" name="key" value="${key}">
							<div>
								<label for="email">Email :</label> <input name="email" type="email" value="${email}" readonly="readonly"/>
							</div>
							<div>
								<label for="password">New Password :</label> <input name="password" type="password" min="5" required="required"/>
							</div>
							<div>
								<label for="confirm">Confirm :</label> <input name="confirm" type="password" min="5" required="required"/>
							</div>
							<c:if test="${error == 'passwordDontMatch' }">
								<div class="error">Passwords didn't match.</div>
							</c:if>
							<div>
								<input type="submit" value="Recover" />
							</div>
						</form:form>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>