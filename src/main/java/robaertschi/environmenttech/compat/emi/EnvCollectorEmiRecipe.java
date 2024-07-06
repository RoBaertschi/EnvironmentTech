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
