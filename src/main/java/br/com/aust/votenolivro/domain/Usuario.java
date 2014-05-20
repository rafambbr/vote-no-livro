package br.com.aust.votenolivro.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import br.com.aust.votenolivro.domain.common.CommonEntity;

@Entity
@CommonEntity
@Table(name = "t_usuario")
@ToString(of={"idUsuario", "nome","email"})
@EqualsAndHashCode(of="idUsuario", callSuper=false)
public @Data class Usuario{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_usuario")
	private Long idUsuario;
	
	@Column(name="nome", length=50)
	private String nome;
	
	@Column(name="email", length=50)
	private String email;

	@ManyToMany( fetch = FetchType.EAGER, cascade={CascadeType.ALL} )
	@JoinTable(name = "t_livro_usuario",
		joinColumns = 		 { @JoinColumn(name = "id_usuario", 	nullable = false) },
		inverseJoinColumns = { @JoinColumn(name = "id_livro", 		nullable = false) } )
	private Set<Livro> livrosFavoritos;
	
	public Usuario(){
		//No-op
	}
	
	public Usuario(String nome, String email){
		this.nome = nome;
		this.email = email;
	}

}
