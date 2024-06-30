package robaertschi.environmenttech.compat.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import robaertschi.environmenttech.client.renderer.EnvStorageRenderer;
import robaertschi.environmenttech.data.recipes.EnvCollectorRecipe;

public class EnvCollectorEmiRecipe extends BasicEmiRecipe {
    private final EnvCollectorRecipe recipe;

    public EnvCollectorEmiRecipe(EnvCollectorRecipe recipe) {
        super(EmiCompatPlugin.ENV_COLLECTOR_CATEGORY, recipe.id(), 76, 30);
        this.inputs.add(EmiIngredient.of(recipe.input()));
        this.outputs.add(EmiStack.of(recipe.output()));
        this.recipe = recipe;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 26, 1);
        widgets.addText(Component.literal(recipe.envUsed() + " ENV"), 42, 20, EnvStorageRenderer.to, true);
        widgets.addSlot(inputs.getFirst(), 0, 0);
        widgets.addSlot(outputs.getFirst(), 58, 0).recipeContext(this);
    }
}
