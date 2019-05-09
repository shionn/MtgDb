<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
	<jsp:attribute name="title">Home</jsp:attribute>
	<jsp:attribute name="content">
		<article class="main container">
			<section>
				<article class="portlet">
					<header>Version BETA</header>
					<section>
						<ul>
							<li>06/05/2019 : Mise à jour vers MtgJson 4 (enfin !, des liens de cartes sont peut être cassé)</li>
							<li>17/08/2018 : Amélioration du crawler de prix</li>
							<li>13/08/2018 : Ajout d'une page prix pour un deck</li>
							<li>09/08/2018 : Ajout d'une page liste des éditions</li>
							<li>06/05/2018 : Possibilité d'éditer des tags sur un deck</li>
							<li><a href="http://dev.shionn.org/dev" target="_blank">Ancienne version disponible ici</a></li>
						</ul>
					</section>
				</article>
				<article class="portlet">
					<header>Crédit</header>
					<section>
						<p>Ce site utilise des ressources fournit par d'autres personne(s) :</p>
						<ul>
							<li><a href="https://github.com/AlexandreArpin/mtg-font/" target="_blank">Mtg font (symbole de mana)</a></li>
							<li><a href="https://github.com/andrewgioia/Keyrune" target="_blank">Keyrune (symbole d'édition)</a></li>
							<li><a href="https://github.com/andrewgioia/Mana" target="_blank">Mana (symbole de guilde et d'autres divers)</a></li>
							<li><a href="https://mtgjson.com/" target="_blank">MtgJson (Pour la bdd des cartes)</a></li>
							<li><a href="https://scryfall.com/" target="_blank">Scryfall (Pour les images)</a></li>
							<li><a href="http://flag-icon-css.lip.is/" target="_blank">Flag-icon-css (Pour les drapeaux)</a></li>
						</ul>
					</section>
				</article>
			</section>
		</article>
	</jsp:attribute>
</t:template>