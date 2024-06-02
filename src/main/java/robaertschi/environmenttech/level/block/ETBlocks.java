package robaertschi.environmenttech.level.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class ETBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "environmenttech" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Creates a new Block with the id "environmenttech:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));


    public static void init(IEventBus iEventBus) {
        BLOCKS.register(iEventBus);
    }
}
