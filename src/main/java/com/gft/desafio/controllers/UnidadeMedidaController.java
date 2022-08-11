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

import com.gft.desafio.entities.UnidadeMedida;
import com.gft.desafio.services.ReceitaService;
import com.gft.desafio.services.UnidadeMedidaService;

@Controller
@RequestMapping("UnidadeMedida")
public class UnidadeMedidaController {

	@Autowired
	private UnidadeMedidaService unidadeMedidaService;

	@Autowired
	private ReceitaService receitaService;

	@RequestMapping(path = "novo")
	public ModelAndView novaUndiadeMedida() {

		ModelAndView mv = new ModelAndView("UnidadeMedida/form.html");
		mv.addObject("UnidadeMedida", new UnidadeMedida());

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "editar")
	public ModelAndView salvarUnidadeMedida(@Valid UnidadeMedida unidadeMedida, BindingResult bindingResult) {

		ModelAndView mv = new ModelAndView("UnidadeMedida/form.html");

		boolean novo = true;

		if (unidadeMedida.getId() != null) {
			novo = false;
		}

		if (bindingResult.hasErrors()) {
			mv.addObject("unidadeMedida", unidadeMedida);
			return mv;
		}

		UnidadeMedida unidadeMedidaSalva = unidadeMedidaService.salvarUnidadeMedida(unidadeMedida);

		if (novo) {
			mv.addObject("unidadeMedida", new UnidadeMedida());
		} else {
			mv.addObject("unidadeMedida", unidadeMedidaSalva);
		}

		mv.addObject("mensagem", "Unidade de medida salva com sucesso");
		mv.addObject("listaReceita", receitaService.listarTodasReceitas());

		return mv;
	}

	@RequestMapping
	public ModelAndView listarUnidadeMedida(String nome) {

		ModelAndView mv = new ModelAndView("UnidadeMedida/listar.html");
		mv.addObject("lista", unidadeMedidaService.listarUnidadeMedida(nome));

		return mv;

	}

	@RequestMapping(path = "editar")
	public ModelAndView editarUnidadeMedida(@RequestParam(required = false) Long id) {

		ModelAndView mv = new ModelAndView("UnidadeMedida/form.html");

		UnidadeMedida unidadeMedida;

		if (id == null) {
			unidadeMedida = new UnidadeMedida();
		} else {
			try {
				unidadeMedida = unidadeMedidaService.obterUnidadeMedida(id);
			} catch (Exception e) {
				unidadeMedida = new UnidadeMedida();
				mv.addObject("mensagem", e.getMessage());
			}

		}

		mv.addObject("unidadeMedida", unidadeMedida);
		mv.addObject("listaReceita", receitaService.listarTodasReceitas());

		return mv;
	}

	@RequestMapping("/excluir")
	public ModelAndView excluirUnidadeMedida(@RequestParam Long id, RedirectAttributes redirectAttributes) {

		ModelAndView mv = new ModelAndView("redirect:/UnidadeMedida");

		try {
			unidadeMedidaService.excluirUnidadeMedida(id);
			redirectAttributes.addFlashAttribute("mensagem", "Unidade de medida excluída com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir unidade de medida!" + e.getMessage());
		}

		return mv;
	}
}
