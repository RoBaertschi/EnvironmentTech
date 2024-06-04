package robaertschi.environmenttech.datagen;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import robaertschi.environmenttech.data.recipes.EnvCollectorRecipe;
import robaertschi.environmenttech.level.item.ETItems;

import java.util.concurrent.CompletableFuture;

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
    }
}
