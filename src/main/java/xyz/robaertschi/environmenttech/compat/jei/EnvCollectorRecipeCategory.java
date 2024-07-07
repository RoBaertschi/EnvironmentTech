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
package xyz.robaertschi.environmenttech.compat.jei;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import xyz.robaertschi.environmenttech.client.renderer.EnvStorageRenderer;
import xyz.robaertschi.environmenttech.client.screen.EnvCollectorScreen;
import xyz.robaertschi.environmenttech.data.recipes.ETRecipes;
import xyz.robaertschi.environmenttech.data.recipes.EnvCollectorRecipe;
import xyz.robaertschi.environmenttech.level.block.ETBlocks;
import xyz.robaertschi.environmenttech.utils.MouseUtils;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EnvCollectorRecipeCategory implements IRecipeCategory<RecipeHolder<EnvCollectorRecipe>> {
    private final IDrawable background;
    private final IDrawable icon;

    public static final RecipeType<RecipeHolder<EnvCollectorRecipe>> ENV_COLLECTOR = RecipeType.createFromVanilla(ETRecipes.ENV_COLLECTOR_RECIPE_TYPE.get());

    public EnvCollectorRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(EnvCollectorScreen.GUI, 48, 18, 110, 50);
        icon = guiHelper.createDrawableItemStack(new ItemStack(ETBlocks.ENV_COLLECTOR_BLOCK));
    }

    @Override
    public RecipeType<RecipeHolder<EnvCollectorRecipe>> getRecipeType() {
        return ENV_COLLECTOR;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("screen.environmenttech.env_collector");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<EnvCollectorRecipe> recipeHolder, IFocusGroup focuses) {
        EnvCollectorRecipe recipe = recipeHolder.value();

        builder.addSlot(RecipeIngredientRole.INPUT, 6, 16).addIngredients(recipe.input());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 68, 17).addItemStack(recipe.output());

    }

    @Override
    public void draw(RecipeHolder<EnvCollectorRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

        EnvStorageRenderer.render(guiGraphics, recipe.value().envUsed(), 64, getWidth() - 10, 2, 8, 45);
    }

    @Override
    public List<Component> getTooltipStrings(RecipeHolder<EnvCollectorRecipe> recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> components = new ArrayList<>();

        int x = getWidth() - 10;
        if (MouseUtils.isMouseOver(Mth.floor(mouseX), Mth.floor(mouseY), x, 2, 8, 45)) {
            components.add(Component.literal(recipe.value().envUsed() + " / " + 64 + " ENV"));
        }

        return components;
    }


}
