package team.unnamed.glow.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import team.unnamed.glow.packet.PacketInjectorHandler;

public class PlayerJoinListener implements Listener {

    private final PacketInjectorHandler injectorHandler;

    public PlayerJoinListener(PacketInjectorHandler injectorHandler) {
        this.injectorHandler = injectorHandler;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        injectorHandler.handlePlayer(event.getPlayer());
    }

}
