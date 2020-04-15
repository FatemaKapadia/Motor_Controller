package abc.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SetFrequency extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;

    public static final String TAG = "setfrequency";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_frequency);

        radioGroup = findViewById(R.id.radioGroup);

    }

    public void checkButton(View v)
    {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        BeginAndEnd.choice = radioButton.getText();

        Log.d(TAG, "checkButton: "+BeginAndEnd.choice);
    }
}
