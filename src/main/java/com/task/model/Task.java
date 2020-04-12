package com.task.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task.util.StatusEnum;

@Entity
@Table(name = "TASK")
public class Task implements Serializable {

	private static final long serialVersionUID = -8566766213663626941L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "DESCRIPTION", nullable = false, length = 50, columnDefinition = "text")
	private String description;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "UTC")
	@Column(name = "CREATIONDATE", nullable = false, updatable=false)
	private Date creationDate;

	//@Enumerated(EnumType.STRING)
	@Column(name = "STATETASK", nullable = false)
	private String stateTask;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "USER", nullable = false, foreignKey = @ForeignKey(name = "FK_USER"))
	private User user;
	
	@Transient
	@JsonIgnore
	private boolean statusSwitch;
	
	@Transient
	private Integer idUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public StatusEnum getStateTask() {
		return StatusEnum.getByDescricao(stateTask);
	}

	public void setStateTask(StatusEnum stateTask) {
		this.stateTask = stateTask.getDescricao();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isStatusSwitch() {
		return statusSwitch;
	}

	public void setStatusSwitch(boolean statusSwitch) {
		Integer codigo = StatusEnum.getByCodigo(statusSwitch ? 1 : 0).getCodigo();
		this.statusSwitch = codigo == 1 ? true : false;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
