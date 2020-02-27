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

public class ExpenditureNpc extends FragmentActivity implements View.OnClickListener {
    private DatabaseReference expenditureDatabase;
    private ImageView addSpriteBtn;
    private ArrayList<Sprite> expenditures;
    private ListView expenditureListView;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.npc_layout);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        addSpriteBtn = (ImageView) findViewById(R.id.imageView6);
        expenditureDatabase = FirebaseDatabase.getInstance().getReference(userId).child("Expenditures");
        expenditures = new ArrayList<>();
        expenditureListView = (ListView) findViewById(R.id.sprites_list);
        addSpriteBtn.setOnClickListener(this);
        expenditureListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                openDialog(expenditures.get(position));
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        expenditureDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenditures.clear();
                for (DataSnapshot expenditureSnapshot: dataSnapshot.getChildren()) {
                    Sprite expenditure = expenditureSnapshot.getValue(Sprite.class);
                    expenditures.add(expenditure);
                }
                SpriteList adapter = new SpriteList(ExpenditureNpc.this, expenditures);
                expenditureListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openDialog(Sprite sprite) {
        EditSpriteDialog dialog = new EditSpriteDialog();
        dialog.setSprite(sprite);
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void onClick(View v) {
        if (v == addSpriteBtn) {
            AddExpenditure dialog = new AddExpenditure();
            dialog.show(getSupportFragmentManager(), "Add Expenditure Dialog");        }
    }
}