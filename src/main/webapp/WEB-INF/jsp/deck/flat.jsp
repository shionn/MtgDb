<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">deck ${deck.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container deck-table">
			<section>
				<article class="portlet nav">
					<t:deck-nav deck="${deck}"/>
					<section class="deck-title">
						<p style="text-align: center">TODO</p>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>