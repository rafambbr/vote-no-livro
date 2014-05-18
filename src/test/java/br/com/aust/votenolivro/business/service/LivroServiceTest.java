package br.com.aust.votenolivro.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.aust.votenolivro.AppConfig;
import br.com.aust.votenolivro.business.repository.LivroRepository;
import br.com.aust.votenolivro.business.repository.LivroUsuarioRepository;
import br.com.aust.votenolivro.business.service.impl.LivroServiceImpl;
import br.com.aust.votenolivro.domain.Livro;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppConfig.class)
public class LivroServiceTest {

	private Livro harryPotter;
	private Livro hobbit;
	private Livro anjosEDemonios;
	private Livro oCodigoDaVinci;
	private Livro senhorDosAneis;

	private LivroService livroService;
	
	@Autowired private LivroRepository livroRepository;
	@Autowired private LivroUsuarioRepository livroUsuarioRepository;
	
	@Before
	public void setup(){
		this.harryPotter = this.livroRepository.findOne(1L);
		this.hobbit = this.livroRepository.findOne(2L);	
		this.anjosEDemonios = this.livroRepository.findOne(3L);
		this.oCodigoDaVinci = this.livroRepository.findOne(4L);
		this.senhorDosAneis = this.livroRepository.findOne(5L);
		
		int totalDeRegistros = 1000;
		PageRequest quantidadeRegistroRetornados = new PageRequest(0, totalDeRegistros);
		this.livroService = new LivroServiceImpl(this.livroRepository, this.livroUsuarioRepository, quantidadeRegistroRetornados);
	}
	
	@Test
	public void deveRetornarOsLivrosAindaNaoVotados(){
		
		this.livroService.votar(this.senhorDosAneis);
		this.livroService.votar(this.anjosEDemonios);
		
		Collection<Livro> livrosNaoVotados = this.livroService.carregarLivrosNaoVotados();
		assertEquals(livrosNaoVotados.size(), 3);
		assertThat(livrosNaoVotados, hasItems(
				this.harryPotter,
				this.hobbit,
				this.oCodigoDaVinci));
	}
	
	
	@Test
	public void deveRetornarLivrosVotados(){
		
		this.livroService.votar(this.anjosEDemonios);
		this.livroService.votar(this.harryPotter);
		Collection<Livro> livrosVotados = this.livroService.getLivrosVotados();
		
		assertEquals(livrosVotados.size(), 2);
		assertThat(livrosVotados, hasItems(
				this.anjosEDemonios,
				this.harryPotter));
		
		this.livroService.votar(this.hobbit);
		livrosVotados = this.livroService.getLivrosVotados();
		
		assertEquals(livrosVotados.size(), 3);
		assertThat(livrosVotados, hasItems(
				this.anjosEDemonios,
				this.harryPotter,
				this.hobbit));
	}
}
