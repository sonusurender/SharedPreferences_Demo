package com.sharedpreference_demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	EditText name, email, phone;
	Button save, show, delete;
	TextView show_saved_data;

	//Shared Preference for storing data
	SharedPreferences preferences;
	
	//Editor to edit shared preference
	Editor editor;

	//String variables for shared preference
	private static final String shared_preferences = "shared_preferences";
	private static final String stored_name = "name";
	private static final String stored_email = "email";
	private static final String stored_phone = "phone";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Finding all views id
		name = (EditText) findViewById(R.id.username);
		email = (EditText) findViewById(R.id.emailid);
		phone = (EditText) findViewById(R.id.phoneno);

		save = (Button) findViewById(R.id.save);
		show = (Button) findViewById(R.id.show);
		delete = (Button) findViewById(R.id.delete);

		show_saved_data = (TextView) findViewById(R.id.saved_data);

		//Getting shared preference
		preferences = getSharedPreferences(shared_preferences,
				Context.MODE_PRIVATE);
		
		//Initializing editor to edit preference
		editor = preferences.edit();

		//Implementing click listener on all buttons that are used
		save.setOnClickListener(this);
		show.setOnClickListener(this);
		delete.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:

			//Getting edittext texts into string variables
			String getName = name.getText().toString();
			String getEmail = email.getText().toString();
			String getPhone = phone.getText().toString();

			//Checking if all text fields are filled or empty 
			if (TextUtils.isEmpty(getName) || TextUtils.isEmpty(getEmail)
					|| TextUtils.isEmpty(getPhone)) {
				
				//If all text fields are not filled an toast is shown
				Toast.makeText(MainActivity.this, "Please Fill all Fields.",
						Toast.LENGTH_SHORT).show();
			} else {
				
				//else text fields data is stored in shared preference
				editor.putString(stored_name, getName);
				editor.putString(stored_email, getEmail);
				editor.putString(stored_phone, getPhone);
				
				//After storing data commit the editor to save data
				editor.commit();

				//And clear the edittext fields
				name.setText("");
				email.setText("");
				phone.setText("");

				//Toast is shown to show that data is saved
				Toast.makeText(MainActivity.this, "Data Saved Successfully.",
						Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.show:

			//Check if preference contains stored data or not
			if (preferences.contains(stored_name)
					&& preferences.contains(stored_email)
					&& preferences.contains(stored_phone)) {

				//If data is stored get the data and display on textView
				String getSavedName = preferences.getString(stored_name, null);
				String getSavedEmail = preferences
						.getString(stored_email, null);
				String getSavedPhone = preferences
						.getString(stored_phone, null);

				show_saved_data.setText("Full Name : " + getSavedName + "\n"
						+ "Email Id : " + getSavedEmail + "\n" + "Phone No. : "
						+ getSavedPhone);

				//Showing delete button that is invisible till
				delete.setVisibility(View.VISIBLE);

			} else {

				//Display the data if data is not saved
				show_saved_data
						.setText("There is no data in Shared Prefrences.");

			}
			
			//Showing textView on show button click
			show_saved_data.setVisibility(View.VISIBLE);
			break;
			
		case R.id.delete:
			
			//Check if data contains in shared preference
			if (preferences.contains(stored_name)
					&& preferences.contains(stored_email)
					&& preferences.contains(stored_phone)) {

				//If contains clear the editor and commit it
				editor.clear();
				editor.commit();
			} else {
				
				//If not present any data then toast is displayed
				Toast.makeText(MainActivity.this, "Already Cleared.",
						Toast.LENGTH_SHORT).show();
			}

			// Both textView and delete button is visible
			show_saved_data.setVisibility(View.GONE);
			delete.setVisibility(View.GONE);

			break;
		}

	}

}
