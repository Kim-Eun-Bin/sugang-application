package com.tpj.teamproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CompleteListAdapter extends RecyclerView.Adapter<CompleteListAdapter.MyViewHolder> {
    ArrayList<String> mDataset;
    public NoCompleteListAdapter noAdapter;
    UserInsertActivity mActivity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public Button button;
        public MyViewHolder(View v) {
            super(v);
            button = v.findViewById(R.id.go_btn);
            textView = v.findViewById(R.id.item_sugang_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CompleteListAdapter(ArrayList<String> myDataset, UserInsertActivity activity) {
        mDataset = myDataset;
        mActivity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CompleteListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sugang_list, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset.get(position));
        holder.button.setText("-");
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noAdapter.mDataset.add(mDataset.get(position));
                mDataset.remove(position);
                mActivity.saveData(mDataset);
                notifyDataSetChanged();
                noAdapter.notifyDataSetChanged();

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
