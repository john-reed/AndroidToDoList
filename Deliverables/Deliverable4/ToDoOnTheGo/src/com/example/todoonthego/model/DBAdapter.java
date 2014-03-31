package com.example.todoonthego.model;

import java.util.UUID;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	// for logging
	public static final String TAG = "todo";
	public static final String DATABASE_NAME = "to_do_on_the_go.db";
	public static final int DATABASE_VERSION = 1;

	private DBHelper dBHelper;
	private SQLiteDatabase sqLiteDatabase;

	private final Context context;
	
	/* User Table */
	// user table fields
	public static final String USER_TABLE_NAME = "_users";
	public static final String USER_TABLE_COLUMN_ID = "_id";
	public static final String USER_TABLE_COLUMN_NAME = "_username";
	public static final String USER_TABLE_COLUMN_PW = "_password";
	public static final String[] USER_ALL_COLUMNS = new String[]{
		USER_TABLE_COLUMN_ID, USER_TABLE_COLUMN_NAME,USER_TABLE_COLUMN_PW};

	
	//create user table
	private static final String USER_TABLE_CREATE = "create table "
			+ USER_TABLE_NAME
			+ " ( "
			+ USER_TABLE_COLUMN_ID + " text primary key, "
			+ USER_TABLE_COLUMN_NAME + " text not null, "
			+ USER_TABLE_COLUMN_PW + " text not null"
			+ " );";
			
	public Cursor getAllUsers() {
		return sqLiteDatabase.query(USER_TABLE_NAME, USER_ALL_COLUMNS, null,
				null, null, null, null);
	}
	
	// Method to insert a new user into the database
	public long insertUser(String UserId, String UserName, String PassWord) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(USER_TABLE_COLUMN_ID, UserId);
		initialValues.put(USER_TABLE_COLUMN_NAME, UserName);
		initialValues.put(USER_TABLE_COLUMN_PW, PassWord);
		return sqLiteDatabase.insert(USER_TABLE_NAME, null, initialValues);
	}
	
	// get a list of a specific user by id
	public Cursor getUserById(String userId) {
		return sqLiteDatabase.query(USER_TABLE_NAME, USER_ALL_COLUMNS,
				USER_TABLE_COLUMN_ID + " = '" + userId + "'", null, null, null,
				null);
	}
	
	// Generate a random uuid for a new user
	// Re-generate if it's already exist
	public String getNewUserId() {
		String uuid = null;
		Cursor cursor = null;
		do {
			uuid = UUID.randomUUID().toString();
			cursor = getUserById(uuid);
		} while (cursor.getCount() > 0);
		return uuid;
	}
	
	// delete the selected list
	public void deleteUser(String userId) {
		sqLiteDatabase.delete(USER_TABLE_NAME, USER_TABLE_COLUMN_ID + " = '"
				+ userId + "'", null);
		sqLiteDatabase.delete(LIST_TABLE_NAME, LIST_TABLE_COLUMN_AFFL_USER + " = '"
				+ userId + "'", null);
	}
	


	/* List Table */
	// list_table fields
	public static final String LIST_TABLE_NAME = "_list";
	public static final String LIST_TABLE_COLUMN_ID = "_id";
	public static final String LIST_TABLE_COULMN_NAME = "_name";
	public static final String LIST_TABLE_COLUMN_AFFL_USER = "_users";
	public static final String[] LIST_ALL_COULMNS = new String[] {
			LIST_TABLE_COLUMN_ID, LIST_TABLE_COULMN_NAME,LIST_TABLE_COLUMN_AFFL_USER };

	// create list_table
	private static final String LIST_TABLE_CREATE = "create table "
			+ LIST_TABLE_NAME 
			+ " ( " 
			+ LIST_TABLE_COLUMN_ID + " text primary key, " 
			+ LIST_TABLE_COULMN_NAME + " text not null, "
			+ LIST_TABLE_COLUMN_AFFL_USER + " text not null, "
			+ "foreign key ( " + LIST_TABLE_COLUMN_AFFL_USER + " ) references "
			+ USER_TABLE_NAME + " ( " + USER_TABLE_COLUMN_ID + " ) "
			+ " );";

	public DBAdapter(Context context) {
		this.context = context;
	}

	public DBAdapter open() {
		dBHelper = new DBHelper(context, DBAdapter.DATABASE_NAME, null,
				DBAdapter.DATABASE_VERSION);
		sqLiteDatabase = dBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dBHelper.close();
	}
	
	

	// For get all lists
	public Cursor getAllLists() {
		return sqLiteDatabase.query(LIST_TABLE_NAME, LIST_ALL_COULMNS, null,
				null, null, null, null);
	}

	// For get a specific list by its Id
	public Cursor getListById(String listId) {
		return sqLiteDatabase.query(LIST_TABLE_NAME, LIST_ALL_COULMNS,
				LIST_TABLE_COLUMN_ID + " = '" + listId + "'", null, null, null,
				null);
	}
	
//	 Find list by its affiliated user
	public Cursor getListByUser(String userId) {
		return sqLiteDatabase.query(LIST_TABLE_NAME, LIST_ALL_COULMNS,
				LIST_TABLE_COLUMN_AFFL_USER + "=?", new String[] { userId },  null, null, null,
				null);
	}

	// Add a new set of data to the list_table
	public long insertList(String listId, String listName, String userId) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(LIST_TABLE_COLUMN_ID, listId);
		initialValues.put(LIST_TABLE_COULMN_NAME, listName);
		initialValues.put(LIST_TABLE_COLUMN_AFFL_USER, userId);
		return sqLiteDatabase.insert(LIST_TABLE_NAME, null, initialValues);
	}

	// Generate a random uuid for a new list
	// Re-generate if it's already exist
	public String getNewListId() {
		String uuid = null;
		Cursor cursor = null;
		do {
			uuid = UUID.randomUUID().toString();
			cursor = getListById(uuid);
		} while (cursor.getCount() > 0);
		return uuid;
	}
	
	// delete the selected list
	public void deleteList(String listId) {
		sqLiteDatabase.delete(LIST_TABLE_NAME, LIST_TABLE_COLUMN_ID + " = '"
				+ listId + "'", null);
		sqLiteDatabase.delete(TASK_TABLE_NAME, TASK_TABLE_COLUMN_AFFI_LIST + " = '"
				+ listId + "'", null);
	}
	
	
	
	
	/*  Task table */

	public static final String TASK_TABLE_NAME = "_task";
	public static final String TASK_TABLE_COLUMN_ID = "_id";
	public static final String TASK_TABLE_COLUMN_NAME = "_title";
	public static final String TASK_TABLE_COLUMN_DUE_DATE = "_due_date";
	public static final String TASK_TABLE_COLUMN_DUE_DATEStr = "_due_dateStr";
	public static final String TASK_TABLE_COLUMN_DETAILS = "_details";
	public static final String TASK_TABLE_COLUMN_PRIORITY = "_priority";
	public static final String TASK_TABLE_COLUMN_PRIORITYStr = "_priorityStr";
	public static final String TASK_TABLE_COLUMN_AFFI_LIST = "_list";
	public static final String TASK_TABLE_COLUMN_AFFI_USER = "_user";
	public static final String TASK_TABLE_COLUMN_COMPLETION_STATUS = "_completion_status";
	public static final String TASK_TABLE_COLUMN_COMPLETION_STATUSStr = "_completion_statusStr";

	public static final String[] TASK_ALL_COULMNS = new String[] {
			TASK_TABLE_COLUMN_ID, TASK_TABLE_COLUMN_NAME,
			TASK_TABLE_COLUMN_DUE_DATE, TASK_TABLE_COLUMN_DUE_DATEStr, TASK_TABLE_COLUMN_DETAILS,
			TASK_TABLE_COLUMN_PRIORITY,  TASK_TABLE_COLUMN_PRIORITYStr, TASK_TABLE_COLUMN_AFFI_LIST,
			TASK_TABLE_COLUMN_AFFI_USER,
			TASK_TABLE_COLUMN_COMPLETION_STATUS, TASK_TABLE_COLUMN_COMPLETION_STATUSStr };

	public static final String TASK_TABLE_CREATE = "create table "
			+ TASK_TABLE_NAME 
			+ " ( " 
			+ TASK_TABLE_COLUMN_ID	+ " text primary key, " 
			+ TASK_TABLE_COLUMN_NAME + " text not null, " 
			+ TASK_TABLE_COLUMN_DUE_DATE + " integer not null, " 
			+ TASK_TABLE_COLUMN_DUE_DATEStr + " text not null, "
			+ TASK_TABLE_COLUMN_DETAILS + " text, "
			+ TASK_TABLE_COLUMN_PRIORITY + " integer not null, "
			+ TASK_TABLE_COLUMN_PRIORITYStr + " text not null,"
			+ TASK_TABLE_COLUMN_AFFI_LIST + " text not null, "
			+ TASK_TABLE_COLUMN_AFFI_USER + " test not null, "
			+ TASK_TABLE_COLUMN_COMPLETION_STATUS + " integer not null, "
			+ TASK_TABLE_COLUMN_COMPLETION_STATUSStr + " text not null, "
			// create the foreign key to link LIST_TABLE and TASK_TABLE
			+ "foreign key ( " + TASK_TABLE_COLUMN_AFFI_LIST + " ) references "
			+ LIST_TABLE_NAME + " ( " + LIST_TABLE_COLUMN_ID + " ) " 
			// create the foreign key to link USER_TABLE and TASK_TABLE
			+ "foreign key ( " + TASK_TABLE_COLUMN_AFFI_USER + " ) references "
			+ USER_TABLE_NAME + " ( " + USER_TABLE_COLUMN_ID + " ) " 
			+ " );";
			

	// Insert a new task into Task table
	public void insertTask(Task task) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(TASK_TABLE_COLUMN_ID, task.getId());
		initialValues.put(TASK_TABLE_COLUMN_NAME, task.getName());
		initialValues.put(TASK_TABLE_COLUMN_DUE_DATE, task.getDueDate()
				.getTimeInMillis());
		initialValues.put(TASK_TABLE_COLUMN_DUE_DATEStr, task.getDueDateStr());
		initialValues.put(TASK_TABLE_COLUMN_DETAILS, task.getDetails());
		initialValues.put(TASK_TABLE_COLUMN_PRIORITY, task.getPriorityLevel());
		initialValues.put(TASK_TABLE_COLUMN_PRIORITYStr, task.getPriorityLevelStr());
		initialValues.put(TASK_TABLE_COLUMN_AFFI_LIST, task.getList().getId());
		initialValues.put(TASK_TABLE_COLUMN_AFFI_USER, task.getUserId());
		initialValues.put(TASK_TABLE_COLUMN_COMPLETION_STATUS, task.getCompletionStatus());
		initialValues.put(TASK_TABLE_COLUMN_COMPLETION_STATUSStr, task.getCompletionStatusStr());
		sqLiteDatabase.insert(TASK_TABLE_NAME, null, initialValues);

	}

	// Return all task currently in database
//	public Cursor getAllTasks() {
//		return sqLiteDatabase.query(TASK_TABLE_NAME, TASK_ALL_COULMNS, null,
//				null, null, null, null);
//	}

	// Find task by its id
	public Cursor getTaskById(String taskId) {
		return sqLiteDatabase.query(TASK_TABLE_NAME, TASK_ALL_COULMNS,
				TASK_TABLE_COLUMN_ID + " = '" + taskId + "'", null, null, null,
				null);
	}

	//	 Find task by its affiliated list
	public Cursor getTaskByList(String listId) {
		return sqLiteDatabase.query(TASK_TABLE_NAME, TASK_ALL_COULMNS,
				TASK_TABLE_COLUMN_AFFI_LIST + "=?", new String[] { listId },  null, null, null,
				null);
	}
	
	public Cursor getTaskByUser(String userId) {
		return sqLiteDatabase.query(TASK_TABLE_NAME, TASK_ALL_COULMNS,
				TASK_TABLE_COLUMN_AFFI_USER + "=?", new String[] { userId },  null, null, null,
				null);
	}
	
//	public Cursor getIncompleteTask(int status) {
//		return sqLiteDatabase.query(TASK_TABLE_NAME, TASK_ALL_COULMNS,
//				TASK_TABLE_COLUMN_COMPLETION_STATUS + "=?", new String[] {String.valueOf(status)},  null, null, null,
//				null);
//	}
	public Cursor getIncompleteTaskByUser(int status, String userId) {
		return sqLiteDatabase.query(TASK_TABLE_NAME, TASK_ALL_COULMNS,
				TASK_TABLE_COLUMN_COMPLETION_STATUS + "=? AND "
				+ TASK_TABLE_COLUMN_AFFI_USER + " = ?"
				, new String[] {String.valueOf(status), userId},  null, null, null,
				null);
	}
	
	public Cursor getIncompleteTaskByList(String listId) {
		return sqLiteDatabase.query(TASK_TABLE_NAME, TASK_ALL_COULMNS,
				TASK_TABLE_COLUMN_COMPLETION_STATUS + " = ? AND "
				+ TASK_TABLE_COLUMN_AFFI_LIST + " = ?", new String[] {"0", listId},  
				null, null, null, null);
	}

	// Edit an existing task
	public void editExistingTask(Task task) {
		ContentValues updateValues;

		// Update Task table first
		updateValues = new ContentValues();
		updateValues.put(TASK_TABLE_COLUMN_NAME, task.getName());
		updateValues.put(TASK_TABLE_COLUMN_DETAILS, task.getDetails());
		updateValues.put(TASK_TABLE_COLUMN_DUE_DATE, task.getDueDate()
				.getTimeInMillis());
		updateValues.put(TASK_TABLE_COLUMN_DUE_DATEStr, task.getDueDateStr());
		updateValues.put(TASK_TABLE_COLUMN_PRIORITY, task.getPriorityLevel());
		updateValues.put(TASK_TABLE_COLUMN_PRIORITYStr, task.getPriorityLevelStr());
		updateValues.put(TASK_TABLE_COLUMN_AFFI_LIST, task.getList().getId());
		updateValues.put(TASK_TABLE_COLUMN_AFFI_USER, task.getUserId());
		updateValues.put(TASK_TABLE_COLUMN_COMPLETION_STATUS,
				task.getCompletionStatus());
		updateValues.put(TASK_TABLE_COLUMN_COMPLETION_STATUSStr,
				task.getCompletionStatusStr());
		sqLiteDatabase.update(TASK_TABLE_NAME, updateValues,
				TASK_TABLE_COLUMN_ID + " = '" + task.getId() + "'", null);

	}
	
	// delete the selected Task
	public void deleteTask(Task task) {
		sqLiteDatabase.delete(TASK_TABLE_NAME, TASK_TABLE_COLUMN_ID + " = '"
				+ task.getId() + "'", null);
	}

	// Return a new randomly generated task id
	public String getNewTaskId() {
		String uuid = null;
		Cursor cursor = null;
		do {
			uuid = UUID.randomUUID().toString();
			cursor = getTaskById(uuid);
		} while (cursor.getCount() > 0);

		return uuid;
	}
	
	
	public void setStatusToComplete(String taskId){
		ContentValues cv = new ContentValues();
		cv.put(TASK_TABLE_COLUMN_COMPLETION_STATUS, 1);
		cv.put(TASK_TABLE_COLUMN_COMPLETION_STATUSStr, "Complete");
		sqLiteDatabase.update(TASK_TABLE_NAME, cv, TASK_TABLE_COLUMN_ID + " = ?", new String[]{taskId});
	}
	
	public void setStatusToIncomplete(String taskId){
		ContentValues cv = new ContentValues();
		cv.put(TASK_TABLE_COLUMN_COMPLETION_STATUS, 0);
		cv.put(TASK_TABLE_COLUMN_COMPLETION_STATUSStr, "Incomplete");
		sqLiteDatabase.update(TASK_TABLE_NAME, cv, TASK_TABLE_COLUMN_ID + " = ?", new String[]{taskId});
	}
	
	
	
	// Helper class for managing db
	private static class DBHelper extends SQLiteOpenHelper {
		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// Create the user table
			db.execSQL(DBAdapter.USER_TABLE_CREATE);
			// Create the list table
			db.execSQL(DBAdapter.LIST_TABLE_CREATE);
			// Create the Task table
			db.execSQL(DBAdapter.TASK_TABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			// Drop list table if already exists
			db.execSQL("Drop table if exists " + DBAdapter.USER_TABLE_NAME);
			// Drop list table if already exists
			db.execSQL("Drop table if exists " + DBAdapter.LIST_TABLE_NAME);
			// Drop Task table if exists
			db.execSQL("Drop table if exists " + DBAdapter.TASK_TABLE_NAME);
		}
	}

}
