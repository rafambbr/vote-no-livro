<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<h2>Vote no Livro</h2>
<p>Gostariamos de saber quais são os livros que você mais gosta entre as opções mostradas abaixo, para isso selecione a sua opção favorita entre as mostradas abaixo, quando você escolher um livro(clicando sobre a foto da capa dele) outras sugestões serão exibidas na tela.</p>

<c:if test="${fn:length(livros) >= 0}">
	<h3>Livros:</h3>
	<table>
		<tr>
			<c:forEach items="${livros}" var="livro">
			<td align="center">
				<figure class="voto-livro" title="Clique aqui para votar nesse livro!">
					<a href="#" class="linkVotarLivro" livro="${livro.idLivro}">
						<img src="resources/img/${livro.fotoCapa}">
					</a>
				</figure>
			</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach items="${livros}" var="livro">
			<td>
				<table class="tb-info-livro">
					<tr>
						<td>Titulo</td>
						<td>${livro.titulo}</td>
					</tr>
					<tr>
						<td>ISBN</td>
						<td>${livro.isbn}</td>
					</tr>
					<tr>
						<td>Paginas</td>
						<td>${livro.paginas}</td>
					</tr>
					<tr>
						<td>Editora</td>
						<td>${livro.editora}</td>
					</tr>
				</table>
			</td>
			</c:forEach>
		</tr>
	</table>
</c:if>

<c:if test="${fn:length(livros) < 2}">

	<c:if test="${!usuarioJaVotou}">
	<h3>Obrigado pelos votos!</h3>
	<p>Digite seu nome e e-mail em seguida clique em "SALVAR" para salvar seus votos.</p>
	
	<form id="formSalvaVotosUsuario">
		<table>
			<tr>
				<td>Nome:</td>
				<td><input type="text" name="nome"></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input type="text" name="email"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="Salvar" class="btnSalvarVotosUsuario"></td>
			</tr>
		</table>
	</form>
	</c:if>
	
</c:if>

<c:if test="${usuarioJaVotou}">
	<h3><b>Você já votou nos livros!</b></h3>
	<p>Para ver a lista de livros mais votados clique <a href="livro/carregar/ranking" class="linkConteudo">aqui</a> ou acesse pelo <b>"MENU --> Mais Votados"</b>.</p>
</c:if>

<script src="resources/js/conteudo.js"></script>
	