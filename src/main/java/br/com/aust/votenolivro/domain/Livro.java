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
@CommonEntity
@Table(name = "t_livro")
@ToString(of={"idLivro", "titulo","editora"})
@EqualsAndHashCode(of = "idLivro", callSuper = false)
public @Data class Livro{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_livro", length=30)
	private Long idLivro;
	
	@Column(name="titulo", length=30)
	private String titulo;
	
	@Column(name="editora", length=30)
	private String editora;
	
	@Column(name="uri_foto_capa", length=100)
	private String fotoCapa;
	
	@Column(name="isbn", length=30)
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
