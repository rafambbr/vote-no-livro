package br.com.aust.votenolivro.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aust.votenolivro.business.exception.ServiceException;
import br.com.aust.votenolivro.business.repository.UsuarioRepository;
import br.com.aust.votenolivro.business.service.UsuarioService;
import br.com.aust.votenolivro.domain.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired private UsuarioRepository usuarioRepository;
	
	@Override
	public void salvarLivrosFavoritosUsuario(Usuario usuario) {
		
		if(usuario == null || usuario.getLivrosFavoritos() == null || usuario.getLivrosFavoritos().isEmpty() ){
			throw new ServiceException("O usuario precisa ter uma lista de livros favoritos");
		}
		
		this.usuarioRepository.save(usuario);
	}

}
