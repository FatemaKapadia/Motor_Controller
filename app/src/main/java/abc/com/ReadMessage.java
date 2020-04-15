package abc.com;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ReadMessage
{

    private static final String TAG = "MyActivity";
    public static long date, dateprev;
    MainActivity maobj = new MainActivity();
    MakeObj moobj = new MakeObj();

    public String getAllSms(Context context)
    {
        Log.d(TAG, "getAllSms: entered");
        List<Sms> lstSms = new ArrayList<Sms>();
        Sms objSms;
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = context.getContentResolver();

        //

        Cursor c = cr.query(message, null, "address='+919373456377'" , null,"date desc");
        assert c != null;
        int totalSMS=c.getCount();

        if (c.moveToFirst())
        {
            Log.d(TAG, " "+totalSMS);
            for (int i = 0; i < totalSMS; i++)
            {
                objSms = new Sms();

                //Log.d(TAG, "i= "+i);

                /*if(c.getString(c.getColumnIndexOrThrow("address")) != null && c.getString(c.getColumnIndexOrThrow("address")).equals("+919373456377"))
                {*/
                //Log.d(TAG, "inside condition ");
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));

                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1"))
                {
                    objSms.setFolderName("inbox");
                }
                else
                {
                    objSms.setFolderName("sent");
                }

                lstSms.add(objSms);
                //}
                c.moveToNext();
            }
        }
        else
        {
            throw new RuntimeException("You have no SMS");
        }
        c.close();

        return receiveList(lstSms);
    }

    public String receiveList(List<Sms> lst)
    {
        Log.d(TAG, "list size"+lst.size());

        Sms objSms = lst.get(0);
        Sms objSms2 = lst.get(1);

        String msg = objSms.getMsg();
        String temp;

        temp = objSms.getTime();
        date = Long.parseLong(temp);

        temp = objSms2.getTime();
        dateprev = Long.parseLong(temp);

        Log.d(TAG, "first message is "+msg);
        Log.d(TAG, "first message received at "+date);

        return msg;
    }
}
