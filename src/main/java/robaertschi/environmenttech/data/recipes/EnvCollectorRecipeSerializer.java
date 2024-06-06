package robaertschi.environmenttech.data.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class EnvCollectorRecipeSerializer implements RecipeSerializer<EnvCollectorRecipe> {
    public static final MapCodec<EnvCollectorRecipe> CODEC = RecordCodecBuilder.mapCodec(
            envCollectorRecipeInstance -> envCollectorRecipeInstance.group(
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
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(byteBuf);
        ItemStack itemStack = ItemStack.STREAM_CODEC.decode(byteBuf);
        int envUsed = byteBuf.readInt();
        return new EnvCollectorRecipe(ingredient, itemStack, envUsed);
    }

    private void toNetwork(RegistryFriendlyByteBuf byteBuf, EnvCollectorRecipe recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(byteBuf, recipe.input());
        ItemStack.STREAM_CODEC.encode(byteBuf, recipe.output());
        byteBuf.writeInt(recipe.envUsed());
    }
}