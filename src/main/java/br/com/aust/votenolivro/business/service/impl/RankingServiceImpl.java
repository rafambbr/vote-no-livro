package br.com.aust.votenolivro.business.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aust.votenolivro.business.exception.ServiceException;
import br.com.aust.votenolivro.business.repository.LivroRepository;
import br.com.aust.votenolivro.business.repository.LivroUsuarioRepository;
import br.com.aust.votenolivro.business.repository.UsuarioRepository;
import br.com.aust.votenolivro.business.service.RankingService;
import br.com.aust.votenolivro.domain.Livro;
import br.com.aust.votenolivro.domain.LivroUsuario;
import br.com.aust.votenolivro.domain.Ranking;
import br.com.aust.votenolivro.domain.Usuario;

@Service
public class RankingServiceImpl implements RankingService{

	@Autowired private LivroUsuarioRepository livroUsuarioRepository;
	@Autowired private LivroRepository livroRepository;
	@Autowired private UsuarioRepository usuarioRepository;
	
	@Override
	@Transactional
	public Collection<Ranking> carregarRanking() {
		
		Map<Livro, Ranking> votosLivro = new HashMap<Livro, Ranking>();
		
		try{
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
		}catch(Exception e){
			throw new ServiceException("Erro ao carregar o ranking", e);
		}
		
		return votosLivro.values();
	}

	@Override
	@Transactional
	public void salvar(Collection<Livro> livros, Usuario usuario) {
		if(livros != null && usuario != null){
			
			usuario = this.usuarioRepository.save(usuario);
			for (Livro livro : livros) {
				LivroUsuario livroUsuario = new LivroUsuario(usuario, livro);
				this.livroUsuarioRepository.save(livroUsuario);
			}
		}
	}
}
