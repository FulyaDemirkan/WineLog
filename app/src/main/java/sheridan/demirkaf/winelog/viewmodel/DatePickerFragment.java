package sheridan.demirkaf.winelog.viewmodel;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String DATE_KEY = "dateKey";

    public interface DateSetListener{
        void onDateSet(int year, int month, int dayOfMonth);
    }

    DateSetListener mDateSetListener;

    public DatePickerFragment() {}

    public static DatePickerFragment getInstance(Date date){
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(DATE_KEY, date);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date;
        Bundle arguments = getArguments();
        if(arguments != null){
            date = (Date) arguments.getSerializable(DATE_KEY);
        }else{
            date = new Date();
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        if(mDateSetListener != null){
            mDateSetListener.onDateSet(year, month, day);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof DateSetListener){
            mDateSetListener = (DateSetListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDateSetListener = null;
    }
}
