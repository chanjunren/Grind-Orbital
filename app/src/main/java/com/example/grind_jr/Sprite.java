package com.example.grind_jr;

public class Sprite {
    private int amount;
    private String source;
    private String spriteId;
    private int freq;
    private SpriteCategory spriteCat;

    public int getFreq() {
        return freq;
    }

    public int getAmount() {
        return amount;
    }

    public String getSource() {
        return source;
    }

    public String getSpriteId() {
        return spriteId;
    }

    public SpriteCategory getSpriteCat() {
        return spriteCat;
    }

    public Sprite(int amount, String source, String spriteId, int freq, SpriteCategory spriteEnum) {
        this.amount = amount;
        this.source = source;
        this.spriteId = spriteId;
        this.freq = freq;
        this.spriteCat = spriteEnum;
    }

    public Sprite updateAmount(int a) {
        return new Sprite(a, source, spriteId, freq, spriteCat);
    }

    public Sprite() {
    }
}