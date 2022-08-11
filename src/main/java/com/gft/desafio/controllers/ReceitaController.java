package com.gft.desafio.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.desafio.entities.Receita;
import com.gft.desafio.services.IngredientesService;
import com.gft.desafio.services.ReceitaService;
import com.gft.desafio.services.UnidadeMedidaService;

@Controller
@RequestMapping("Receita")
public class ReceitaController {

	@Autowired
	private IngredientesService ingredientesService;

	@Autowired
	private UnidadeMedidaService unidadeMedidaService;

	@Autowired
	private ReceitaService receitaService;

	@RequestMapping(path = "receita")
	public ModelAndView verReceita(@RequestParam(required = false) Long id) {

		ModelAndView mv = new ModelAndView("Receita/receita.html");

		Receita receita;

		if (id == null) {
			receita = new Receita();

		} else {
			try {
				receita = receitaService.obterReceita(id);

			} catch (Exception e) {
				receita = new Receita();
				mv.addObject("mensagem", e.getMessage());
			}
		}

		mv.addObject("receita", receita);
		mv.addObject("listaIngredientes", ingredientesService.listarTodosIngredientes());
		mv.addObject("listaUnidadeMedida", unidadeMedidaService.listarTodasUnidadeMedida());

		return mv;
	}

	@RequestMapping(path = "editar")
	public ModelAndView editarReceita(@RequestParam(required = false) Long id) {

		ModelAndView mv = new ModelAndView("Receita/form.html");

		Receita receita;

		if (id == null) {
			receita = new Receita();
		} else {
			try {
				receita = receitaService.obterReceita(id);
			} catch (Exception e) {
				receita = new Receita();
				mv.addObject("mensagem", e.getMessage());
			}
		}

		mv.addObject("receita", receita);
		mv.addObject("listaIngredientes", ingredientesService.listarTodosIngredientes());
		mv.addObject("listaUnidadeMedida", unidadeMedidaService.listarTodasUnidadeMedida());

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "editar")
	public ModelAndView salvarReceita(@Valid Receita receita, BindingResult bindingResult) {

		ModelAndView mv = new ModelAndView("Receita/form.html");

		boolean novo = true;

		if (receita.getId() != null) {
			novo = false;
		}

		if (bindingResult.hasErrors()) {
			mv.addObject("receita", receita);
			return mv;
		}

		receitaService.salvarReceita(receita);
		if (novo) {
			mv.addObject("receita", new Receita());
		} else {
			mv.addObject("receita", receita);
		}

		mv.addObject("mensagem", "Receita salva com sucesso");
		mv.addObject("listaIngredientes", ingredientesService.listarTodosIngredientes());
		mv.addObject("listaUnidadeMedida", unidadeMedidaService.listarTodasUnidadeMedida());

		return mv;
	}

	@RequestMapping
	public ModelAndView listarReceita(String nome) {

		ModelAndView mv = new ModelAndView("Receita/listar.html");
		
		mv.addObject("lista", receitaService.listarReceita(nome));

		return mv;
	}

	@RequestMapping("/excluir")
	public ModelAndView excluirReceita(@RequestParam Long id, RedirectAttributes redirectAttributes) {

		ModelAndView mv = new ModelAndView("redirect:/Receita");

		try {
			receitaService.excluirReceita(id);
			// receitaIngredientesService.excluirReceitaIngredientes(id);
			redirectAttributes.addFlashAttribute("mensagem", "Receita excluída com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir receita!" + e.getMessage());
		}

		return mv;
	}

}
