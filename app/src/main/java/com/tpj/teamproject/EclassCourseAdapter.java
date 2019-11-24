package com.tpj.teamproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class EclassCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList mData;
    Fragment mFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        View mView;
        public MyViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.course_title);
            //url = view.findViewById(R.id.course_url);
            this.mView = view;
        }
    }

    public EclassCourseAdapter(Fragment activity, HashMap<String,Course> list){
        mData = new ArrayList(list.values());
        this.mFragment = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_eclass_course,viewGroup,false);
        return new EclassCourseAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
        final EclassCourseAdapter.MyViewHolder myViewHolder = (EclassCourseAdapter.MyViewHolder)viewHolder;
        final Course course = (Course)mData.get(i);
        System.out.println(course.toString());
        myViewHolder.title.setText(course.title);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}