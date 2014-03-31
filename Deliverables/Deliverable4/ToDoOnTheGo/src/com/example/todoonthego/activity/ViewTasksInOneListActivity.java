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
import com.example.todoonthego.dialog.ListDeletionDialog;
import com.example.todoonthego.model.DBAdapter;
import com.example.todoonthego.model.Task;
import com.example.todoonthego.model.ToDoList;

/**
 * This activity displays tasks belonging to a specific list
 * 
 */
public class ViewTasksInOneListActivity extends Activity implements OnCheckedChangeListener {

//	private Task task;
	
	private TextView list_name;

	private ListView taskListView;

	private DBAdapter dbAdapter;

	private Cursor taskCursor;

	// customized ListView cursor adapter
	private MyAdapter mListAdapter;

	private String clickedListID;

	private String clickedListName;
	
	private String clickedUserID;
	
	public static final int ADD_TASK_REQUEST_CODE = 0;

	private Button showIncomplete;
	private Button showAll;
	private Button delete;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_tasks_in_one_list_layout);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent in = getIntent();

		clickedListName = in.getStringExtra("listName");
		clickedListID = in.getStringExtra("listId");
		clickedUserID = in.getStringExtra("userId");
		
		list_name = (TextView) findViewById(R.id.textView_list_name);
		list_name.setText("You Selected List: " + clickedListName);

		taskListView = (ListView) findViewById(R.id.listView_view_containing_tasks);

		dbAdapter = new DBAdapter(this);
		dbAdapter.open();

		populateListViewForIncompleteTasksInList();

		// populate only incomplete tasks when button is clicked
		showIncomplete = (Button) findViewById(R.id.button_show_incomplete_in_one_list);
		showIncomplete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "show incomplete tasks",
						Toast.LENGTH_SHORT).show();
				populateListViewForIncompleteTasksInList();
			}
		});

		// populate all tasks when button is clicked
		showAll = (Button) findViewById(R.id.button_show_all_in_one_list);
		showAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "show all tasks",
						Toast.LENGTH_SHORT).show();
				populateListViewForAllTasksInList();
			}
		});

		// populate DeletionDialog when delete button is clicked
		delete = (Button) findViewById(R.id.button_delete_current_list);
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ListDeletionDialog.showConfirmDeleteDialogForList(
						ViewTasksInOneListActivity.this, clickedListID, dbAdapter);
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
	
	public void gotoTaskDetail(Activity sourceActivity, Task task) {

		Intent viewTaskDetailIntent = new Intent(sourceActivity,
				ViewTaskDetailActivity.class);
		// put the Task object into the bundle
		Bundle viewTaskDetailBundle = new Bundle();
		viewTaskDetailBundle.putSerializable(Task.TASK_BUNDLE_KEY, task);
		// put the task as bundle into the intent
		viewTaskDetailIntent.putExtras(viewTaskDetailBundle);
		// put userId into the intent
		viewTaskDetailIntent.putExtra("userId", clickedUserID);
		// start the activity
		sourceActivity.startActivity(viewTaskDetailIntent);
	}

	// go to EditTask activity when user clicked "add a new task" button
	public void gotoEditTaskActivity(View v) {
		Intent addNewTaskIntent = new Intent(this, EditTaskActivity.class);
		addNewTaskIntent.putExtra("userId", clickedUserID);
		startActivity(addNewTaskIntent);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	// Load incomplete tasks from database and put them to list view
	public void populateListViewForIncompleteTasksInList() {
		// Check if the databaseAdapter is not null
		if (this.dbAdapter != null) {
			// Get incomplete tasks in the selected list
			taskCursor = dbAdapter.getIncompleteTaskByList(clickedListID);
			mListAdapter = new MyAdapter(ViewTasksInOneListActivity.this,
					taskCursor);
			ViewTasksInOneListActivity.this.taskListView
					.setAdapter(mListAdapter);
		}
	}
	
	// Load all tasks from database and put them to list view
	public void populateListViewForAllTasksInList() {
		// Check if the databaseAdapter is not null
		if (this.dbAdapter != null) {
			// Get all Tasks in the selected list
			taskCursor = dbAdapter.getTaskByList(clickedListID);
			mListAdapter = new MyAdapter(ViewTasksInOneListActivity.this,
					taskCursor);
			ViewTasksInOneListActivity.this.taskListView
					.setAdapter(mListAdapter);

		}
	}
	
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
		selectedTask
				.setCompletionStatus(taskCursor.getInt(taskCursor
						.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_COMPLETION_STATUS)));
		selectedTask.setList(this.getListByTask(taskCursor.getString(taskCursor
				.getColumnIndex(DBAdapter.TASK_TABLE_COLUMN_AFFI_LIST))));

		gotoTaskDetail(this, selectedTask);
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
			return li.inflate(R.layout.item_view_for__display_task_layout,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cur) {
			CheckBox checker = (CheckBox) view
					.findViewById(R.id.checkBox_completion_status);
			checker.setFocusable(false);
			checker.setOnCheckedChangeListener(ViewTasksInOneListActivity.this);
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
