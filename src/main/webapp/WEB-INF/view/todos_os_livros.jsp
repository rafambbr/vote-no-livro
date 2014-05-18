<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Insert title here</title>
</head>
<body>

	TODOS OS LIVROS
	<table>
		<c:forEach items="${livros}" var="livro">
			<tr>
				<td>${livro.idLivro}</td>
				<td>${livro.titulo}</td>
				<td>${livro.editora}</td>
				<td>${livro.fotoCapa}</td>
				<td>${livro.isbn}</td>
				<td>${livro.paginas}</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>
