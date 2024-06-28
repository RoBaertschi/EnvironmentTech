/*
 *  EnvironmentTech  Copyright (C) 2024 Robin B??rtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
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
