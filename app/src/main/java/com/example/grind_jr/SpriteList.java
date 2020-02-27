package com.example.grind_jr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpriteList extends ArrayAdapter<Sprite> {
    private Activity context;
    private List<Sprite> sprites;

    public SpriteList(Activity context, List<Sprite> sprites) {
        super(context, R.layout.income_list_item_layout, sprites);
        this.context = context;
        this.sprites = sprites;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.income_list_item_layout, null, true);
        Sprite sprite = sprites.get(position);
        TextView spriteAmount = (TextView) listItemView.findViewById(R.id.sprite_amount);
        TextView spriteSource = (TextView) listItemView.findViewById(R.id.sprite_source);
        TextView spriteFreq = (TextView) listItemView.findViewById(R.id.sprite_frequency);

        spriteAmount.setText("$ " + String.valueOf(sprite.getAmount()));
        spriteSource.setText(sprite.getSource());
        switch (sprite.getFreq()) {
            case 0: {
                spriteFreq.setText("One - time");
                break;
            }
            case 1: {
                spriteFreq.setText("Daily");
                break;
            }
            case 2: {
                spriteFreq.setText("Weekly");
                break;
            }
            case 3: {
                spriteFreq.setText("Monthly");
                break;
            }
            default: {
                spriteFreq.setText("This shouldn't happen");
                break;
            }
        }
        return listItemView;
    }
}
