<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">Editions</jsp:attribute>
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
												<i class="${e.iconClass}"></i>
												<a href="<spring:url value="/as/ed/${e.code}"/>">${e.name}</a>
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
					<c:forEach items="${editions}" var="e">
						<tr>
							<td><i class="${e.iconClass}"></i></td>
							<td>${e.name}</td>
							<td><fmt:formatDate value="${e.releaseDate}" /></td>
						</tr>
					</c:forEach>
				</table>
			</section>
		</article>
	</jsp:attribute>
</t:template>