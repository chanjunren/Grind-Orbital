package com.example.grind_jr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddQuestDialog extends DialogFragment implements DialogInterface.OnDismissListener, AdapterView.OnItemSelectedListener{

    private String userId;
    private DatabaseReference questDatabase;
    private Quest currentQuest;
    private Spinner enumSpinner;
    private QuestEnum questEnum;

    public AddQuestDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        currentQuest = new Quest();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_quest_dialog, null);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        questDatabase = FirebaseDatabase.getInstance().getReference(userId).child("Quests");

        enumSpinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.quest_enums,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        enumSpinner.setAdapter(adapter);

        final TextView tvQuestDate = (TextView) view.findViewById(R.id.etQuestDate);
        final EditText tvQuestPurpose = (EditText) view.findViewById(R.id.etQuestPurpose);
        final EditText tvQuestAmount = (EditText) view.findViewById(R.id.etQuestAmt);

        enumSpinner.setOnItemSelectedListener(this);

        tvQuestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                CalendarDialog dialog = new CalendarDialog();
                dialog.setQuest(currentQuest);
                dialog.show(fm, "Idk man");
                fm.executePendingTransactions();
                dialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(currentQuest.getDate());
                        tvQuestDate.setText(Integer.valueOf(calendar.get(Calendar.DAY_OF_MONTH)).toString()
                        + " " + Integer.valueOf(calendar.get(Calendar.MONTH) + 1).toString() + " " +
                        Integer.valueOf(Calendar.YEAR).toString());
                    }
                });
            }
        });

        builder.setView(view);
        builder.setTitle("Creating A Quest...")
                .setNegativeButton("X", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("O", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        questDatabase = questDatabase.child(questEnum.toString());
                        String questId = questDatabase.push().getKey();
                        currentQuest.setAmount(Double.valueOf(tvQuestAmount.getText().toString()));
                        currentQuest.setPurpose(tvQuestPurpose.getText().toString());
                        currentQuest.setQuestId(questId);
                        currentQuest.setQuestEnum(questEnum);
                        questDatabase.child(questId).setValue(currentQuest);
                        Toast.makeText(getContext(), "Quest has been created!", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String enumString = parent.getItemAtPosition(position).toString();
        switch (enumString) {
            case "Daily": {
                questEnum = QuestEnum.DAILY;
                break;
            }
            case "Main": {
                questEnum = QuestEnum.MAIN;
                break;
            }
            default: {
                Toast.makeText(getContext(), "This shoulnd't be appearing", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}