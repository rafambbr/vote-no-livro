<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<h2>Mais Votados</h2>
<p>Abaixo esta sendo exibido a lista dos livros mais votados, para votar nos livros clique <a href="livro/carregar/naovotados" class="linkConteudo">aqui</a> ou acesse pelo <b>"MENU --> Votar no Livro"</b>. </p>

<h3>Ranking</h3>

<span class="listaRanking">
<c:if test="${fn:length(rankingLivrosUsuario) >= 1}">
<p>Livros votados por você:</p>
<table class="tb-ranking-livro">
	<tr>
		<th>Capa</th>
		<th>Livro</th>
	</tr>
	<c:forEach items="${rankingLivrosUsuario}" var="rankingUsuario">
	<tr>
		<td>
			<figure>
				<img src="resources/img/miniaturas/${rankingUsuario.livro.fotoCapa}">
			</figure>
		</td>
		<td>${rankingUsuario.livro.titulo}</td>
	</tr>
	</c:forEach>
</table>
<br/>
<br/>
<br/>
</c:if>

<c:if test="${fn:length(rankingLivros) >= 1}">
<p>Livros votados por todas as pessoas que utilizaram o sistema:</p>
<table class="tb-ranking-livro">
	<tr>
		<th>Capa</th>
		<th>Livro</th>
		<th align="center">Votos</th>
	</tr>
	<c:forEach items="${rankingLivros}" var="ranking">
	<tr>
		<td>
			<figure>
				<img src="resources/img/miniaturas/${ranking.livro.fotoCapa}">
			</figure>
		</td>
		<td>${ranking.livro.titulo}</td>
		<td align="center">${ranking.totalVotos}</td>
	</tr>
	</c:forEach>
</table>
</c:if>
</span>

<c:if test="${fn:length(rankingLivros) <= 0}">
	<span style="center">
		<p><b>Nenhum livro foi votado ainda, clique <a href="livro/carregar/naovotados" class="linkConteudo">aqui</a> para votar!</b></p>
	</span>
</c:if>

<script src="resources/js/conteudo.js"></script>