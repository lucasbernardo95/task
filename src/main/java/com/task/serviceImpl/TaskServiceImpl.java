package com.task.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.model.Task;
import com.task.repository.TaskRepository;
import com.task.service.TasktService;
import com.task.util.StatusEnum;

@Service
public class TaskServiceImpl implements TasktService{

	@Autowired
	TaskRepository taskRepository;
	
	@Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
	
	public List<Task> findAllByStateActived() {
    	return taskRepository.findByStateTask(StatusEnum.ACTIVE.getDescricao());   	
    }
    
    public List<Task> findAllByStateCompleted() {
    	return taskRepository.findByStateTask(StatusEnum.COMPLETED.getDescricao());   	
    }
    
    @Override
    public List<Task> findByUserId(Integer id) {
    	return taskRepository.findByUserId(id);
    }
	
    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }   
    
    @Override
    public void deleteById(Integer id) {
        taskRepository.deleteById(id); ;
    }         
                             
    @Override
    public Task findById(Integer id) {
        return taskRepository.findById(id).get();
    }
    
}
