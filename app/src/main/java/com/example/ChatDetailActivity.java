package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatsapp.Adapters.ChatAdapter;
import com.example.whatsapp.MainActivity;
import com.example.whatsapp.Modals.MessagesModal;
import com.example.whatsapp.R;
import com.example.whatsapp.databinding.ActivityChatDetailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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




    }
}