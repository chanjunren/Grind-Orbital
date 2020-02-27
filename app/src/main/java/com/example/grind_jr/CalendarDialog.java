package com.example.grind_jr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarDialog extends DialogFragment {

    private Quest quest;
    private CalendarView calendarView;
    private Calendar calendar;
    private Long tempDate;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.calendar_layout, null);
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        tempDate = calendarView.getDate();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView v, int year, int month, int dayOfMonth) {
                Long epoch = (new Date(year, month, dayOfMonth)).getTime();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Toast.makeText(getContext(), formatter.format(epoch), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view)
                .setNegativeButton("X", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("O", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quest.setDate(tempDate);
                    }
                });
        return builder.create();
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }
}