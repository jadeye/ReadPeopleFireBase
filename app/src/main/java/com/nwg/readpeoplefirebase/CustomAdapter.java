package com.nwg.readpeoplefirebase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = CustomAdapter.class.getSimpleName();
    Context c;
    ArrayList<Person> people;
    ArrayList<Person> mDataFiltered;
    ArrayList<Integer> selected;

    public CustomAdapter(Context c, ArrayList<Person> people) {
        this.c = c;
        this.people = people;
        this.mDataFiltered = people;
        selected = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return people.size();
    }

    @Override
    public Object getItem(int position) {
        return people.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecyclerView.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.one_line_list_item, parent, false);
        }

        CheckBox cbId = convertView.findViewById(R.id.cbid);
        TextView nameTextView = convertView.findViewById(R.id.name);
        TextView dateTextView = convertView.findViewById(R.id.date);
        TextView descriptionTextView = convertView.findViewById(R.id.description);

        final Person s = (Person) this.getItem(position);

        /* cdId.setId(s.get_id());
        Log.i(TAG, "" + this.getItem(position));
         */

         Log.i(TAG, "" + s.get_id());
        //cdId.setId(s.get_id());
        nameTextView.setText(s.getName());
        descriptionTextView.setText(s.getDetails());
        dateTextView.setText(s.getDay() + " " + s.getMonth() + " " + s.getYear());

        //Log.i(TAG, "convertView.getTag --- " + convertView.getTag(position));

        if (cbId != null ) {

            if (selected.contains(s.get_id())) {
                cbId.setChecked(true);
            } else {
                cbId.setChecked(false);
            }

            cbId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(c, "" + s.get_id(), Toast.LENGTH_SHORT).show();
                }
            });

            cbId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // update your model (or other business logic) based on isChecked
                    int selected_id = s.get_id();
                    if (selected.contains(selected_id) && !isChecked) {
                        selected.remove(selected.indexOf(selected_id));
                        Toast.makeText(c, "REMOVED: " + selected_id + " - " + isChecked, Toast.LENGTH_SHORT).show();
                    } else {
                        selected.add(selected_id);
                        Toast.makeText(c, "ADDED: " + selected_id + " - " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, s.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(c, " -- onLongClick -- " + s.get_id() + " - " + s.getName(), Toast.LENGTH_SHORT).show();

                int selected_id = s.get_id();
                if (selected.contains(selected_id)) {
                    selected.remove(selected_id);
                } else {
                    selected.add(selected_id);
                }

                notifyDataSetChanged();

                return false;
            }
        });
/*

        convertView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                */
/*Toast.makeText(c, "CustomAdapter -- onLongClick -- " + s.get_id() + " - " + s.getName(), Toast.LENGTH_SHORT).show();
                return false;*//*

                Log.i(TAG, "convertView.getTag --- " + v.getTag());
                int selected_id = s.get_id();
                if (selected.contains(selected_id)) {
                    selected.remove(selected.indexOf(selected_id));
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.default_color));
                    v.setSelected(false);
                    Toast.makeText(v.getContext(), "CustomAdapter REMOVED: " + selected_id + " - " , Toast.LENGTH_SHORT).show();
                } else {
                    selected.add(selected_id);
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.selected_color));
                    v.setSelected(true);
                    Toast.makeText(v.getContext(), "CustomAdapter ADDED: " + selected_id + " - " , Toast.LENGTH_SHORT).show();
                }

                */
/*int selected_id = s.get_id();
                if (selected.contains(selected_id)) {
                    selected.remove(selected.indexOf(selected_id));
                    Toast.makeText(c, "REMOVED: " + selected_id + " - " , Toast.LENGTH_SHORT).show();
                } else {
                    selected.add(selected_id);
                    Toast.makeText(c, "ADDED: " + selected_id + " - " , Toast.LENGTH_SHORT).show();
                }*//*

                return false;
            }
        });
*/

        return convertView;
    }

    private boolean isEmpty(String name) {
        if (isEmpty(name)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    //no constraint given, just return all the data. (no search)
                    results.count = people.size();
                    results.values = people;
                } else {//do the search
                    List<Person> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString().toUpperCase();
                    for (Person o : people)
                        if (o.getName().toUpperCase().startsWith(searchStr)) resultsData.add(o);
                    results.count = resultsData.size();
                    results.values = resultsData;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered = (ArrayList<Person>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
