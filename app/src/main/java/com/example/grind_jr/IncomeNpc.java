package com.example.grind_jr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IncomeNpc extends FragmentActivity implements View.OnClickListener {
    private Button deleteButton;
    private DatabaseReference incomeDatabase;
    private ImageView addSpriteBtn;
    private ArrayList<Sprite> incomes;
    private ListView incomeListView;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.npc_layout);
        addSpriteBtn = (ImageView) findViewById(R.id.imageView6);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        incomeDatabase = FirebaseDatabase.getInstance().getReference(userId).child("Incomes");
        incomes = new ArrayList<>();
        incomeListView = (ListView) findViewById(R.id.sprites_list);
        addSpriteBtn.setOnClickListener(this);
        incomeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                openDialog(incomes.get(position));
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        incomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                incomes.clear();
                for (DataSnapshot incomeSnapshot: dataSnapshot.getChildren()) {
                    Sprite income = incomeSnapshot.getValue(Sprite.class);
                    incomes.add(income);
                }
                SpriteList adapter = new SpriteList(IncomeNpc.this, incomes);
                incomeListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openDialog(Sprite sprite) {
        EditSpriteDialog dialog = new EditSpriteDialog();
        dialog.setSprite(sprite);
        dialog.setAsIncome();
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void onClick(View v) {
        if (v == addSpriteBtn) {
            AddIncome dialog = new AddIncome();
            dialog.show(getSupportFragmentManager(), "Add Quest Dialog");
        }
    }
}