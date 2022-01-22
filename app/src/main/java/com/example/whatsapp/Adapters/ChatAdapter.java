package com.example.whatsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Modals.MessagesModal;
import com.example.whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter
{
    ArrayList<MessagesModal> messagesModals;
    Context context;
    int SENDER_VIEW_TYPE=1;
    int RECIEVER_VIEW_TYPE=2;


    public ChatAdapter(ArrayList<MessagesModal> messagesModals, Context context) {
        this.messagesModals = messagesModals;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(viewType==SENDER_VIEW_TYPE)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return  new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return  new RecieverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(messagesModals.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
            return SENDER_VIEW_TYPE;
        else
            return RECIEVER_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessagesModal messagesModal=messagesModals.get(position);

        if(holder.getClass()==SenderViewHolder.class)
        {
            ((SenderViewHolder) holder).sendermsg.setText(messagesModal.getMessage());
        }
        else
            ((RecieverViewHolder) holder).recievermsg.setText(messagesModal.getMessage());


    }

    @Override
    public int getItemCount() {
        return messagesModals.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder
    {
        TextView recievermsg,recievertime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recievermsg=itemView.findViewById(R.id.recievermessage);
            recievertime=itemView.findViewById(R.id.recievertime);

        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder
    {
        TextView sendermsg,sendertime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sendermsg=itemView.findViewById(R.id.sendermessage);
            sendertime=itemView.findViewById(R.id.sendertime);
        }
    }

}
