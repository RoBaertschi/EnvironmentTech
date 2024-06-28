/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.level.block;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.world.level.block.state.BlockBehaviour;

import static robaertschi.environmenttech.ET.MODID;

public class ETBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "environmenttech" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Creates a new Block with the id "environmenttech:example_block", combining the namespace and path
//    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredBlock<EnvCollectorBlock> ENV_COLLECTOR_BLOCK = BLOCKS.registerBlock("env_collector", EnvCollectorBlock::new, BlockBehaviour.Properties.of());
    public static final DeferredBlock<EnvDistributorBlock> ENV_DISTRIBUTOR_BLOCK =BLOCKS.registerBlock("env_distributor", EnvDistributorBlock::new, BlockBehaviour.Properties.of());


    public static void init(IEventBus iEventBus) {
        BLOCKS.register(iEventBus);
    }
}
