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
package xyz.robaertschi.environmenttech.level.block.entity;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import xyz.robaertschi.environmenttech.ET;
import xyz.robaertschi.environmenttech.level.block.ETBlocks;


@SuppressWarnings("DataFlowIssue")
public class ETBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ET.MODID);

    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnvCollectorBlockEntity>> ENV_COLLECTOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("env_collector",
                    () -> BlockEntityType.Builder.of(EnvCollectorBlockEntity::new,
                            ETBlocks.ENV_COLLECTOR_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnvDistributorBlockEntity>> ENV_DISTRIBUTOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("env_distributor",
                    () -> BlockEntityType.Builder.of(EnvDistributorBlockEntity::new, ETBlocks.ENV_DISTRIBUTOR_BLOCK.get()).build(null));

    public static void init(IEventBus iEventBus) {
        BLOCK_ENTITIES.register(iEventBus);
    }
}
