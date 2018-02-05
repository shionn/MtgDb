<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">deck ${deck.name}</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container deck-table">
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
					<section>
						<table style="margin-top:5px">
							<thead>
								<tr>
									<th></th>
									<th>Name</th>
									<th>Type</th>
									<th colspan="2">Edition</th>
									<th>Manacost</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${empty deck.mains}">
									<tr>
										<td colspan="7">
											<p>Votre deck est vide, rechercher une carte et cliquer sur <em>Ajouter</em> dans le groupe <em>Deck</em>.</p>
										</td>
									</tr>
								</c:if>
								<c:forEach items="${deck.mains}" var="e">
									<tr>
										<td>${e.qty}</td>
										<td><a href='<spring:url value="/c/${e.card.id}"/>'>${e.card.name}</a></td>
										<td><t:card-type-symbole card="${e.card}"/> ${e.card.type}</td>
										<td><i class="ss ss-fw ss-grad ss-${e.card.rarity.ss} ss-${e.card.edition.icon}"></i></td>
										<td>
											<c:if test="${e.foil}">
												<img src='<spring:url value="/img/foil.gif"/>'>
											</c:if>
										</td>
										<td>${e.card.manaCost}</td>
										<td>
											<div class="btn-select">
												<button type="button" class="secondary">Action</button>
												<ul>
													<li><a href="/todo">Add One</a></li>
													<li><a href="/todo">Remove One</a></li>
													<li><a href="/todo">Remove All</a></li>
													<li><a href="/todo">Move one to Side</a></li>
													<li><a href="/todo">Move all Side</a></li>
													<li><a href="/todo">Change edition</a></li>
													<li><a href="/todo">Set foiled</a></li>
												</ul>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<thead>
								<tr>
									<th colspan="7">Sideboard</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${deck.sides}" var="e">
									<tr>
										<td>${e.qty}</td>
										<td><a href='<spring:url value="/c/${e.card.id}"/>'>${e.card.name}</a></td>
										<td><t:card-type-symbole card="${e.card}"/> ${e.card.type}</td>
										<td><i class="ss ss-fw ss-grad ss-${e.card.rarity.ss} ss-${e.card.edition.icon}"></i></td>
										<td>
											<c:if test="${e.foil}">
												<img src='<spring:url value="/img/foil.gif"/>'>
											</c:if>
										</td>
										<td>${e.card.manaCost}</td>
										<td>
											<div class="btn-select">
												<button type="button" class="secondary">Action</button>
												<ul>
													<li><a href="/todo">Add One</a></li>
													<li><a href="/todo">Remove One</a></li>
													<li><a href="/todo">Remove All</a></li>
													<li><a href="/todo">Move one to Main</a></li>
													<li><a href="/todo">Move all Main</a></li>
													<li><a href="/todo">Change edition</a></li>
													<li><a href="/todo">Set foiled</a></li>
												</ul>
											</div>
										</td>
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