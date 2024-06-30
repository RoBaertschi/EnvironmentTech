package robaertschi.environmenttech.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import net.minecraft.world.inventory.Slot;
import org.apache.commons.compress.utils.Lists;
import robaertschi.environmenttech.menu.EnvCollectorMenu;

import java.util.List;

public class EnvCollectorEmiRecipeHandler implements StandardRecipeHandler<EnvCollectorMenu> {
    @Override
    public List<Slot> getInputSources(EnvCollectorMenu handler) {
        List<Slot> list = Lists.newArrayList();
        list.add(handler.getSlot(0));
        int invStart = 2;
        for (int i = invStart; i < invStart + 36; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public List<Slot> getCraftingSlots(EnvCollectorMenu handler) {
        return List.of(handler.getSlot(0));
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe.getCategory() == EmiCompatPlugin.ENV_COLLECTOR_CATEGORY && recipe.supportsRecipeTree();
    }
}
