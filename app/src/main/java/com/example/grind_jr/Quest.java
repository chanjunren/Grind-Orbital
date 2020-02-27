package com.example.grind_jr;

import java.util.Date;
import java.util.Optional;

public class Quest {

    // Variable to store date: int vs Date object
    private long date;
    private double amount, progressAmount;
    private String purpose;
    private String questId;
    private QuestEnum questEnum = QuestEnum.INVALID;
    private boolean isCompleted;


    public Quest(long date, double amount, String purpose, String questId, QuestEnum questEnum) {
        this.date = date;
        this.amount = amount;
        this.purpose = purpose;
        this.questId = questId;
        this.questEnum = questEnum;
        isCompleted = false;
    }

    public Quest() {
        progressAmount = 500;
    }

    public long getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public QuestEnum getQuestEnum() {
        return questEnum;
    }

    public String getQuestId() {
        return questId;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public void setQuestEnum(QuestEnum questEnum) {
        this.questEnum = questEnum;
    }

    public double getProgressAmount() {
        return progressAmount;
    }
}