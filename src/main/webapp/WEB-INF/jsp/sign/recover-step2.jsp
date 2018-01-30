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
						<p>You should have received an email with the procedure to complete your account's reset.</p>
						<p>If you can not find the email, consider looking in your spam.</p>
						<p>If you encounter any problem, contact us at <a href="mailto:shionn@gmail.com">shionn@gmail.com</a></p>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>