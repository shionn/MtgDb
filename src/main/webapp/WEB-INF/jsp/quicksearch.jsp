<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul>
	<c:forEach items="${cards}" varStatus="i" var="c">
		<li <c:if test="${i.index==0}">class="select"</c:if>>
			<a href="<spring:url value="/c/${c.id}"/>">${c.name} - ${c.lang('fr').name}</a>
		</li>
	</c:forEach>
	<c:if test="${empty cards}">
		<li><spring:message code="QUICK_SEARCH_NO_RESULT"/></li>
	</c:if>
</ul>