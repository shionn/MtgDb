<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="deck" type="tcg.db.dbo.Deck" %>
<%@ attribute name="user" type="tcg.security.User" %>
<%@ attribute name="entries" type="java.util.List" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<ul>
	<c:forEach items="${entries}" var="e">
		<li data-card="${e.card.card}">
			<c:forEach begin="1" end="${e.qty}" var="i">
				<c:if test="${i==1 and deck.user == user.user}">
					<a href='<spring:url value="/d/alter/${e.deck}/${e.card.id}/${e.category}/${e.foil}"/>' class="modal"><i class="fa fa-cog"></i></a>
				</c:if>
				<img src='<spring:url value="/c/img/${e.card.id}.jpg"/>'/>
			</c:forEach>
		</li>
	</c:forEach>
</ul>
