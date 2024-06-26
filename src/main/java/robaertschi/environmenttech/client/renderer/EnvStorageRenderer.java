package robaertschi.environmenttech.client.renderer;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import robaertschi.environmenttech.data.capabilities.IEnvStorage;
import robaertschi.environmenttech.utils.MouseUtils;

import java.util.List;
import java.util.Optional;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  https://github.com/BluSunrize/ImmersiveEngineering/blob/1.19.2/LICENSE
 *
 *  Slightly Modified Version by: Kaupenjoe
 *  Modified by RoBaertschi to fit personal needs.
 */

/**
 * @author BluSunrize, Kaupenjoe, RoBaertschi
 * @since 0.1.0
 */
public class EnvStorageRenderer {
    public static final int from = FastColor.ARGB32.color(0, 103, 29);
    public static final int to = FastColor.ARGB32.color(0,191, 38);


    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final IEnvStorage storage;

    private final ContentBoxRenderer boxRenderer;

    public EnvStorageRenderer(int x, int y, int width, int height, IEnvStorage storage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.storage = storage;
        boxRenderer = new ContentBoxRenderer(x, y, width, height);
    }

    public EnvStorageRenderer(int x, int y, IEnvStorage storage) {
        this(x, y, 8, 64, storage);
    }

    public List<Component> getTooltips() {
        return List.of(Component.literal(storage.getEnvStored() + " / " + storage.getMaxEnv() + " ENV"));
    }

    public void render(GuiGraphics guiGraphics) {
        int stored = (int)(height * (storage.getEnvStored() / (float)storage.getMaxEnv()));

        boxRenderer.render(guiGraphics);

        guiGraphics.fillGradient(x, y + (height - stored), x + width, y + height,
                from, to);

    }

    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, int leftPos, int topPos, Font font) {
        if (isMouseAboveArea(mouseX, mouseY, x, y)) {
            guiGraphics.renderTooltip(font, getTooltips(), Optional.empty(), mouseX - leftPos, mouseY - topPos);
        }
    }


    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y) {
        return MouseUtils.isMouseOver(pMouseX, pMouseY, x, y, width, height);
    }
}
