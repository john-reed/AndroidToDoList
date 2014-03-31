package com.example.todoonthego.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.todoonthego.R;
import com.example.todoonthego.model.DBAdapter;
import com.example.todoonthego.model.Task;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * This activity is used for either editing exiting task or creating a new task
 * 
 */
public class EditTaskActivity extends Activity {

	private Task task = null;

	private int existingOrNew;
	private final int EXISTING_TASK = 1;
	private final int NEW_TASK = 2;

	private DBAdapter dbAdapter;
	private Cursor listCursor;
	private SimpleCursorAdapter listSpinnerAdapter;
	private Spinner listSpinner;
	private String clickedUserId;
	
	private Button save;
	private Button clear;
	private Button cancel;
	
	private Bundle editTaskBundle;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_task_layout);
		
		Intent in = getIntent();
		clickedUserId = in.getStringExtra("userId");

		dbAdapter = new DBAdapter(this);
		dbAdapter.open();

		// Retrieve the list spinner
		listSpinner = (Spinner) findViewById(R.id.edit_task_Spinner_list);
		// Initiate the list spinner
		this.initListSpinner();
		
		// Retrieve the task object from the bundle
		editTaskBundle = this.getIntent().getExtras();
		try {
			this.task = (Task) editTaskBundle
					.getSerializable(Task.TASK_BUNDLE_KEY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
		
		/*
		 * Check if the current activity is to edit an existing task or add a
		 * new task
		 */

		// Check if task exists. If yes, set to edit task and load task
		// information onto the screen.
		if (task != null) {
			this.existingOrNew = this.EXISTING_TASK;
			displayTaskInfo();
			// Otherwise, initiate a new task and set to add new task
		} else {
			this.task = new Task();
			this.existingOrNew = this.NEW_TASK;
		}

		// implement save/clear/cancel button functions
		save = (Button) findViewById(R.id.button_save_task);
		clear = (Button) findViewById(R.id.button_clear_task);
		cancel = (Button) findViewById(R.id.button_cancel_editing_task);

		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				Bundle resultBundle = new Bundle();
				// check whether the current job is to add new task or edit
				// existing task
				if (EditTaskActivity.this.existingOrNew == NEW_TASK) {
					addNewTask();
					Intent in = new Intent(EditTaskActivity.this,
							ViewTasksInOneListActivity.class);
					in.putExtra("listName", task.getList().getName());
					in.putExtra("listId", task.getList().getId());
					in.putExtra("userId", clickedUserId);
					startActivity(in);
					
				} else {
					editExistingTask();
					// set the result for the previous activity
					resultBundle.putSerializable(Task.TASK_BUNDLE_KEY,
							EditTaskActivity.this.task);
					resultIntent.putExtras(resultBundle);
					setResult(RESULT_OK, resultIntent);
					EditTaskActivity.this.finish();
				}
				// close this activity
//				Toast.makeText(getBaseContext(), "Your task has been saved",
//						Toast.LENGTH_LONG).show();
//				EditTaskActivity.this.finish();
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				Bundle resultBundle = new Bundle();
				resultBundle.putSerializable(Task.TASK_BUNDLE_KEY,
						EditTaskActivity.this.task);
				resultIntent.putExtras(resultBundle);
				setResult(RESULT_CANCELED, resultIntent);
				EditTaskActivity.this.finish();
			}
		});

		clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent(EditTaskActivity.this,
						EditTaskActivity.class);
				
				resultIntent.putExtra("userId", clickedUserId);
				startActivity(resultIntent);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * This method inits the list spinner, loads all lists from the database
	 * into the spinner
	 */
	private void initListSpinner() {
		// Check if the dBAdapter is not null
		if (this.dbAdapter != null) {
			// Get lists from a user
			listCursor = dbAdapter.getListByUser(clickedUserId);
			startManagingCursor(listCursor);
			// Get data from the Name column
			String[] from = new String[] {DBAdapter.LIST_TABLE_COULMN_NAME };
			// Put data to a Textview component in layout
			int[] to = new int[] { R.id.textView_show_list_name_lv };
			// Init the adapter for spinner
			this.listSpinnerAdapter = new SimpleCursorAdapter(this,
					R.layout.item_view_for_display_list_layout, listCursor,
					from, to);
			// Set the adapter for the spinner
			listSpinner.setAdapter(listSpinnerAdapter);
		} else {
			finish();
		}
	}

	/**
	 * This function is used to edit the existing task
	 */
	private void editExistingTask() {
		// Load data from screen into this.task object
		updateTaskObject();
		// Update the existing task in database with new data
		dbAdapter.editExistingTask(this.task);
	}

	/**
	 * This function is used to retrieve data from screen and update the
	 * this.task object
	 */
	private void updateTaskObject() {
		// Retrieve data from form and put into this.task object
		// set task name
		String taskName = ((EditText) findViewById(R.id.edit_task_EditText_task_name))
				.getText().toString();
		this.task.setName(taskName);
		// set due date
		DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.edit_task_DatePicker_due_date);
		this.task.getDueDate().set(Calendar.DATE,
				taskDueDatePicker.getDayOfMonth());
		this.task.getDueDate()
				.set(Calendar.MONTH, taskDueDatePicker.getMonth());
		this.task.getDueDate().set(Calendar.YEAR, taskDueDatePicker.getYear());
		// set string representation of due date
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String dueDateStr = sdf.format(this.task.getDueDate().getTime());
		this.task.setDueDateStr(dueDateStr);

		// set details
		String taskDetails = ((EditText) findViewById(R.id.edit_task_EditText_details))
				.getText().toString();
		this.task.setDetails(taskDetails);
		// set priority
		int priorityLevel = ((Spinner) findViewById(R.id.edit_task_Spinner_priority_level))
				.getSelectedItemPosition();
		this.task.setPriorityLevel(priorityLevel);
		String priorityLevelStr;
		switch (priorityLevel) {
		case 0:
			priorityLevelStr = "High priority";
			break;
		case 1:
			priorityLevelStr = "Medium priority";
			break;
		default:
			priorityLevelStr = "Low priority";
			break;
		}
		this.task.setPriorityLevelStr(priorityLevelStr);
		// set completion status
		int completionStatus = ((Spinner) findViewById(R.id.edit_task_Spinner_completion_status))
				.getSelectedItemPosition();
		this.task.setCompletionStatus(completionStatus);
		if (completionStatus == 0) {
			this.task.setCompletionStatusStr("Incomplete");
		} else {
			this.task.setCompletionStatusStr("Complete");
		}

		// set list
		Spinner listSpinner = (Spinner) findViewById(R.id.edit_task_Spinner_list);
		this.task.getList().setId(
				getListIdByPosition(listCursor,
						listSpinner.getSelectedItemPosition()));
		this.task.getList().setName(
				getListNameByPosition(listCursor,
						listSpinner.getSelectedItemPosition()));
		this.task.setUserId(clickedUserId);
	}

	// get list Id with the input position
	private String getListIdByPosition(Cursor cursor, int position) {
		String listId = null;
		cursor.moveToFirst();
		cursor.move(position);
		listId = cursor.getString(cursor
				.getColumnIndex(DBAdapter.LIST_TABLE_COLUMN_ID));
		return listId;
	}
	
	// get listName with the input position
	private String getListNameByPosition(Cursor cursor, int position) {
		String listName = null;
		cursor.moveToFirst();
		cursor.move(position);
		listName = cursor.getString(cursor
				.getColumnIndex(DBAdapter.LIST_TABLE_COULMN_NAME));
		return listName;
	}

	// This function is used to add new task to database
	private void addNewTask() {
		// Load data from form to this.task object
		updateTaskObject();
		// get a new Id for the new task
		String taskId = dbAdapter.getNewTaskId();
		this.task.setId(taskId);
		// Call the database adapter to add new task
		this.dbAdapter.insertTask(this.task);
	}

	// This method fetches data from this.task object and put onto screen

	private void displayTaskInfo() {
		// Check if the current job is to edit existing task
		if (this.existingOrNew == this.EXISTING_TASK) {

			// Load task data onto screen
			// set task name
			EditText taskNameEditText = (EditText) findViewById(R.id.edit_task_EditText_task_name);
			taskNameEditText.setText(this.task.getName());

			// set task date
			DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.edit_task_DatePicker_due_date);
			taskDueDatePicker.updateDate(
					this.task.getDueDate().get(Calendar.YEAR), this.task
							.getDueDate().get(Calendar.MONTH), this.task
							.getDueDate().get(Calendar.DATE));

			// set task details
			EditText taskDetailsEditText = (EditText) findViewById(R.id.edit_task_EditText_details);
			taskDetailsEditText.setText(this.task.getDetails());

			// set priority level
			Spinner taskPriorityLevelSpinner = (Spinner) findViewById(R.id.edit_task_Spinner_priority_level);
			taskPriorityLevelSpinner.setSelection(this.task.getPriorityLevel());

			// set list
			listSpinner.setSelection(this.getListPositionInCursor(listCursor,
					this.task.getList().getId()));

			// set completion status
			Spinner completionStatusSpinner = (Spinner) findViewById(R.id.edit_task_Spinner_completion_status);
			completionStatusSpinner.setSelection(this.task
					.getCompletionStatus());
		}
	}

	// get the position of the list with id String listId in the cursor
	private int getListPositionInCursor(Cursor cursor, String listId) {
		int position = -1;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String currentListId = cursor.getString(cursor
					.getColumnIndex(DBAdapter.LIST_TABLE_COLUMN_ID));
			if (currentListId.equals(listId)) {
				position = cursor.getPosition();
			}
			cursor.moveToNext();
		}
		return position;
	}
}