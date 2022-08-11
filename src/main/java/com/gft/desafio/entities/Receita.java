package com.gft.desafio.entities;




import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;


@Embeddable
@Entity
public class Receita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@ManyToMany
	@JoinTable(name ="listaIngredientes")
	private List<Ingredientes> ingredientes;
	
	@ManyToMany
	private List<UnidadeMedida> unidadeMedida;
	
	public List<UnidadeMedida> getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(List<UnidadeMedida> unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public List<Ingredientes> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingredientes> ingredientes) {
		this.ingredientes = ingredientes;
	}

	private String nome;

	private Integer tempoPreparo;
	
	private String tempo;

	@Size(max = 3000)
	private String modoPreparo;
	
	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModoPreparo() {
		return modoPreparo;
	}

	public void setModoPreparo(String modoPreparo) {
		this.modoPreparo = modoPreparo;
	}

	public Integer getTempoPreparo() {
		return tempoPreparo;
	}

	public void setTempoPreparo(Integer tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}
	
}
