<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">deck list</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container deck-list">
			<section>
				<article class="portlet">
					<header>Deck List</header>
					<section>
						<form>
							<input type="text" placeholder="Name" name="name">
							<div class="btn-select">
								<button type="button" class="secondary">Format</button>
								<input type="hidden" name="type" value="none">
								<ul>
									<li><a href="#standart">Standart</a></li>
									<li><a href="#modern">Modern</a></li>
									<li><a href="#legacy">Legacy</a></li>
									<li><a href="#vintage">Vintage</a></li>
									<li><a href="#classic">Classic</a></li>
									<li><a href="#commander">Commander</a></li>
									<li><a href="#cube">Cube</a></li>
									<li><a href="#wagic">Wagic</a></li>
									<li><a href="#pauper">Pauper</a></li>
									<li><a href="#other">Other</a></li>
								</ul>
							</div>
							<button type="submit">Add new Deck</button>
						</form>
					</section>
					<section>
						<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Type</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${decks}" var="deck">
									<tr>
										<td><a href='<spring:url value="/deck/${deck.id}"/>'>${deck.name}</a></td>
										<td></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>