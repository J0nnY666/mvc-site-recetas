package com.gft.desafio.security;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gft.desafio.entities.Role;
import com.gft.desafio.entities.Usuario;
import com.gft.desafio.repositories.UsuarioRepository;

@Service
@Transactional
public class DetalhesUsuarioServico implements UserDetailsService {

	private UsuarioRepository usuarioRepository;

	public DetalhesUsuarioServico(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLogin(username);

		if (usuario != null) {
			Set<GrantedAuthority> rolesDoUsuario = new HashSet<GrantedAuthority>();
			for (Role role : usuario.getRole()) {
				GrantedAuthority pp = new SimpleGrantedAuthority(role.getRole());
				rolesDoUsuario.add(pp);
			}
			User user = new User(usuario.getLogin(), usuario.getSenha(), rolesDoUsuario);
			return user;
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}

	}

}
