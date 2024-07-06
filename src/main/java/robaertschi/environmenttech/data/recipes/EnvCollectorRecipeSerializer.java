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

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class EnvCollectorRecipeSerializer implements RecipeSerializer<EnvCollectorRecipe> {
    public static final MapCodec<EnvCollectorRecipe> CODEC = RecordCodecBuilder.mapCodec(
            envCollectorRecipeInstance -> envCollectorRecipeInstance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(EnvCollectorRecipe::id),
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(EnvCollectorRecipe::input),
                    ItemStack.CODEC.fieldOf("output").forGetter(EnvCollectorRecipe::output),
                    Codec.INT.fieldOf("envUsed").forGetter(EnvCollectorRecipe::envUsed)
            ).apply(envCollectorRecipeInstance, EnvCollectorRecipe::new)
    );
    public final StreamCodec<RegistryFriendlyByteBuf, EnvCollectorRecipe> streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);

    @Override
    public @NotNull MapCodec<EnvCollectorRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, EnvCollectorRecipe> streamCodec() {
        return streamCodec;
    }

    private EnvCollectorRecipe fromNetwork(RegistryFriendlyByteBuf byteBuf) {
        ResourceLocation id = ResourceLocation.STREAM_CODEC.decode(byteBuf);
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(byteBuf);
        ItemStack itemStack = ItemStack.STREAM_CODEC.decode(byteBuf);
        int envUsed = byteBuf.readInt();
        return new EnvCollectorRecipe(id, ingredient, itemStack, envUsed);
    }

    private void toNetwork(RegistryFriendlyByteBuf byteBuf, EnvCollectorRecipe recipe) {
        ResourceLocation.STREAM_CODEC.encode(byteBuf, recipe.id());
        Ingredient.CONTENTS_STREAM_CODEC.encode(byteBuf, recipe.input());
        ItemStack.STREAM_CODEC.encode(byteBuf, recipe.output());
        byteBuf.writeInt(recipe.envUsed());
    }
}
