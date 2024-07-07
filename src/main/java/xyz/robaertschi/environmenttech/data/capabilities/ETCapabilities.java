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
package xyz.robaertschi.environmenttech.data.capabilities;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import net.minecraft.core.Direction;

import xyz.robaertschi.environmenttech.ET;
import xyz.robaertschi.environmenttech.level.block.entity.ETBlockEntities;

public class ETCapabilities {
    public static final BlockCapability<IEnvStorage, EnvType> ENV_STORAGE_BLOCK =
            BlockCapability.create(ET.id("env_storage"),
                    IEnvStorage.class,
                    EnvType.class
                    );

    public static void init(IEventBus iEventBus) {
        iEventBus.addListener(ETCapabilities::registerCapabilities);
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ETBlockEntities.ENV_COLLECTOR_BLOCK_ENTITY.get(),
                (object, context) -> {
                    if (context == null) {
                        return object.getInventory().get();
                    }
                    if (context == Direction.DOWN) {
                        return object.getOutputItemHandler().get();
                    }
                    return object.getInputItemHandler().get();
                }
        );

        event.registerBlockEntity(
                ENV_STORAGE_BLOCK,
                ETBlockEntities.ENV_COLLECTOR_BLOCK_ENTITY.get(),
                (object, context) -> {
                    if (context == EnvType.Chunk) return object.getEnvStorage();
                    return null;
                }
        );

        event.registerBlockEntity(
                ENV_STORAGE_BLOCK,
                ETBlockEntities.ENV_DISTRIBUTOR_BLOCK_ENTITY.get(),
                (object, context) -> {
                    if (context == EnvType.Chunk) return object.getEnvStorage();
                    return null;
                }
        );
    }

}
