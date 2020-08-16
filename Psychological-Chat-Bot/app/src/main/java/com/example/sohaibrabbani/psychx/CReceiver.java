package com.example.sohaibrabbani.psychx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

/**
 * Created by sohaibrabbani on 1/5/2018.
 */

public class CReceiver extends BroadcastReceiver {

    LoginActivity mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] msgs= Telephony.Sms.Intents.getMessagesFromIntent(intent);
        String phone = msgs[0].getOriginatingAddress();
        String body = msgs[0].getMessageBody();
//        mContext.input.setText(body);
    }
}
