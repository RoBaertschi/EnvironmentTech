package robaertschi.environmenttech.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import robaertschi.environmenttech.data.attachments.ETAttachments;

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
        );
    }
}
