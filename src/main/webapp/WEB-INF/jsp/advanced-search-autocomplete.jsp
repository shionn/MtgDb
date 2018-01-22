<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul>
	<c:forEach items="${filters}" varStatus="i" var="f">
		<li <c:if test="${i.index==0}">class="select"</c:if>>
			<a href="<spring:url value="/as/${f.type}/${f.value}"/>">${f.type} - ${f.display}</a>
		</li>
	</c:forEach>
</ul>