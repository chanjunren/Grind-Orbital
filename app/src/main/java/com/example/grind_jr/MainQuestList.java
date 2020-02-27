package com.example.grind_jr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainQuestList extends ArrayAdapter<Quest> {
    private Activity context;
    private List<Quest> quests;

    public MainQuestList(Activity context, List<Quest> q) {
        super(context, R.layout.main_quest_list_item_layout, q);
        this.context = context;
        quests = q;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.main_quest_list_item_layout, null, true);
        Quest quest = quests.get(position);
        TextView mainQuestPurposeTv = (TextView) listItemView.findViewById(R.id.mainQuestPurposeTv);
        TextView mainQuestProgressTv = (TextView) listItemView.findViewById(R.id.mainQuestProgressTv);
        ProgressBar progressBar = (ProgressBar) listItemView.findViewById(R.id.mainQuestPb);

        mainQuestPurposeTv.setText("- "  + quest.getPurpose());
        mainQuestProgressTv.setText(Double.valueOf(quest.getProgressAmount()).toString() + " / " +
                Double.valueOf(Double.valueOf(quest.getAmount()).toString()));
        progressBar.setMax((int) quest.getAmount());
        progressBar.setProgress((int) quest.getProgressAmount());
        return listItemView;
    }
}