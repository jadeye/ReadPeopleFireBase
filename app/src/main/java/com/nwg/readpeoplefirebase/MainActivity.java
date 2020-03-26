package com.nwg.readpeoplefirebase;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView mListView;
    ArrayList<Integer> selected;
    EditText editTextSearch;
    SearchView searchView;
    SearchManager searchManager;
    EditText nameEditTxt, quoteEditText, descriptionEditText;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        mListView = (ListView) findViewById(R.id.list_view);
        searchView = (SearchView) findViewById(R.id.search);
        //searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        //initialize firebase database
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db, this, mListView);

        adapter = new CustomAdapter(context, helper.people);
        mListView.setLongClickable(true);
        selected = new ArrayList<>();
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // int selected_id = s.get_id();
                int selected_id = position;
                if (selected.contains(selected_id)) {
                    selected.remove(selected.indexOf(selected_id));
                    Log.i(TAG, " -- remove -- " + parent.getChildAt(selected_id));
                    parent.getChildAt(selected_id).setBackgroundColor(ContextCompat.getColor(context, R.color.default_color));
                    //view.setBackgroundColor(ContextCompat.getColor(context, R.color.default_color));
                    //view.setSelected(false);
                    Toast.makeText(context, "MainAct REMOVED: " + selected_id + " - " , Toast.LENGTH_SHORT).show();
                } else {
                    selected.add(selected_id);
                    parent.getChildAt(selected_id).setBackgroundColor(ContextCompat.getColor(context, R.color.selected_color));
                    Log.i(TAG, " -- add -- " + parent.getChildAt(selected_id));
                    //view.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_color));
                    //view.setSelected(true);
                    Toast.makeText(context, "MainAct ADDED: " + selected_id + " - " , Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
/*
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "setOnSearchClickListener: " + v);
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "OnClickListener: " + v);
            }
        });*/


        searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //mListView.getAdapter()
                Toast.makeText(context,"Our word : " + query,Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onQueryTextSubmit: " + query);
                // Query textQuery = db.orderByChild("name").equalTo(query);
                adapter.getFilter().filter(query);
                mListView.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, "onQueryTextChange: " + newText);
                adapter.getFilter().filter(newText);

                mListView.setAdapter(adapter);
                return true;
            }
        });

        /*mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                //Toast.makeText(context,"onItemCheckedStateChanged -- " + position,Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onItemCheckedStateChanged : " + position);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });*/

        /*editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            // requery/filter your adapter then set it to your listview>
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });*/
    }
}
