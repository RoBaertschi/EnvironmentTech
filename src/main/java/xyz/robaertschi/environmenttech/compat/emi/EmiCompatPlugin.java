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
package xyz.robaertschi.environmenttech.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import xyz.robaertschi.environmenttech.ET;
import xyz.robaertschi.environmenttech.data.recipes.ETRecipes;
import xyz.robaertschi.environmenttech.data.recipes.EnvCollectorRecipe;
import xyz.robaertschi.environmenttech.level.item.ETItems;
import xyz.robaertschi.environmenttech.menu.ETMenus;

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
