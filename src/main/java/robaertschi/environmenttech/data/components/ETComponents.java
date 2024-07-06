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
package robaertschi.environmenttech.data.components;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.component.DataComponentType;

import static robaertschi.environmenttech.ET.MODID;

public class ETComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FilledComponent>> FILLED_COMPONENT = DATA_COMPONENTS.registerComponentType("filled_component",
            filledComponentBuilder -> filledComponentBuilder.persistent(FilledComponent.CODEC).networkSynchronized(FilledComponent.STREAM_CODEC)
            );

    public static void init(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}
