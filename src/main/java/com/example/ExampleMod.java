package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.quest.QuestGenerator;
import com.example.quest.Quest;
import com.example.quest.PlayerQuestManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "modid";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // Event Entity Terbunuh untuk Tracker & Reward
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (entity instanceof PlayerEntity player) {
                Quest activeQuest = PlayerQuestManager.getQuest(player);
                
                if (activeQuest != null && !activeQuest.isComplete()) {
                    // Cek jika entity yang dibunuh sesuai dengan target quest
                    if (killedEntity.getType() == activeQuest.getTargetEntity()) {
                        activeQuest.addProgress(1);
                        
                        // Cek jika sudah selesai
                        if (activeQuest.isComplete()) {
                            // Berikan Reward
                            player.giveItemStack(activeQuest.getReward().copy());
                            // Hapus Quest dari manager
                            PlayerQuestManager.clearQuest(player);
                            // Kirim pesan ke pemain
                            player.sendMessage(Text.literal("Quest Selesai! Kamu mendapatkan reward."), false);
                        }
                    }
                }
            }
        });
    }
}