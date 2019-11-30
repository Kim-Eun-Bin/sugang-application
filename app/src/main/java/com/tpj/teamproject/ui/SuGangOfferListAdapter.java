package com.tpj.teamproject.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tpj.teamproject.R;
import com.tpj.teamproject.controller.SuGangDTO;

import java.util.ArrayList;


public class SuGangOfferListAdapter extends RecyclerView.Adapter<SuGangOfferListAdapter.MyViewHolder> {
    ArrayList<SuGangDTO> mDataset;
    // MypageFragment mFragment;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textName;

        public View view;

        public MyViewHolder(View v) { // = item
            super(v);
            this.view = v;
            textName = v.findViewById(R.id.item_sugang_offer_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SuGangOfferListAdapter(ArrayList<SuGangDTO> myDataset) {
        mDataset = myDataset;
        // mFragment = fragment;
    }

    public SuGangOfferListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sugang_offer_list, parent, false);
        SuGangOfferListAdapter.MyViewHolder vh = new SuGangOfferListAdapter.MyViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(final SuGangOfferListAdapter.MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

            holder.textName.setText(mDataset.get(position).name);
        }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}