package br.com.aust.votenolivro.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aust.votenolivro.business.repository.LivroRepository;
import br.com.aust.votenolivro.business.repository.LivroUsuarioRepository;
import br.com.aust.votenolivro.business.repository.UsuarioRepository;
import br.com.aust.votenolivro.domain.Livro;

@Service
public class CarregaDadosNoBanco implements InitializationStrategy {

	@Autowired private LivroRepository livroRepository;
	@Autowired private UsuarioRepository usuarioRepository;
	@Autowired private LivroUsuarioRepository livroUsuarioRepository;
	
	@Override
	public void init() {
		System.out.println("Salvando livros........");
		Livro harryPotter = 	new Livro("Harry Potter", 		"Rocco", 			"02_harry_potter.png", 		"9780307524338", 	435);
		Livro hobbit = 			new Livro("Hobbit", 			"Harper Colllins", 	"03_hobbit.png", 			"9780007525508", 	400);
		Livro anjosEDemonios = 	new Livro("Anjos e demonios", 	"Sextante", 		"01_anjos_e_demonios.png", 	"9788575421468", 	464);
		Livro oCodigoDaVinci = 	new Livro("O Codigo da Vinci", 	"Sextante", 		"04_o_codigo_da_vinci.png", "9788575421130", 	480);
		Livro senhorDosAneis = 	new Livro("Senhor dos Aneis", 	"Harper Collins", 	"05_senhor_dos_aneis.png", 	"9780007488308", 	576);
		
		harryPotter = this.livroRepository.save(harryPotter);
		hobbit = this.livroRepository.save(hobbit);
		anjosEDemonios = this.livroRepository.save(anjosEDemonios);
		oCodigoDaVinci = this.livroRepository.save(oCodigoDaVinci);
		senhorDosAneis = this.livroRepository.save(senhorDosAneis);
		System.out.println("Livros salvos........");
		
		/*
		System.out.println("Salvando usuarios..............");
		Usuario rafael = new Usuario("Rafael Camargo", "rafael.camargo.sp@gmail.com");
		Usuario pedro = new Usuario("Pedro", "pedro@gmail.com");
		Usuario maria = new Usuario("Maria", "maria@zipmail.com.br");
		
		this.usuarioRepository.save(rafael);
		this.usuarioRepository.save(pedro);
		this.usuarioRepository.save(maria);
		
		System.out.println("Usuarios salvos..............");
		
		
		
		System.out.println("Salvando livroUsuario..............");
		LivroUsuario livroUsuario1 = new LivroUsuario(rafael, harryPotter);
		LivroUsuario livroUsuario2 = new LivroUsuario(pedro, harryPotter);
		LivroUsuario livroUsuario3 = new LivroUsuario(maria, harryPotter);
		LivroUsuario livroUsuario4 = new LivroUsuario(rafael, hobbit);
		LivroUsuario livroUsuario5 = new LivroUsuario(maria, hobbit);
		this.livroUsuarioRepository.save(livroUsuario1);
		this.livroUsuarioRepository.save(livroUsuario2);
		this.livroUsuarioRepository.save(livroUsuario3);
		this.livroUsuarioRepository.save(livroUsuario4);
		this.livroUsuarioRepository.save(livroUsuario5);
		*/
	}

}
