package com.example.quest;

import net.minecraft.entity.player.PlayerEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerQuestManager {
    // Map untuk menyimpan quest aktif per player berdasarkan UUID
    public static final Map<UUID, Quest> ACTIVE_QUESTS = new HashMap<>();

    public static void setQuest(PlayerEntity player, Quest quest) {
        ACTIVE_QUESTS.put(player.getUuid(), quest);
    }

    public static Quest getQuest(PlayerEntity player) {
        return ACTIVE_QUESTS.get(player.getUuid());
    }

    public static void clearQuest(PlayerEntity player) {
        ACTIVE_QUESTS.remove(player.getUuid());
    }
}