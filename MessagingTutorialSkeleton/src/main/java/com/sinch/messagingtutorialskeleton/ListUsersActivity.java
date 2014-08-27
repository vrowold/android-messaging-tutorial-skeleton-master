package com.sinch.messagingtutorialskeleton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.messagingtutorialskeleton.R;
import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince on 7/24/14.
 */



public class ListUsersActivity extends Activity {

    private String currentUserId;
    private ArrayAdapter<String> namesArrayAdapter;
    private ArrayList<String> names;
    private ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        setConversationsList();
    }
    private void setConversationsList() {
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        names = new ArrayList<String>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", currentUserId);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, ParseException e) {
                if (e == null) {
                    for (int i=0; i<userList.size(); i++) {
                        names.add(userList.get(i).getUsername().toString());
                    }

                    usersListView = (ListView)findViewById(R.id.usersListView);
                    namesArrayAdapter =
                            new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.user_list_item, names);
                    usersListView.setAdapter(namesArrayAdapter);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
                usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                        Toast.makeText(getApplicationContext(),
                                "You clicked on user: " + i,
                                Toast.LENGTH_SHORT).show();
                        //Look up user id and open conversation
                    }
                });
            }



            @Override
            public void done(List<ParseUser> parseUsers, com.parse.ParseException e) {

            }

        });
    }
}
