package com.abdulrehman.i170357_i170015;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {
    static final int TYPE_MESSAGE_SENT = 0;
    static final int TYPE_MESSAGE_RECEIVED = 1;
    //static final int TYPE_IMAGE_SENT = 2;
    //static final int TYPE_IMAGE_RECEIVED = 3;

    LayoutInflater inflater;
    List<JSONObject> messages = new ArrayList<>();

    public MessageAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView MessageTxt;
        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            MessageTxt = itemView.findViewById(R.id.send_message_text);
        }
    }
    /*private  class SentImageHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public SentImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.send_message_Image);
        }
    }*/
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView Fname, Lname, MessageTxt;
        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            MessageTxt = itemView.findViewById(R.id.received_message_text);
            Fname = itemView.findViewById(R.id.sender_FnameTxt);
            Lname = itemView.findViewById(R.id.sender_LnameTxt);
        }
    }
    /*private  class RecievedImageHolder extends RecyclerView.ViewHolder {

        TextView Fname, Lname;
        ImageView imageView;
        public RecievedImageHolder(@NonNull View itemView) {
            super(itemView);
            Fname = itemView.findViewById(R.id.sender_FnameTxt);
            Lname = itemView.findViewById(R.id.sender_LnameTxt);
            imageView = itemView.findViewById(R.id.received_message_Image);
        }
    }*/

    @Override
    public int getItemViewType(int position) {
        JSONObject message = messages.get(position);
        try {
            if(message.getBoolean("isSent")){
                if(message.has("message")){
                    return TYPE_MESSAGE_SENT;
                }
                /*else{
                    return TYPE_IMAGE_SENT;
                }*/
            }
            else{
                if(message.has("message")){
                    return TYPE_MESSAGE_RECEIVED;
                }
                /*else{
                    return TYPE_IMAGE_RECEIVED;
                }*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.item_send_message,parent,false);
                return new SentMessageHolder(view);
            /*case TYPE_IMAGE_SENT:
                view = inflater.inflate(R.layout.item_sent_image,parent,false);
                return new SentImageHolder(view);*/
            case TYPE_MESSAGE_RECEIVED:
                view = inflater.inflate(R.layout.item_received_message,parent,false);
                return new ReceivedMessageHolder(view);
            /*case TYPE_IMAGE_RECEIVED:
                view = inflater.inflate(R.layout.item_receive_image,parent,false);
                return new RecievedImageHolder(view);*/
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JSONObject message = messages.get(position);
        try {
            if(message.getBoolean("isSent")){
                if(message.has("message")){
                    SentMessageHolder messageHolder = (SentMessageHolder) holder;
                    messageHolder.MessageTxt.setText(message.getString("message"));
                }
                /*else{
                    SentImageHolder imageHolder = (SentImageHolder) holder;
                    Bitmap bitmap = getBitmapFromString(message.getString("image"));
                    imageHolder.imageView.setImageBitmap(bitmap);
                }*/
            }
            else{
                if(message.has("message")){
                    ReceivedMessageHolder receivedMessageHolder = (ReceivedMessageHolder) holder;
                    receivedMessageHolder.Fname.setText(message.getString("firstName"));
                    receivedMessageHolder.Lname.setText(message.getString("lastName"));
                    receivedMessageHolder.MessageTxt.setText(message.getString("message"));
                }
                /*else{
                    RecievedImageHolder recievedImageHolder = (RecievedImageHolder) holder;
                    recievedImageHolder.Fname.setText(message.getString("firstName"));
                    recievedImageHolder.Fname.setText(message.getString("lastName"));
                    Bitmap bitmap = getBitmapFromString(message.getString("image"));
                    recievedImageHolder.imageView.setImageBitmap(bitmap);
                }*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*private Bitmap getBitmapFromString(String image) {
        byte[] bytes = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }*/

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItem(JSONObject jsonObject){
        messages.add(jsonObject);
        notifyDataSetChanged();
    }
}
