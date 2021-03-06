package br.com.aust.votenolivro.business.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.aust.votenolivro.business.repository.LivroUsuarioRepository;
import br.com.aust.votenolivro.business.repository.UsuarioRepository;
import br.com.aust.votenolivro.business.service.LivroService;
import br.com.aust.votenolivro.business.service.RankingService;
import br.com.aust.votenolivro.domain.Livro;
import br.com.aust.votenolivro.domain.LivroUsuario;
import br.com.aust.votenolivro.domain.Ranking;
import br.com.aust.votenolivro.domain.Usuario;

@Service
@Scope("session")
public class RankingServiceImpl implements RankingService{

	@Autowired private LivroUsuarioRepository livroUsuarioRepository;
	@Autowired private UsuarioRepository usuarioRepository;
	@Autowired private LivroService livroService;
	
	public RankingServiceImpl(){
		//No-op
	}
	
	public RankingServiceImpl(LivroUsuarioRepository livroUsuarioRepository, UsuarioRepository usuarioRepository, LivroService livroService){
		this.livroUsuarioRepository = livroUsuarioRepository;
		this.usuarioRepository = usuarioRepository;
		this.livroService = livroService;
	}
	
	@Override
	public Collection<Ranking> carregarRanking() {
		
		List<Ranking> rankingOrdenado = null;
		
		Map<Livro, Ranking> votosLivro = new HashMap<Livro, Ranking>();
		Collection<LivroUsuario> livrosUsuarios = this.livroUsuarioRepository.findAll();
		for (LivroUsuario livroUsuario : livrosUsuarios) {
			Livro livro = livroUsuario.getLivro();
			if( votosLivro.containsKey(livro) ){
				votosLivro.get(livro).adicionarVoto();
			}else{
				Ranking ranking = new Ranking(livro);
				ranking.adicionarVoto();
				
				votosLivro.put(livro, ranking );
			}
		}
		
		rankingOrdenado = new ArrayList<Ranking>(votosLivro.values());
		Collections.sort(rankingOrdenado);
		
		return rankingOrdenado;
	}
	
	@Override
	public Collection<Ranking> carregarRankingUsuario(){
		Collection<Ranking> livrosVotadosPeloUsuario = new HashSet<Ranking>();
		for (Livro livro : this.livroService.getLivrosVotados()) {
			Ranking rankingUsuario = new Ranking(livro, 1L);
			livrosVotadosPeloUsuario.add(rankingUsuario);
		}
		
		return livrosVotadosPeloUsuario;
	}

	@Override
	public void salvar(Collection<Livro> livros, Usuario usuario){
		if(livros != null && usuario != null){
			usuario = this.usuarioRepository.save(usuario);
			for (Livro livro : livros) {
				LivroUsuario livroUsuario = new LivroUsuario(usuario, livro);
				this.livroUsuarioRepository.save(livroUsuario);
			}
		}
	}
	
}
