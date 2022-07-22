package com.hirain.hirain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hirain.hirain.bean.CustomMode;
import com.hirain.hirain.bean.ModelItem;
import com.hirain.hirain.fragment.Bean;
import com.hirain.hirain.myview.SwipeMenuLayout;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public ArrayList<ModelItem> mList;

    public  MyAdapter(ArrayList<ModelItem> myList){
        mList = myList;
    }

    public OnItemClickListener onItemClickListener;
    public OnMenuItemClickListener onMenuItemClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_letter,
                parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.textlayout.setText(mList.get(position).getModeName());
        if(mList.get(position).isSel){
            holder.item.setBackgroundResource(R.color.white_50);
        }else {
            holder.item.setBackgroundResource(R.color.tm);
        }

        holder.swipeMenuLayout.setSwipeeMenuMoveListener(new SwipeMenuLayout.SwipeeMenuMoveListener() {
            @Override
            public void menuOpen() {
                holder.imglayout.setVisibility(View.GONE);
            }

            @Override
            public void menuClose() {
                holder.imglayout.setVisibility(View.VISIBLE);
            }
        });
        holder.imglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.swipeMenuLayout.smoothExpand();
                holder.imglayout.setVisibility(View.GONE);
            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mList.get(position).isSel){
                    mList.get(position).setSel(false);
                }else {
                    for (int i = 0; i < mList.size(); i++) {
                        mList.get(i).setSel(false);
                    }
                    mList.get(position).setSel(true);

                }
                notifyDataSetChanged();
                if(onItemClickListener!=null){
                    onItemClickListener.itemClick(position);
                }
            }
        });
        //删除模式
        holder.modelDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.swipeMenuLayout.smoothClose();
                if(onMenuItemClickListener!=null){
                    onMenuItemClickListener.delClick(position);
                }
                mList.remove(position);
                notifyDataSetChanged();
            }
        });
        //编辑模式
        holder.modelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.swipeMenuLayout.smoothClose();
                if(onMenuItemClickListener!=null){
                    onMenuItemClickListener.editClick(position);
                }
            }
        });
    }


    public interface OnItemClickListener{
        void itemClick(int position);
    }
    public interface OnMenuItemClickListener{
        void editClick(int position);
        void delClick(int position);
    }

    public void  setOnItemListenter(OnItemClickListener onItemListenter){
        this.onItemClickListener=onItemListenter;
    }
    public void  setOnMenuItemListenter(OnMenuItemClickListener onItemListenter){
        this.onMenuItemClickListener=onItemListenter;
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView textlayout;
        public ImageView imglayout;
        private  SwipeMenuLayout swipeMenuLayout;
        private  RelativeLayout item;
        private  LinearLayout modelDel;
        private  LinearLayout modelEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textlayout = itemView.findViewById(R.id.model_name);
            imglayout = itemView.findViewById(R.id.detail_bt);
            swipeMenuLayout = itemView.findViewById(R.id.swipe_menu);
            item = itemView.findViewById(R.id.item);
            modelDel = itemView.findViewById(R.id.model_del);
            modelEdit = itemView.findViewById(R.id.model_edit);


        }
    }

}
