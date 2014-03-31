package com.example.todoonthego.activity;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoonthego.R;
import com.example.todoonthego.dialog.TaskDeletionDialog;
import com.example.todoonthego.model.DBAdapter;
import com.example.todoonthego.model.Task;

/**
 * This activity displays the all the information of the selected task
 *
 */
public class ViewTaskDetailActivity extends Activity {

	private Task task;
	private DBAdapter dbAdapter;
	private String clickedUserId;
	private Button edit;
	private Button delete;

	public static final int EDIT_TASK_REQUEST_CODE = 1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_task_detail_layout);

		// Open the connection to database
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();

		// Retrieve the task object from the bundle
		Bundle taskDetailBundle = this.getIntent().getExtras();
		try {
			this.task = (Task) taskDetailBundle
					.getSerializable(Task.TASK_BUNDLE_KEY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Intent in = getIntent();
		clickedUserId = in.getStringExtra("userId");
		
		// Check if the task object is null or not
		if (this.task == null) {
			// If null, close this activity
			this.finish();
		} else {
			// If not null, get all data from the task object and then display
			// it on the screen
			this.displayTaskDetails();
		}
		
		// go to EditTask activity when edit button is clicked
		edit = (Button) findViewById(R.id.button_edit_task);
		edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoEditTaskActivity(ViewTaskDetailActivity.this, ViewTaskDetailActivity.this.task);
			}
		});
		
		// populate DeletionDialog when delete button is clicked
		delete = (Button) findViewById(R.id.button_delete_task);
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TaskDeletionDialog.showConfirmDeleteDialogForTask(ViewTaskDetailActivity.this, task, dbAdapter);
			}
		});		
	}

	// Fetches data from task object and displays it on the screen
	private void displayTaskDetails() {
		// Check if the task object is null
		if (this.task == null) {
			this.finish();
		} else {
			// set the name
			TextView taskTitleTextView = (TextView) findViewById(R.id.textView_view_task_name);
			taskTitleTextView.setText(this.task.getName());
			// set the due date
			TextView taskDueDateTextView = (TextView) findViewById(R.id.textView_view_task_due_date);
			Calendar dueDate = this.task.getDueDate();
			String dueDateString = dueDate.getDisplayName(Calendar.MONTH,
					Calendar.SHORT, Locale.US)
					+ " "
					+ dueDate.get(Calendar.DATE)
					+ " "
					+ dueDate.get(Calendar.YEAR);
			taskDueDateTextView.setText(dueDateString);
			// set the details
			TextView taskDetailsTextView = (TextView) findViewById(R.id.textView_task_details);
			taskDetailsTextView.setText(this.task.getDetails());
			// set the priority
			TextView priorityTextView = (TextView) findViewById(R.id.textView_view_task_priority);
			String priorityString;
			switch (this.task.getPriorityLevel()) {
			case Task.HIGH_PRIORITY:
				priorityString = this.getString(R.string.high);
				break;
			case Task.MEDIUM_PRIORITY:
				priorityString = this.getString(R.string.medium);
				break;
			default:
				priorityString = this.getString(R.string.low);
				break;
			}
			priorityTextView.setText(priorityString);
			// set the completion status
			TextView completionTextView = (TextView) findViewById(R.id.textView_view_task_completion_status);
			String completionString;
			if (this.task.getCompletionStatus() == Task.COMPLETED) {
				completionString = getString(R.string.completed);
			} else {
				completionString = getString(R.string.incomplete);
			}
			completionTextView.setText(completionString);
			// set the affiliated list
			TextView listTextView = (TextView) findViewById(R.id.textView_view_task_affi_list);
			listTextView.setText(this.task.getList().getName());
		}
	}

	// go to EditTask activity when users press "Edit this task" button
	public void gotoEditTaskActivity(Activity sourceActivity, Task existingTask) {
		Intent editExistingTaskIntent = new Intent(sourceActivity, EditTaskActivity.class);
		// put the task into the bundle
		Bundle editExistingTaskBundle = new Bundle();
		editExistingTaskBundle.putSerializable(Task.TASK_BUNDLE_KEY,
				existingTask);
		// put the task bundle into intent
		editExistingTaskIntent.putExtras(editExistingTaskBundle);
		// put userId into intent
		editExistingTaskIntent.putExtra("userId", clickedUserId);
		// start the activity for result
		sourceActivity.startActivityForResult(editExistingTaskIntent,
				ViewTaskDetailActivity.EDIT_TASK_REQUEST_CODE);
	}

	//get results back from EditTask activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == EDIT_TASK_REQUEST_CODE) {
			if (resultCode == RESULT_OK)
			{ this.task = (Task) data.getExtras().getSerializable(Task.TASK_BUNDLE_KEY);
			this.displayTaskDetails();}
		}
	}	
}
