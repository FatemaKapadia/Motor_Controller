package abc.com;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TimePicker;


public class ListFragment extends Fragment
{
    ListView lv;
    static boolean bset=false, eset=false;
    static long choice;

    public static final String TAG = "ListFragment";

    SearchView searchView;
    ArrayAdapter<String> adapter;
    String[] data = {"SET BEGIN TIME", "SET END TIME", "SET FREQUENCY"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_list, container, false);
        lv = (ListView) view.findViewById(R.id.idListView);
        adapter = new ArrayAdapter<String>( getActivity(), android.R.layout.simple_list_item_1, data);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                choice =id;
                Log.d(TAG, "onItemClick: "+id);

                if( id == 0 )
                {
                    bset=true;
                    callTimePicker();
                }

                else if( id == 1 )
                {
                    eset=true;
                    callTimePicker();
                }

                else if( id == 2 )
                    setFrequency();
            }
        });
        return view;
    }

    public void callTimePicker()
    {
        //Log.d(TAG, "callTimePicker: in the method");
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getChildFragmentManager(), "time picker");
    }

    public void setFrequency()
    {
        startActivity(new Intent(getActivity(), SetFrequency.class));
    }

}
