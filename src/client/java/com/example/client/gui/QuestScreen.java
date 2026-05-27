package com.example.client.gui;

import com.example.quest.Quest;
import com.example.quest.PlayerQuestManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

public class QuestScreen extends Screen {
    private final Quest offeredQuest;

    public QuestScreen(Quest quest) {
        super(Text.literal("Villager Quest"));
        this.offeredQuest = quest;
    }

    @Override
    protected void init() {
        int btnWidth = 100;
        int btnHeight = 20;
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Tombol Accept
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Accept"), button -> {
            if (MinecraftClient.getInstance().player != null) {
                PlayerQuestManager.setQuest(MinecraftClient.getInstance().player, offeredQuest);
            }
            this.close();
        }).dimensions(centerX - 110, centerY + 30, btnWidth, btnHeight).build());

        // Tombol Decline
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Decline"), button -> {
            this.close();
        }).dimensions(centerX + 10, centerY + 30, btnWidth, btnHeight).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta); // Background gelap layar
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        context.drawCenteredTextWithShadow(this.textRenderer, "New Quest Available!", centerX, centerY - 60, 0xFFD700);
        context.drawCenteredTextWithShadow(this.textRenderer, offeredQuest.getDescription(), centerX, centerY - 40, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "Difficulty: " + offeredQuest.getDifficulty().name(), centerX, centerY - 20, 0xFF5555);
        context.drawCenteredTextWithShadow(this.textRenderer, "Reward: " + offeredQuest.getReward().getName().getString(), centerX, centerY, 0x55FF55);

        super.render(context, mouseX, mouseY, delta);
    }
}