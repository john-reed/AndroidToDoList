package com.example.todoonthego.test;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.example.todoonthego.model.DBAdapter;
import com.example.todoonthego.model.Task;

public class DBAdapterTest extends AndroidTestCase {
	private Activity context;
	private DBAdapter adapter;
	private Task testTask;
	
	public DBAdapterTest() { 
		super();
		try {
			setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testPreconditions();
		testAddUser();
		testDeleteUser();
		testAddList();
		testDeleteList();
		testAddTask();
		testDeleteTask();
		testMarkTaskComplete();
		testMarkTaskIncomplete();
		try {
			tearDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void setUp() throws Exception{
		context = new Activity();
		adapter = new DBAdapter(context);
		adapter.open();
		adapter.insertUser("tester", "guinea", "pig");
		adapter.insertList("tester_list", "guinea_list", "tester");
		testTask = new Task();
		testTask.setId("tester_task");
		testTask.setName("eat");
		adapter.insertTask(testTask);
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception{
		adapter.deleteTask(testTask);
		adapter.deleteList("tester_list");
		adapter.deleteUser("tester");
		adapter.close();
		adapter = null;
		testTask = null;
		try {
			super.tearDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testPreconditions() {
		assertTrue(true);
	}
	
	public void testAddUser() {
		Cursor current = adapter.getAllUsers();
		int oldCount = current.getCount();
		adapter.insertUser("test", "test", "test");
		int newCount = current.getCount();
		assertTrue("Test user was not added.", newCount > oldCount);
		
	}
	
	public void testDeleteUser() {
		Cursor current = adapter.getAllUsers();
		int oldCount = current.getCount();
		adapter.deleteUser("test");
		int newCount = current.getCount();
		assertTrue("Test user was not deleted.", newCount < oldCount);
	}
	
	public void testAddList() {
		Cursor current = adapter.getAllLists();
		int oldCount = current.getCount();
		adapter.insertList("test", "test", "tester");
		int newCount = current.getCount();
		assertTrue("Test list was not added.", newCount > oldCount);
	}
	
	public void testDeleteList() {
		Cursor current = adapter.getAllLists();
		int oldCount = current.getCount();
		adapter.deleteList("test");
		int newCount = current.getCount();
		assertTrue("Test list was not deleted.", newCount < oldCount);
	}
	
	public void testAddTask() {
		Cursor current = adapter.getAllTasks();
		int oldCount = current.getCount();
		Task newTask = new Task();
		testTask.setId("test");
		testTask.setName("test");
		adapter.insertTask(newTask);
		int newCount = current.getCount();
		assertTrue("Test task was not added.", newCount > oldCount);
	}
	
	public void testEditTask() {
		Cursor oldTask = adapter.getTaskById("test");
		Task editTask = new Task();
		editTask.setId("test");
		editTask.setName("test_again");
		adapter.editExistingTask(editTask);
		Cursor newTask = adapter.getTaskById("test");
		assertNotSame("Test task was not edited.", newTask, oldTask);
	}
	
	public void testDeleteTask() {
		Cursor current = adapter.getAllTasks();
		int oldCount = current.getCount();
		Task newTask = new Task();
		testTask.setId("test");
		testTask.setName("test_again");
		adapter.deleteTask(newTask);
		int newCount = current.getCount();
		assertTrue("Test task was not deleted.", newCount < oldCount);
	}
	
	public void testMarkTaskComplete() {
		Cursor oldTask = adapter.getTaskById("tester_task");
		adapter.setStatusToComplete("tester_task");
		Cursor newTask = adapter.getTaskById("tester_task");
		assertNotSame("Test task was not marked as complete.", newTask, oldTask);
	}
	
	public void testMarkTaskIncomplete() {
		Cursor oldTask = adapter.getTaskById("tester_task");
		adapter.setStatusToIncomplete("tester_task");
		Cursor newTask = adapter.getTaskById("tester_task");
		assertNotSame("Test task was not marked as incomplete.", newTask, oldTask);
		assertNotSame("Test task was not marked as incomplete.", newTask, oldTask);
	}
}
