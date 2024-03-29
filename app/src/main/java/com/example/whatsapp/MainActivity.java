package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whatsapp.Adapters.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ViewPager viewpager;
    TabLayout tablayout;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        viewpager=findViewById(R.id.viewpager);
        tablayout=findViewById(R.id.tablayout);



        viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewpager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                auth.signOut();
                startActivity(new Intent(MainActivity.this,SigninActivity.class));
                break;
            case R.id.settings:
                Toast.makeText(MainActivity.this, "Settings .....", Toast.LENGTH_SHORT).show();
                break;
            case R.id.groupchat:
                startActivity(new Intent(MainActivity.this,GroupChatActivity.class));

        }
        return true;
    }
}