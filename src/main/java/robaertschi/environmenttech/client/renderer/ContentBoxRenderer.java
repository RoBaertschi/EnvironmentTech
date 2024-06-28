/*
 *  EnvironmentTech  Copyright (C) 2024 Robin B??rtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.client.renderer;

import lombok.AllArgsConstructor;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FastColor;

/**
 * @author RoBaertschi
 *
 * A ContentBoxRender that renders a box with the typical MC colors.
 * @implNote The x, y, width and height are for the content, that means, the border is drawn around these values.
 */
@AllArgsConstructor
public class ContentBoxRenderer {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public void render(GuiGraphics guiGraphics) {
        int cornerAndBackground = FastColor.ARGB32.color(139, 139, 139);
        int dark = FastColor.ARGB32.color(55, 55, 55);
        int bright = FastColor.ARGB32.color(255, 255, 255);
        guiGraphics.fill(x - 1, y - 1, x + width + 1, y + height + 1, cornerAndBackground);
        guiGraphics.fill(x - 1, y - 1, x + width, y, dark);
        guiGraphics.fill(x - 1, y - 1, x, y + height, dark);
        guiGraphics.fill(x + width, y + height, x + width + 1, y, bright);
        guiGraphics.fill(x, y + height, x + width + 1, y + height + 1, bright);
    }
}
