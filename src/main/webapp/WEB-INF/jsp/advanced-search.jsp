<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">Advanced Search</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container advanced-search">
			<section>
				<article class="portlet left">
					<header>Filters</header>
					<section>
						<form action='<spring:url value="/as/filter"/>'>
							<span class="autocomplete">
								<input type="text" name="filter" placeholder="Advanced Search" data-source="<spring:url value="/as/filter"/>" autocomplete="off" data-length="1"/>
							</span>
						</form>
						<ul>
							<c:forEach items="${filters}" var="f">
								<li><a href="<spring:url value="/as/${f.type}/${f.value}"/>">${f.type}: ${f.display}</a></li>
							</c:forEach>
						</ul>
					</section>
				</article>
			</section>
			<c:if test="${not empty cards}">
				<section>
					<article class="portlet">
						<section>
							<table>
								<thead>
									<tr>
										<th>Name</th>
										<th>Name (fr)</th>
										<th>Types</th>
										<th>Mana Cost</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${cards}" var="c">
										<tr>
											<td><a href='<spring:url value="/c/${c.id}"/>'>${c.name}</a></td>
											<td><a href='<spring:url value="/c/${c.id}"/>'>${c.lang('fr').name}</a></td>
											<td>${c.type}</td>
											<td>${c.manaCost}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</section>
					</article>
				</section>
			</c:if>
		</article>
	</jsp:attribute>
</t:template>