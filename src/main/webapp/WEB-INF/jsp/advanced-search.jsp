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
								<li>${f.type}: ${f.value}</li>
							</c:forEach>
						</ul>
					</section>
					<section>

					</section>
				</article>
			</section>

		</article>
	</jsp:attribute>
</t:template>