package com.example.guanzonsms.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guanzonsms.Entity.ESmsInfo;
import com.example.guanzonsms.R;

import java.util.List;

public class SmsListAdapter extends RecyclerView.Adapter<SmsListAdapter.SmsHolder> {
    private final List<ESmsInfo> smsList;

    public SmsListAdapter(List<ESmsInfo> foSmsList) {
        this.smsList = foSmsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SmsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sms_item, parent, false);
        return new SmsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsHolder holder, int position) {
        ESmsInfo sms = smsList.get(position);
        holder.lsReceivr = sms.getReceiverNumber();
        holder.lblTimesx.setText(sms.getTimeStamp());
        holder.lblMobile.setText(sms.getSenderNumber());
        holder.lblTxtMsg.setText(sms.getTextMessage());
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    class SmsHolder extends RecyclerView.ViewHolder {
        private String lsReceivr = "";
        private TextView lblTimesx;
        private TextView lblMobile;
        private TextView lblTxtMsg;

        public SmsHolder(View itemView) {
            super(itemView);
            lblTimesx = itemView.findViewById(R.id.lbl_timestamp);
            lblMobile = itemView.findViewById(R.id.lbl_mobileno);
            lblTxtMsg = itemView.findViewById(R.id.lbl_txt_message);
        }

    }

}
