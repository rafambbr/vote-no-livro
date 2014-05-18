package br.com.aust.votenolivro.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(of={"livro", "totalVotos"})
@EqualsAndHashCode(of = "livro", callSuper = false)
public @Data class Ranking implements Comparable<Ranking>{

	private Livro livro;
	private long totalVotos;
	
	public Ranking(){
		//No-op
	}
	
	public Ranking(Livro livro){
		this.livro = livro;
	}
	
	public Ranking(Livro livro, Long totalVotos){
		this.livro = livro;
		this.totalVotos = totalVotos;
	}
	
	public void adicionarVoto(){
		this.totalVotos++;
	}

	@Override
	public int compareTo(Ranking ranking) {
		int retorno = 0;
		long totalVotosCompare = ranking.getTotalVotos();
		if(totalVotosCompare < this.totalVotos){
			retorno = 1;
		}else if(totalVotosCompare > this.totalVotos){
			retorno = -1;
		}
		
		return retorno;
	}
}
