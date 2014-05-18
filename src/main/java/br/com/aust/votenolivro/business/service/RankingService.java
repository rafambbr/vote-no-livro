package br.com.aust.votenolivro.business.service;

import java.util.Collection;

import br.com.aust.votenolivro.domain.Livro;
import br.com.aust.votenolivro.domain.Ranking;
import br.com.aust.votenolivro.domain.Usuario;

public interface RankingService {
	public void salvar(Collection<Livro> livros, Usuario usuario);
	public Collection<Ranking> carregarRanking();
	public Collection<Ranking> carregarRankingUsuario();
}
