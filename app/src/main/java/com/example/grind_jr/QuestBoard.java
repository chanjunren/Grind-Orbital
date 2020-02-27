package com.example.grind_jr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestBoard extends FragmentActivity {

    private ImageView addQuestBtn;
    private ListView dailyQuestLv, mainQuestLv;
    private DatabaseReference dailyQuestDb, mainQuestDb;
    private String userId;
    private ArrayList<Quest> dailyQuests, mainQuests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quests_page);
        addQuestBtn = (ImageView) findViewById(R.id.addQuestButton);
        dailyQuestLv = (ListView) findViewById(R.id.dailyQuestLv);
        mainQuestLv = (ListView) findViewById(R.id.storyQuestLv);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dailyQuestDb = FirebaseDatabase.getInstance().getReference(userId).child("Quests").child("DAILY");
        mainQuestDb = FirebaseDatabase.getInstance().getReference(userId).child("Quests").child("MAIN");
        dailyQuests = new ArrayList<>();
        mainQuests = new ArrayList<>();

        addQuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        dailyQuestDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dailyQuests.clear();
                for (DataSnapshot dailyQuestSs: dataSnapshot.getChildren()) {
                    Quest quest = dailyQuestSs.getValue(Quest.class);
                    dailyQuests.add(quest);
                }
                QuestList adapter = new QuestList(QuestBoard.this, dailyQuests);
                dailyQuestLv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mainQuestDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mainQuests.clear();
                for (DataSnapshot mainQuestSs: dataSnapshot.getChildren()) {
                    Quest quest = mainQuestSs.getValue(Quest.class);
                    mainQuests.add(quest);
                }
                MainQuestList adapter = new MainQuestList(QuestBoard.this, mainQuests);
                mainQuestLv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openDialog() {
        AddQuestDialog dialog = new AddQuestDialog();
        dialog.show(getSupportFragmentManager(), "Add Quest Dialog");
    }
}