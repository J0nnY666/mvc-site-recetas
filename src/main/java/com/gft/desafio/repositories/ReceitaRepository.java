package com.gft.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.desafio.entities.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long>{

	List<Receita> findByNomeContainsOrderByNome(String nome);
	List<Receita> findByOrderByNome();
	
	List<Receita> findByNomeContainsAndIngredientesContains(String nome, String ingredientes);
	
}
