package br.nauber.flickrbrowser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by naubergois on 5/22/16.
 */
public class OmeleteBootReicever extends BroadcastReceiver {

    OmeleteAlarmReicever alarmReicever=new OmeleteAlarmReicever();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            alarmReicever.setAlarm(context);
        }

    }
}
