package br.com.aust.votenolivro.business.repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
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
import br.com.aust.votenolivro.domain.Livro;
import br.com.aust.votenolivro.domain.LivroUsuario;
import br.com.aust.votenolivro.domain.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppConfig.class)
public class LivroRepositoryTest {

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
	}
	
	@Test
	public void deveBuscarLivrosQueAindaNaoForamVotados(){
		
		LivroUsuario rafaelHarryPotter = new LivroUsuario(this.rafael, this.harryPotter);
		LivroUsuario mariaOCodigoDaVinci = new LivroUsuario(this.maria, this.oCodigoDaVinci);
		LivroUsuario pedroHobbit = new LivroUsuario(this.pedro, this.hobbit);
		LivroUsuario rafaelSenhorDosAneis = new LivroUsuario(this.rafael, this.senhorDosAneis);
		this.livroUsuarioRepository.save(rafaelHarryPotter);
		this.livroUsuarioRepository.save(mariaOCodigoDaVinci);
		this.livroUsuarioRepository.save(pedroHobbit);
		this.livroUsuarioRepository.save(rafaelSenhorDosAneis);
		
		Collection<Long> livrosVotados = new HashSet<Long>();
		livrosVotados.add(this.harryPotter.getIdLivro());
		livrosVotados.add(this.oCodigoDaVinci.getIdLivro());
		livrosVotados.add(this.hobbit.getIdLivro());
		livrosVotados.add(this.senhorDosAneis.getIdLivro());
		
		int totalDeRegistros = 100;
		PageRequest pageRequest = new PageRequest(0, totalDeRegistros);
		List<Livro> livrosNaoVotados = this.livroRepository.carregarLivrosNaoVotados(livrosVotados, pageRequest);
		Assert.assertEquals(livrosNaoVotados.size(), 1);
		Assert.assertEquals(livrosNaoVotados.get(0), this.anjosEDemonios);
	}
}
