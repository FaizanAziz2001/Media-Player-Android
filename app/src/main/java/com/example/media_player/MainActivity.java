package com.example.media_player;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        bottom_nav=findViewById(R.id.bottom_nav);
        bottom_nav.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.audio) {
                LoadFragment(new SongDisplay());
            }
            else if(item.getItemId()==R.id.video) {
                LoadFragment(new Video_Display());
            }
            else if(item.getItemId()==R.id.stream)
            {
                LoadFragment(new Stream_Video());
            }
            return true;
        });

        LoadFragment(new SongDisplay());

    }

    private void LoadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    void checkPermission()
    {
        requestPermission();
        int result=ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result==PackageManager.PERMISSION_DENIED)
            finish();
    }

    void requestPermission()
    {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
    }

}