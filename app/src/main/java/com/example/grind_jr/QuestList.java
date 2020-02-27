package com.example.grind_jr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class QuestList extends ArrayAdapter<Quest> {
    private Activity context;
    private List<Quest> quests;

    public QuestList(Activity context, List<Quest> q) {
        super(context, R.layout.quest_list_item_layout, q);
        this.context = context;
        quests = q;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.quest_list_item_layout, null, true);
        Quest quest = quests.get(position);

        TextView questPurposeTv = (TextView) listItemView.findViewById(R.id.questPurposeTv);
        TextView questDetailsTv = (TextView) listItemView.findViewById(R.id.questDetailsTv);

        questPurposeTv.setText("- " + quest.getPurpose());
        questDetailsTv.setText("Save $ " + Double.valueOf(quest.getAmount()).toString() + " " +
                quest.getQuestEnum().toString().toLowerCase() + "!");

        return listItemView;
    }
}
