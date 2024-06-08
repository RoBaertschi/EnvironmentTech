package robaertschi.environmenttech;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.gametest.GameTestHooks;
import org.slf4j.Logger;
import robaertschi.environmenttech.client.ETClient;
import robaertschi.environmenttech.command.EnvironmenttechCommand;
import robaertschi.environmenttech.data.attachments.ETAttachments;
import robaertschi.environmenttech.data.capabilities.ETCapabilities;
import robaertschi.environmenttech.data.recipes.ETRecipes;
import robaertschi.environmenttech.level.block.ETBlocks;
import robaertschi.environmenttech.level.block.entity.ETBlockEntities;
import robaertschi.environmenttech.level.item.ETItems;
import robaertschi.environmenttech.level.particle.ETParticles;
import robaertschi.environmenttech.menu.ETMenus;

@Mod(EnvironmentTech.MODID)
public class EnvironmentTech
{
    public static final boolean DEBUG;

    public static final String MODID = "environmenttech";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MODID, name);
    }

    static {
        // Why a static block? Because I can.

        // Enable debugging Tools and Screen
        // TODO: Add a config entry for modpack devs and so on.
        DEBUG = !FMLLoader.isProduction();
    }

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public EnvironmentTech(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.addListener(ETClient::onClientTickEnd);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ETBlocks.init(modEventBus);
        ETBlockEntities.init(modEventBus);
        ETItems.init(modEventBus);
        ETRecipes.init(modEventBus);
        ETAttachments.init(modEventBus);
        ETCapabilities.init(modEventBus);
        ETMenus.init(modEventBus);
        ETParticles.init(modEventBus);

        NeoForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    private void registerCommands(RegisterCommandsEvent event) {
        new EnvironmenttechCommand(event.getDispatcher());
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    @SubscribeEvent()
    public void onPlayerTick(PlayerTickEvent.Post event) {
        var level = event.getEntity().level();
        if (level.isClientSide()) {
            return;
        }
        var env = level.getChunk(event.getEntity().blockPosition()).getData(ETAttachments.ENV);
//        event.getEntity().sendSystemMessage(Component.literal("ENV in chunk: " + env));

    }
}
