package com.task.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.task.model.Task;
import com.task.model.User;
import com.task.util.StatusEnum;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TaskRepositoryTest {

	@MockBean
	private TaskRepository repository;
	
	private User user;
	private Task task;
	
	@Before
	public void setUp() {
		user = createUser(1);
		task = createTask(1);
	}
	
	@Test
	public void testSave() {
		when(repository.save(task)).thenReturn(createTask(1));
		Task response = repository.save(task);
		assertThat(response, is(equalTo(task)));
	}
	
	@Test
	public void testFindById() {
		when(repository.findById(1)).thenReturn(Optional.of(task));
		Optional<Task> optional = repository.findById(1);
		assertTrue(optional.isPresent());
		assertThat(optional.get(), is(equalTo(task)));
	}
	
	@Test
	public void testfindByUserId() {
		Integer id = user.getId();
		
		when(repository.findByUserId(id))
			.thenReturn(Arrays.asList(createTask(1), createTask(2), createTask(3)));
		
		List<Task> list = repository.findByUserId(id);
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void testDeleteByyId() {
		
		Integer id = task.getId();
		
		when(repository.findById(id)).thenReturn(Optional.empty());
		
		repository.delete(task);
		
		Optional<Task> optional = repository.findById(id);
		
		verify(repository, times(1)).delete(task);
		assertFalse(optional.isPresent());
	}
	
	private Task createTask(Integer id) {
		Task task = new Task();
		task.setId(id);
		task.setCreationDate(new Date());
		task.setDescription("description");
		task.setStateTask(StatusEnum.ACTIVE);
		task.setUser(user);
		return task;
	}
	
	private User createUser(Integer id) {
		return new User("User", "admin", "admin", null);
	}
	
}
