<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">deck ${deck.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container deck-list">
			<section>
				<article class="portlet nav">
					<header>
						<ul>
							<li><a href="#">Table Liste</a></li>
							<li><a href="#">Flat liste</a></li>
							<li><a href="#">Cube liste</a></li>
							<li><a href="#">Prix</a></li>
							<li><a href="#">Historique</a></li>
							<li><a href="#">Statistique</a></li>
						</ul>
					</header>
				</article>
				<section>
					<table>
						<thead>
							<tr>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty decks.mains}">
								<tr>
									<td colspan="5">
										<p>Rechercher une carte et cliquer sur Ajouter aux deck.
									</td>
								</tr>
							</c:if>
							<c:forEach items="${decks.mains}" var="e">
								<tr>
									<td>${e.qty}</td>
									<td>${e.card.name}</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</c:forEach>
						</tbody>
						<thead>
							<tr>
								<th rowspan="5">Sideboard</th>
							<tr>
						</thead>
					</table>
				</section>
			</section>
		</article>
	</jsp:attribute>
</t:template>