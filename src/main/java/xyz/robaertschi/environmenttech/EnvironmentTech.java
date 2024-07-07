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
package xyz.robaertschi.environmenttech;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import xyz.robaertschi.environmenttech.command.EnvironmenttechCommand;
import xyz.robaertschi.environmenttech.compat.ETCompat;
import xyz.robaertschi.environmenttech.data.attachments.ETAttachments;
import xyz.robaertschi.environmenttech.data.capabilities.ETCapabilities;
import xyz.robaertschi.environmenttech.data.components.ETComponents;
import xyz.robaertschi.environmenttech.data.recipes.ETRecipes;
import xyz.robaertschi.environmenttech.level.block.ETBlocks;
import xyz.robaertschi.environmenttech.level.block.entity.ETBlockEntities;
import xyz.robaertschi.environmenttech.level.fluid.ETFluidTypes;
import xyz.robaertschi.environmenttech.level.fluid.ETFluids;
import xyz.robaertschi.environmenttech.level.item.ETItems;
import xyz.robaertschi.environmenttech.level.particle.ETParticles;
import xyz.robaertschi.environmenttech.menu.ETMenus;

import static xyz.robaertschi.environmenttech.ET.MODID;


@Mod(MODID)
public class EnvironmentTech
{

    public static final Logger LOGGER = LogUtils.getLogger();

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
        ETFluids.init(modEventBus);
        ETFluidTypes.init(modEventBus);
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
