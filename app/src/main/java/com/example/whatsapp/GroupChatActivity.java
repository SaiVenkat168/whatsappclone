package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatsapp.Adapters.ChatAdapter;
import com.example.whatsapp.Modals.MessagesModal;
import com.example.whatsapp.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
    ActivityGroupChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.groupchatsimageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupChatActivity.this, MainActivity.class));
            }
        });

        FirebaseDatabase database=FirebaseDatabase.getInstance();

        final ArrayList<MessagesModal> messagesModals=new ArrayList<>();
        final ChatAdapter chatAdapter=new ChatAdapter(messagesModals,this);

        final String senderId= FirebaseAuth.getInstance().getUid();
        binding.groupchatsusername.setText("Amigos....");

        binding.groupchatsrecyclerview.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        binding.groupchatsrecyclerview.setLayoutManager(linearLayoutManager);

        database.getReference().child("Group Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

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

        binding.groupchatssend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String msg=binding.groupchatsetmessage.getText().toString();
                final MessagesModal modal=new MessagesModal(senderId,msg);
                modal.setTimestamp(new Date().getTime());
                binding.groupchatsetmessage.setText("");

                database.getReference().child("Group Chat")
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