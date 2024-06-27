package robaertschi.environmenttech;

import net.minecraft.resources.ResourceLocation;

/**
 * Static methods and members for faster and easier access.
 */
public class ET {
    public static final String MODID = "environmenttech";


    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
