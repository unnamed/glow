package team.unnamed.glow.adapt.v1_19_R1;

import io.netty.channel.ChannelHandler;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import team.unnamed.glow.GlowManager;
import team.unnamed.glow.packet.PacketInjectorHandler;

public class PacketInjectorHandlerImpl implements PacketInjectorHandler {

    private final GlowManager glowManager;

    public PacketInjectorHandlerImpl(GlowManager glowManager) {
        this.glowManager = glowManager;
    }

    @Override
    public void handlePlayer(Player player) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        int playerEntityId = serverPlayer.getId();
        ChannelHandler channelHandler = new EntityDataChannelHandler(playerEntityId, glowManager);
        serverPlayer.networkManager.channel
                .pipeline()
                .addBefore("packet_handler", "unnamed-glow", channelHandler);
    }
}
