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

import javax.annotation.ParametersAreNonnullByDefault;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import org.jetbrains.annotations.NotNull;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import xyz.robaertschi.environmenttech.ET;
import xyz.robaertschi.environmenttech.data.recipes.ETRecipes;
import xyz.robaertschi.environmenttech.level.block.ETBlocks;
import xyz.robaertschi.environmenttech.level.block.entity.EnvCollectorBlockEntity;
import xyz.robaertschi.environmenttech.menu.ETMenus;
import xyz.robaertschi.environmenttech.menu.EnvCollectorMenu;

@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class JeiCompatPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ET.id("jei_default");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new EnvCollectorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        assert Minecraft.getInstance().level != null;
        registration.addRecipes(EnvCollectorRecipeCategory.ENV_COLLECTOR, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ETRecipes.ENV_COLLECTOR_RECIPE_TYPE.get()).stream().toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ETBlocks.ENV_COLLECTOR_BLOCK), EnvCollectorRecipeCategory.ENV_COLLECTOR);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(EnvCollectorMenu.class, ETMenus.ENV_COLLECTOR_MENU.get(), EnvCollectorRecipeCategory.ENV_COLLECTOR,
                EnvCollectorBlockEntity.SLOT_INPUT,
                EnvCollectorBlockEntity.SLOT_INPUT_COUNT,
                EnvCollectorBlockEntity.SLOT_COUNT,
                Inventory.INVENTORY_SIZE);
    }
}
