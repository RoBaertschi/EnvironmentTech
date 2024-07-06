/*
 *  EnvironmentTech MC Mod
    Copyright (C) 2024 Robin Bärtschi and Contributors

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, by version 3 of the License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package robaertschi.environmenttech.client.screen;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import robaertschi.environmenttech.ET;
import robaertschi.environmenttech.client.renderer.EnvStorageRenderer;
import robaertschi.environmenttech.menu.EnvCollectorMenu;

public class EnvCollectorScreen extends AbstractContainerScreen<EnvCollectorMenu> {
    public static final ResourceLocation GUI = ET.id("textures/gui/container/env_collector.png");
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
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

}