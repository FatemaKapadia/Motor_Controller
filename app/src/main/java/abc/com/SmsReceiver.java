package abc.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class SmsReceiver extends BroadcastReceiver
{
    public static final String TAG = "SmsReceiver";
    public static final String pdu_type = "pdus";
    private static MessageListener mListener;
    MainActivity maobj = new MainActivity();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent )
    {
        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");

        for (int i = 0; i < pdus.length; i++)
        {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String message = smsMessage.getMessageBody();
            mListener.messageReceived(message);
            Log.d(TAG, "onReceive: " + message);
        }
    }

    public static void bindListener(MessageListener listener)
    {
        mListener = listener;
    }
}
