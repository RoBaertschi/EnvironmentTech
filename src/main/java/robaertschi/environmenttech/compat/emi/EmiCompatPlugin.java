/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import robaertschi.environmenttech.ET;
import robaertschi.environmenttech.data.recipes.ETRecipes;
import robaertschi.environmenttech.data.recipes.EnvCollectorRecipe;
import robaertschi.environmenttech.level.item.ETItems;
import robaertschi.environmenttech.menu.ETMenus;

@EmiEntrypoint
public class EmiCompatPlugin implements EmiPlugin {
    public static final ResourceLocation ENV_COLLECTOR_SHEET = ET.id("textures/gui/container/env_collector.png");
    public static final EmiStack ENV_COLLECTOR = EmiStack.of(ETItems.ENV_COLLECTOR_BLOCK_ITEM);
    public static final EmiRecipeCategory ENV_COLLECTOR_CATEGORY = new EmiRecipeCategory(ET.id("env_collector"), ENV_COLLECTOR, new EmiTexture(ENV_COLLECTOR_SHEET,48, 18, 110, 50));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(ENV_COLLECTOR_CATEGORY);
        registry.addWorkstation(ENV_COLLECTOR_CATEGORY, ENV_COLLECTOR);

        RecipeManager manager = registry.getRecipeManager();

        for (RecipeHolder<EnvCollectorRecipe> recipe : manager.getAllRecipesFor(ETRecipes.ENV_COLLECTOR_RECIPE_TYPE.get())) {
            registry.addRecipe(new EnvCollectorEmiRecipe(recipe.value()));
        }

        registry.addRecipeHandler(ETMenus.ENV_COLLECTOR_MENU.get(), new EnvCollectorEmiRecipeHandler());

    }

}
