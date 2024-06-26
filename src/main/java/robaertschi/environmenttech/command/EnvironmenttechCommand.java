/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.command;

import net.neoforged.neoforge.server.command.EnumArgument;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;

import robaertschi.environmenttech.data.attachments.ETAttachments;
import robaertschi.environmenttech.data.capabilities.ETCapabilities;
import robaertschi.environmenttech.data.capabilities.EnvType;

import static net.minecraft.commands.Commands.*;

public class EnvironmenttechCommand {
    public EnvironmenttechCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                literal("environmenttech")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(1) && commandSourceStack.isPlayer())
                        .then(literal("set_chunk_env").then(argument("amount", LongArgumentType.longArg(0)).executes(
                                context -> {
                                    long amount = context.getArgument("amount", Long.class);
                                    Player player = context.getSource().getPlayerOrException();
                                    Level level = player.level();
                                    ChunkAccess chunk = level.getChunkAt(player.blockPosition());

                                    chunk.setData(ETAttachments.ENV, amount);
                                    context.getSource().sendSuccess(() -> Component.literal("Updated chunk at position " + chunk.getPos() + " to have " + amount + "ENV"), true);

                                    return 1;
                                }
                        )))
                        .then(literal("get_chunk_env").executes(context -> {
                            Player player = context.getSource().getPlayerOrException();
                            Level level = player.level();
                            ChunkAccess chunk = level.getChunkAt(player.blockPosition());

                            long env = chunk.getData(ETAttachments.ENV);

                            context.getSource().sendSuccess(() -> Component.literal("The current Chunk contains " + env + "ENV"), true);

                            return 1;
                        }))
                        .then(literal("set_env").then(argument("pos", BlockPosArgument.blockPos()).then(argument("env_type", EnumArgument.enumArgument(EnvType.class)).then(argument("amount", LongArgumentType.longArg(0)).executes(
                                context -> {
                                    WorldCoordinates pos = context.getArgument("pos", WorldCoordinates.class);
                                    EnvType type = context.getArgument("env_type", EnvType.class);
                                    long amount = context.getArgument("amount", Long.class);
                                    var cap = context.getSource().getPlayerOrException().level().getCapability(ETCapabilities.ENV_STORAGE_BLOCK, pos.getBlockPos(context.getSource()), type);
                                    if (cap != null) {
                                        cap.receiveEnv(amount, false);
                                    }

                                    return 1;
                                }
                        )))))
        );
    }
}
