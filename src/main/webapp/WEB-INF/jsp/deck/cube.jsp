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
								<li class="title">${color}</li>
								<c:forEach items="${deck.cubeColor(color)}" var="e">
									<li>
										<a href="#"><i class="fa fa-cog"></i></a>
										<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
									</li>
								</c:forEach>
							</ul>
						</c:forEach>
						<ul>
							<li class="title">Golds</li>
							<c:forEach items="${guilds}" var="color">
								<li class="title">${color}</li>
								<c:forEach items="${deck.cubeColor(color)}" var="e">
									<li>
										<a href="#"><i class="fa fa-cog"></i></a>
										<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
									</li>
								</c:forEach>
							</c:forEach>
						</ul>
						<ul>
							<li class="title">Colorless</li>
							<c:forEach items="${deck.cubeColor('none')}" var="e">
								<li>
									<a href="#"><i class="fa fa-cog"></i></a>
									<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
								</li>
							</c:forEach>
						</ul>
						<ul>
							<li class="title">Lands</li>
							<c:forEach items="${deck.cubeLand()}" var="e">
								<li>
									<a href="#"><i class="fa fa-cog"></i></a>
									<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
								</li>
							</c:forEach>
						</ul>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>