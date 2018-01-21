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
								<input type="text" name="filter" placeholder="Advanced Search" data-source="<spring:url value="/as/filter"/>" autocomplete="off"/>
							</span>
						</form>
						<ul>
							<c:forEach items="${filters}" var="f">
								<li><a href="<spring:url value="/as/${f.type}/${f.value}"/>">${f.type}: ${f.value}</a></li>
							</c:forEach>
						</ul>
					</section>
				</article>
			</section>
			<section>
				<article class="portlet">
					<header>Result(s)</header>
					<section>
						<table>
							<c:forEach items="${cards}" var="c">
								<tr>
									<td><a href='<spring:url value="/c/${c.id}"/>'>${c.name}</a></td>
								</tr>
							</c:forEach>
						</table>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>