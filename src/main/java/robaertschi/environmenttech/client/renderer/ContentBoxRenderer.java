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
