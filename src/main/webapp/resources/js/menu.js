/**
* Rafael Camargo  V0.1("kiss" => keep it simple stupid)
* 
*/
var onItemMenu = function(event){
	event.preventDefault() ;	
	var arquivo = $(this).attr("arquivo");
	injetarArquivoNoConteudo(arquivo);
};

$(".itemMenu").on("click", onItemMenu);