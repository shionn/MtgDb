<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<ul>
	<c:forEach items="${prices}" var="p">
		<c:if test="${p.price != null}">
			<li>
				<a href="${p.link}" target="_blank">
					<img src='<spring:url value="/img/${p.source.icon}"/>' title='<fmt:formatDate value="${p.priceDate}"/>'>
					<c:if test="${p.source.foil}">
						<img src='<spring:url value="/img/foil.gif"/>'>
					</c:if>
				</a>
				${p.price} ${p.source.currency}
			</li>
		</c:if>
	</c:forEach>
</ul>
