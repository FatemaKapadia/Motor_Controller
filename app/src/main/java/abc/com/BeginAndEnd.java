package abc.com;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

import static abc.com.TimePickerFragment.bhour;
import static abc.com.TimePickerFragment.bminute;
import static abc.com.TimePickerFragment.ehour;
import static abc.com.TimePickerFragment.eminute;

public class BeginAndEnd extends AppCompatActivity
{
    public static final String TAG = "SettingTime";
    private static int hdur, mdur;
    public static CharSequence choice;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_and_end);

        Button button = findViewById(R.id.button1);

        choice = "Only Once";

        button.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v)
            {
                if (ListFragment.bset && ListFragment.eset)
                {
                    Calendar c1 = makeObj(bhour, bminute);
                    Calendar c2 = makeObj(ehour, eminute);

                    addByOne( c1, c2 );

                    setAlarm(c1, 1);
                    setAlarm(c2, 2);

                    AlertDialog.Builder builder = new AlertDialog.Builder(BeginAndEnd.this);
                    builder.setTitle("The motor will run for "+ hdur +":"+mdur+ " "+ choice);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BeginAndEnd.this.finish();
                        }
                    });
                    builder.create().show();
                }
                else
                {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BeginAndEnd.this);
                        builder.setTitle("You need to set the Start time and the End time");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.create().show();

                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Calendar makeObj(int hour, int minute)
    {
        Calendar now = Calendar.getInstance();
        Calendar c = Calendar.getInstance();

        Log.d(TAG, "makeObj: "+ hour + " " +  minute );
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        return c;
    }

    public void addByOne( Calendar c1, Calendar c2 )
    {
        Calendar now = Calendar.getInstance();

        if( c1.before(now) )
        {
            Log.d(TAG, "inside addByOne");
            c1.add( Calendar.DAY_OF_MONTH, 1);
            c2.add( Calendar.DAY_OF_MONTH, 1);
        }

        if( c2.before(c1) )
        {
            Log.d(TAG, "inside addByOne");
            c1.add( Calendar.DAY_OF_MONTH, 1 );

            if( bhour == ehour )
            {
                hdur = 23;
                mdur = eminute + 60 - bminute;
            }

            else
            {
                if( eminute < bminute )
                {
                    hdur = ehour + 24 - bhour - 1;
                    mdur = eminute + 60 - bminute;
                }

                else
                {
                    hdur = ehour + 24 - bhour;
                    mdur = eminute - bminute;
                }
            }
        }

        else
        {
            if( eminute < bminute )
            {
                hdur = ehour - bhour - 1;
                mdur = eminute + 60 - bminute;
            }

            else
            {
                hdur = ehour - bhour;
                mdur = eminute - bminute;
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setAlarm(Calendar c, int temp)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent( this, AlarmService.class);
        intent.putExtra("id", temp);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, temp, intent, 0);

        Log.d(TAG, "setAlarm: "+ temp);

        if( choice.toString().equals("Only Once"))
        {
            assert alarmManager != null;
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            Log.d(TAG, "frequency is once only");
        }

        else if( choice.toString().equals("Everyday"))
        {
            assert alarmManager != null;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),1000 * 60 * 60 * 24 , pendingIntent);
            Log.d(TAG, "frequency is everyday");
        }
    }
}
