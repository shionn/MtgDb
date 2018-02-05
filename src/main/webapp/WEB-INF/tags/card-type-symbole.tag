<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="card" type="tcg.db.dbo.Card" %>
<c:if test="${card.creature}"><i class="mi mi-creature" title="creature"></i></c:if>
<c:if test="${card.enchantment}"><i class="mi mi-enchantment" title="enchantment"></i></c:if>
<c:if test="${card.planeswalker}"><i class="mi mi-planeswalker" title="planeswalker"></i></c:if>
<c:if test="${card.instant}"><i class="mi mi-instant" title="instant"></i></c:if>
<c:if test="${card.sorcery}"><i class="mi mi-sorcery" title="sorcery"></i></c:if>
<c:if test="${card.land}"><i class="mi mi-land" title="land"></i></c:if>
<c:if test="${card.artifact}"><i class="mi mi-artifact" title="artifact"></i></c:if>
