<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">MtgDb by Shionn - ${card.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container card">
			<header>${card.name}</header>
			<section class="editions">
				<ul>
					<c:forEach items="${card.printings}" var="p">
						<li<c:if test="${card.id == p.id}"> class="active"</c:if>><a href='<spring:url value="/c/${p.id}"/>'>${p.edition}</a></li>
					</c:forEach>
				</ul>
			</section>
			<section class="main">
				<article class="image portlet">
					<header>${card.name}</header>
					<section>
						<img src='<spring:url value="/c/img/${card.id}.jpg"/>'/>
					</section>
					<footer></footer>
				</article>
				<article class="text">text</article>
				<article class="prices portlet">
					<header>prix</header>
					<section>
						todo
					</section>
					<footer></footer>
				</article>
			</section>
			<footer>
				<ul>
					<li>rules</li>
				</ul>
			</footer>
		</article>
	</jsp:attribute>
</t:template>