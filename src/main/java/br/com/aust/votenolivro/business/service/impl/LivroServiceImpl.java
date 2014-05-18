package br.com.aust.votenolivro.business.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.aust.votenolivro.business.repository.LivroRepository;
import br.com.aust.votenolivro.business.repository.LivroUsuarioRepository;
import br.com.aust.votenolivro.business.service.LivroService;
import br.com.aust.votenolivro.domain.Livro;

@Service
@Scope("session")
public class LivroServiceImpl implements LivroService{

	@Autowired private LivroRepository livroRepository;
	@Autowired private LivroUsuarioRepository livroUsuarioRepository;
	
	private final Set<Long> idsLivrosVotados = new HashSet<Long>();
	private PageRequest quantidadeRegistroRetornados;
	
	public LivroServiceImpl(){
		int totalDeRegistros = 2;
		this.quantidadeRegistroRetornados = new PageRequest(0, totalDeRegistros);
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
	public void votar(Livro livro){
		if(livro != null && livro.getIdLivro() != null){
			this.idsLivrosVotados.add(livro.getIdLivro());
		}
	}
	
	@Override
	@Transactional
	public Collection<Livro> carregarLivrosNaoVotados(){
		Collection<Livro> livrosNaoVotados = null;
		if( this.idsLivrosVotados.isEmpty() ){
			livrosNaoVotados = this.livroRepository.findAll(this.quantidadeRegistroRetornados).getContent();
		}else{
			livrosNaoVotados = this.livroRepository.carregarLivrosNaoVotados(this.idsLivrosVotados, this.quantidadeRegistroRetornados);
		}
		
		return livrosNaoVotados;
	}

	@Override
	@Transactional
	public Collection<Livro> carregarTodos(){
		return this.livroRepository.findAll();
	}

	@Override
	public Collection<Livro> getLivrosVotados() {
		Collection<Livro> livrosVotados = new HashSet<Livro>();
		for(Long idLivro : this.idsLivrosVotados){
			Livro livro = new Livro(idLivro);
			livrosVotados.add(livro);
		}
		return livrosVotados;
	}

	
}
