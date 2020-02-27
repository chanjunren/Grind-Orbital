package com.example.grind_jr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class IncomeHabitat extends Activity  {

    ImageView incomeNpc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_page);
        incomeNpc = (ImageView) findViewById(R.id.incomeNpc);

        incomeNpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IncomeHabitat.this, IncomeNpc.class));
            }
        });
    }
}
