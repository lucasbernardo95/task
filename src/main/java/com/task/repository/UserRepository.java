package com.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.task.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(" from User where login like %:login% and senha like %:senha% ")
	public User findUserByLoginAndPassword(@Param("login") String login, @Param("senha") String senha);
	
	@Query(" from User where login like %:login% ")
	public User findUserByLogin(@Param("login") String login);
}
