package com.example.homeworkthree;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmFragment extends Fragment {
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView selectedTime, selectedDate;
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alarm_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button timePicker = view.findViewById(R.id.time_picker);
        Button datePicker = view.findViewById(R.id.date_picker);
        Button saveAlarm = view.findViewById(R.id.save_alarm);
        selectedTime = view.findViewById(R.id.picked_time);
        selectedDate = view.findViewById(R.id.picked_date);
        mNotificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);

        datePicker.setOnClickListener((v) -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        selectedDate.setText(date);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        timePicker.setOnClickListener((v) -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view12, hourOfDay, minute) -> {
                        String time = hourOfDay + ":" + minute;
                        selectedTime.setText(time);
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        });

        saveAlarm.setOnClickListener((v) -> {
            Intent notifyIntent = new Intent(getContext(), AlarmReceiver.class);
            notifyIntent.putExtra("title", String.valueOf(getArguments().get("title")));
            PendingIntent notifyPendingIntent = PendingIntent.getBroadcast (getContext(), NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

            if (alarmManager != null) {
                TextView selectedTime = view.findViewById(R.id.picked_time);
                String selectedTimeValue = (String) selectedTime.getText();
                TextView selectedDate = view.findViewById(R.id.picked_date);
                String selectedDateValue = (String) selectedDate.getText();
                String desiredDate = selectedDateValue + " " + selectedTimeValue;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy HH:mm");
                try {
                    Date mDate = simpleDateFormat.parse(desiredDate);
                    long triggerTime = mDate.getTime();
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, notifyPendingIntent);
                    Toast.makeText(getContext(), "Created alarm!", Toast.LENGTH_SHORT).show();

                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment alarmFragment = fragmentManager.findFragmentByTag("alarmFragment");
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(alarmFragment);
                    fragmentTransaction.commit();
                } catch (ParseException e) {
                    Log.e("ParseError","Error parsing: " + e.getMessage());
                    Toast.makeText(getContext(), "Failed to create alarm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createNotificationChannel();
    }

    public void createNotificationChannel() {
        mNotificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel (PRIMARY_CHANNEL_ID, "Stand up notification", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Description");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
