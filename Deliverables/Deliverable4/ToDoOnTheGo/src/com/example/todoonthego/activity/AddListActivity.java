package com.example.todoonthego.activity;

import com.example.todoonthego.R;
import com.example.todoonthego.model.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This Activity allow user to add a new list.
 *
 */
public class AddListActivity extends Activity {

	private DBAdapter dbAdapter;

	private Button save;
	
	private String clickedUserID;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_list_layout);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent in = getIntent();

		clickedUserID = in.getStringExtra("userId");

		dbAdapter = new DBAdapter(this);
		dbAdapter.open();

		save = (Button) findViewById(R.id.save);

		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addNewList();
//				Toast.makeText(getBaseContext(), "List saved",
//						Toast.LENGTH_SHORT).show();
			}
		});

	}

	// Insert the current list into database
	private void addNewList() {
		// Get the list name
		EditText listName = (EditText) findViewById(R.id.add_list_name);
		// insert new list
		dbAdapter.insertList(dbAdapter.getNewListId(), listName.getText()
				.toString(), clickedUserID);
		// kill this activity and return to the last activity
		this.finish();
	}

	protected void onDestroy() {
		super.onDestroy();
		dbAdapter.close();
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			Intent intent = new Intent(this, ViewAllLists.class);
			startActivity(intent);
			}
		return super.onOptionsItemSelected(item);
	}

}
