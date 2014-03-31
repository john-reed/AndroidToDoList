package com.example.todoonthego.model;

import java.io.Serializable;
import java.util.List;


public class ToDoList implements Serializable {

	// A static variable to use when put it into bundle
	public static final String LIST_BUNDLE_KEY = "list_bundle_key";  
	
	// list Id, which is a randomly generated combination of numbers and letters
	private String id;
	
	private String name;
	
	// The tasks belonging to this list
	private List<Task> tasksInList;

	public ToDoList(){
	
	}

	public ToDoList(String id, String name, List<Task> tasksInList) {
		super();
		this.id = id;
		this.name = name;
		this.tasksInList = tasksInList;
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

	public List<Task> getTasksList() {
		return tasksInList;
	}

	public void setTasksList(List<Task> tasksList) {
		this.tasksInList = tasksList;
	}
	
}
