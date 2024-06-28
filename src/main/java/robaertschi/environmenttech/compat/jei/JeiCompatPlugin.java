/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.compat.jei;

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

import robaertschi.environmenttech.ET;
import robaertschi.environmenttech.data.recipes.ETRecipes;
import robaertschi.environmenttech.level.block.ETBlocks;
import robaertschi.environmenttech.level.block.entity.EnvCollectorBlockEntity;
import robaertschi.environmenttech.menu.ETMenus;
import robaertschi.environmenttech.menu.EnvCollectorMenu;

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
