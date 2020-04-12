package com.task.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.task.model.Task;
import com.task.model.User;
import com.task.repository.UserRepository;
import com.task.service.TasktService;
import com.task.util.StatusEnum;


@Controller
@CrossOrigin("*")
public class TaskController {

	@Autowired
	private TasktService tasktService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/tasks-all")
	public ResponseEntity<List<Task>> getAllTodoList() {	
		List<Task> todoListAll = tasktService.findAll();
		if(todoListAll.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<List<Task>>(todoListAll, HttpStatus.OK ) ;
		}
	}
	
	@GetMapping("/tasks-completed")
	public ResponseEntity<List<Task>> getCompletedTodoList() {	
		List<Task> todoListAll = tasktService.findAllByStateCompleted();
		if(todoListAll.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<List<Task>>(todoListAll, HttpStatus.OK ) ;
		}
	}
	
	@GetMapping("/tasks-active")
	public ResponseEntity<List<Task>> getActiveTodoList() {	
		List<Task> todoListAll = tasktService.findAllByStateActived();
		if(todoListAll.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<List<Task>>(todoListAll, HttpStatus.OK ) ;
		}
	}
	
	@GetMapping("/tasks-by-user/{id}")
	public ResponseEntity<List<Task>> getByUser(@PathVariable Integer id) {	
		List<Task> todoListAll = tasktService.findByUserId(id);
		if(todoListAll.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<List<Task>>(todoListAll, HttpStatus.OK ) ;
		}
	}
	
	@PostMapping(value = "/add-task")
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		//if(result.hasErrors()){}	        
	    task.setCreationDate(new Date());
	    task.setStateTask(StatusEnum.ACTIVE);
	    
	    Optional<User> optional = userRepository.findById(task.getIdUser());
	    if(!optional.isPresent()) {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    task.setUser(optional.get());
	    return ResponseEntity.ok(tasktService.save(task));		
	}
	
	@DeleteMapping(value = "/delete-task/{id}")
    public ResponseEntity<Integer> deleteTask(@PathVariable Integer id) {
        tasktService.deleteById(id);        
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
	
	@GetMapping(value="/task-done-completed/{id}")
	 public ResponseEntity<Task> taskDone(@PathVariable Integer id) {
        Task task = tasktService.findById(id);
        if (!task.getStateTask().equals(StatusEnum.COMPLETED)) {       
             task.setStateTask(StatusEnum.COMPLETED);
        	 tasktService.save(task);
        }        
        return ResponseEntity.ok(task);	                
    }
	

	/*==========================================================================================*/
	
	
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(value="/todoMVC", method=RequestMethod.GET)
    public ModelAndView getTasks(){
        ModelAndView mv = new ModelAndView("tasks");
        List<Task> tasks = tasktService.findAll();
        mv.addObject("tasks", tasks);
        return mv;
    }

    @RequestMapping(value="/tasks/{id}", method=RequestMethod.GET)
    public ModelAndView getPostDetails(@PathVariable("id") Integer id){
        ModelAndView mv = new ModelAndView("TaskDetails");
        Task task = tasktService.findById(id);
        mv.addObject("task", task);
        return mv;
    }

    @RequestMapping(path = "/newtask", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String savePost(@Valid Task task, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            //attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
            return "redirect:/newpost";
        }
        task.setCreationDate(new Date());
        tasktService.save(task);
        return "redirect:/task";
    }
   
}
