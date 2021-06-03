package com.example.assignment_final.MyReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            String state= bundle.getString( TelephonyManager.EXTRA_STATE );
            if(state.equals( TelephonyManager.EXTRA_STATE_RINGING ));
                String phone= bundle.getString( TelephonyManager.EXTRA_INCOMING_NUMBER );
            Toast.makeText(context, phone, Toast.LENGTH_LONG).show();
        }
    }
}
