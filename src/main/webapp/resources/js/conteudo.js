/**
* Rafael Camargo
*
*/	


/**
 * Vota no livro
 */
var votaNoLivro = function(event){	
	event.preventDefault();
	var idLivro = $(this).attr("livro");
	var uri = "livro/votar?idLivro="+idLivro;
	$.get(uri, callback_votaNoLivro);
};

/**
 * Carrega lista de livros apos votar em um livro
 */
var callback_votaNoLivro = function(event){
	$("#conteudo").load("livro/carregar/naovotados");
};



/**
 * Salva os votos do usuario
 */
var salvarVotosUsuario = function(event){	
	event.preventDefault();
	var nome = $("#formSalvaVotosUsuario").find('input[name="nome"]').val();
	var email = $("#formSalvaVotosUsuario").find('input[name="email"]').val();
	if(nome.length > 0 && email.length > 0){
		var uri = "usuario/salvar/livrosvotados?nome="+nome+"&email="+email;
		$.get(uri, callback_salvarVotosUsuario);
	}else{
		alert("Preencha os campos nome e e-mail!");
	}
};

/**
 * redireciona para o ranking depois de salvar os votos do usuario
 */
var callback_salvarVotosUsuario = function(event){
	$("#conteudo").load("livro/carregar/ranking");
};


/**
 * Abre paginas com animação
 */
var abreLinkDoConteudo = function(event){	
	event.preventDefault() ;	
	var arquivo = $(this).attr("href");
	injetarArquivoNoConteudo(arquivo);
	
	$("html, body").animate({ scrollTop: 0 }, "slow");
};

/**
 * Configura evetos ao carregar a pagina
 */
var onInicializarConteudo = function(){
	$(".linkConteudo").on("click", abreLinkDoConteudo);
	$(".linkVotarLivro").on("click", votaNoLivro);
	$(".btnSalvarVotosUsuario").on("click", salvarVotosUsuario);
};

$(onInicializarConteudo);