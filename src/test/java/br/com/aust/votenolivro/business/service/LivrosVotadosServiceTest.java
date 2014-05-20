package br.com.aust.votenolivro.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.aust.votenolivro.AppConfig;
import br.com.aust.votenolivro.business.exception.ServiceException;
import br.com.aust.votenolivro.business.service.impl.LivrosVotadosServiceImpl;
import br.com.aust.votenolivro.domain.Livro;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppConfig.class)
public class LivrosVotadosServiceTest {

	private static Livro harryPotter;
	private static Livro hobbit;
	private static Livro anjosEDemonios;
	private static Livro oCodigoDaVinci;
	private static Livro senhorDosAneis;
	
	private static LivrosVotadosService livrosVotadosService;
	
	@BeforeClass
	public static void setup(){
		livrosVotadosService = new LivrosVotadosServiceImpl();
		
		harryPotter = 		new Livro(1L, "Harry Potter", 		"Rocco", 			"02_harry_potter.png", 		"9780307524338", 	435);
		hobbit = 			new Livro(2L, "Hobbit", 			"Harper Colllins", 	"03_hobbit.png", 			"9780007525508", 	400);
		anjosEDemonios = 	new Livro(3L, "Anjos e demonios", 	"Sextante", 		"01_anjos_e_demonios.png", 	"9788575421468", 	464);
		oCodigoDaVinci = 	new Livro(4L, "O Codigo da Vinci", 	"Sextante", 		"04_o_codigo_da_vinci.png", "9788575421130", 	480);
		senhorDosAneis = 	new Livro(5L, "Senhor dos Aneis", 	"Harper Collins", 	"05_senhor_dos_aneis.png", 	"9780007488308", 	576);
		
		livrosVotadosService.votar(harryPotter);
		livrosVotadosService.votar(hobbit);
		livrosVotadosService.votar(anjosEDemonios);
		livrosVotadosService.descartar(oCodigoDaVinci);
		livrosVotadosService.descartar(senhorDosAneis);
	}
	
	@Test
	public void deveRetornarOsLivrosVotados(){
		boolean existeLivrosVotados = livrosVotadosService.existeLivrosVotados();
		assertTrue(existeLivrosVotados);
		
		Collection<Long> idsLivrosVotados = livrosVotadosService.getIdsLivrosVotados();
		assertEquals(idsLivrosVotados.size(), 3);
		assertThat(idsLivrosVotados, hasItems(
				1L,
				2L,
				3L));
	}
	
	@Test
	public void deveRetornarOsLivrosDescartados(){
		boolean existeLivrosDescartados = livrosVotadosService.existeLivrosDescartados();
		assertTrue(existeLivrosDescartados);
		
		Collection<Long> idsLivrosDescartados = livrosVotadosService.getIdsLivrosDescartados();
		assertEquals(idsLivrosDescartados.size(), 2);
		assertThat(idsLivrosDescartados, hasItems(
				4L,
				5L));
	}
	
	@Test
	public void deveRetornarLivrosVisualizados(){
		boolean existeLivrosVisualizados = livrosVotadosService.existeLivrosVisualizados();
		assertTrue(existeLivrosVisualizados);
		
		Collection<Long> idsLivrosVisualizados = livrosVotadosService.getIdsLivrosVisualizados();
		assertEquals(idsLivrosVisualizados.size(), 5);
		assertThat(idsLivrosVisualizados, hasItems(
				1L,
				2L,
				3L,
				4L,
				5L));
		
	}
	
	@Test(expected=ServiceException.class)
	public void deveRetornarExceptionCasoOLivroNaoVotadoNaoTenhaId(){
		Livro livroSemId = new Livro("Harry Potter", 		"Rocco", 			"02_harry_potter.png", 		"9780307524338", 	435);
		livrosVotadosService.votar(livroSemId);
	}
	
	@Test(expected=ServiceException.class)
	public void deveRetornarExceptionCasoOLivroNaoDescartadoNaoTenhaId(){
		Livro livroSemId = new Livro("Harry Potter", 		"Rocco", 			"02_harry_potter.png", 		"9780307524338", 	435);
		livrosVotadosService.descartar(livroSemId);
	}
}
