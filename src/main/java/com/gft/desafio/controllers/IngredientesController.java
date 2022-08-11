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

import com.gft.desafio.entities.Ingredientes;
import com.gft.desafio.services.IngredientesService;
import com.gft.desafio.services.ReceitaService;

@Controller
@RequestMapping("Ingredientes")
public class IngredientesController {

	@Autowired
	private IngredientesService ingredientesService;

	@Autowired
	private ReceitaService receitaService;

	@RequestMapping(path = "/novo")
	private ModelAndView novoIngrediente() {

		ModelAndView mv = new ModelAndView("Ingrediente/form.html");
		mv.addObject("Ingredientes", new Ingredientes());

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "editar")
	public ModelAndView salvarIngrediente(@Valid Ingredientes ingredientes, BindingResult bindingResult) {

		ModelAndView mv = new ModelAndView("Ingredientes/form.html");

		boolean novo = true;

		if (ingredientes.getId() != null) {
			novo = false;
		}

		if (bindingResult.hasErrors()) {
			mv.addObject("ingredientes", ingredientes);
			return mv;

		}

		Ingredientes ingredientesSalvo = ingredientesService.salvarIngredientes(ingredientes);

		if (novo) {
			mv.addObject("ingredientes", new Ingredientes());
		} else {
			mv.addObject("ingredientes", ingredientesSalvo);
		}

		mv.addObject("mensagem", "Ingrediente salvo com sucesso");
		mv.addObject("listaReceita", receitaService.listarTodasReceitas());
		return mv;
	}

	@RequestMapping
	public ModelAndView listarIngredientes(String nome) {

		ModelAndView mv = new ModelAndView("Ingredientes/listar.html");
		mv.addObject("lista", ingredientesService.listarIngredientes(nome));

		return mv;

	}

	@RequestMapping(path = "editar")
	public ModelAndView editarIngredientes(@RequestParam(required = false) Long id) {

		ModelAndView mv = new ModelAndView("Ingredientes/form.html");

		Ingredientes ingredientes;


		if (id == null) {
			ingredientes = new Ingredientes();

		} else {
			try {
				ingredientes = ingredientesService.obterIngredientes(id);

			} catch (Exception e) {
				ingredientes = new Ingredientes();
				mv.addObject("mensagem", e.getMessage());
			}
		}

		mv.addObject("ingredientes", ingredientes);
		mv.addObject("listaReceita", receitaService.listarTodasReceitas());

		return mv;
	}

	@RequestMapping("/excluir")
	public ModelAndView excluirIngrediente(@RequestParam Long id, RedirectAttributes redirectAttributes) {

		ModelAndView mv = new ModelAndView("redirect:/Ingredientes");

		try {
			ingredientesService.excluirIngredientes(id);
			redirectAttributes.addFlashAttribute("mensagem", "Ingrediente excluído com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir ingrediente!" + e.getMessage());
		}

		return mv;
	}
	
}
