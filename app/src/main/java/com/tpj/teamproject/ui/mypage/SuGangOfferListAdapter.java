package com.tpj.teamproject.ui.mypage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tpj.teamproject.R;
import com.tpj.teamproject.controller.database.SuGang;

import java.util.ArrayList;


public class SuGangOfferListAdapter extends RecyclerView.Adapter<SuGangOfferListAdapter.MyViewHolder> {
    ArrayList<SuGang> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;

        public View view;

        public MyViewHolder(View v) {
            super(v);
            this.view = v;
            textName = v.findViewById(R.id.item_sugang_offer_name);
        }
    }

    public SuGangOfferListAdapter(ArrayList<SuGang> myDataset) {
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
            holder.textName.setText(mDataset.get(position).name);
        }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}