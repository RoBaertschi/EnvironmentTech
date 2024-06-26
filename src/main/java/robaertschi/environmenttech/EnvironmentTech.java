package robaertschi.environmenttech;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;
import robaertschi.environmenttech.command.EnvironmenttechCommand;
import robaertschi.environmenttech.compat.ETCompat;
import robaertschi.environmenttech.data.attachments.ETAttachments;
import robaertschi.environmenttech.data.capabilities.ETCapabilities;
import robaertschi.environmenttech.data.components.ETComponents;
import robaertschi.environmenttech.data.recipes.ETRecipes;
import robaertschi.environmenttech.level.block.ETBlocks;
import robaertschi.environmenttech.level.block.entity.ETBlockEntities;
import robaertschi.environmenttech.level.item.ETItems;
import robaertschi.environmenttech.level.particle.ETParticles;
import robaertschi.environmenttech.menu.ETMenus;

@Mod(EnvironmentTech.MODID)
public class EnvironmentTech
{

    public static final String MODID = "environmenttech";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public EnvironmentTech(IEventBus modEventBus, ModContainer modContainer)
    {

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ETBlocks.init(modEventBus);
        ETBlockEntities.init(modEventBus);
        ETItems.init(modEventBus);
        ETRecipes.init(modEventBus);
        ETAttachments.init(modEventBus);
        ETCapabilities.init(modEventBus);
        ETMenus.init(modEventBus);
        ETParticles.init(modEventBus);
        ETComponents.init(modEventBus);
        ETCompat.init(modEventBus);

        NeoForge.EVENT_BUS.register(this);

    }

    @SubscribeEvent
    private void registerCommands(RegisterCommandsEvent event) {
        new EnvironmenttechCommand(event.getDispatcher());
    }


//    @SubscribeEvent()
//    public void onPlayerTick(PlayerTickEvent.Post event) {
//        var level = event.getEntity().level();
//        if (level.isClientSide()) {
//            return;
//        }
//        var env = level.getChunk(event.getEntity().blockPosition()).getData(ETAttachments.ENV);
////        event.getEntity().sendSystemMessage(Component.literal("ENV in chunk: " + env));
//
//    }
}
