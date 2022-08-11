package com.gft.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.desafio.entities.Receita;
import com.gft.desafio.repositories.ReceitaRepository;

@Service
public class ReceitaService {

	@Autowired
	private ReceitaRepository receitaRepository;

	public Receita salvarReceita(Receita receita) {

		return receitaRepository.save(receita);
	}

	public List<Receita> listarReceita(String nome) {

		if (nome != null)
			return listarReceitaPorNome(nome);

		return listarTodasReceitas();

	}

	public List<Receita> listarTodasReceitas() {

		return receitaRepository.findByOrderByNome();
	}

	public List<Receita> listarReceitaPorNome(String nome) {

		return receitaRepository.findByNomeContainsOrderByNome(nome);
	}

	public Receita obterReceita(Long id) throws Exception {

		Optional<Receita> receita = receitaRepository.findById(id);

		if (receita.isEmpty()) {
			throw new Exception("Receita não encontrada");
		}
		return receita.get();
	}

	public void excluirReceita(Long id) {

		receitaRepository.deleteById(id);

	}

}