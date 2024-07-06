/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.datagen;

import java.util.concurrent.CompletableFuture;

import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import robaertschi.environmenttech.ET;
import robaertschi.environmenttech.data.recipes.EnvCollectorRecipe;
import robaertschi.environmenttech.level.item.ETItems;

public class ETRecipeProvider extends RecipeProvider {
    public ETRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        new EnvCollectorRecipe.Builder(
                Ingredient.of(Items.IRON_INGOT),
                new ItemStack(ETItems.ENVIRONMENTAL_ESSENCE_ITEM.get()),
                10
        ).unlockedBy(
                        "iron_ingot",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT)
                ).unlockedBy("env_collector", InventoryChangeTrigger.TriggerInstance.hasItems(ETItems.ENV_COLLECTOR_BLOCK_ITEM))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ETItems.GLASS_TANK)
                .define('g', Tags.Items.GLASS_PANES)
                .define('i', Tags.Items.INGOTS_IRON)
                .pattern("ggg")
                .pattern("gig")
                .pattern("ggg")
                .unlockedBy("iron_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build()))
                .unlockedBy("glass_pain", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(Tags.Items.GLASS_PANES).build()))
                .save(recipeOutput, ET.id("glass_tank"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ETItems.ENV_COLLECTOR_BLOCK_ITEM)
                .define('p', Items.OAK_PLANKS)
                .define('l', Items.OAK_LOG)
                .define('h', Items.HOPPER)
                .pattern("lhl")
                .pattern("l l")
                .pattern("ppp")
                .unlockedBy("oak_plank", InventoryChangeTrigger.TriggerInstance.hasItems(Items.OAK_PLANKS))
                .unlockedBy("oak_log", InventoryChangeTrigger.TriggerInstance.hasItems(Items.OAK_LOG))
                .unlockedBy("hopper", InventoryChangeTrigger.TriggerInstance.hasItems(Items.HOPPER))
                .save(recipeOutput, ET.id("env_collector_block_item"));


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ETItems.ENV_DISTRIBUTOR_BLOCK_ITEM)
                .define('i', Items.IRON_INGOT)
                .define('p', Items.OAK_PLANKS)
                .define('g', ETItems.GLASS_TANK)
                .define('e', ETItems.ENVIRONMENTAL_ESSENCE_ITEM)
                .pattern("iei")
                .pattern("ggg")
                .pattern("ppp")
                .unlockedBy("oak_planks", InventoryChangeTrigger.TriggerInstance.hasItems(Items.OAK_PLANKS))
                .unlockedBy("iron_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .unlockedBy("glass_tank", InventoryChangeTrigger.TriggerInstance.hasItems(ETItems.GLASS_TANK))
                .unlockedBy("environmental_essence", InventoryChangeTrigger.TriggerInstance.hasItems(ETItems.ENVIRONMENTAL_ESSENCE_ITEM))
                .save(recipeOutput, ET.id("env_distributor_block_item"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ETItems.ENV_DETECTOR_ITEM)
                .define('t', ETItems.GLASS_TANK)
                .define('s', Tags.Items.RODS_WOODEN)
                .define('h', Items.HOPPER)
                .pattern("h")
                .pattern("t")
                .pattern("s")
                .unlockedBy("glass_tank", InventoryChangeTrigger.TriggerInstance.hasItems(ETItems.GLASS_TANK))
                .unlockedBy("hopper", InventoryChangeTrigger.TriggerInstance.hasItems(Items.HOPPER))
                .unlockedBy("stick", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(Tags.Items.RODS_WOODEN).build()))
                .save(recipeOutput, ET.id("env_detector_item"));

    }
}
