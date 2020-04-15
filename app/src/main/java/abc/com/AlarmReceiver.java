package abc.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("AlarmReceiver", "Received the Alarm Broadcast ");
        intent = new Intent(context, AlarmService.class);
        context.startService(intent);
    }

}
