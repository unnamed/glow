package team.unnamed.glow.packet;

import org.bukkit.entity.Player;

public interface PacketInjectorHandler {

    /**
     * Called when a player joins the server.
     * @param player The player.
     */
    void handlePlayer(Player player);

}
