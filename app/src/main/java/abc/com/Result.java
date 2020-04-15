package abc.com;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Result {
    private boolean curr, power;
    public static String TAG = "Result";

    public Result()
    {
        curr=true;
        power=true;
    }

    public void setCurr(int code) {

        if (code == 1) {
            curr = true;
            power = true;
        } else if (code == 2) {
            curr = false;
            power = true;
        }

    }

    public void setPower(int code) {
        if (code == 41) {
            power = false;
        } else if (code == 31) {
            power = true;
        }
    }

    public boolean getCurr() {
        return curr;
    }

    public boolean getPower() {
        return power;
    }

}

