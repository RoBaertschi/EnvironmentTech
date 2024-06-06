package robaertschi.environmenttech.test;

import net.neoforged.testframework.conf.ClientConfiguration;
import net.neoforged.testframework.conf.FrameworkConfiguration;
import net.neoforged.testframework.impl.MutableTestFramework;
import org.lwjgl.glfw.GLFW;
import robaertschi.environmenttech.EnvironmentTech;

public class TestMain {
    final MutableTestFramework framework = FrameworkConfiguration.builder(EnvironmentTech.id("tests"))
            .clientConfiguration(() -> ClientConfiguration.builder()
                    .toggleOverlayKey(GLFW.GLFW_KEY_J)
                    .openManagerKey(GLFW.GLFW_KEY_N)
                    .build())

            .build().create();
}
