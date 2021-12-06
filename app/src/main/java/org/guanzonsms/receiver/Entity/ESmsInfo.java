package org.guanzonsms.receiver.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "smsInfo")
public class ESmsInfo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "nSmsIdxxx")
    private int id;

    @ColumnInfo(name = "sSenderNo")
    private String senderNumber;

    @ColumnInfo(name = "sReceivNo")
    private String receiverNumber;

    @ColumnInfo(name = "sTextMsgx")
    private String textMessage;

    @ColumnInfo(name = "sTimestmp")
    private String timeStamp;

    public ESmsInfo() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

}
