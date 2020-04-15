package abc.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class CallingActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    private static final String TAG = "CallingActivity";
    Result resultObj;
    MakeObj mobj = new MakeObj();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);

        resultObj = mobj.makeObj();
        int x = getIntent().getIntExtra("id", -1);

        if (resultObj.getPower()) {
            if ((x == 1 && resultObj.getCurr()) || (x == 2 && !resultObj.getCurr()))
            {
                Log.d(TAG, "power is there but the motor is already in the required state");
            }

            else {

                Log.d(TAG, "Making the phone call ");

                String number = "+919373456377";

                if (ContextCompat.checkSelfPermission(CallingActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CallingActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                } else {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }

                CallingActivity.this.finish();
            }
        }

        else
        {
            Log.d(TAG, "There is no power, hence no call has been made");
        }
    }
}
