/*
 *  EnvironmentTech  Copyright (C) 2024 Robin B??rtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.client.screen;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import robaertschi.environmenttech.EnvironmentTech;
import robaertschi.environmenttech.client.renderer.EnvStorageRenderer;
import robaertschi.environmenttech.menu.EnvCollectorMenu;

public class EnvCollectorScreen extends AbstractContainerScreen<EnvCollectorMenu> {
    private final ResourceLocation GUI = EnvironmentTech.id("textures/gui/container/env_collector.png");
    private EnvStorageRenderer storageRenderer;

    public EnvCollectorScreen(EnvCollectorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        storageRenderer = new EnvStorageRenderer(leftPos + imageWidth - 18, topPos + 11, menu.getBlockEntity().getEnvStorage());
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        pGuiGraphics.blit(ProgressArrowUtils.SPRITE,
                relX + 78, relY + 35,
                0, 0,
                ProgressArrowUtils.getScaledProgress(menu.getProgress(), menu.getMaxProgress()),16,
                24,
                16
        );

        storageRenderer.render(pGuiGraphics);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        storageRenderer.renderTooltip(pGuiGraphics, pMouseX, pMouseY, leftPos, topPos, font);
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

}