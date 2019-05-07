<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">deck ${deck.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container deck-flat">
			<section>
				<article class="portlet nav">
					<t:deck-nav deck="${deck}"/>
					<section class="deck-list">
						<ul>
							<c:forEach items="${deck.cards}" var="e">
								<li>
									<c:forEach begin="1" end="${e.qty}" var="i">
										<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
									</c:forEach>
								</li>
							</c:forEach>
						</ul>
						<p style="text-align: center">TODO</p>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>