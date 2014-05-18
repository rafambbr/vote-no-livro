package br.com.aust.votenolivro.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import br.com.aust.votenolivro.domain.common.CommonEntity;
import br.com.aust.votenolivro.domain.id.LivroUsuarioPK;

@Entity
@Table(name = "t_livro_usuario")
@EqualsAndHashCode(of = "id", callSuper = false)
public @Data class LivroUsuario implements CommonEntity{

	private static final long serialVersionUID = 5500629304462740954L;
	
	@EmbeddedId
	private LivroUsuarioPK id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_usuario", referencedColumnName="id_usuario", nullable = false, insertable=false, updatable=false)
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_livro", referencedColumnName="id_livro", nullable = false, insertable=false, updatable=false)
	private Livro livro;
	
	public LivroUsuario(){
		//No-op
	}
	
	public LivroUsuario(Usuario usuario, Livro livro){
		this.usuario = usuario;
		this.livro = livro;
		
		if(usuario != null && livro != null){
			Long idLivro = livro.getIdLivro();
			Long idUsuario = usuario.getIdUsuario();
			this.id = new LivroUsuarioPK(idLivro, idUsuario);
		}
	}
}
