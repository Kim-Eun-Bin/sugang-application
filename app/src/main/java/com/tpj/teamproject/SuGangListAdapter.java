package com.tpj.teamproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.tpj.teamproject.controller.SuGangDTO;

import java.util.ArrayList;


    public class SuGangListAdapter extends RecyclerView.Adapter<SuGangListAdapter.MyViewHolder> {
        ArrayList<SuGangDTO> mDataset;
        SuGangFragment mFragment;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView textView, textSaveSuGang;
            public RadioButton radioIsComplete;
            public EditText editGrade;
            public View view;
            public MyViewHolder(View v) { // = item
                super(v);
                this.view = v;
                textView = v.findViewById(R.id.item_sugang_name);
                radioIsComplete = v.findViewById(R.id.radio_sugang_isComplete);
                editGrade = v.findViewById(R.id.edit_sugang_grade);
                textSaveSuGang = v.findViewById(R.id.text_sugang_save_grade);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public SuGangListAdapter(SuGangFragment fragment, ArrayList<SuGangDTO> myDataset) {
            mDataset = myDataset;
            mFragment = fragment;
        }

    // Create new views (invoked by the layout manager)
    @Override
    public SuGangListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sugang_list, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        String str = String.valueOf(mDataset.get(position).semester);

        String semester = str.toCharArray()[0] + "-" + str.toCharArray()[1] + "   ";

        if(str.equals("51")){
            semester= "";
        }

        holder.textView.setText(semester + mDataset.get(position).name);

        holder.radioIsComplete.setChecked(mDataset.get(position).isComplete);


        holder.textSaveSuGang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataset.get(position).grade = Double.parseDouble(holder.editGrade.getText().toString());
                if(!mDataset.get(position).isComplete){
                    mDataset.get(position).isComplete = true;
                }
                mFragment.updateUI();
                notifyDataSetChanged();
                Toast.makeText(holder.view.getContext(), "학점이 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        holder.radioIsComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataset.get(position).isComplete = !mDataset.get(position).isComplete;
                holder.radioIsComplete.setChecked(mDataset.get(position).isComplete);
            }
        });

        if(mDataset.get(position).grade == -1){
            holder.editGrade.setText("");
            holder.editGrade.setHint("학점 입력");
        }else{
            holder.editGrade.setText(String.valueOf(mDataset.get(position).grade));
            //mFragment.updateUI();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
