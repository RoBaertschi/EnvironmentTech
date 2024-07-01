/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
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
