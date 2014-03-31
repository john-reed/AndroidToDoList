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
public class ViewAllLists extends Activity {

	private DBAdapter dbAdapter;  //for connection to database
	private ListView myListView;  //for display lists
	private Cursor listCursor;	  //for fetching list data from database
	
	private String clickedUserName;
	private String clickedUserID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_all_lists_layout);
		
		Intent in = getIntent();

		clickedUserName = in.getStringExtra("userName");
		clickedUserID = in.getStringExtra("userId");

		//connect to database
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		
		myListView = (ListView) findViewById(R.id.listViewForList);

		populateListViewForList();

		// set click listener for go to ViewTaskInOneList activity when user selected a specific list
		myListView.setOnItemClickListener(new OnItemClickListener() {
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
		listCursor.moveToFirst();
		listCursor.move(selectedItemId);

		String messageId = "";
		messageId = listCursor.getString(listCursor
				.getColumnIndex(DBAdapter.LIST_TABLE_COLUMN_ID));

		String messageName = "";
		messageName = listCursor.getString(listCursor
				.getColumnIndex(DBAdapter.LIST_TABLE_COULMN_NAME));

//		Toast.makeText(getBaseContext(),
//				"Your selected " + " " + messageName,
//				Toast.LENGTH_SHORT).show();

		Intent intent = new Intent(ViewAllLists.this, ViewTasksInOneListActivity.class);
		intent.putExtra("listName", messageName);
		intent.putExtra("listId", messageId);
		intent.putExtra("userId", clickedUserID);
		startActivity(intent);
	}

	private void populateListViewForList() {
		listCursor = dbAdapter.getListByUser(clickedUserID);
		// allow activity to manage the life time of cursor
		startManagingCursor(listCursor);

		// Setup mapping from cursor to view fields:
		String[] fromDBNames = new String[] { DBAdapter.LIST_TABLE_COULMN_NAME };

		int[] toViewNames = new int[] { R.id.textView_show_list_name_lv };

		// Create adapter to map columns of the database onto elements in the UI.
		SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, // context
				R.layout.item_view_for_display_list_layout, // Row layout template
				listCursor, fromDBNames, toViewNames);

		// set the adapter for the list view
		myListView.setAdapter(myCursorAdapter);
	}

	// close database onDestroy
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbAdapter.close();
	}

	public void gotoAddListActivity(View v) {
		Intent intent = new Intent(this, AddListActivity.class);
		intent.putExtra("userId", clickedUserID);
		startActivity(intent);
	}
	
	public void gotoViewAllTasksActivity(View v) {
		Intent viewAllTaskIntent = new Intent(this, ViewAllTasksActivity.class);
		viewAllTaskIntent.putExtra("userId", clickedUserID);
		startActivity(viewAllTaskIntent);
	}
	
	public void onClickSignOut(View v){
		this.finish();
	}
	
}
