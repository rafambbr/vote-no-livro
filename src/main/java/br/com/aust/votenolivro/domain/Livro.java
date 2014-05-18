package br.com.aust.votenolivro.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import br.com.aust.votenolivro.domain.common.CommonEntity;

@Entity
@Table(name = "t_livro")
@ToString(of={"idLivro", "titulo","editora"})
@EqualsAndHashCode(of = "idLivro", callSuper = false)
public @Data class Livro implements CommonEntity{

	private static final long serialVersionUID = -834796842304631600L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_livro")
	private Long idLivro;
	
	@Column(name="titulo")
	private String titulo;
	
	@Column(name="editora")
	private String editora;
	
	@Column(name="uri_foto_capa")
	private String fotoCapa;
	
	@Column(name="isbn")
	private String isbn;
	
	@Column(name="paginas")
	private Integer paginas;
	
	@ManyToMany(mappedBy="livrosFavoritos", fetch=FetchType.LAZY)
	private Set<Usuario> usuarios;
	
	public Livro(){
		//No-op
	}
	
	public Livro(Long idLivro){
		this.idLivro = idLivro;
	}
	
	public Livro(String titulo, String editora, String fotoCapa, String isbn, Integer paginas){
		this.titulo = titulo;
		this.editora = editora;
		this.fotoCapa = fotoCapa;
		this.isbn = isbn;
		this.paginas = paginas;
	}
	
	public Livro(Long idLivro, String titulo, String editora, String fotoCapa, String isbn, Integer paginas){
		this.idLivro = idLivro;
		this.titulo = titulo;
		this.editora = editora;
		this.fotoCapa = fotoCapa;
		this.isbn = isbn;
		this.paginas = paginas;
	}

}
