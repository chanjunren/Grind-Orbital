package com.example.grind_jr;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Summary extends Activity {
    private String userId;
    private DatabaseReference incomeDbRef, expDbRef;

    private ArrayList<Sprite> incomes, expenditures;

    private ArrayList<Color> incomePieColors = new ArrayList<>();
    private ArrayList<Color> expPieColors = new ArrayList<>();

    private double iSalary = 0;
    private double iInvestments = 0;
    private double iPocketMoney = 0;
    private double iOthers = 0;

    private double eTransport = 0;
    private double eFood = 0;
    private double eBills = 0;
    private double eEntertainment = 0;
    private double eOthers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_page);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        incomeDbRef = FirebaseDatabase.getInstance().getReference(userId).child("Incomes");
        expDbRef = FirebaseDatabase.getInstance().getReference(userId).child("Expenditures");

        incomes = new ArrayList<>();
        expenditures = new ArrayList<>();

        double totalInc = iSalary + iInvestments + iPocketMoney +iOthers;
        double totalExp = eBills + eEntertainment + eFood + eTransport + eOthers;
        Toast.makeText(Summary.this, "Total income: " + Double.valueOf(totalInc).toString() + " total Exp: "
                + Double.valueOf(totalExp).toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        incomeDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                incomes.clear();
                for (DataSnapshot incomeSnapshot: dataSnapshot.getChildren()) {
                    Sprite income = incomeSnapshot.getValue(Sprite.class);
                    incomes.add(income);
                }

                for (Sprite incomeSprite: incomes) {
                    SpriteCategory tempEnum = incomeSprite.getSpriteCat();
                    if (tempEnum == SpriteCategory.SALARY) {
                        iSalary += incomeSprite.getAmount();
                    } else if (tempEnum == SpriteCategory.POCKET_MONEY) {
                        iPocketMoney += incomeSprite.getAmount();
                    } else if (tempEnum == SpriteCategory.INVESTMENTS) {
                        iInvestments += incomeSprite.getAmount();
                    } else if (tempEnum == SpriteCategory.OTHERS) {
                        iOthers += incomeSprite.getAmount();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        expDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenditures.clear();
                for (DataSnapshot expSnapshot: dataSnapshot.getChildren()) {
                    Sprite exp = expSnapshot.getValue(Sprite.class);
                    expenditures.add(exp);
                }

                for (Sprite expenditureSprite: expenditures) {
                    SpriteCategory tempEnum = expenditureSprite.getSpriteCat();
                    if (tempEnum == SpriteCategory.TRANSPORT) {
                        eTransport += expenditureSprite.getAmount();
                    } else if (tempEnum == SpriteCategory.FOOD) {
                        eFood += expenditureSprite.getAmount();
                    } else if (tempEnum == SpriteCategory.BILLS) {
                        eBills += expenditureSprite.getAmount();
                    } else if (tempEnum == SpriteCategory.OTHERS) {
                        eOthers += expenditureSprite.getAmount();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
