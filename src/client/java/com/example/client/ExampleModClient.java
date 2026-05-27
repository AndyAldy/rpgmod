package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import com.example.quest.PlayerQuestManager;
import com.example.quest.Quest;
import com.example.quest.QuestGenerator;
import com.example.client.gui.QuestScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;

public class ExampleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Event Klik Kanan Villager
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient() && player.isSneaking()) {
                // Check if entity is a Villager (basic check by entity type name)
                if (entity.getType().toString().contains("villager")) {
                    // Jangan paksa buka UI jika player sudah punya quest
                    if (PlayerQuestManager.getQuest(player) == null) {
                        Quest newQuest = QuestGenerator.generateRandomQuest();
                        MinecraftClient.getInstance().setScreen(new QuestScreen(newQuest));
                        return ActionResult.SUCCESS;
                    }
                }
            }
            return ActionResult.PASS;
        });

        // HUD Rendering untuk Quest Tracker
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                Quest activeQuest = PlayerQuestManager.getQuest(client.player);
                if (activeQuest != null) {
                    // Gambar Tracker di Kanan Layar
                    int screenWidth = context.getScaledWindowWidth();
                    int startY = 20;

                    context.drawTextWithShadow(client.textRenderer, "=== Active Quest ===", screenWidth - 120, startY, 0xFFAA00);
                    context.drawTextWithShadow(client.textRenderer, activeQuest.getDifficulty().name(), screenWidth - 120, startY + 10, 0xFF5555);
                    
                    // Contoh teks: "Zombie Kills: 5/20"
                    String progressText = activeQuest.getTargetEntity().getName().getString() + " Kills: " + 
                                          activeQuest.getCurrentAmount() + "/" + activeQuest.getTargetAmount();
                    
                    int color = activeQuest.isComplete() ? 0x55FF55 : 0xFFFFFF;
                    context.drawTextWithShadow(client.textRenderer, progressText, screenWidth - 120, startY + 20, color);
                }
            }
        });
    }
}