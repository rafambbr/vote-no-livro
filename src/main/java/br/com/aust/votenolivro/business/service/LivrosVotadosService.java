package br.com.aust.votenolivro.business.service;

import java.util.Collection;

import br.com.aust.votenolivro.domain.Livro;

public interface LivrosVotadosService {

	public void votar(Livro livroVotado);
	public void descartar(Livro livroDescartado);
	public Collection<Long> getIdsLivrosVotados();
	public Collection<Long> getIdsLivrosDescartados();
	public Collection<Long> getIdsLivrosVisualizados();
	public boolean existeLivrosVotados();
	public boolean existeLivrosDescartados();
	public boolean existeLivrosVisualizados();
}
