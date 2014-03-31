package com.example.todoonthego.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoonthego.R;
import com.example.todoonthego.model.DBAdapter;
import com.example.todoonthego.model.Task;
import com.example.todoonthego.model.ToDoList;

/**
 * This activity populates all the tasks of one user on the screen
 * 
 */
public class ViewAllTasksActivity extends Activity implements OnCheckedChangeListener {

	private ListView taskListView;

	private DBAdapter dbAdapter;

	private Cursor taskCursor;
	
	private String clickedUserID;
	
	//customized ListView cursor adapter
	MyAdapter mListAdapter;

	private Button showIncomplete;
	private Button showAll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_all_tasks_layout);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	
		Intent in = getIntent();
		clickedUserID = in.getStringExtra("userId");

		taskListView = (ListView) findViewById(R.id.listView_view_all_tasks);

		dbAdapter = new DBAdapter(this);
		dbAdapter.open();

		populateListViewForIncompleteTasks();

		//populate only incomplete tasks when button is clicked
		showIncomplete = (Button) findViewById(R.id.button_unchecked);
		showIncomplete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "show incomplete tasks",
						Toast.LENGTH_SHORT).show();
				populateListViewForIncompleteTasks();
			}
		});
		
		//populate all tasks when button is clicked
		showAll = (Button) findViewById(R.id.button_all);
		showAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "show all tasks",
						Toast.LENGTH_SHORT).show();
				populateListViewForAllTasks();
			}
		});

		taskListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				allTaskViewItemClickHandler(arg0, arg1, arg2);
			}
		});

	}
	
//	// go to EditTask activity when user clicked "add a new task" button
//	public void gotoEditTaskActivity(View v) {
//		Intent editTaskIntent = new Intent(this, EditTaskActivity.class);
//		startActivity(editTaskIntent);
//	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			}
		return super.onOptionsItemSelected(item);
	}	

	// Load all tasks from database and put them to list view
	public void populateListViewForAllTasks() {
		// Check if the databaseAdapter is not null
		if (this.dbAdapter != null) {
			// Get all Tasks
			taskCursor = dbAdapter.getTaskByUser(clickedUserID);
			
			mListAdapter = new MyAdapter(ViewAllTasksActivity.this, taskCursor);
			ViewAllTasksActivity.this.taskListView.setAdapter(mListAdapter);
		}
	}
	
	// Load incomplete tasks from database and put them to list view
	public void populateListViewForIncompleteTasks() {
		if (this.dbAdapter != null) {
			taskCursor = dbAdapter.getIncompleteTaskByUser(Task.INCOMPLETE, clickedUserID);
			
			mListAdapter = new MyAdapter(ViewAllTasksActivity.this, taskCursor);
			ViewAllTasksActivity.this.taskListView.setAdapter(mListAdapter);
		}
	}

	// go to ViewTaskDetail activity when a task is clicked 
	private void allTaskViewItemClickHandler(AdapterView<?> adapterView,
			View listView, int selectedItemId) {

		Task selectedTask = new Task();

		taskCursor.moveToFirst();
		taskCursor.move(selectedItemId);

		selectedTask.setId(taskCursor.getString(taskCursor
				.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_ID)));
		selectedTask.setName(taskCursor.getString(taskCursor
				.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_NAME)));
		selectedTask.getDueDate().setTimeInMillis(
				taskCursor.getLong(taskCursor
						.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_DUE_DATE)));
		selectedTask.setDetails(taskCursor.getString(taskCursor
				.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_DETAILS)));
		selectedTask.setPriorityLevel(taskCursor.getInt(taskCursor
				.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_PRIORITY)));
		selectedTask.setPriorityLevelStr(taskCursor.getString(taskCursor
				.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_PRIORITYStr)));
		selectedTask
				.setCompletionStatus(taskCursor.getInt(taskCursor
						.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_COMPLETION_STATUS)));
		selectedTask
				.setCompletionStatusStr(taskCursor.getString(taskCursor
						.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_COMPLETION_STATUSStr)));
		selectedTask.setList(this.getListByTask(taskCursor.getString(taskCursor
				.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_AFFI_LIST))));

		gotoTaskDetail(this, selectedTask);
	}

	public void gotoTaskDetail(Activity sourceActivity, Task task) {

		Intent viewTaskDetailIntent = new Intent(sourceActivity,
				ViewTaskDetailActivity.class);
		// put the Task object into the bundle
		Bundle viewTaskDetailBundle = new Bundle();
		viewTaskDetailBundle.putSerializable(Task.TASK_BUNDLE_KEY, task);
		// put the bundle into the intent
		viewTaskDetailIntent.putExtras(viewTaskDetailBundle);
		viewTaskDetailIntent.putExtra("userId", clickedUserID);
		// start the activity
		sourceActivity.startActivity(viewTaskDetailIntent);
	}

	// Get the list by the input Id
	private ToDoList getListByTask(String listId) {
		ToDoList toDoList = new ToDoList();
		Cursor listCursor = this.dbAdapter.getListById(listId);
		listCursor.moveToFirst();
		toDoList.setId(listCursor.getString(listCursor
				.getColumnIndex(DBAdapter.LIST_TABLE_COLUMN_ID)));
		toDoList.setName(listCursor.getString(listCursor
				.getColumnIndex(DBAdapter.LIST_TABLE_COULMN_NAME)));
		return toDoList;
	}
	
	// customized checkable ListView cursor adapter
	private class MyAdapter extends CursorAdapter {

		public MyAdapter(Context context, Cursor cur) {
			super(context, cur, R.layout.item_view_for__display_task_layout);
		}

		@Override
		public View newView(Context context, Cursor cur, ViewGroup parent) {
			LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return li.inflate(R.layout.item_view_for__display_task_layout, parent,
					false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cur) {
			CheckBox checker = (CheckBox) view.findViewById(R.id.checkBox_completion_status);
			checker.setFocusable(false);
			checker.setOnCheckedChangeListener(ViewAllTasksActivity.this);
			checker.setTag(cur.getString(cur
					.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_ID)));

			TextView taskName = (TextView) view
					.findViewById(R.id.textView_task_name_lv);
			TextView taskPriority = (TextView) view
					.findViewById(R.id.textView_task_priority_lv);
			TextView taskDueDate = (TextView) view
					.findViewById(R.id.textView_task_due_date_lv);

			taskName.setText(cur.getString(cur
					.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_NAME)));
			taskPriority.setText(cur.getString(cur
					.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_PRIORITYStr)));
			taskDueDate.setText(cur.getString(cur
					.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_DUE_DATEStr)));

			checker.setChecked((cur
					.getString(
							cur.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_COMPLETION_STATUSStr))
					.equals("Incomplete") ? false : true));			
		}
	}
	
	// change the completion status to Incomplete or complete when checkbox is unchecked or checked
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String taskId = (String) buttonView.getTag();
     
        if ( taskId!=null){
        	if (isChecked){
//        		Toast.makeText(getBaseContext(), taskId + " is checked", Toast.LENGTH_SHORT).show();
        		dbAdapter.setStatusToComplete(taskId);
        	}
        	else{
//        		Toast.makeText(getBaseContext(), taskId + " is UNchecked", Toast.LENGTH_SHORT).show();
        		dbAdapter.setStatusToIncomplete(taskId);
        	}
        }
    }
	
}
