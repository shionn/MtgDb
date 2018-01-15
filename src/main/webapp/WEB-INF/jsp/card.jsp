<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">MtgDb by Shionn - ${card.name}</jsp:attribute>
	<jsp:attribute name="content">
		<section class="main container card">
			<header>${card.name}</header>
			<section class="editions">
				<ul>
					<c:forEach items="${card.printings}" var="p">
						<li<c:if test="${card.id == p.id}"> class="active"</c:if>><a href='<spring:url value="/c/${p.id}"/>'>${p.edition}</a></li>
					</c:forEach>
				</ul>
			</section>
			<section>
				<section class="image">img</section>
				<section class="text">text</section>
				<section class="prices">prix</section>
			</section>
			<footer>
				<ul>
					<li>rules</li>
				</ul>
			</footer>
		</section>
	</jsp:attribute>
</t:template>