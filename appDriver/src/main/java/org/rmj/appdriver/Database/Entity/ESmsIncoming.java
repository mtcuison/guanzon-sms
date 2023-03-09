package org.rmj.appdriver.Database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SMS_Incoming")
public class ESmsIncoming {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "sTransnox")
    private int Transnox;
    @ColumnInfo(name = "dTransact")
    private String Transact = "";
    @ColumnInfo(name = "sSourceCd")
    private String SourceCd = "";
    @ColumnInfo(name = "sMessagex")
    private String Messagex = "";
    @ColumnInfo(name = "sMobileNo")
    private String MobileNo = "";
    @ColumnInfo(name = "cSubscrbr")
    private String Subscrbr = "";
    @ColumnInfo(name = "dFollowUp")
    private String FollowUp = "";
    @ColumnInfo(name = "nNoRetryx")
    private String NoRetryx = "";
    @ColumnInfo(name = "dReceived")
    private String Received = "";
    @ColumnInfo(name = "cRepliedx")
    private String Repliedx = "";
    @ColumnInfo(name = "dRepliedx")
    private String DateRpld = "";
    @ColumnInfo(name = "dPostedxx")
    private String Postedxx = "";
    @ColumnInfo(name = "cTranStat")
    private String TranStat = "";
    @ColumnInfo(name = "cSendStat")
    private String SendStat = "";
    @ColumnInfo(name = "dSendDate")
    private String SendDate = "";

    public ESmsIncoming() {
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getSourceCd() {
        return SourceCd;
    }

    public void setSourceCd(String sourceCd) {
        SourceCd = sourceCd;
    }

    public String getMessagex() {
        return Messagex;
    }

    public void setMessagex(String messagex) {
        Messagex = messagex;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getSubscrbr() {
        return Subscrbr;
    }

    public void setSubscrbr(String subscrbr) {
        Subscrbr = subscrbr;
    }

    public String getFollowUp() {
        return FollowUp;
    }

    public void setFollowUp(String followUp) {
        FollowUp = followUp;
    }

    public String getNoRetryx() {
        return NoRetryx;
    }

    public void setNoRetryx(String noRetryx) {
        NoRetryx = noRetryx;
    }

    public String getReceived() {
        return Received;
    }

    public void setReceived(String received) {
        Received = received;
    }

    public String getRepliedx() {
        return Repliedx;
    }

    public void setRepliedx(String repliedx) {
        Repliedx = repliedx;
    }

    public String getDateRpld() {
        return DateRpld;
    }

    public void setDateRpld(String dateRpld) {
        DateRpld = dateRpld;
    }

    public String getPostedxx() {
        return Postedxx;
    }

    public void setPostedxx(String postedxx) {
        Postedxx = postedxx;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getSendStat() {
        return SendStat;
    }

    public void setSendStat(String sendStat) {
        SendStat = sendStat;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String sendDate) {
        SendDate = sendDate;
    }

    public int getTransnox() {
        return Transnox;
    }

    public void setTransnox(int transnox) {
        Transnox = transnox;
    }
}
