package com.tpj.teamproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class NoCompleteListAdapter extends RecyclerView.Adapter<NoCompleteListAdapter.MyViewHolder> {
    ArrayList<String> mDataset;
    CompleteListAdapter compAdapter;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public Button goButton;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.item_sugang_name);
            goButton = v.findViewById(R.id.go_btn);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NoCompleteListAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NoCompleteListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
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
        if(compAdapter!=null){
            for(String s : compAdapter.mDataset){
                if(s.equals(mDataset.get(position))){

                    return;
                }
            }
        }

        holder.textView.setText(mDataset.get(position));
        holder.goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(compAdapter!=null){
                    compAdapter.mDataset.add(mDataset.get(position));
                    mDataset.remove(position);
                    notifyDataSetChanged();
                    compAdapter.notifyDataSetChanged();
                    compAdapter.mActivity.saveData(compAdapter.mDataset);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
