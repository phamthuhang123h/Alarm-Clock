package com.example.alarmclock;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity
{
    Button btnTimer,btnTurnOff,btn_next;
    TextView txtHienThi,month,year,day;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    List<AlarmReceiver> list_item;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_item = new List<AlarmReceiver>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<AlarmReceiver> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(AlarmReceiver alarmReceiver) {
                return false;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends AlarmReceiver> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends AlarmReceiver> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean equals(@Nullable Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public AlarmReceiver get(int index) {
                return null;
            }

            @Override
            public AlarmReceiver set(int index, AlarmReceiver element) {
                return null;
            }

            @Override
            public void add(int index, AlarmReceiver element) {

            }

            @Override
            public AlarmReceiver remove(int index) {
                return null;
            }

            @Override
            public int indexOf(@Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<AlarmReceiver> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<AlarmReceiver> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<AlarmReceiver> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
//        list_item = (ListView) findViewById(R.id.list_item);
        btnTimer = (Button) findViewById(R.id.btnTimer);
        btnTurnOff = (Button) findViewById(R.id.btnTurnOff);
        txtHienThi = (TextView) findViewById(R.id.textView);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        day = (TextView) findViewById(R.id.day);
        month = (TextView) findViewById(R.id.month);
        year = (TextView) findViewById(R.id.year);
        btn_next = (Button) findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, StopWatchActivity.class);
                startActivity(intent1);
            }
        });

        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        String[] splitDate = formattedDate.split(",");

        Log.d("myLOG", currentTime.toString());
        Log.d("myLOG", formattedDate);

        day.setText(splitDate[0]);
        month.setText(splitDate[1]);
        year.setText(splitDate[2]);
        Log.d("myLOG", splitDate[0].trim());
        Log.d("myLOG", splitDate[1].trim());
        Log.d("myLOG", splitDate[2].trim());

        final Intent intent = new Intent(MainActivity.this,AlarmReceiver.class);

        btnTimer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());

                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();

                String string_gio = String.valueOf(gio);
                String string_phut = String.valueOf(phut);

                if (phut<10)
                {
                    string_phut = "0" + String.valueOf(phut);
                }

                intent.putExtra("extra", "on");
                alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

                pendingIntent = PendingIntent.getBroadcast(
                        MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT
                );
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
                txtHienThi.setText("The Alarm Time You Have Set Is: " + string_gio + ":" + string_phut);
                showAlertDialog("Successful Timer!");
            }
        });

        btnTurnOff.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                txtHienThi.setText("Turn Off");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","off");
                sendBroadcast(intent);
                showAlertDialog("Turn Off The Alarm!");
            }
        });
    }

    public void showAlertDialog(String message)
    {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alarm")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
    }
}