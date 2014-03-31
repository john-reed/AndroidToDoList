package com.example.todoonthego.test;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.widget.ListView;
import com.example.testpassing.R;
import com.example.todoonthego.activity.AddUserActivity;
import com.example.todoonthego.activity.MainActivity;
import com.example.todoonthego.model.DBAdapter;
import com.example.todoonthego.model.Task;
import com.example.todoonthego.model.ToDoList;

public class DBAdapterTest extends ActivityInstrumentationTestCase2<MainActivity> {//extends AndroidTestCase {
	private Activity activity;
	private ListView activityGui;
	private DBAdapter adapter;
	
	public DBAdapterTest() { 
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		activity = getActivity();
		activityGui = (ListView) activity.findViewById(R.id.listViewForUsers);
		adapter = new DBAdapter(activity);
	}
	
	@Override
	protected void tearDown() throws Exception{
		try {
			super.tearDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testPreconditions() {
		assertNotNull("activity is null", activity);
	    assertNotNull("activityGui is null", activityGui);
	}
	
	public void testAddUser() {
		adapter.open();
		adapter.insertUser("test", "test", "test");
		Cursor newUser = adapter.getUserById("test");
		newUser.moveToFirst();
		String newUserName = newUser.getString(1);
		assertTrue("Test user was not added.", newUserName.equals("test"));
		adapter.close();
	}
	
	public void testDeleteUser() {
		adapter.open();
		adapter.insertUser("test", "test", "test");
		adapter.deleteUser("test");
		Cursor testUser = adapter.getUserById("Test");
		testUser.moveToFirst();
		assertTrue("Test user was not deleted.", testUser.getCount() == 0);
		adapter.close();
	}
	
	public void testAddList() {
		adapter.open();
		adapter.insertList("test", "test", "test");
		Cursor newList = adapter.getListById("test");
		newList.moveToFirst();
		String newListName = newList.getString(1);
		assertTrue("Test list was not added.", newListName.equals("test"));
		adapter.close();
	}
	
	public void testDeleteList() {
		adapter.open();
		adapter.insertList("test", "test", "test");
		adapter.deleteList("test");
		Cursor testList = adapter.getListById("test");
		testList.moveToFirst();
		assertTrue("Test list was not deleted.", testList.getCount() == 0);
		adapter.close();
	}
	
	public void testAddTask() {
		adapter.open();
		Task testTask = new Task();
		testTask.setId("test");
		testTask.setName("test");
		Calendar dueDate = Calendar.getInstance();
		testTask.setDueDate(dueDate);
		testTask.setDueDateStr("222223");
		testTask.setPriorityLevel(1);
		testTask.setPriorityLevelStr("1");
		testTask.setCompletionStatus(1);
		testTask.setCompletionStatusStr("1");
		testTask.setUserId("test");
		ToDoList testList = new ToDoList();
		testList.setId("test");
		testTask.setList(testList);
		adapter.insertTask(testTask);
		Cursor newTask = adapter.getTaskById("test");
		newTask.moveToFirst();
		String newTaskName = newTask.getString(newTask
				.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_ID));
		assertTrue("Test task was not added.", newTaskName.equals("test"));
		adapter.close();
	}
	
	public void testEditTask() {
		adapter.open();
		
		Task testTask = new Task();
		testTask.setId("test");
		testTask.setName("test");
		Calendar dueDate = Calendar.getInstance();
		testTask.setDueDate(dueDate);
		testTask.setDueDateStr("222223");
		testTask.setPriorityLevel(1);
		testTask.setPriorityLevelStr("1");
		testTask.setCompletionStatus(1);
		testTask.setCompletionStatusStr("1");
		testTask.setUserId("test");
		ToDoList testList = new ToDoList();
		testList.setId("test");
		testTask.setList(testList);
		adapter.insertTask(testTask);
		//Cursor testTask = adapter.getTaskById("test");
		//testTask.moveToFirst();
		Task editTask = new Task();
		editTask.setId("test");
		editTask.setName("test_again");
		Calendar newdueDate = Calendar.getInstance();
		editTask.setDueDate(newdueDate);
		editTask.setDueDateStr("22223");
		editTask.setPriorityLevel(1);
		editTask.setPriorityLevelStr("2");
		editTask.setCompletionStatus(1);
		editTask.setCompletionStatusStr("1");
		editTask.setUserId("test");
		ToDoList testList1 = new ToDoList();
		testList1.setId("test");
		editTask.setList(testList);
		adapter.editExistingTask(editTask);
		Cursor testTask1 = adapter.getTaskById("test");
		testTask1.moveToFirst();
		String testTaskName = testTask1.getString(testTask1
				.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_NAME));
		assertTrue("Test task was not edited.", testTaskName.equals("test_again"));
		adapter.close();
	}
	
	public void testDeleteTask() {
		adapter.open();
		Task testTask = new Task();
		testTask.setId("test");
		testTask.setName("test_again");
		ToDoList testList = new ToDoList();
		testTask.setList(testList);
		adapter.deleteTask(testTask);
		Cursor checkTask = adapter.getTaskById("test");
		checkTask.moveToFirst();
		assertTrue("Test task was not deleted.", checkTask.getCount() == 0);
		adapter.close();
	}
	
	public void testMarkTaskComplete() {
		adapter.open();
		Task testTask = new Task();
		testTask.setId("test");
		testTask.setName("test");
		Calendar dueDate = Calendar.getInstance();
		testTask.setDueDate(dueDate);
		testTask.setDueDateStr("222223");
		testTask.setPriorityLevel(1);
		testTask.setPriorityLevelStr("1");
		testTask.setCompletionStatus(1);
		testTask.setCompletionStatusStr("1");
		testTask.setUserId("test");
		ToDoList testList = new ToDoList();
		testList.setId("test");
		testTask.setList(testList);
		adapter.insertTask(testTask);
		adapter.setStatusToComplete("test");
		Cursor completeTask = adapter.getTaskById("test");
		completeTask.moveToFirst();
		String status = completeTask.getString(10);
		assertTrue("Test task was not marked as complete.", status.equals("Complete"));
		adapter.deleteTask(testTask);
		adapter.close();
	}
	
	public void testMarkTaskIncomplete() {
		adapter.open();
		Task testTask = new Task();
		testTask.setId("test");
		testTask.setName("test");
		Calendar dueDate = Calendar.getInstance();
		testTask.setDueDate(dueDate);
		testTask.setDueDateStr("222223");
		testTask.setPriorityLevel(1);
		testTask.setPriorityLevelStr("1");
		testTask.setCompletionStatus(1);
		testTask.setCompletionStatusStr("1");
		testTask.setUserId("test");
		ToDoList testList = new ToDoList();
		testList.setId("test");
		testTask.setList(testList);
		adapter.insertTask(testTask);
		adapter.setStatusToIncomplete("test");
		Cursor incompleteTask = adapter.getTaskById("test");
		incompleteTask.moveToFirst();
		String status = incompleteTask.getString(10);
		assertTrue("Test task was not marked as incomplete.", status.equals("Incomplete"));
		adapter.deleteTask(testTask);
		adapter.close();
	}
}
