package com.nwg.readpeoplefirebase;

import android.widget.Filter;

import java.util.ArrayList;

public class PersonFilter extends Filter {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        if (constraint!=null && constraint.length()>0) {
            ArrayList<Person> tempList = new ArrayList<Person>();

            // search content in friend list
            /*for (Person person : BaseAdapter.personList) {
                if (person.getEmail().toLowerCase().contains(constraint.toString().toLowerCase())) {
                    tempList.add(user);
                }
            }*/

            filterResults.count = tempList.size();
            filterResults.values = tempList;
        } else {
            /*filterResults.count = friendList.size();
            filterResults.values = friendList;*/
        }

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

    }
}
