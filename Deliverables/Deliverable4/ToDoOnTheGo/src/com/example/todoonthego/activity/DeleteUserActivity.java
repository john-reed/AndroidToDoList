package com.example.todoonthego.activity;

import com.example.todoonthego.R;
import com.example.todoonthego.dialog.UserDeletionDialog;
import com.example.todoonthego.model.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DeleteUserActivity extends Activity{


	private DBAdapter dbAdapter;
	
	private Cursor userCursor;
	
	private ListView userListView;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.delete_user_layout);
				
			dbAdapter = new DBAdapter(this);
			dbAdapter.open();
			
			userListView = (ListView) findViewById(R.id.listViewForDelete);
			
			populateListViewForDelete();
			
			// set click listener delete user activity when user selected a specific user
			userListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					myListViewItemClickHandler(arg0, arg1, arg2);
				}
			});
			
			
		}
	
	private void myListViewItemClickHandler(AdapterView<?> adapterView,
			View listView, int selectedItemId) {

		// move the cursor to the right position
		userCursor.moveToFirst();
		userCursor.move(selectedItemId);

		String messageId = "";
		messageId = userCursor.getString(userCursor
				.getColumnIndex(DBAdapter.USER_TABLE_COLUMN_ID));

		String messageName = "";
		messageName = userCursor.getString(userCursor
				.getColumnIndex(DBAdapter.USER_TABLE_COLUMN_NAME));

//		Toast.makeText(getBaseContext(),
//				"Your selected " + " " + messageName,
//				Toast.LENGTH_SHORT).show();
		
		UserDeletionDialog.showConfirmDeleteDialogForUser(DeleteUserActivity.this, messageId, dbAdapter);
		
		
		
		

	}
	private void populateListViewForDelete() {
		userCursor = dbAdapter.getAllUsers();
		// allow activity to manage the life time of cursor
		startManagingCursor(userCursor);

		// Setup mapping from cursor to view fields:
		String[] fromDBNames = new String[] { DBAdapter.USER_TABLE_COLUMN_NAME };

		int[] toViewNames = new int[] { R.id.textView_show_user_lv };

		// Create adapter to map columns of the database onto elements in the UI.
		SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, // context
				R.layout.user_list_item_layout, // Row layout template
				userCursor, fromDBNames, toViewNames);

		// set the adapter for the list view
		userListView.setAdapter(myCursorAdapter);
	}

}
