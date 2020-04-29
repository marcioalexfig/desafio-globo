package br.com.marcioalexfig.gradeprogramacao.enumerador;

public enum TipoEnum {
	CADASTRO("S"), PERGUNTA("Q"), RESPOSTA("A");
	
	private String descricao;
	
	TipoEnum(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

}
