package com.azizulhakim.todotogether;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimeSetPage extends InterfaceActivity{
    static final int DILOG_ID=0;
    int hrs,min;
    public static String result_time="";
    String mode;
    Calendar cal= Calendar.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hrs= cal.get(Calendar.HOUR_OF_DAY);
        min=cal.get(Calendar.MINUTE);
        showDialog(DILOG_ID);
    }
   protected Dialog onCreateDialog(int id)
   {
       if(id==DILOG_ID)
           return new TimePickerDialog(TimeSetPage.this,tpickerListner,hrs,min,false);
       return null;

   }
    private TimePickerDialog.OnTimeSetListener tpickerListner
            =new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hrs=hourOfDay;
            min=minute;
            //result_time="";
            if(hrs>12)
                mode="PM";
            else
                mode="AM";
            Toaster(hrs+" : "+min+" : "+mode);
            result_time=hrs+" : "+min+" : "+mode;
            for(int i=0;i<10000000;i++);
            finish();
        }
    };

}
