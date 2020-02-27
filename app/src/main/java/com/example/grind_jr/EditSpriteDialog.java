package com.example.grind_jr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditSpriteDialog extends DialogFragment {

    private EditText updateAmtEditText;
    private Sprite sprite;
    private boolean isIncome;
    private String userId;

    public EditSpriteDialog() {
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setAsIncome() {
        this.isIncome = true;
    }

    public void setAsExpenditure() {
        this.isIncome = false;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_sprite_dialog, null);
        updateAmtEditText = view.findViewById(R.id.updateAmtEditText);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        builder.setView(view)
                .setTitle("Edit Sprite (Leave blank if no change)")
                .setNegativeButton("Delete Sprite", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSprite(sprite.getSpriteId());
                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double amount;
                        try {
                            amount = Double.valueOf(updateAmtEditText.getText().toString());
                            DatabaseReference databaseReference;
                            if (isIncome) {
                                databaseReference = FirebaseDatabase.getInstance()
                                        .getReference(userId).child("Incomes").child(sprite.getSpriteId());
                                Toast.makeText(getContext(), "Sprite successfully updated step 1!", Toast.LENGTH_SHORT).show();

                            } else {
                                databaseReference = FirebaseDatabase.getInstance()
                                        .getReference(userId).child("Expenditures").child(sprite.getSpriteId());
                                Toast.makeText(getContext(), "Sprite successfully updated step 1a !", Toast.LENGTH_SHORT).show();

                            }
                            databaseReference.setValue(sprite.updateAmount((int) amount));
                            Toast.makeText(getContext(), "Sprite successfully updated!", Toast.LENGTH_SHORT).show();
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        return builder.create();
    }
    private void deleteSprite(String spriteId) {
        DatabaseReference databaseReference;
        if (isIncome) {
            databaseReference = FirebaseDatabase.getInstance().getReference(userId).child("Incomes")
                    .child(spriteId);
            databaseReference.removeValue();
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference(userId).child("Expenditures")
                    .child(spriteId);
            databaseReference.removeValue();
        }
    }
}