package xyz.robaertschi.environmenttech.utils;

/**
 * This file is from <a href="https://github.com/Tutorials-By-Kaupenjoe/Forge-Tutorial-1.19/">here</a>.
 * So all credits go to Kaupenjoe. The code is under the following license: <a href="https://github.com/Tutorials-By-Kaupenjoe/Forge-Tutorial-1.19/blob/main/LICENSE">here</a>
 * @author Kaupenjoe
 */

public class MouseUtils {
    @SuppressWarnings("unused")
    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y) {
        return isMouseOver(mouseX, mouseY, x, y, 16);
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int size) {
        return isMouseOver(mouseX, mouseY, x, y, size, size);
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int sizeX, int sizeY) {
        return (mouseX >= x && mouseX <= x + sizeX) && (mouseY >= y && mouseY <= y + sizeY);
    }
}
