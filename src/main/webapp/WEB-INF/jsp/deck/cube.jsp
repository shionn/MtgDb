<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">deck ${deck.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container deck-cube">
			<section>
				<article class="portlet nav">
					<t:deck-nav deck="${deck}"/>
					<section class="deck-title">
						<p style="text-align: center">TODO</p>
					</section>
					<section class="cube">
						<c:forEach items="${colors}" var="color">
							<ul>
								<c:forEach items="${deck.mains(color)}" var="e">
									<li><img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/></li>
								</c:forEach>
							</ul>
						</c:forEach>
						<ul>
							<c:forEach items="${guilds}" var="color">
								<c:forEach items="${deck.mains(color)}" var="e">
									<li><img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/></li>
								</c:forEach>
							</c:forEach>
						</ul>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>