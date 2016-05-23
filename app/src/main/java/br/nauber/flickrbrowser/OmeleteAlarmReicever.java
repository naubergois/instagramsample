package br.nauber.flickrbrowser;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by naubergois on 5/22/16.
 */
public class OmeleteAlarmReicever extends WakefulBroadcastReceiver {

    private AlarmManager alarmMgr;
    private final String LOG_TAG = "Alarm Receiver";

    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "Alarm Start");
        Intent service = new Intent(context, OmeleteSchedulingService.class);
        startWakefulService(context, service);

    }

    public void setAlarm(Context context){
        Log.d(LOG_TAG, "Set Alarm");
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, OmeleteAlarmReicever.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);


        alarmMgr.setInexactRepeating(AlarmManager.RTC,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_DAY, alarmIntent);

        ComponentName receiver = new ComponentName(context,OmeleteBootReicever.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }

    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, OmeleteBootReicever.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

}
