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
					<header>Password Updated</header>
					<section>
						<p>Your password is updated. Try to connect by following this <a href='<spring:url value="/signin"/>'>link</a>.</p>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>