<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">Home</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container">
			<section style="padding:10px">
				Version BETA<br>
				locale : ${locale}<br>
				<a href="http://dev.shionn.org/dev" target="_blank">Ancienne version disponible ici</a>
			</section>
		</article>
	</jsp:attribute>
</t:template>