<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title"><spring:message code="PAGE_EDITION_TITLE"/></jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container editions">
			<c:forEach items="${groups}" var="group">
				<section>
					<c:forEach items="${group}" var="block">
						<c:if test="${not empty block.editions}">
							<article class="portlet">
								<header>${block.name}</header>
								<section>
									<ul>
										<c:forEach items="${block.editions}" var="e">
											<li>
												<i class="${e.iconClass}" title="${e.code}"></i>
												<a href="<spring:url value="/as/ed/${e.code}"/>">${e.simplifiedName}</a>
											</li>
										</c:forEach>
									</ul>
								</section>
							</article>
						</c:if>
					</c:forEach>
				</section>
			</c:forEach>
			<section>
				<table>
					<tr>
						<th colspan="3">Others (${editions.size()})</th>
					</tr>
					<c:forEach items="${editions}" var="e">
						<tr>
							<td><i class="${e.iconClass}" title="${e.code}"></i></td>
							<td><a href="<spring:url value="/as/ed/${e.code}"/>">${e.name} (${e.type},${e.parentCode},${e.block})</a></td>
							<td><fmt:formatDate value="${e.releaseDate}" /></td>
						</tr>
					</c:forEach>
				</table>
			</section>
		</article>
	</jsp:attribute>
</t:template>