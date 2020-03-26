package com.nwg.readpeoplefirebase;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved;
    ArrayList<Person> people = new ArrayList<>();
    ListView mListView;
    Context c;
    CustomAdapter adapter;

    public FirebaseHelper(DatabaseReference db, Context context, ListView mListView) {
        this.db = db;
        this.c = context;
        this.mListView = mListView;
        this.retrieve();
    }

    public ArrayList<Person> retrieve() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                people.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Now get Person Objects and populate our arraylist.
                        Person person = ds.getValue(Person.class);
                        people.add(person);
                    }
                    adapter = new CustomAdapter(c, people);
                    mListView.setAdapter(adapter);

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mListView.smoothScrollToPosition(people.size());
                            //mListView.smoothScrollByOffset(people.size());
                            mListView.smoothScrollToPosition(people.indexOf(0));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("mTAG", databaseError.getMessage());
                Toast.makeText(c, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        return people;
    }

    public Boolean save(Person person) {
        if (person == null) {
            saved = false;
        } else {
            try {
                //save here
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
        //db.child("Person").push().setValue(person);
    }


}
