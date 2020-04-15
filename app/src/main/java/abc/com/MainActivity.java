package abc.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.OnNmeaMessageListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MessageListener
{

    private static final int REQUEST_CALL = 1;
    private static final int READ_SMS_PERMISSIONS_REQUEST = 2;
    private static final String TAG = "MyActivity";

    private Button button;
    private boolean curr, power;
    private MakeObj mobj=new MakeObj();
    Result obj;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.push_button);

        getPermissionToCall();
        getPermissionToReadSMS();

        SmsReceiver.bindListener(this);

        accessSMS();
        setOnOff();
    }

    //Reading Messages
    public void accessSMS()
    {
        Log.d(TAG, "Inside accessSMS: ");
        ReadMessage rmobj = new ReadMessage();
        MakeObj.msg = rmobj.getAllSms(MainActivity.this);
    }

    //Setting value to the On Off Button
    public void setOnOff( )
    {
        Log.d(TAG, "inside setOnOff: ");

        obj = mobj.makeObj();

        curr = obj.getCurr();
        power = obj.getPower();

        CharSequence on="ON";
        CharSequence off="OFF";
        CharSequence disabled="NO POWER";

        if( power )
        {
            if (curr)
                button.setText(off);

            else
                button.setText(on);
        }

        else
        {
            button.setText(disabled);
        }
    }

    //Taking permissions
    public void getPermissionToReadSMS()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_SMS},
                    READ_SMS_PERMISSIONS_REQUEST);
        }
    }

    public void getPermissionToCall()
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults)
    {
        switch( requestCode )
        {
            case REQUEST_CALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Call Permission granted", Toast.LENGTH_SHORT).show();
                    makePhoneCall();
                }

                else
                {
                    Toast.makeText(this, "Call Permission DENIED", Toast.LENGTH_SHORT).show();
                }
                break;

            case READ_SMS_PERMISSIONS_REQUEST:
                if (grantResults.length == 1 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
                    //refreshSmsInbox();
                }

                else
                {
                    Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("This app cannot function without the permission to read and send sms.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }
                    });
                    builder.setNegativeButton("Try again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getPermissionToReadSMS();
                        }
                    });
                    builder.create().show();
                }
                break;
        }
    }

    //Making the call
    public void phoneCall(View view)
    {
        if ( power )
        {
            makePhoneCall();
        }

        else
        {
            Toast.makeText(this, "Power is currently off and you cannot switch on or off the motor", Toast.LENGTH_SHORT).show();
        }
    }


    public void makePhoneCall()
    {

        Log.d(TAG, "Making the phone call ");


        String number = "+919373456377";

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else
        {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    //Current Status button
    public void status( View view )
    {
        Log.d(TAG, "clicked the status button ");
        Intent intent = new Intent(MainActivity.this, Status.class);

        startActivity( intent );
    }

    //Receives messages at runtime

    @Override
    public void messageReceived(String message)
    {
        ReadRuntimeMessage rdrtmmsg = new ReadRuntimeMessage();
        Log.d(TAG, "messageReceived: I've received the message");
        Toast.makeText(this, "New Message Received: " + message, Toast.LENGTH_SHORT).show();
        rdrtmmsg.receiveMsg(message);
    }

    //Schedule Alarm button

    public void scheduleAlarm(View view){
        Log.d(TAG, "in scheduleAlarm: ");

        Intent intent = new Intent(MainActivity.this, SetAlarm.class);
        startActivity( intent );
    }

}


