package robaertschi.environmenttech.level.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static robaertschi.environmenttech.EnvironmentTech.MODID;
import static robaertschi.environmenttech.level.block.ETBlocks.EXAMPLE_BLOCK;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ETItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);


    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    public static final DeferredItem<EnvDetectorItem> ENV_DETECTOR_ITEM = ITEMS.registerItem("env_detector",
            EnvDetectorItem::new,
            new Item.Properties()
                    .stacksTo(1)
                    .durability(10)
    );

    public static final DeferredItem<Item> ENVIRONMENTAL_ESSENCE = ITEMS.registerSimpleItem("environmental_essence");


    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_MODE_TAB = CREATIVE_MODE_TABS.register("environmenttech", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.environmenttech"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ENV_DETECTOR_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ENV_DETECTOR_ITEM.get());
                output.accept(EXAMPLE_BLOCK_ITEM.get());
                output.accept(ENVIRONMENTAL_ESSENCE.get());
            }).build());


    public static void init(IEventBus iModBus) {

        ITEMS.register(iModBus);
        CREATIVE_MODE_TABS.register(iModBus);
    }


    @SuppressWarnings("unused")
    @SubscribeEvent()
    // Add the example block item to the building blocks tab
    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }
}
