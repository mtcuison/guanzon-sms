package org.guanzonsms.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guanzonsms.R;

public class Activity_Compose extends AppCompatActivity {
    private EditText txtNumber, txtSmsMsg;
    private Button btnSendxx;
    private String poMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initObjects();
        btnSendxx.setOnClickListener(v -> sendSMS());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initObjects() {
        txtNumber = findViewById(R.id.txtMobileN);
        txtSmsMsg = findViewById(R.id.txtMessage);
        btnSendxx = findViewById(R.id.btnMessage);
    }

    private boolean checkInputs() {
        if(txtNumber.getText().toString().trim().isEmpty()) {
            poMessage = "Please enter recipient mobile number";
            return false;
        }

        if(txtSmsMsg.getText().toString().trim().isEmpty()) {
            poMessage = "Please enter a message";
            return false;
        }

        return true;
    }

    private void sendSMS() {
        if(checkInputs()) {
            try {
                SmsManager poSmsMngr = SmsManager.getDefault();
                poSmsMngr.sendTextMessage(txtNumber.getText().toString().trim(), null, txtSmsMsg.getText().toString().trim(), null, null);

                Toast.makeText(Activity_Compose.this, "Message Sent", Toast.LENGTH_SHORT).show();
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(Activity_Compose.this, poMessage, Toast.LENGTH_SHORT).show();
        }
    }
}