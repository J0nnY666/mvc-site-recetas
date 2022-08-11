package com.gft.desafio.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.desafio.entities.Role;
import com.gft.desafio.entities.Usuario;
import com.gft.desafio.repositories.RoleRepository;
import com.gft.desafio.repositories.UsuarioRepository;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder criptografia;
	
	private boolean temAutorizacao(Usuario usuario, String role) {
		for (Role pp : usuario.getRole()) {
			if (pp.getRole().equals(role)) {
				return true;
			}
	    }
		return false;
	}
	
	@GetMapping("/index")
	public String index(@CurrentSecurityContext(expression = "authentcation.name") String login) {
		Usuario usuario = usuarioRepository.findByLogin(login);	
		
		String redirectURL = "";
		if (temAutorizacao(usuario, "ADMIN")) {
            redirectURL = "/Usuario/admin/admin-index";
        } else if (temAutorizacao(usuario, "USER")) {
            redirectURL = "/Usuario/user/user-index";
        }	
        return redirectURL;         
	}

	@GetMapping("/novo")
	public String adicionarUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "/Usuario/criar-usuario";
	}

	@PostMapping("/salvar")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return "/Usuario/criar-usuario";
		}
		
		Usuario user = usuarioRepository.findByLogin(usuario.getLogin());
		if(user != null) {
			model.addAttribute("mensagem", "Este e-mail já está cadastrado");
			return "/Usuario/criar-usuario";
		}
		
		Role role = roleRepository.findByRole("USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		usuario.setRole(roles);
		
		String senhaCriptografada = criptografia.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		
		usuarioRepository.save(usuario);
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		return "redirect:/usuario/novo";

	}

	@RequestMapping("/admin/listar")
	public String listarUsuario(Model model) {
		model.addAttribute("usuarios", usuarioRepository.findAll());
		return "/Usuario/admin/admin-listar-usuarios";
	}
	
	@RequestMapping("/logout")
	public String logout(Model model) {
		return "/Usuario/logout";
	}

	@GetMapping("/admin/apagar/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model, RedirectAttributes attributes) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Id inválido:" + id));
		usuarioRepository.delete(usuario);
		attributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso!");
		return "redirect:/usuario/admin/listar";
	}

	@GetMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, Model model) {
		Optional<Usuario> usuarioVelho = usuarioRepository.findById(id);
		if (!usuarioVelho.isPresent()) {
			throw new IllegalArgumentException("Usuário inválido:" + id);
		}
		Usuario usuario = usuarioVelho.get();
		model.addAttribute("usuario", usuario);
		return "/Usuario/user/user-alterar-usuario";
	}

	@PostMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, @Valid Usuario usuario, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			usuario.setId(id);
			return "/Usuario/user/user-alterar-usuario";
		}
		usuarioRepository.save(usuario);
		attributes.addFlashAttribute("mensagem", "Usuário alterado com sucesso!");
		return "redirect:/usuario/admin/listar";
	}

	@GetMapping("/editarRole/{id}")
	public String selecionarRole(@PathVariable("id") long id, Model model) {
		Optional<Usuario> usuarioVelho = usuarioRepository.findById(id);
		if (!usuarioVelho.isPresent()) {
			throw new IllegalArgumentException("Usuário inválido" + id);
		}
		Usuario usuario = usuarioVelho.get();
		model.addAttribute("usuario", usuario);
		model.addAttribute("listaRole", roleRepository.findAll());
		return "/Usuario/admin/admin-editar-role-usuario";
	}
	
	@PostMapping("/editarRole/{id}")
	public String atribuirRole(@PathVariable("id") long idUsuario, 
								@RequestParam(value = "pps", required=false) int[] pps, 
								Usuario usuario, 
								RedirectAttributes attributes) {
		if (pps == null) {
			usuario.setId(idUsuario);
			attributes.addFlashAttribute("mensagem", "Pelo menos um privilégio deve ser informado");
			return "redirect:/usuario/editarRole/"+idUsuario;
		} else {
			List<Role> roles = new ArrayList<Role>();			 
			for (int i = 0; i < pps.length; i++) {
				long idRole = pps[i];
				Optional<Role> roleOptional = roleRepository.findById(idRole);
				if (roleOptional.isPresent()) {
					Role role = roleOptional.get();
					roles.add(role);
		        }
			}
			Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
			if (usuarioOptional.isPresent()) {
				Usuario usr = usuarioOptional.get();
				usr.setRole(roles); 
				usuarioRepository.save(usr);
				attributes.addFlashAttribute("mensagem", "Privilégios Alterados Com Sucesso");
	        }			
		}		
	    return "redirect:/usuario/admin/listar";
	}

	
	
}
