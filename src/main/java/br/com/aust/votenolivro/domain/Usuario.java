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
@Table(name = "tusuario")
@ToString(of={"idUsuario", "nome","email"})
@EqualsAndHashCode(of="idUsuario", callSuper=false)
public @Data class Usuario{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idusuario")
	private Long idUsuario;
	
	@Column(name="nome", length=30)
	private String nome;
	
	@Column(name="email", length=30)
	private String email;

	@ManyToMany( fetch = FetchType.EAGER, cascade={CascadeType.ALL} )
	@JoinTable(name = "tlivrousuario",
		joinColumns = 		 { @JoinColumn(name = "idusuario", 	nullable = false) },
		inverseJoinColumns = { @JoinColumn(name = "idlivro", 		nullable = false) } )
	private Set<Livro> livrosFavoritos;
	
	public Usuario(){
		//No-op
	}
	
	public Usuario(String nome, String email){
		this.nome = nome;
		this.email = email;
	}

}
