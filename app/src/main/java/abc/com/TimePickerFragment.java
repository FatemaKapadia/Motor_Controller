package abc.com;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
{
    ListFragment lfobj = new ListFragment();
    public static final String TAG = "TimePickerFragment";
    public static int bhour, bminute, ehour, eminute;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                if( lfobj.choice ==  0)
                {
                    bhour = hourOfDay;
                    bminute = minute;
                    Log.d(TAG, "onTimeSet: begin hour and begin minutes " + bhour + " " + bminute);
                }

                else if( lfobj.choice == 1 )
                {
                    ehour = hourOfDay;
                    eminute = minute;
                    Log.d(TAG, "onTimeSet: end hour and end minutes " + ehour + " " + eminute);
                }
            }
        }, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}
