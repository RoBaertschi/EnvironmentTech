package robaertschi.environmenttech.data.recipes;

import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public record EnvCollectorRecipe(Ingredient input, ItemStack output, int envUsed) implements Recipe<Container> {
    @Override
    public boolean matches(@NotNull Container pContainer, @NotNull Level pLevel) {
        return this.input.test(pContainer.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container pCraftingContainer, HolderLookup.@NotNull Provider pRegistries) {
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
        @SuppressWarnings("FieldCanBeLocal")
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

            EnvCollectorRecipe envCollectorRecipe = new EnvCollectorRecipe(this.ingredient,  this.output, this.envUsed);

            pRecipeOutput.accept(pId, envCollectorRecipe, advancementBuilder.build(pId.withPrefix("recipes/")));
        }

        private void ensureValid(ResourceLocation pId) {
            if (this.criteria.isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + pId);
            }
        }
    }
}
