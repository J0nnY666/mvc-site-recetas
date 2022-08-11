package com.gft.desafio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.gft.desafio.repositories.UsuarioRepository;


@Configuration
@EnableWebSecurity
public class ConfiguraçãoSegurança extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private DetalhesUsuarioServico userDetailService;
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception{
		DetalhesUsuarioServico detalheDoUsuario = new DetalhesUsuarioServico(usuarioRepository);
		return detalheDoUsuario;
	}
	
	@Bean
	public BCryptPasswordEncoder gerarCriptografia() {
		BCryptPasswordEncoder criptografia = new BCryptPasswordEncoder();
		return criptografia;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/Usuario/user/*").hasAnyAuthority("USER","ADMIN")
		.antMatchers("/Usuario/admin/*").hasAnyAuthority("ADMIN")
		.antMatchers("/usuario/admin/*").hasAnyAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/Receita/editar").hasAnyAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/Receita/excluir").hasAnyAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/Ingredientes/editar").hasAnyAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/Ingredientes/excluir").hasAnyAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/UnidadeMedida/editar").hasAnyAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/UnidadeMedida/excluir").hasAnyAuthority("ADMIN")
		.and()
		.exceptionHandling().accessDeniedPage("/Usuario/acesso-negado")
		.and()
		.formLogin()
		.loginPage("/login").permitAll()
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/usuario/logout").permitAll();
	}
		
		
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService)
		.passwordEncoder(new BCryptPasswordEncoder());
	
	}

}
