package br.com.aust.votenolivro.business.service.impl;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.aust.votenolivro.business.repository.LivroRepository;
import br.com.aust.votenolivro.business.repository.LivroUsuarioRepository;
import br.com.aust.votenolivro.business.service.LivroService;
import br.com.aust.votenolivro.business.service.LivrosVotadosService;
import br.com.aust.votenolivro.domain.Livro;

@Service
@Scope("session")
public class LivroServiceImpl implements LivroService{

	@Autowired private LivroRepository livroRepository;
	@Autowired private LivroUsuarioRepository livroUsuarioRepository;
	@Autowired private LivrosVotadosService livrosVotadosService;
	
	private static final int TOTAL_DE_LIVROS_VISUALIZADOS_POR_PAGINA = 2;
	private PageRequest quantidadeRegistroRetornados;
	
	public LivroServiceImpl(){
		this.quantidadeRegistroRetornados = new PageRequest(0, TOTAL_DE_LIVROS_VISUALIZADOS_POR_PAGINA);
	}
	
	public LivroServiceImpl(LivroRepository livroRepository, LivroUsuarioRepository livroUsuarioRepository, PageRequest quantidadeRegistroRetornados){
		this.livroRepository = livroRepository;
		this.livroUsuarioRepository = livroUsuarioRepository;
		this.quantidadeRegistroRetornados = quantidadeRegistroRetornados;
	}
	
	@Override
	@Transactional
	public Livro salvar(Livro livro){
		livro = this.livroRepository.save(livro);
		return livro;
	}
	
	@Override
	@Transactional
	public void votar(Livro livro){
		this.livrosVotadosService.votar(livro);
	}
	
	@Override
	@Transactional
	public void descartar(Livro livro) {
		this.livrosVotadosService.descartar(livro);
	}
	
	@Override
	@Transactional
	public Collection<Livro> carregarLivrosNaoVotados(){
		Collection<Livro> livrosNaoVotados = null;
		if( this.livrosVotadosService.existeLivrosVotados() ){
			//Se já foi votado algum livro carrega os proximos
			Collection<Long> idsLivrosVisualizados = this.livrosVotadosService.getIdsLivrosVisualizados();
			livrosNaoVotados = this.livroRepository.carregarLivrosNaoVotados( idsLivrosVisualizados, this.quantidadeRegistroRetornados);

			if(livrosNaoVotados.size() > 0){
				long totalDeLivros = this.livroRepository.count();
				if( precisaCompletarAListaDeLivros(idsLivrosVisualizados, totalDeLivros) ){
					//Completa a lista de livros com livros descartados para que todos os livros sejam comparados
					completaItensASeremVisualizadosComLivrosDescartados(livrosNaoVotados);
				}
			}
		}else{
			//Se ainda não foi votato nenhum livro carrega todos
			livrosNaoVotados = this.livroRepository.findAll(this.quantidadeRegistroRetornados).getContent();
		}
		
		return livrosNaoVotados;
	}

	private boolean precisaCompletarAListaDeLivros(Collection<Long> idsLivrosVisualizados, long totalDeLivros) {
		int totalDeLivrosVisualizados = idsLivrosVisualizados.size();
		return (totalDeLivros - totalDeLivrosVisualizados) <= TOTAL_DE_LIVROS_VISUALIZADOS_POR_PAGINA;
	}

	private void completaItensASeremVisualizadosComLivrosDescartados(Collection<Livro> livrosASeremVisualizados){
		int numeroLivrosNaoVisualizados = livrosASeremVisualizados.size();
		int numeroLivrosParaCompletarNaLista = TOTAL_DE_LIVROS_VISUALIZADOS_POR_PAGINA - numeroLivrosNaoVisualizados;
		int numeroLivrosAdicionados=0;
		for (Long idLivro : this.livrosVotadosService.getIdsLivrosDescartados()) {
			numeroLivrosAdicionados++;
			if(numeroLivrosAdicionados <= numeroLivrosParaCompletarNaLista){
				Livro livroDescartado = this.livroRepository.findOne(idLivro);
				livrosASeremVisualizados.add(livroDescartado);
			}else{
				break;
			}
		}
	}

	@Override
	@Transactional
	public Collection<Livro> carregarTodos(){
		return this.livroRepository.findAll();
	}

	@Override
	public Collection<Livro> getLivrosVotados() {
		Collection<Livro> livrosVotados = new HashSet<Livro>();
		for(Long idLivro : this.livrosVotadosService.getIdsLivrosVotados()){
			Livro livro = this.livroRepository.findOne(idLivro);
			livrosVotados.add(livro);
		}
		return livrosVotados;
	}
}
