package com.example.todoonthego.activity;

import com.example.todoonthego.R;
import com.example.todoonthego.model.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUserActivity extends Activity{
	private DBAdapter dbAdapter;
	private Button addButton;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.add_user_layout);
			
			dbAdapter = new DBAdapter(this);
			dbAdapter.open();
			
			addButton = (Button)findViewById(R.id.buttonAdd);
			
//			addButton.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					addUser();
//					Toast.makeText(getBaseContext(), "User added",
//							Toast.LENGTH_SHORT).show();
//				}
//			});
	}
		
		public void onClickAddUser1(View v) {
			addUser();
//			Toast.makeText(getBaseContext(), "User added",
//					Toast.LENGTH_SHORT).show();
		}
		private void addUser(){
			EditText userName = (EditText) findViewById(R.id.userText);
			EditText password = (EditText) findViewById(R.id.passText);
			
			// Write method to insert username and password to database
			dbAdapter.insertUser(dbAdapter.getNewUserId(), userName.getText().toString(), 
					password.getText().toString());
			
			
			
			this.finish();

		}
		
		protected void onDestroy() {
			super.onDestroy();
			dbAdapter.close();
		}

}
