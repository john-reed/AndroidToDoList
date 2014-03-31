package com.example.todoonthego.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.todoonthego.R;
import com.example.todoonthego.model.DBAdapter;

/**
 * This class displays all the lists belonging to one user.
 *
 */
public class MainActivity extends Activity {

	private DBAdapter dbAdapter;  //database connection
	private ListView userListView;  //displaying a list of users
	private Cursor userCursor;	  // Cursor for getting users from database

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//connect to database
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		
		userListView = (ListView) findViewById(R.id.listViewForUsers);

		populateListViewForUser();

		// set click listener for go to ViewTaskInOneList activity when user selected a specific list
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

		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		intent.putExtra("userName", messageName);
		intent.putExtra("userId", messageId);
		startActivity(intent);
	}

	private void populateListViewForUser() {
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

	// close database onDestroy
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbAdapter.close();
	}

	public void onClickAddUser(View v) {
		Intent intent = new Intent(this, AddUserActivity.class);
		startActivity(intent);
	}
	
	public void onClickDeleteUser(View v) {
		Intent viewAllTaskIntent = new Intent(this, DeleteUserActivity.class);
		startActivity(viewAllTaskIntent);
	}
	
}
