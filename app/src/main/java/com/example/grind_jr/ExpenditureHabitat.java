package com.example.grind_jr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ExpenditureHabitat extends Activity  {

    ImageView expenditureNpc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenditure_page);
        expenditureNpc = (ImageView) findViewById(R.id.expenditureNpc);

        expenditureNpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpenditureHabitat.this, ExpenditureNpc.class));
            }
        });
    }
}