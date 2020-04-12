package com.task.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // indica ao spring que essa é uma classe de configuração
@EnableWebSecurity // habilita que esta classe vai configurar o websecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImplements userDetailsService;
	
    private static final String[] AUTH_LIST = {
            "/",
            "/tasks-all",
            "/javax.faces.resource/**",
            "/login",
            "/task",
            "/tasks/",
            "/newtask",
            "/add-task",
            "/delete-task/**",
            "/tasks-completed",
            "/tasks-completed/**",
            "/tasks-active",        
            "/todoMVC"
        };
	
	// Define as URLs que precisam de autenticação para ser acessadas ou não
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests() 
			.antMatchers(AUTH_LIST).permitAll()
			.anyRequest().authenticated() 
			.antMatchers("/task/usuario.xhtml").authenticated()//o que estiver dentro /eventos/ precisa estar autenticado
			.and().formLogin().permitAll()
//			.loginPage("/login.xhtml")
//			.loginProcessingUrl("/appLogin")
//			.usernameParameter("app_username")
//			.passwordParameter("app_password")
			.defaultSuccessUrl("/task/usuario.xhtml") //define o formulário padrão do security e se tiver autenticado redireciona para /eventos/index.xhtml
	        .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));// /logout encerra a cessão
	}

	// define como vai ser feita a autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	// serve para que o spring security não bloqueie as páginas estáticas como
	// styles.css e etc
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/");
		web.ignoring().antMatchers("/bootstrap/**");
        web.ignoring().antMatchers("/resources/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}