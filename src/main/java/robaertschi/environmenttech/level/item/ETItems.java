/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
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

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class ETItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);


    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


//    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    public static final DeferredItem<BlockItem> ENV_COLLECTOR_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("env_collector", ETBlocks.ENV_COLLECTOR_BLOCK);

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
