package abc.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class SetAlarm extends AppCompatActivity {
    public static final String TAG = "AlarmSet";
    Result resultObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

    }

    public void setAlarm(View view) {

        Intent intent = new Intent(SetAlarm.this, BeginAndEnd.class);
        startActivity( intent );
    }

}
