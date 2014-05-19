package br.com.aust.votenolivro.business.service;

import java.util.Collection;

import br.com.aust.votenolivro.domain.Livro;


public interface LivroService {
	public Livro salvar(Livro livro);
	public void votar(Livro livro);
	public Collection<Livro> carregarLivrosNaoVotados();
	public Collection<Livro> getLivrosVotados();
	public void descartar(Livro livro);
}
