package com.task.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.task.model.Task;
import com.task.model.User;
import com.task.repository.TaskRepository;
import com.task.repository.UserRepository;
import com.task.util.MessageUtil;
import com.task.util.StatusEnum;

@Named
@SessionScoped
public class TaskBean implements Serializable {

	private static final long serialVersionUID = -7111078386567073279L;
	
	@Autowired
	private TaskRepository repository;
	
	@Autowired
	private UserRepository userRepository;

	private Task selected, entity;
	private User usuarioSession;
	
	public TaskBean() {}
	
	@PostConstruct
	public void init() {
		this.selected = new Task();
		this.entity = new Task();
		usuarioSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
	
	public void reset() {
		selected = new Task();
	}
	
	public void novo() {
		entity.setCreationDate(new Date());
		entity.setUser(usuarioSession);
		entity.setStateTask(StatusEnum.ACTIVE);
		if(salvar(entity)) 
			entity = new Task();
	}
	
	public void alterar() {
		if (selected.equals(null)) {
			MessageUtil.warningMensage("Tarefa não informada!");
			return;
		}

		salvar(selected);
		this.selected = new Task();
	}
	
	public boolean salvar(Task task) {
		if(task != null && !isCampoVazio(task.getDescription()) && task.getUser()!= null) {
			
			int status = task.isStatusSwitch() ? 1 : 0;
			task.setStateTask(StatusEnum.getByCodigo(status));
			task.setUser(usuarioSession);
			
			repository.save(task);
			buscarUsuario();
			return true;
		}
		MessageUtil.warningMensage("Dados da tarefa inválidos.");
		return false;
	}
	
	public void delete() {
		try {
			repository.deleteById(selected.getId());
			selected = new Task();
			buscarUsuario();
		} catch (RuntimeException r) {
			MessageUtil.errorMessage("Erro ao tentar deletar.");
		}
	}
	
	public void onRowSelect(SelectEvent event) {
		selected = (Task) event.getObject();
	}

	public void onRowUnselect(UnselectEvent event) {
		// MessageUtil.successMensage("");
	}
	
	private boolean isCampoVazio(String valor) {
		return (valor == null || valor.trim().isEmpty());
	}

	public void buscarUsuario() {
		Optional<User> optional = userRepository.findById(usuarioSession.getId());
		if(optional.isPresent()) {
			usuarioSession = optional.get();
		}
	}
	
	public Task getSelected() {
		return selected;
	}

	public void setSelected(Task selected) {
		this.selected = selected;
	}

	public Task getEntity() {
		return entity;
	}

	public void setEntity(Task entity) {
		this.entity = entity;
	}

	public User getUsuarioSession() {
		return usuarioSession;
	}

	public void setUsuarioSession(User usuarioSession) {
		this.usuarioSession = usuarioSession;
	}
	
}
