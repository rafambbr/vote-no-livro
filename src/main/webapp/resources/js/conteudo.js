/**
* Rafael Camargo
*
*/	


/**
 * Vota no livro
 */
var votaNoLivro = function(event){	
	event.preventDefault();
	var idLivroVotado = $(this).attr("livro");
	
	$(".linkVotarLivro").each(function(index) {
		var idLivroDescartado =  $(this).attr("livro");
		if(idLivroDescartado != idLivroVotado){
			//Marca livro descartado
			var uriLivroDescartado = "livro/descartar?idLivro="+idLivroDescartado;
			$.get(uriLivroDescartado);
		}
	});
	
	//Marca livro votado
	var uriLivroVotado = "livro/votar?idLivro="+idLivroVotado;
	$.get(uriLivroVotado, callback_votaNoLivro);
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
	
	var mensagemDeInvalidacao = "Invalidações: ";
	var nomeValido = Boolean(nome.length >= 4);
	
	if(!nomeValido){
		mensagemDeInvalidacao += "O nome deve ter no minimo 4 caracteres!\n";
	}

	var pattern = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
	var emailValido = pattern.test(email);
	
	if(!emailValido){
		mensagemDeInvalidacao += "Email inválido!\n";
	}
	
	if(nomeValido && emailValido){
		var uri = "usuario/salvar/livrosvotados?nome="+nome+"&email="+email;
		$.get(uri, callback_salvarVotosUsuario);
	}else{
		alert(mensagemDeInvalidacao);
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
	$("#conteudo").load(arquivo);
	
	$("html, body").animate({ scrollTop: 0}, "slow");
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