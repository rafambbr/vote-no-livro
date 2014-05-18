package br.com.aust.votenolivro.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.aust.votenolivro.SampleDataJpaApplication;
import br.com.aust.votenolivro.business.exception.ServiceException;
import br.com.aust.votenolivro.domain.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleDataJpaApplication.class)
public class UsuarioServiceTest {
	
	private Usuario rafael;

	@Autowired private UsuarioService usuarioService;
	
	@Test(expected=ServiceException.class)
	public void deveRetornarExceptionCasoSalveOUsuarioSemOsLivrosVotados(){
		this.rafael = new Usuario("Rafael Camargo", "rafael.camargo.sp@gmail.com");
		this.usuarioService.salvarLivrosFavoritosUsuario(rafael);
	}
	
}
