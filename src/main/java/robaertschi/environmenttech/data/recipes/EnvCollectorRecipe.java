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
package robaertschi.environmenttech.data.recipes;

import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record EnvCollectorRecipe(ResourceLocation id, Ingredient input, ItemStack output, int envUsed) implements Recipe<RecipeInput> {
    @Override
    public boolean matches(@NotNull RecipeInput pContainer, @NotNull Level pLevel) {
        return this.input.test(pContainer.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput pCraftingContainer, HolderLookup.@NotNull Provider pRegistries) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider pRegistries) {
        return this.output;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ETRecipes.ENV_COLLECTOR_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ETRecipes.ENV_COLLECTOR_RECIPE_TYPE.get();
    }

    @RequiredArgsConstructor
    public static class Builder implements RecipeBuilder {
        private final Ingredient ingredient;
        private final ItemStack output;
        private final int envUsed;

        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        // Currently we just ignore the warning, maybe we will use it later, maybe never.
        @SuppressWarnings({"FieldCanBeLocal", "unused"})
        @Nullable
        private String group;


        @Override
        public @NotNull RecipeBuilder group(@Nullable String pGroupName) {
            this.group = pGroupName;
            return this;
        }

        @Override
        public @NotNull Item getResult() {
            return this.output.getItem();
        }

        public @NotNull Builder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
            this.criteria.put(name, criterion);
            return this;
        }

        @Override
        public void save(RecipeOutput pRecipeOutput, @NotNull ResourceLocation pId) {
            this.ensureValid(pId);

            Advancement.Builder advancementBuilder = pRecipeOutput.advancement()
                            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                                    .rewards(AdvancementRewards.Builder.recipe(pId))
                                            .requirements(AdvancementRequirements.Strategy.OR);

            this.criteria.forEach(advancementBuilder::addCriterion);

            EnvCollectorRecipe envCollectorRecipe = new EnvCollectorRecipe(pId, this.ingredient,  this.output, this.envUsed);

            pRecipeOutput.accept(pId, envCollectorRecipe, advancementBuilder.build(pId.withPrefix("recipes/")));
        }

        private void ensureValid(ResourceLocation pId) {
            if (this.criteria.isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + pId);
            }
        }
    }
}
