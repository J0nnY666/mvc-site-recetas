package com.gft.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.desafio.entities.Ingredientes;

@Repository
public interface IngredientesRepository extends JpaRepository<Ingredientes, Long> {
	
	List<Ingredientes> findByNomeContainsOrderByNome(String nome);
	List<Ingredientes> findByOrderByNome();

}
