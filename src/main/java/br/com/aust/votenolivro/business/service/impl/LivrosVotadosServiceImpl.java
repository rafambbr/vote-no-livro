package br.com.aust.votenolivro.business.service.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.aust.votenolivro.business.service.LivrosVotadosService;
import br.com.aust.votenolivro.domain.Livro;

@Service
@Scope("session")
public class LivrosVotadosServiceImpl implements LivrosVotadosService {

	private final Collection<Livro> livrosVotados = new HashSet<Livro>();
	private final Collection<Livro> livrosDescartados = new HashSet<Livro>();

	@Override
	public void votar(Livro livroVotado){
		if(livroVotado != null && livroVotado.getIdLivro() != null){
			this.livrosVotados.add(livroVotado);
		}
	}
	
	@Override
	public void descartar(Livro livroDescartado){
		if(livroDescartado != null && livroDescartado.getIdLivro() != null){
			this.livrosDescartados.add(livroDescartado);
		}
	}
	
	@Override
	public Collection<Long> getIdsLivrosVotados(){
		
		Collection<Long> idsLivrosVotados = new HashSet<Long>();
		for (Livro livroVotado : this.livrosVotados) {
			idsLivrosVotados.add(livroVotado.getIdLivro());
		}
		
		return idsLivrosVotados;
	}
	
	@Override
	public Collection<Long> getIdsLivrosDescartados(){
		
		Collection<Long> idsLivrosDescartados = new HashSet<Long>();
		for (Livro livroDescartado : this.livrosDescartados) {
			idsLivrosDescartados.add(livroDescartado.getIdLivro());
		}
		
		return idsLivrosDescartados;
	}
	
	@Override
	public Collection<Long> getIdsLivrosVisualizados() {
		Collection<Long> idsLivrosVisualizados = new HashSet<Long>();
		for (Livro livroVotado : this.livrosVotados) {
			idsLivrosVisualizados.add(livroVotado.getIdLivro());
		}
		
		for (Livro livroVotado : this.livrosDescartados) {
			idsLivrosVisualizados.add(livroVotado.getIdLivro());
		}
		
		return idsLivrosVisualizados;
	}
	
	@Override
	public boolean existeLivrosVotados(){
		return !this.livrosVotados.isEmpty();
	}
	
	@Override
	public boolean existeLivrosDescartados(){
		return !this.livrosDescartados.isEmpty();
	}

	@Override
	public boolean existeLivrosVisualizados() {
		return (!this.livrosVotados.isEmpty() || !this.livrosDescartados.isEmpty());
	}
	
}
