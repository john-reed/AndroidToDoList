package com.example.todoonthego.model;

import java.io.Serializable;
import java.util.Calendar;


public class Task implements Serializable {
	
	// Priority level of the task
	public static final int HIGH_PRIORITY = 0;
	public static final int MEDIUM_PRIORITY = 1;
	public static final int LOW_PRIORITY = 2;
	
	// Completion status
	public static final int COMPLETED = 1;
	public static final int INCOMPLETE = 0;
	
	// A static variable to use when put it into bundle
	public static final String TASK_BUNDLE_KEY = "task_bundle_key";  
	
	// Task Id
	private String id;
	
	// Task name
	private String name;
	
	// Task due date
	private Calendar dueDate;
	private String dueDateStr;

	public String getDueDateStr() {
		return dueDateStr;
	}

	public void setDueDateStr(String dueDateStr) {
		this.dueDateStr = dueDateStr;
	}

	// Task details
	private String details;
	
	// The priority level of this task
	private int priorityLevel;
	private String priorityLevelStr;
	
	// The list this task belongs to
	private ToDoList toDoList;
	
	// The user this task belongs to
	private String userId;
	
	// The completion status of this task
	private int completionStatus;
	private String completionStatusStr;
	
	public Task(){
		this.id = "";
		this.name = "";
		this.dueDate = Calendar.getInstance();
		this.details = "";
		this.priorityLevel = HIGH_PRIORITY;
		this.priorityLevelStr = "High priority";
		this.toDoList = new ToDoList();
		this.userId = "";
		this.completionStatus = INCOMPLETE;
		this.completionStatusStr = "Incomplete";
	}
	
	public Task(String id, String title, Calendar dueDate, String details,
			int priorityLevel,  String priorityLevelStr, ToDoList toDoList, String userId,
			int completionStatus, String completionStatusStr) {
		super();
		this.id = id;
		this.name = title;
		this.dueDate = dueDate;
		this.details = details;
		this.priorityLevel = priorityLevel;
		this.priorityLevelStr = priorityLevelStr;
		this.toDoList = toDoList;
		this.userId = userId;
		this.completionStatus = completionStatus;
		this.completionStatusStr = completionStatusStr;
	}

	// Getters and Setters
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getDueDate() {
		return dueDate;
	}

	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	public String getPriorityLevelStr() {
		return priorityLevelStr;
	}

	public void setPriorityLevelStr(String priorityLevelStr) {
		this.priorityLevelStr = priorityLevelStr;
	}

	public ToDoList getList() {
		return toDoList;
	}

	public void setList(ToDoList toDoList) {
		this.toDoList = toDoList;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getCompletionStatus() {
		return completionStatus;
	}

	public void setCompletionStatus(int completionStatus) {
		this.completionStatus = completionStatus;
	}
	public String getCompletionStatusStr() {
		return completionStatusStr;
	}

	public void setCompletionStatusStr(String completionStatusStr) {
		this.completionStatusStr = completionStatusStr;
	}
}
