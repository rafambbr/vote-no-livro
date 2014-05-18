package br.com.aust.votenolivro.domain.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode(callSuper=false, of={"idLivro", "idUsuario"})
public @Data class LivroUsuarioPK implements Serializable{

	private static final long serialVersionUID = 4258019472590035679L;

	@Column( name="id_livro", nullable=false, insertable=false, updatable=false )
	private Long idLivro;

	@Column( name="id_usuario", nullable=false, insertable=false, updatable=false )
	private Long idUsuario;

	public LivroUsuarioPK(){
		//No-op
	}

	public LivroUsuarioPK(Long idLivro,  Long idUsuario){
		this.idLivro = idLivro;
		this.idUsuario = idUsuario;
	}
}
