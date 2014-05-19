package br.com.aust.votenolivro.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.aust.votenolivro.business.service.LivroService;
import br.com.aust.votenolivro.business.service.RankingService;
import br.com.aust.votenolivro.domain.Livro;
import br.com.aust.votenolivro.domain.Usuario;

@Slf4j
@Controller
@Scope("session")
public class UsuarioController {

	@Autowired private LivroService livroService;
	@Autowired private RankingService rankingService;
	
	@Transactional
	@RequestMapping("/usuario/salvar/livrosvotados")
	public String salvarLivrosFavoritosUsuario(Usuario usuario, HttpServletRequest request){
		try{
			Collection<Livro> livrosVotados = this.livroService.getLivrosVotados();
			this.rankingService.salvar(livrosVotados, usuario);
			request.getSession().setAttribute("usuarioJaVotou", true);
		}catch(Exception e){
			log.warn("Erro ao salvar votos do usuario", e);
		}
	
		return "redirect:/livro/carregar/ranking";
	}
}
