package br.com.aust.votenolivro.web;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.aust.votenolivro.business.exception.BusinessException;
import br.com.aust.votenolivro.business.service.LivroService;
import br.com.aust.votenolivro.business.service.RankingService;
import br.com.aust.votenolivro.domain.Livro;
import br.com.aust.votenolivro.domain.Ranking;

@Slf4j
@Controller
@Scope("session")
public class LivroController {

	@Autowired private LivroService livroService;
	@Autowired private RankingService rankingService;

	@ResponseBody
	@RequestMapping("/livro/salvar")
	public void salvar(Livro livro) {
		livro = this.livroService.salvar(livro);
	}
	
	@RequestMapping("/livro/votar")
	public void votar(Livro livro, HttpServletResponse response){
		this.livroService.votar(livro);
		response.setStatus(200);
	}
	
	@RequestMapping("/livro/carregar/naovotados")
	public ModelAndView carregarLivrosNaoVotados(){
		Collection<Livro> livros = this.livroService.carregarLivrosNaoVotados();
		
		ModelAndView mv = new ModelAndView("votar_no_livro");
		mv.addObject("livros", livros);
		
		return mv;
	}
	
	@RequestMapping("/livro/carregar/todos")
	public ModelAndView carregarTodos(){
		Collection<Livro> livros = this.livroService.carregarTodos();
		
		ModelAndView mv = new ModelAndView("votar_no_livro");
		mv.addObject("livros", livros);
		
		return mv;
	}
	
	@RequestMapping("/livro/carregar/ranking")
	public ModelAndView carregarRanking(){

		ModelAndView mv = new ModelAndView("ranking");
		try{
			Collection<Ranking> rankingLivros = this.rankingService.carregarRanking();
			mv.addObject("rankingLivros", rankingLivros);
			
			Collection<Ranking> rankingLivrosUsuario = this.rankingService.carregarRankingUsuario();
			mv.addObject("rankingLivrosUsuario", rankingLivrosUsuario);
		
		}catch(BusinessException e){
			log.warn("Erro ao carregar o ranking", e);
		}
		
		return mv;
	}
}
