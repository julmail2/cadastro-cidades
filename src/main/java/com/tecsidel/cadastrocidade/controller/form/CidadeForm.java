package com.tecsidel.cadastrocidade.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.tecsidel.cadastrocidade.model.Cidade;
import com.tecsidel.cadastrocidade.model.UF;

public class CidadeForm {

	private Long id;

	@NotBlank(message = " *Campo obrigatório")
	@Size(max = 200, message = "*O nome da cidade deve ter no máximo 200 caracteres")
	private String nome;

	@javax.validation.constraints.NotNull(message = " *Campo obrigatório")
	private Integer ufId;

	public Cidade toCidade() {
		Cidade cidade = new Cidade();
		cidade.setId(this.getId());
		UF uf = new UF();
		uf.setId(this.getUfId());
		cidade.setUf(uf.getId() == null ? null : uf);
		cidade.setNome(this.getNome());
		return cidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getUfId() {
		return ufId;
	}

	public void setUfId(Integer ufId) {
		this.ufId = ufId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void reset() {
		this.id=null;
		this.nome="";
		this.ufId=null;
		
	}

}
