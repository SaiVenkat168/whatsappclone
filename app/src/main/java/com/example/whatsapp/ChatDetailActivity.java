package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp.Adapters.ChatAdapter;
import com.example.whatsapp.Modals.MessagesModal;
import com.example.whatsapp.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {
    ActivityChatDetailBinding binding;
    String recieveid,recievername,recieverimg;
    String senderid;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        Intent i=getIntent();
        database=FirebaseDatabase.getInstance();
        recieveid=i.getStringExtra("userId");
        recieverimg=i.getStringExtra("profilePic");
        recievername=i.getStringExtra("username");

        auth=FirebaseAuth.getInstance();
        senderid=auth.getUid();

        binding.username.setText(recievername);
        Picasso.get().load(recieverimg).placeholder(R.drawable.profile).into(binding.profileimg);
        binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatDetailActivity.this, MainActivity.class));
            }
        });

        final ArrayList<MessagesModal> messagesModals=new ArrayList<>();
        final ChatAdapter chatAdapter=new ChatAdapter(messagesModals,this);
        binding.chatsdetailrecyclerview.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        binding.chatsdetailrecyclerview.setLayoutManager(linearLayoutManager);

        final String senderRoom=senderid+recieveid;
        final String recieverRoom=recieveid+senderid;

        database.getReference().child("Chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagesModals.clear();
                        for(DataSnapshot snapshot1:snapshot.getChildren())
                        {
                            MessagesModal modal=snapshot1.getValue(MessagesModal.class);
                            messagesModals.add(modal);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message=binding.etmessage.getText().toString();
                if(message=="")
                    Toast.makeText(ChatDetailActivity.this, "Enter a valid message", Toast.LENGTH_SHORT).show();
                else {
                    final MessagesModal modal = new MessagesModal(senderid, message);
                    modal.setTimestamp(new Date().getTime());
                    binding.etmessage.setText("");
                    database.getReference().child("Chats")
                            .child(senderRoom)
                            .push().setValue(modal)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    database.getReference().child("Chats").child(recieverRoom)
                                            .push().setValue(modal)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            });
                                }
                            });
                }

            }
        });





    }
}