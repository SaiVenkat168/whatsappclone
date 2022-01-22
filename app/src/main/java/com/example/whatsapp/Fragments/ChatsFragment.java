package com.example.whatsapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsapp.Modals.Users;
import com.example.whatsapp.Adapters.UsersAdapter;
import com.example.whatsapp.databinding.FragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class ChatsFragment extends Fragment {

    public ChatsFragment()
    {

    }
    FragmentChatsBinding binding;
    ArrayList<Users> list=new ArrayList<>();
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        binding=FragmentChatsBinding.inflate(inflater, container, false);
        UsersAdapter adapter=new UsersAdapter(list,getContext());
        binding.chatsrecyclerview.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.chatsrecyclerview.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Users users=dataSnapshot.getValue(Users.class);
                    users.getUserId(dataSnapshot.getKey());
                    if(dataSnapshot.getKey()!=auth.getUid())
                    list.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }
}