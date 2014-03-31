package com.example.todoonthego.activity;

import com.example.todoonthego.R;
import com.example.todoonthego.model.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity{
	private String clickedUserName;
	private String clickedUserID;
	
	
//	private Button signIn;
	
	private DBAdapter dbAdapter;
	private Cursor userCursor;
	
	EditText passWord;
	String checkPW = "";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent in = getIntent();

		clickedUserName = in.getStringExtra("userName");
		clickedUserID = in.getStringExtra("userId");
		
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		
		TextView username = (TextView) findViewById(R.id.textView2);
		username.setText(clickedUserName);
		
		passWord = (EditText) findViewById(R.id.passWord1);

		// check database for userName and password
		userCursor = dbAdapter.getUserById(clickedUserID);
		
		userCursor.moveToFirst();
		
		if (userCursor != null){
	
			checkPW = userCursor.getString(userCursor
				.getColumnIndex(dbAdapter.USER_TABLE_COLUMN_PW));
		}
		
//		signIn = (Button)findViewById(R.id.buttonLogin); 
//		signIn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				String inputPW = passWord.toString();
//				
//				// Move to intent ViewAllLists if good, Put up Toast if not.
//				if (inputPW == checkPW){
//					Intent intent = new Intent(LoginActivity.this, ViewAllLists.class);
//					intent.putExtra("userName", clickedUserName);
//					intent.putExtra("userid", clickedUserID);
//					startActivity(intent);
//					
//					this.finish();
//				}
//				else{
//					Toast.makeText(getBaseContext(), "Wrong Password, Try again",
//							Toast.LENGTH_SHORT).show();
//				}
//				
//			}
//		});

		
		

		
	}
	
	public void onClickLogin(View v) {
		String inputPW = passWord.getText().toString();
		
		// Move to intent ViewAllLists if good, Put up Toast if not.
		if (inputPW.equals(checkPW)){
			Intent intent = new Intent(LoginActivity.this, ViewAllLists.class);
			intent.putExtra("userName", clickedUserName);
			intent.putExtra("userId", clickedUserID);
			startActivity(intent);
			
			this.finish();
		}
		else{
			Toast.makeText(getBaseContext(), "Wrong Password, Try again",
					Toast.LENGTH_SHORT).show();
		}
	}


}
