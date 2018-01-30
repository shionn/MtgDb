<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">Sign in</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container signin">
			<section>
				<article class="portlet">
					<header>Sign in</header>
					<section>
						<form method="POST" action='<spring:url value="/signin"/>'>
							<div>
								<label for="username">Email :</label> <input name="username" type="email" />
							</div>
							<div>
								<label for="password">Password :</label> <input name="password" type="password" />
							</div>
							<div>
								<input type="submit" value="Sign In" />
							</div>
						</form>
					</section>
					<footer>
						<a class="button grey" href='<spring:url value="/signup"/>'>Create an account</a>
						<a class="button grey" href='<spring:url value="/recover"/>'>Forgot password?</a>
					</footer>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>