package br.com.aust.votenolivro.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

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
import br.com.aust.votenolivro.business.repository.UsuarioRepository;
import br.com.aust.votenolivro.business.service.impl.LivroServiceImpl;
import br.com.aust.votenolivro.business.service.impl.RankingServiceImpl;
import br.com.aust.votenolivro.domain.Livro;
import br.com.aust.votenolivro.domain.LivroUsuario;
import br.com.aust.votenolivro.domain.Ranking;
import br.com.aust.votenolivro.domain.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppConfig.class)
public class RankingServiceTest {

	private Livro harryPotter;
	private Livro hobbit;
	private Livro anjosEDemonios;
	private Livro oCodigoDaVinci;
	private Livro senhorDosAneis;
	
	private Usuario rafael;
	private Usuario pedro;
	private Usuario maria;

	@Autowired private LivroRepository livroRepository;
	@Autowired private UsuarioRepository usuarioRepository;
	@Autowired private LivroUsuarioRepository livroUsuarioRepository;
	
	private LivrosVotadosService livrosVotadosServiceMock;
	private LivroService livroService;
	private RankingService rankingService;
	
	@Before
	public void setup(){
		this.harryPotter = this.livroRepository.findOne(1L);
		this.hobbit = this.livroRepository.findOne(2L);	
		this.anjosEDemonios = this.livroRepository.findOne(3L);
		this.oCodigoDaVinci = this.livroRepository.findOne(4L);
		this.senhorDosAneis = this.livroRepository.findOne(5L);
		
		this.rafael = new Usuario("Rafael Camargo", "rafael.camargo.sp@gmail.com");
		this.pedro = new Usuario("Pedro", "pedro@mail.com");
		this.maria = new Usuario("Maria", "maria@terra.com.br");
		this.rafael = this.usuarioRepository.save(this.rafael);
		this.pedro = this.usuarioRepository.save(this.pedro);
		this.maria = this.usuarioRepository.save(this.maria);
		
		PageRequest pageRequest = new PageRequest(0, LivroServiceImpl.TOTAL_DE_LIVROS_VISUALIZADOS_POR_PAGINA);
		this.livrosVotadosServiceMock = mock(LivrosVotadosService.class);
		this.livroService = new LivroServiceImpl(this.livroRepository, this.livroUsuarioRepository, pageRequest, this.livrosVotadosServiceMock);
		this.rankingService = new RankingServiceImpl(this.livroUsuarioRepository, this.usuarioRepository, this.livroService);
	}
	
	@Test
	public void deveRetornarApenasOsLivrosVotadosComAQuantidadeDeVotos(){
		//Votos Herry Potter
		LivroUsuario votoRafaelHarryPotter = new LivroUsuario(this.rafael, this.harryPotter);
		LivroUsuario votoMariaHarryPotter = new LivroUsuario(this.maria, this.harryPotter);
		LivroUsuario votoPedroHarryPotter = new LivroUsuario(this.pedro, this.harryPotter);
		this.livroUsuarioRepository.save(votoRafaelHarryPotter);
		this.livroUsuarioRepository.save(votoMariaHarryPotter);
		this.livroUsuarioRepository.save(votoPedroHarryPotter);
		
		//Votos Senhor dos Aneis
		LivroUsuario votoRafaelSenhorDosAneis = new LivroUsuario(this.rafael, this.senhorDosAneis);
		LivroUsuario votoPedroSenhorDosAneis = new LivroUsuario(this.pedro, this.senhorDosAneis);
		this.livroUsuarioRepository.save(votoRafaelSenhorDosAneis);
		this.livroUsuarioRepository.save(votoPedroSenhorDosAneis);
		
		//Votos O Codigo da Vinci
		LivroUsuario votoMariaOCodigoDaVinci = new LivroUsuario(this.maria, this.oCodigoDaVinci);
		this.livroUsuarioRepository.save(votoMariaOCodigoDaVinci);
		
		//Votos Hobbit
		LivroUsuario votoPedroHobbit = new LivroUsuario(this.pedro, this.hobbit);
		this.livroUsuarioRepository.save(votoPedroHobbit);
		Collection<Ranking> votos = this.rankingService.carregarRanking();

		Ranking rankingHarryPotter = new Ranking(this.harryPotter, 3L);
		Ranking rankingSenhorDosAneis = new Ranking(this.senhorDosAneis, 2L);
		Ranking rankingCodigoDaVinci = new Ranking(this.oCodigoDaVinci, 1L);
		Ranking rankingHobbit = new Ranking(this.hobbit, 1L);
		assertThat(votos, hasItems(
				rankingHarryPotter,
				rankingSenhorDosAneis,
				rankingCodigoDaVinci,
				rankingHobbit ));
		
		boolean testouHarryPotter = false;
		boolean testouSenhorDosAneis = false;
		boolean testouCodigoDaVinci = false;
		boolean testouHobbit = false;
		for (Ranking ranking : votos) {
			
			Livro livro = ranking.getLivro();
			long totalVotos = ranking.getTotalVotos();
			
			if(livro.equals(this.harryPotter)){
				//Deve ter 3 votos
				assertEquals(totalVotos, 3);
				testouHarryPotter = true;
			}else if(livro.equals(this.senhorDosAneis)){
				//Deve ter 2 votos
				assertEquals(totalVotos, 2);
				testouSenhorDosAneis = true;
			}else if(livro.equals(this.oCodigoDaVinci)){
				//Deve ter 1 voto
				assertEquals(totalVotos, 1);
				testouCodigoDaVinci = true;
			}else if(livro.equals(this.hobbit)){
				//Deve ter 1 voto
				assertEquals(totalVotos, 1);
				testouHobbit = true;
			}else if(livro.equals(this.anjosEDemonios)){
				//Não deve ter votos
				fail("Não deveria ter votos para esse livro");
			}else{
				fail("Livro errado");
			}
		}
		assertTrue(testouHarryPotter);
		assertTrue(testouSenhorDosAneis);
		assertTrue(testouCodigoDaVinci);
		assertTrue(testouHobbit);
	}
}
