package com.example.todoonthego.dialog;

import com.example.todoonthego.model.DBAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * This class populates a confirmation dialog when a user selected to delete a list
 *
 */
public class UserDeletionDialog {
	public static void showConfirmDeleteDialogForUser(Activity sourceActivity, String userId, DBAdapter dBAdapter){
		Dialog confirmDeletionDialog = new AlertDialog.Builder(sourceActivity)
		.setTitle("Are you sure you want to delete this?")
		.setIcon(android.R.drawable.ic_menu_help)
		.setPositiveButton("Yes",
				new ConfirmDeletionDialogPositiveButtonListener(sourceActivity, userId, dBAdapter))
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
	
		// the user to delete
		private String userId;
		
		private DBAdapter dbAdapter;

		public ConfirmDeletionDialogPositiveButtonListener(Activity sourceActivity, String userId, DBAdapter dBAdapter){
			this.sourceActivity = sourceActivity;
			this.userId = userId;
			this.dbAdapter = dBAdapter;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// Delete the selected task and then return to the last activity 
			
			dbAdapter.deleteUser(userId);
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

