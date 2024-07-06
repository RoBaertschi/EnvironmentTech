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
