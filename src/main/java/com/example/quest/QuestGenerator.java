package com.example.quest;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import java.util.Random;

public class QuestGenerator {
    private static final Random RANDOM = new Random();

    public static Quest generateRandomQuest() {
        int rand = RANDOM.nextInt(100);
        Quest.Difficulty difficulty;
        
        // RNG Peluang: Mudah 40%, Sedang 30%, Sulit 20%, Mustahil 10%
        if (rand < 40) difficulty = Quest.Difficulty.MUDAH;
        else if (rand < 70) difficulty = Quest.Difficulty.SEDANG;
        else if (rand < 90) difficulty = Quest.Difficulty.SULIT;
        else difficulty = Quest.Difficulty.MUSTAHIL;

        return generateQuestByDifficulty(difficulty);
    }

    private static Quest generateQuestByDifficulty(Quest.Difficulty difficulty) {
        String desc;
        EntityType<?> target;
        int amount;
        ItemStack reward;

        switch (difficulty) {
            case MUDAH:
                target = EntityType.ZOMBIE;
                amount = 10 + RANDOM.nextInt(11); // 10-20
                desc = "Bunuh " + amount + " Zombie di sekitar Village";
                // Reward makanan biasa (bukan gapple)
                reward = new ItemStack(RANDOM.nextBoolean() ? Items.BREAD : Items.COOKED_BEEF, 16);
                break;
                
            case SEDANG:
                // Logika Illager Outpost
                target = RANDOM.nextBoolean() ? EntityType.PILLAGER : EntityType.VINDICATOR;
                amount = 5 + RANDOM.nextInt(6); // 5-10
                desc = "Habisi " + amount + " " + target.getName().getString() + " di Outpost terdekat!";
                // Reward Iron armor/weapons
                Item[] ironGear = {Items.IRON_SWORD, Items.SHIELD, Items.IRON_CHESTPLATE};
                reward = new ItemStack(ironGear[RANDOM.nextInt(ironGear.length)]);
                break;
                
            case SULIT:
                target = EntityType.ENDERMAN;
                amount = 15;
                desc = "Kalahkan 15 Enderman.";
                // Reward 2 Netherite, 10 Diamond, atau 1 Netherite Armor
                int rewardType = RANDOM.nextInt(3);
                if (rewardType == 0) reward = new ItemStack(Items.NETHERITE_INGOT, 2);
                else if (rewardType == 1) reward = new ItemStack(Items.DIAMOND, 10);
                else {
                    Item[] netheriteArmor = {Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS};
                    reward = new ItemStack(netheriteArmor[RANDOM.nextInt(netheriteArmor.length)]);
                }
                break;
                
            case MUSTAHIL:
            default:
                target = EntityType.WARDEN;
                amount = 1;
                desc = "Buktikan kekuatanmu! Bunuh 1 Warden.";
                // Reward OP
                reward = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 5); 
                break;
        }
        
        return new Quest(desc, difficulty, target, amount, reward);
    }
}