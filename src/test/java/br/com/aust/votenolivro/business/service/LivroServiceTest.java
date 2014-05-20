package br.com.aust.votenolivro.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashSet;

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
	private LivrosVotadosService livrosVotadosServiceMock;
	
	@Autowired private LivroRepository livroRepository;
	@Autowired private LivroUsuarioRepository livroUsuarioRepository;
	
	@Before
	public void setup(){
		this.harryPotter = this.livroRepository.findOne(1L);
		this.hobbit = this.livroRepository.findOne(2L);	
		this.anjosEDemonios = this.livroRepository.findOne(3L);
		this.oCodigoDaVinci = this.livroRepository.findOne(4L);
		this.senhorDosAneis = this.livroRepository.findOne(5L);
		
		this.livrosVotadosServiceMock = mock(LivrosVotadosService.class);
		
		int totalDeRegistros = 1000;
		PageRequest quantidadeRegistroRetornados = new PageRequest(0, totalDeRegistros);
		this.livroService = new LivroServiceImpl(this.livroRepository, this.livroUsuarioRepository, quantidadeRegistroRetornados, this.livrosVotadosServiceMock);
	}
	
	@Test
	public void deveRetornarOsLivrosAindaNaoVotados(){
		
		Collection<Long> idsLivrosVisualizados = new HashSet<Long>();
		idsLivrosVisualizados.add( this.senhorDosAneis.getIdLivro() );
		idsLivrosVisualizados.add( this.anjosEDemonios.getIdLivro() );
		
		when(this.livrosVotadosServiceMock.existeLivrosVotados()).thenReturn(true);
		when(this.livrosVotadosServiceMock.getIdsLivrosVisualizados()).thenReturn(idsLivrosVisualizados);
		
		Collection<Livro> livrosNaoVotados = this.livroService.carregarLivrosNaoVotados();
		assertEquals(livrosNaoVotados.size(), 3);
		assertThat(livrosNaoVotados, hasItems(
				this.harryPotter,
				this.hobbit,
				this.oCodigoDaVinci));
	}
	
	
	@Test
	public void deveRetornarLivrosVotados(){
		
		
		Collection<Long> idsLivrosVotados = new HashSet<Long>();
		idsLivrosVotados.add( this.anjosEDemonios.getIdLivro() );
		idsLivrosVotados.add( this.harryPotter.getIdLivro() );
		
		when(this.livrosVotadosServiceMock.existeLivrosVotados()).thenReturn(true);
		when(this.livrosVotadosServiceMock.getIdsLivrosVotados()).thenReturn(idsLivrosVotados);
		
		Collection<Livro> livrosVotados = this.livroService.getLivrosVotados();
		
		assertEquals(livrosVotados.size(), 2);
		assertThat(livrosVotados, hasItems(
				this.anjosEDemonios,
				this.harryPotter));
		
		idsLivrosVotados.add( this.hobbit.getIdLivro() );
		livrosVotados = this.livroService.getLivrosVotados();
		
		assertEquals(livrosVotados.size(), 3);
		assertThat(livrosVotados, hasItems(
				this.anjosEDemonios,
				this.harryPotter,
				this.hobbit));
	}
	
	@Test
	public void deveCarregarLivrosNaoVotados(){

		Collection<Livro> livrosNaoVotados = this.livroService.carregarLivrosNaoVotados();
		assertThat(livrosNaoVotados, hasItems(
				this.harryPotter,
				this.hobbit,
				this.anjosEDemonios,
				this.oCodigoDaVinci,
				this.senhorDosAneis));
		
		this.livroService.votar(harryPotter);
		this.livroService.votar(hobbit);
		this.livroService.descartar(anjosEDemonios);
		this.livroService.descartar(oCodigoDaVinci);
		livrosNaoVotados = this.livroService.carregarLivrosNaoVotados();

		assertThat(livrosNaoVotados, hasItems(
				this.senhorDosAneis,
				this.anjosEDemonios,
				this.oCodigoDaVinci));
		
	}
}
