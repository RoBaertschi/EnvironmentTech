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
package robaertschi.environmenttech.level.item;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomModelData;

import robaertschi.environmenttech.level.block.ETBlocks;

import static robaertschi.environmenttech.ET.MODID;

public class ETItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);


    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


//    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    public static final DeferredItem<BlockItem> ENV_COLLECTOR_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("env_collector", ETBlocks.ENV_COLLECTOR_BLOCK);
    public static final DeferredItem<BlockItem> ENV_DISTRIBUTOR_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("env_distributor", ETBlocks.ENV_DISTRIBUTOR_BLOCK);

    public static final DeferredItem<EnvDetectorItem> ENV_DETECTOR_ITEM = ITEMS.registerItem("env_detector",
            EnvDetectorItem::new,
            new Item.Properties()
                    .component(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(0))
                    .stacksTo(1)
                    .durability(10)
    );

    public static final DeferredItem<Item> ENVIRONMENTAL_ESSENCE_ITEM = ITEMS.registerSimpleItem("environmental_essence");
    public static final DeferredItem<Item> GLASS_TANK = ITEMS.registerSimpleItem("glass_tank");


    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_MODE_TAB = CREATIVE_MODE_TABS.register("environmenttech", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.environmenttech"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ENV_COLLECTOR_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ENV_COLLECTOR_BLOCK_ITEM.get());
                output.accept(ENV_DISTRIBUTOR_BLOCK_ITEM);
                output.accept(ENV_DETECTOR_ITEM.get());
//                output.accept(EXAMPLE_BLOCK_ITEM.get());
                output.accept(ENVIRONMENTAL_ESSENCE_ITEM.get());
                output.accept(GLASS_TANK);
            }).build());


    public static void init(IEventBus iModBus) {

        ITEMS.register(iModBus);
        CREATIVE_MODE_TABS.register(iModBus);
    }


}
