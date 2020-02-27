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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddIncome extends DialogFragment implements AdapterView.OnItemClickListener{

    private TextView freqTextView;
    private EditText amountText, sourceText;
    private DatabaseReference incomeDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private SeekBar freqSeekBar;
    private String userId;
    private Spinner incomeEnumSpinner;
    private SpriteCategory spriteCat = SpriteCategory.SALARY;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_sprite, null);
        amountText = (EditText) view.findViewById(R.id.newSpriteAmount);
        sourceText = (EditText) view.findViewById(R.id.newSpriteSource);
        freqSeekBar = (SeekBar) view.findViewById(R.id.freqSeekBar);
        freqTextView = (TextView) view.findViewById(R.id.frequencyLabelTextView);
        incomeEnumSpinner = (Spinner) view.findViewById(R.id.spriteCatSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.income_cats,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeEnumSpinner.setAdapter(adapter);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        incomeDatabaseReference = FirebaseDatabase.getInstance().getReference(userId).child("Incomes");
        firebaseAuth = FirebaseAuth.getInstance();

        freqSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0:
                        freqTextView.setText("Once");
                        break;
                    case 1:
                        freqTextView.setText("Daily");
                        break;
                    case 2:
                        freqTextView.setText("Weekly");
                        break;
                    case 3:
                        freqTextView.setText("Monthly");
                        break;
                    default:
                        Toast.makeText(getContext(), "This shouldn't be appearing", Toast.LENGTH_SHORT).show();
                        break;
                }
            }



            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        builder.setView(view);
        builder.setTitle("Adding income...")
                .setNegativeButton("X", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("O", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addIncomeSprite();
                    }
                });


        return builder.create();
    }


    public void addIncomeSprite() {
        incomeDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = incomeDatabaseReference.push().getKey();
                String source = sourceText.getText().toString().trim();
                int amount = Integer.valueOf(amountText.getText().toString().trim());
                int frequency = freqSeekBar.getProgress();
                long count = dataSnapshot.getChildrenCount();
                incomeDatabaseReference.child(Long.toString(count)).setValue(new Sprite(amount, source, id, frequency, spriteCat));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        switch (selected) {
            case "Salary": {
                spriteCat = SpriteCategory.SALARY;
                break;
            }
            case "Investment": {
                spriteCat = SpriteCategory.INVESTMENTS;
                break;
            }
            case "Pocket Money": {
                spriteCat = SpriteCategory.POCKET_MONEY;
                break;
            }
            case "Others": {
                spriteCat = SpriteCategory.OTHERS;
                break;
            } default: {
                Toast.makeText(getContext(), "This shouldn't appear", Toast.LENGTH_SHORT).show();
            }
        }
    }
}