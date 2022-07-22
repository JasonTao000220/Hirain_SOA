package com.hirain.hirain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hirain.hirain.fragment.Bean;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public ArrayList<Bean> arraylist;

    public  MyAdapter(ArrayList<Bean> myList){
        arraylist = myList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_letter,
                null,false);
        final MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Bean bean = arraylist.get(position);
        holder.textlayout.setText(bean.getText());
        holder.imglayout.setImageResource(bean.getImg());
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView textlayout;
        public ImageView imglayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textlayout = itemView.findViewById(R.id.modelname);
            imglayout = itemView.findViewById(R.id.detailbutton);


        }
    }

}
