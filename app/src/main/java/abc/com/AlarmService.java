package abc.com;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;


public class AlarmService extends IntentService {

    public static final String TAG = "AlarmService";
    Result resultObj;

    public AlarmService() {
        super("MyServiceName");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        resultObj =  intent.getParcelableExtra("Object of the result class");

        Log.d( TAG , "About to execute MyTask");

        Intent dialogIntent = new Intent(this, CallingActivity.class);
        dialogIntent.putExtra("id", intent.getIntExtra("id", -1));
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

}