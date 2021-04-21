package com.abdulrehman.i170357_i170015;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {
    List<Contact> ls;
    Context c;
    private OnItemListner itListner;
    public MyRvAdapter(List<Contact> ls, Context c, OnItemListner itListner) {
        this.c = c;
        this.ls = ls;
        this.itListner = itListner;
    }

    @NonNull
    @Override
    public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.row,parent,false);
        return new MyViewHolder(itemView,itListner);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder holder, int position) {
        holder.name.setText(ls.get(position).getFirstName()+ " "+ ls.get(position).getLastName());
        if (ls.get(position).getImage()!=null) {
            System.out.println("NOT NULL");
            byte[] decodedString = Base64.decode(ls.get(position).getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.image.setImageBitmap(decodedByte);
        }
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        CircleImageView image;
        LinearLayout l;
        OnItemListner onItemListner;
        public MyViewHolder(@NonNull View itemView, OnItemListner onItemListner) {
            super(itemView);
            name = itemView.findViewById(R.id.main_chat_profile_name);
            image = itemView.findViewById(R.id.main_chat_profile_pic);
            l = itemView.findViewById(R.id.ll);
            this.onItemListner = onItemListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListner.ONItemClick(getAdapterPosition());
        }
    }
    public interface OnItemListner{
        void ONItemClick(int position);
    }
}
