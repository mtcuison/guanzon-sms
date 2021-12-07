package org.guanzonsms.receiver.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.guanzongroup.smsAppDriver.Database.ESmsIncoming;
import com.example.guanzonsms.R;

import java.util.List;

public class SmsListAdapter extends RecyclerView.Adapter<SmsListAdapter.SmsHolder> {
    private final List<ESmsIncoming> smsList;

    public SmsListAdapter(List<ESmsIncoming> foSmsList) {
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
        ESmsIncoming sms = smsList.get(position);
        holder.lsReceivr = "0";
        holder.lblTimesx.setText(sms.getSendDate());
        holder.lblMobile.setText(sms.getMobileNo());
        holder.lblTxtMsg.setText(sms.getMessagex());
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
