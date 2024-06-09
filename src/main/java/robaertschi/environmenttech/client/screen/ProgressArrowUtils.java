package robaertschi.environmenttech.client.screen;

import net.minecraft.resources.ResourceLocation;
import robaertschi.environmenttech.EnvironmentTech;

public class ProgressArrowUtils {
    public static final ResourceLocation SPRITE = EnvironmentTech.id("textures/gui/sprites/component/progress_arrow.png");

    public static int getScaledProgress(int progress, int maxProgress) {
        int progressArrowSize = 24; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
}
