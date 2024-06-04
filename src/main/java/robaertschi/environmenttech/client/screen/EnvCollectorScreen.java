package robaertschi.environmenttech.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import robaertschi.environmenttech.EnvironmentTech;
import robaertschi.environmenttech.menu.EnvCollectorMenu;

public class EnvCollectorScreen extends AbstractContainerScreen<EnvCollectorMenu> {
    private final ResourceLocation GUI = EnvironmentTech.id("textures/gui/container/env_collector.png");

    public EnvCollectorScreen(EnvCollectorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
//        this.inventoryLabelY = this.imageHeight - 110;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
