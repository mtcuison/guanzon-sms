package org.guanzonsms.receiver.Activity;

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

import org.guanzonsms.receiver.Adapter.SmsListAdapter;
import com.example.guanzonsms.R;

import org.guanzonsms.receiver.Object.AppConstants;
import org.guanzonsms.receiver.Object.ServiceScheduler;
import org.guanzonsms.receiver.Service.SmsServerUpdateService;
import org.guanzonsms.receiver.ViewModel.VMSmsIncoming;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Activity_Main extends AppCompatActivity {
    private VMSmsIncoming mViewModel;
    private SmsListAdapter poAdapter;
    private LinearLayout poNoMsgsx;
    private RecyclerView rvSmsList;
    private FloatingActionButton btnCmpMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        smsUpdateSchedule();
        initObjects();
        displaySmsList();

        btnCmpMsg.setOnClickListener(v -> {
            composeMessage();
        });

    }

    private void requestPermissions() {
        List<String> requiredPermissions = new ArrayList<>();
        requiredPermissions.add(Manifest.permission.CALL_PHONE);
        requiredPermissions.add(Manifest.permission.READ_PHONE_STATE);
        requiredPermissions.add(Manifest.permission.READ_SMS);
        requiredPermissions.add(Manifest.permission.RECEIVE_SMS);
        requiredPermissions.add(Manifest.permission.SEND_SMS);
        requiredPermissions.add(Manifest.permission.RECEIVE_WAP_PUSH);
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
        mViewModel = new ViewModelProvider(Activity_Main.this).get(VMSmsIncoming.class);
        poNoMsgsx = findViewById(R.id.ln_noMessages);
        btnCmpMsg = findViewById(R.id.fab);
        rvSmsList = findViewById(R.id.rv_sms_list);
        rvSmsList.setLayoutManager(new LinearLayoutManager(Activity_Main.this));
        rvSmsList.setHasFixedSize(true);
    }

    private void displaySmsList() {
        mViewModel.getSmsIncomingListForViewing().observe(Activity_Main.this, eSmsinfos -> {
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

    private void smsUpdateSchedule() {
        ServiceScheduler.scheduleJob(Activity_Main.this, SmsServerUpdateService.class,
                ServiceScheduler.FIFTEEN_MINUTE_PERIODIC,
                AppConstants.DataServiceID);
    }

}