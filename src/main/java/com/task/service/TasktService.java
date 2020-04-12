package com.task.service;

import java.util.List;

import com.task.model.Task;

public interface TasktService {
  Task findById(Integer id);
  List<Task> findAll();  
  List<Task> findByUserId(Integer id);  
  List<Task> findAllByStateActived();
  List<Task> findAllByStateCompleted();
  Task save(Task todo);
  void deleteById(Integer id);  
}
