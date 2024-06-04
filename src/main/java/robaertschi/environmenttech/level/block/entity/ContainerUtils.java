package robaertschi.environmenttech.level.block.entity;

import net.minecraft.world.Container;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public class ContainerUtils {
    public static Container itemHandlerToContainer(IItemHandlerModifiable itemHandler) {
        return new RecipeWrapper(itemHandler);
    }
}
