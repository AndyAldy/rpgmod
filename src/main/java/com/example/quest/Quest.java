package com.example.quest;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;

public class Quest {
    public enum Difficulty {
        MUDAH, SEDANG, SULIT, MUSTAHIL
    }

    private final String description;
    private final Difficulty difficulty;
    private final EntityType<?> targetEntity;
    private final int targetAmount;
    private int currentAmount;
    private final ItemStack reward;

    public Quest(String description, Difficulty difficulty, EntityType<?> targetEntity, int targetAmount, ItemStack reward) {
        this.description = description;
        this.difficulty = difficulty;
        this.targetEntity = targetEntity;
        this.targetAmount = targetAmount;
        this.reward = reward;
        this.currentAmount = 0;
    }

    public String getDescription() { return description; }
    public Difficulty getDifficulty() { return difficulty; }
    public EntityType<?> getTargetEntity() { return targetEntity; }
    public int getTargetAmount() { return targetAmount; }
    public int getCurrentAmount() { return currentAmount; }
    public ItemStack getReward() { return reward; }
    
    public void addProgress(int amount) {
        this.currentAmount = Math.min(this.currentAmount + amount, this.targetAmount);
    }
    
    public boolean isComplete() {
        return this.currentAmount >= this.targetAmount;
    }
}