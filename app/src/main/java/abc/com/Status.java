package abc.com;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;

public class Status extends AppCompatActivity
{
    ReadMessage rdmsg;
    private boolean curr, power;
    private static final String TAG = "MyActivity";
    long hours, minutes;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        TextView currStatus = findViewById(R.id.textView);
        TextView addInfo = findViewById(R.id.textView2);
        calculateTime();

        MakeObj mobj = new MakeObj();

        Result obj = mobj.makeObj();

        assert obj != null;
        curr = obj.getCurr();
        power = obj.getPower();
        rdmsg = new ReadMessage();

        if( curr && power )
        {
            calculateTime();
            currStatus.setText("The Motor is currently ON");
            addInfo.setText("It has been on for "+hours+":"+minutes);
        }

        else if( curr== false && power)
        {
            currStatus.setText("The Motor is currently OFF");
            addInfo.setText("");
        }

        else if(curr && !power)
        {
            calculateTime();
            currStatus.setText("There is NO POWER and the motor was ON before the power cut");
            addInfo.setText("The motor ran for "+hours+":"+minutes+" before the power cut");
        }

        else
        {
            currStatus.setText("There is NO POWER and the motor was OFF before the power cut");
            addInfo.setText("");
        }
    }

    private void calculateTime()
    {
        if( curr && power ) {
            long time = rdmsg.date;
            long diff = System.currentTimeMillis() - time;

            Log.d(TAG, "calculateTime: "+ diff + " received at = "+time);

            hours = diff / (1000 * 60 * 60);
            minutes = diff % (1000 * 60 * 60) / (1000 * 60);

            Date d1 = new Date(time);

            Log.d(TAG, "calculateTime: " + d1);

            Log.d(TAG, "calculateTime: " + hours + " " + minutes);
        }

        else if(curr && !power)
        {
            long diff = rdmsg.date - rdmsg.dateprev;

            hours = diff / (1000 * 60 * 60);
            minutes = diff % (1000 * 60 * 60) / (1000 * 60);

        }
    }
}
