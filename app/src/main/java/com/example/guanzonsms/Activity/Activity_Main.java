package com.example.guanzonsms.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.guanzonsms.Adapter.SmsListAdapter;
import com.example.guanzonsms.R;
import com.example.guanzonsms.ViewModel.VMSmsInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Activity_Main extends AppCompatActivity {
    private VMSmsInfo mViewModel;
    private SmsListAdapter poAdapter;
    private RecyclerView rvSmsList;
    private FloatingActionButton btnCmpMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        initObjects();
        displaySmsList();

        btnCmpMsg.setOnClickListener(v -> {
            composeMessage();
        });

    }

    private void checkPermissions() {
        int checkReadSms = ContextCompat.checkSelfPermission(Activity_Main.this, Manifest.permission.READ_SMS);
        int checkReceiveSms = ContextCompat.checkSelfPermission(Activity_Main.this, Manifest.permission.RECEIVE_SMS);
        int checkSendSms = ContextCompat.checkSelfPermission(Activity_Main.this, Manifest.permission.SEND_SMS);
        int checkReceiveWapPush = ContextCompat.checkSelfPermission(Activity_Main.this, Manifest.permission.RECEIVE_WAP_PUSH);

        if(checkReadSms != PackageManager.PERMISSION_GRANTED || checkReceiveSms != PackageManager.PERMISSION_GRANTED ||
                checkSendSms != PackageManager.PERMISSION_GRANTED || checkReceiveWapPush != PackageManager.PERMISSION_GRANTED) {

            String[] permissions = new String[] {
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_WAP_PUSH
            };

            ActivityCompat.requestPermissions(Activity_Main.this, permissions,0);
        }
    }

    private void initObjects() {
        mViewModel = new ViewModelProvider(Activity_Main.this).get(VMSmsInfo.class);
        btnCmpMsg = findViewById(R.id.fab);
        rvSmsList = findViewById(R.id.rv_sms_list);
        rvSmsList.setLayoutManager(new LinearLayoutManager(Activity_Main.this));
        rvSmsList.setHasFixedSize(true);
    }

    private void displaySmsList() {
        mViewModel.getSmsList().observe(Activity_Main.this, eSmsinfos -> {
            try {
                poAdapter = new SmsListAdapter(eSmsinfos);
                rvSmsList.setAdapter(poAdapter);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void composeMessage() {
        Intent loIntent = new Intent(Activity_Main.this, Activity_Compose.class);
        startActivity(loIntent);
    }

}