package br.com.aust.votenolivro.business.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.aust.votenolivro.business.exception.RepositoryException;
import br.com.aust.votenolivro.domain.Livro;

@Repository
public interface LivroRepository  extends JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro>{

	@Query("select l from Livro l where l.idLivro NOT IN :idsLivros ")
	public List<Livro> carregarLivrosNaoVotados( 
			@Param("idsLivros") Collection<Long> livrosVotados,
			Pageable pageable) throws RepositoryException;
	
}
