package br.com.aust.votenolivro.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.aust.votenolivro.domain.LivroUsuario;
import br.com.aust.votenolivro.domain.id.LivroUsuarioPK;

@Repository
public interface LivroUsuarioRepository extends JpaRepository<LivroUsuario, LivroUsuarioPK>, JpaSpecificationExecutor<LivroUsuario> {

}
