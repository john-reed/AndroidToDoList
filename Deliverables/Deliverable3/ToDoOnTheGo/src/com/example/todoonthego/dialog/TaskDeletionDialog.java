package com.example.todoonthego.dialog;

import com.example.todoonthego.model.DBAdapter;
import com.example.todoonthego.model.Task;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * This class populates a confirmation dialog when a user selected to delete a task
 *
 */
public class TaskDeletionDialog {
	public static void showConfirmDeleteDialogForTask(Activity sourceActivity, Task task, DBAdapter dBAdapter){
		Dialog confirmDeletionDialog = new AlertDialog.Builder(sourceActivity)
		.setTitle("Are you sure you want to delete this?")
		.setIcon(android.R.drawable.ic_menu_help)
		.setPositiveButton("Yes",
				new ConfirmDeletionDialogPositiveButtonListener(sourceActivity, task, dBAdapter))
		.setNegativeButton("No",
				new ConfirmDeletionDialogNegativeButtonListener())
		.create();
		confirmDeletionDialog.show();
	}
	
	/**
	 * An inner class to handle click event when user select the Yes button
	 *
	 */
	private static class ConfirmDeletionDialogPositiveButtonListener implements OnClickListener{
		// sourceActivity to indicate the activity that call the dialog
		private Activity sourceActivity;
	
		// the task to delete
		private Task task;
		
		private DBAdapter dbAdapter;

		public ConfirmDeletionDialogPositiveButtonListener(Activity sourceActivity, Task task, DBAdapter dBAdapter){
			this.sourceActivity = sourceActivity;
			this.task = task;
			this.dbAdapter = dBAdapter;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// Delete the selected task and then return to the last activity 
			dbAdapter.deleteTask(task);
			sourceActivity.finish();
		}
	}

	/**
	 * An inner class to handle click event when user select the No button
	 *
	 */
	private static class ConfirmDeletionDialogNegativeButtonListener implements OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}
}
