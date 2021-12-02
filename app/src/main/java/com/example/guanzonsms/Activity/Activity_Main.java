package com.example.guanzonsms.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guanzonsms.Adapter.SmsListAdapter;
import com.example.guanzonsms.R;
import com.example.guanzonsms.ViewModel.VMSmsInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Activity_Main extends AppCompatActivity {
    private VMSmsInfo mViewModel;
    private SmsListAdapter poAdapter;
    private LinearLayout poNoMsgsx;
    private RecyclerView rvSmsList;
    private FloatingActionButton btnCmpMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        requestCallPermissions();
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

    private void requestCallPermissions() {
        List<String> requiredPermissions = new ArrayList<>();
        requiredPermissions.add(Manifest.permission.CALL_PHONE);
        requiredPermissions.add(Manifest.permission.READ_PHONE_STATE);
        requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requiredPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        requiredPermissions.add(Manifest.permission.READ_CALL_LOG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requiredPermissions.add(Manifest.permission.ANSWER_PHONE_CALLS);
        }

        List<String> missingPermissions = new ArrayList<>();

        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }

        if (!missingPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    missingPermissions.toArray(new String[0]), 0);
        }
    }

    private void initObjects() {
        mViewModel = new ViewModelProvider(Activity_Main.this).get(VMSmsInfo.class);
        poNoMsgsx = findViewById(R.id.ln_noMessages);
        btnCmpMsg = findViewById(R.id.fab);
        rvSmsList = findViewById(R.id.rv_sms_list);
        rvSmsList.setLayoutManager(new LinearLayoutManager(Activity_Main.this));
        rvSmsList.setHasFixedSize(true);
    }

    private void displaySmsList() {
        mViewModel.getSmsList().observe(Activity_Main.this, eSmsinfos -> {
            try {
                if(eSmsinfos.size() > 0) {
                    poNoMsgsx.setVisibility(View.GONE);
                    rvSmsList.setVisibility(View.VISIBLE);
                    poAdapter = new SmsListAdapter(eSmsinfos);
                    rvSmsList.setAdapter(poAdapter);
                } else {
                    poNoMsgsx.setVisibility(View.VISIBLE);
                    rvSmsList.setVisibility(View.GONE);
                }
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