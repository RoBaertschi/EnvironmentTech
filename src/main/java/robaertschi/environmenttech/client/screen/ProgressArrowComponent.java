package robaertschi.environmenttech.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import robaertschi.environmenttech.EnvironmentTech;

public class ProgressArrowComponent {
    public static final ResourceLocation SPRITE = EnvironmentTech.id("textures/gui/sprites/component/progress_arrow.png");

    public static void draw(GuiGraphics guiGraphics, int x, int y, int progress) {
        int j1 = Mth.ceil(progress * 22.0F);
        guiGraphics.blit(SPRITE, x, y, 1, 0, 0, 0, 0, 22, 16);
    }

    public static int getScaledProgress(int progress, int maxProgress) {
        int progressArrowSize = 24; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
}
