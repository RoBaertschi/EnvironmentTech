package robaertschi.environmenttech.data.recipes;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class ETRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<EnvCollectorRecipe>> ENV_COLLECTOR_RECIPE_TYPE = RECIPE_TYPES.register("env_collector", RecipeType::simple);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<EnvCollectorRecipe>> ENV_COLLECTOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("env_collector", EnvCollectorRecipeSerializer::new);

    public static void init(IEventBus modEventBus) {
        RECIPE_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
    }
}
