package com.task.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.task.model.User;
import com.task.repository.UserRepository;
import com.task.util.MessageUtil;

@Named
@ApplicationScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 6887759820207514366L;

	@Autowired
	private UserRepository repository;
	
	private User entity, usuarioSessao;
	
	public UserBean() {}
	
	@PostConstruct
	public void init() {
		this.entity = new User();
		usuarioSessao = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public void alterar() {
		if (usuarioSessao.equals(null)) {
			MessageUtil.warningMensage("Usuário não informado!");
			return;
		}
		usuarioSessao.setSenha(new BCryptPasswordEncoder().encode(usuarioSessao.getSenha()));
		User novo = repository.save(usuarioSessao);
		if(novo != null) {
			usuarioSessao = novo;
			MessageUtil.warningMensage("Dados atualizados.");
			return;
		}
		MessageUtil.warningMensage("Dados do usuário inválidos.");
	}

	public User getEntity() {
		return entity;
	}

	public void setEntity(User entity) {
		this.entity = entity;
	}

	public User getUsuarioSessao() {
		return usuarioSessao;
	}

	public void setUsuarioSessao(User usuarioSessao) {
		this.usuarioSessao = usuarioSessao;
	}
    
}
