package abc.com;

import android.util.Log;

public class ReadRuntimeMessage
{
    ReadMessage rdmsg= new ReadMessage();
    MainActivity objMain = new MainActivity();
    public static final String TAG = "ReadRuntimeMessage";
    MainActivity maobj = new MainActivity();
    MakeObj moobj = new MakeObj();

    public void receiveMsg( String msg )
    {
        Log.d(TAG, "I have received the message and can now use it");

        MakeObj.msg = msg;

        maobj.setOnOff();
    }
}
